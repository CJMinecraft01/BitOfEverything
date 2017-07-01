package cjminecraft.bitofeverything.tileentity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.mojang.authlib.GameProfile;

import cjminecraft.bitofeverything.blocks.BlockBreaker;
import cjminecraft.bitofeverything.capabilties.Worker;
import cjminecraft.bitofeverything.client.gui.GuiBlockBreaker;
import cjminecraft.bitofeverything.config.BoeConfig;
import cjminecraft.bitofeverything.handlers.EnumHandler.ChipTypes;
import cjminecraft.bitofeverything.init.ModBlocks;
import cjminecraft.bitofeverything.init.ModCapabilities;
import cjminecraft.bitofeverything.util.Utils;
import cjminecraft.core.energy.CustomForgeEnergyStorage;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDynamicLiquid;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

/**
 * Our block breaker tile entity which handles updating the block state and
 * breaking blocks!
 * 
 * @author CJMinecraft
 *
 */
public class TileEntityBlockBreaker extends TileEntity implements ITickable, ICapabilityProvider {

	/**
	 * New 1.9.4 onwards. Using forge capabilities instead of {@link IInventory}
	 */
	private ItemStackHandler handler;
	private Random random;
	private Worker worker;
	private CustomForgeEnergyStorage storage;

	/**
	 * Initializes our variables. MUST NOT HAVE ANY PARAMETERS
	 */
	public TileEntityBlockBreaker() {
		this.worker = new Worker(BoeConfig.machineCooldownBasic, () -> {
			if (this.world.isBlockPowered(pos) && this.storage.getEnergyStored() > 5) { // Calls it server side and checks if our block is powered
				IBlockState currentState = this.world.getBlockState(pos); // Gets our block state
				this.world.setBlockState(pos, currentState.withProperty(BlockBreaker.ACTIVATED, Boolean.valueOf(true))); // Updates it if it is powered
				this.storage.extractEnergyInternal(5, false);
				updateCooldownCap();
			}
		}, () -> {
			IBlockState currentState = this.world.getBlockState(pos); // Updates our current state variable
			EnumFacing facing = (EnumFacing) currentState.getValue(BlockBreaker.FACING); // Gets which way our block is facing
			breakBlock(facing); // Calls our break block method which handles the actual breaking of the block
		});
		this.handler = new ItemStackHandler(10);
		this.random = new Random();
		this.storage = new CustomForgeEnergyStorage(100000, 1000, 0);
	}
	
	public TileEntityBlockBreaker(ChipTypes type) {
		this.worker = new Worker(type == ChipTypes.BASIC ? BoeConfig.machineCooldownBasic : BoeConfig.machineCooldownAdvanced, () -> {
			if (this.world.isBlockPowered(pos) && this.storage.getEnergyStored() > 5) { // Calls it server side and checks if our block is powered
				IBlockState currentState = this.world.getBlockState(pos); // Gets our block state
				this.world.setBlockState(pos, currentState.withProperty(BlockBreaker.ACTIVATED, Boolean.valueOf(true))); // Updates it if it is powered
				this.storage.extractEnergyInternal(5, false);
				updateCooldownCap();
			}
		}, () -> {
			IBlockState currentState = this.world.getBlockState(pos); // Updates our current state variable
			EnumFacing facing = (EnumFacing) currentState.getValue(BlockBreaker.FACING); // Gets which way our block is facing
			breakBlock(facing); // Calls our break block method which handles the actual breaking of the block
		});
		this.handler = new ItemStackHandler(10);
		this.random = new Random();
		this.storage = new CustomForgeEnergyStorage(type == ChipTypes.BASIC ? 100000 : 500000, 1000, 0);
	}

	/**
	 * Reads data from nbt where data is stored
	 */
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		this.worker.deserializeNBT(nbt.getCompoundTag("Worker"));
		this.handler.deserializeNBT(nbt.getCompoundTag("ItemStackHandler")); // Gets the ItemStackHandler from tag within a tag
		this.storage.readFromNBT(nbt);
		super.readFromNBT(nbt);
	}

	/**
	 * Writes data to nbt so it is stored
	 */
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setTag("Worker", this.worker.serializeNBT());
		nbt.setTag("ItemStackHandler", this.handler.serializeNBT()); // We write our ItemStackHandler as a tag in a tag
		this.storage.writeToNBT(nbt);
		return super.writeToNBT(nbt);
	}

	/**
	 * Updates the tile entity (breaks the block etc) Called every tick
	 */
	@Override
	public void update() {
		if (this.world != null && !this.world.isRemote) { // Makes sure we have a world. RENAMED IN
									// 1.11.2 from worldObj to world
			if (!this.world.isBlockPowered(pos)) { // If the block is not powered
				if (!this.world.isAirBlock(pos) && this.world.getBlockState(pos).getBlock() == ModBlocks.breaker) { // The block is not air and it is a block breaker
					if (this.world.getBlockState(pos).getValue(BlockBreaker.ACTIVATED)) { // Checks if it is activated
						IBlockState currentState = this.world.getBlockState(pos);
						this.world.setBlockState(pos,
								currentState.withProperty(BlockBreaker.ACTIVATED, Boolean.valueOf(false))); // Makes it not activated
					}
				}
			}
			else if(this.storage.getEnergyStored() > 5) {
				this.worker.doWork();
			}
		}
	}

	public void updateCooldownCap() {
		int cap = this.worker.getMaxWork();
		if (this.world.getBlockState(pos).getValue(BlockBreaker.TYPE) == ChipTypes.BASIC)
			cap = BoeConfig.machineCooldownBasic;
		else
			cap = BoeConfig.machineCooldownAdvanced;
		if (this.handler.getStackInSlot(9).getItem() == Items.ENCHANTED_BOOK) {
			Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(this.handler.getStackInSlot(9));
			if (enchantments.containsKey(Enchantments.EFFICIENCY)) {
				cap -= Math.pow(enchantments.get(Enchantments.EFFICIENCY), 2) % cap;
			}
		}
		this.worker.setMaxCooldown(cap);
	}

	/**
	 * Breaks blocks
	 * 
	 * @param facing
	 *            The direction in which the block breaker is facing
	 */
	public void breakBlock(EnumFacing facing) {
		if(this.storage.getEnergyStored() == 0)
			return;
		BlockPos newPos = pos.offset(facing, 1); // Gets the block pos in front of the block breaker
		IBlockState state = this.world.getBlockState(newPos); // Gets the block state
		Block block = state.getBlock(); // Gets the block
		// If the block is not air, is not unbreakable or a liquid it will try and break it
		if (!block.isAir(state, this.world, newPos) && block.getBlockHardness(state, this.world, newPos) >= 0
				&& !(block instanceof BlockDynamicLiquid) && !(block instanceof BlockStaticLiquid)) {
			// Creates a fake player which will berak the block
			EntityPlayer player = new EntityPlayer(world, new GameProfile(null, "BlockBreaker")) {

				@Override
				public boolean isSpectator() {
					return true;
				}

				@Override
				public boolean isCreative() {
					return false;
				}
			};
			List<ItemStack> drops = new ArrayList<ItemStack>();
			boolean customDrops = false;
			if (this.handler.getStackInSlot(9).getItem() == Items.ENCHANTED_BOOK) {
				ItemStack enchantedBook = this.handler.getStackInSlot(9);
				Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(enchantedBook);
				if (enchantments.containsKey(Enchantments.FORTUNE)) {
					int fortune = enchantments.get(Enchantments.FORTUNE);
					drops.add(new ItemStack(block.getItemDropped(state, this.random, fortune),
							block.quantityDroppedWithBonus(fortune, this.random), block.damageDropped(state)));
					customDrops = true;
					this.storage.extractEnergyInternal(10 * enchantments.get(Enchantments.FORTUNE), false);
				}
				if (enchantments.containsKey(Enchantments.SILK_TOUCH)
						&& block.canSilkHarvest(world, newPos, state, player)) {
					// HARD FIX FOR LAPIS
					if (block == Blocks.LAPIS_ORE)
						drops.add(new ItemStack(block, 1));
					else
						drops.add(new ItemStack(block, 1, block.damageDropped(state)));
					customDrops = true;
					this.storage.extractEnergyInternal(10 * enchantments.get(Enchantments.SILK_TOUCH), false);
				}
				if(enchantments.containsKey(Enchantments.EFFICIENCY))
					this.storage.extractEnergyInternal(10 * enchantments.get(Enchantments.EFFICIENCY), false);
			}
			if (!customDrops)
				drops = block.getDrops(world, newPos, state, 0);
			// Use block.harvestBlock if you don't want the item to go into the inventory
			for (ItemStack stack : drops) { // This then puts the item into the inventory correctly
				Utils.addStackToInventory(this.handler, 9, stack, false);
			}
			if (!Utils.isInventoryFull(this.handler, 9)) {
				this.world.playEvent(2001, pos, Block.getStateId(state));
				this.world.playSound(null, pos, block.getSoundType(state, world, newPos, player).getBreakSound(),
						SoundCategory.BLOCKS, 1, 1); // Plays the block breaking
														// sound
				this.world.setBlockToAir(newPos); // Makes the block air
				this.storage.extractEnergyInternal(20, false);
				if (block == Blocks.ICE) // If the block was ice it will place
											// flowing water there instead
					this.world.setBlockState(newPos, Blocks.FLOWING_WATER.getDefaultState());
			}
		}
	}

	/**
	 * The packet which is used to update the tile entity which holds all of the
	 * tileentities data
	 */
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		int metadata = getBlockMetadata();
		return new SPacketUpdateTileEntity(this.pos, metadata, nbt);
	}

	/**
	 * Reads the nbt when it receives a packet
	 */
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		this.readFromNBT(pkt.getNbtCompound());
	}

	/**
	 * Gets the nbt for a new packet
	 */
	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		return nbt;
	}

	/**
	 * Handles when you get an update
	 */
	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		this.readFromNBT(tag);
	}

	/**
	 * Gets the tile entities nbt with all of the data stored in it
	 */
	@Override
	public NBTTagCompound getTileData() {
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		return nbt;
	}

	/**
	 * New 1.9.4 onwards. Capability system
	 */
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return (T) this.handler; // Makes it so that you can get the capability from our tile entity
		if(capability == ModCapabilities.CAPABILITY_WORKER)
			return (T) this.worker;
		if(capability == CapabilityEnergy.ENERGY)
			return (T) this.storage;
		return super.getCapability(capability, facing);
	}

	/**
	 * Says what our block is capable of
	 */
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || capability == ModCapabilities.CAPABILITY_WORKER || capability == CapabilityEnergy.ENERGY)
			return true;
		return super.hasCapability(capability, facing);
	}

	/**
	 * Says whether the player can interact with the block - used for our
	 * {@link GuiBlockBreaker}
	 * 
	 * @param player
	 *            The player to test
	 * @return If the player can interact with the block
	 */
	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.world.getTileEntity(this.getPos()) == this
				&& player.getDistanceSq(this.pos.add(0.5, 0.5, 0.5)) <= 64;
	}

	/**
	 * Says that if the block state updates, the tile entity shouldn't get
	 * destroyed but should not refresh
	 */
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
		return false;
	}

}
