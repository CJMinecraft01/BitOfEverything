package cjminecraft.bitofeverything.tileentity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.mojang.authlib.GameProfile;

import cjminecraft.bitofeverything.blocks.BlockBreaker;
import cjminecraft.bitofeverything.client.gui.GuiBlockBreaker;
import cjminecraft.bitofeverything.handlers.EnumHandler.ChipTypes;
import cjminecraft.bitofeverything.init.ModBlocks;
import cjminecraft.bitofeverything.util.Utils;
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
import net.minecraft.item.ItemBlock;
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
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

/**
 * Our block breaker tile entity which handles updating the block state and breaking blocks!
 * @author CJMinecraft
 *
 */
public class TileEntityBlockBreaker extends TileEntity implements ITickable, ICapabilityProvider {

	/**
	 * New 1.9.4 onwards. Using forge capabilities instead of {@link IInventory}
	 */
	private ItemStackHandler handler;
	private int cooldown;
	private int cooldownCap = 100;
	private Random random;

	/**
	 * Initializes our variables. MUST NOT HAVE ANY PARAMETERS
	 */
	public TileEntityBlockBreaker() {
		this.cooldown = 0;
		this.handler = new ItemStackHandler(10);
		this.random = new Random();
	}

	/**
	 * Reads data from nbt where data is stored
	 */
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		this.cooldown = nbt.getInteger("Cooldown");
		this.handler.deserializeNBT(nbt.getCompoundTag("ItemStackHandler")); //Gets the ItemStackHandler from tag within a tag
		super.readFromNBT(nbt);
	}

	/**
	 * Writes data to nbt so it is stored
	 */
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("Cooldown", this.cooldown);
		nbt.setTag("ItemStackHandler", this.handler.serializeNBT()); //We write our ItemStackHandler as a tag in a tag
		return super.writeToNBT(nbt);
	}

	/**
	 * Updates the tile entity (breaks the block etc)
	 * Called every tick
	 */
	@Override
	public void update() {
		if (this.world != null) { //Makes sure we have a world. RENAMED IN 1.11.2 from worldObj to world
			if (!this.world.isRemote && this.world.isBlockPowered(pos)) { //Calls it server side and checks if our block is powered
				IBlockState currentState = this.world.getBlockState(pos); //Gets our block state
				this.world.setBlockState(pos,
						currentState.withProperty(BlockBreaker.ACTIVATED, Boolean.valueOf(true))); //Updates it if it is powered
				updateCooldownCap();
				this.cooldown++; //Increases the cooldown
				this.cooldown %= this.cooldownCap;
				if (this.cooldown == 0) { //Only runs when the cooldown is 0 (i.e every 50 or 100 ticks (2.5 or 5 seconds))
					currentState = this.world.getBlockState(pos); //Updates our current state variable
					EnumFacing facing = (EnumFacing) currentState.getValue(BlockBreaker.FACING); //Gets which way our block is facing
					breakBlock(facing); //Calls our break block method which handles the actual breaking of the block
				}
			} else if (!this.world.isBlockPowered(pos)) { //If the block is not powered
				if (!this.world.isAirBlock(pos) && this.world.getBlockState(pos).getBlock() == ModBlocks.breaker) { //The block is not air and it is a block breaker
					if (this.world.getBlockState(pos).getValue(BlockBreaker.ACTIVATED)) { //Checks if it is activated
						IBlockState currentState = this.world.getBlockState(pos);
						this.world.setBlockState(pos,
								currentState.withProperty(BlockBreaker.ACTIVATED, Boolean.valueOf(false))); //Makes it not activated
					}
				}
			}
		}
	}
	
	public void updateCooldownCap() {
		int cap = this.cooldownCap;
		if (this.world.getBlockState(pos).getValue(BlockBreaker.TYPE) == ChipTypes.BASIC)
			cap = 100;
		else
			cap = 50;
		if(this.handler.getStackInSlot(9).getItem() == Items.ENCHANTED_BOOK) {
			Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(this.handler.getStackInSlot(9));
			if(enchantments.containsKey(Enchantments.EFFICIENCY)) {
				cap -= Math.pow(enchantments.get(Enchantments.EFFICIENCY), 2) % cap;
			}
		}
		this.cooldownCap = cap;
	}

	/**
	 * Breaks blocks
	 * @param facing The direction in which the block breaker is facing
	 */
	public void breakBlock(EnumFacing facing) {
		BlockPos newPos = pos.offset(facing, 1); //Gets the block pos in front of the block breaker
		IBlockState state = this.world.getBlockState(newPos); //Gets the block state
		Block block = state.getBlock(); //Gets the block
		//If the block is not air, is not unbreakable or a liquid it will try and break it
		if (!block.isAir(state, this.world, newPos) && block.getBlockHardness(state, this.world, newPos) >= 0 && !(block instanceof BlockDynamicLiquid) && !(block instanceof BlockStaticLiquid)) {
			//Creates a fake player which will berak the block
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
			if(this.handler.getStackInSlot(9).getItem() == Items.ENCHANTED_BOOK) {
				ItemStack enchantedBook = this.handler.getStackInSlot(9);
				Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(enchantedBook);
				if(enchantments.containsKey(Enchantments.FORTUNE)) {
					int fortune = enchantments.get(Enchantments.FORTUNE);
					drops.add(new ItemStack(block.getItemDropped(state, this.random, fortune), block.quantityDroppedWithBonus(fortune, this.random), block.damageDropped(state)));
					customDrops = true;
				}
				if(enchantments.containsKey(Enchantments.SILK_TOUCH) && block.canSilkHarvest(world, newPos, state, player)) {
					//HARD FIX FOR LAPIS
					if(block == Blocks.LAPIS_ORE)
						drops.add(new ItemStack(block, 1));
					else
						drops.add(new ItemStack(block, 1, block.damageDropped(state)));
					customDrops = true;
				}
			}
			if(!customDrops)
				drops = block.getDrops(world, newPos, state, 0);
			int full = 0; //So that if our tile entity is not full, it will add the block to the inventory
			//Use block.harvestBlock if you don't want the item to go into the inventory
			for (ItemStack stack : drops) { //This then puts the item into the inventory correctly
				ItemStack remainder = this.handler.insertItem(0, stack, false);
				if (remainder == ItemStack.EMPTY)// If it is a null
															// item
					continue;
				else
					full++;
				remainder = this.handler.insertItem(1, remainder, false);
				if (remainder == ItemStack.EMPTY)// If it is a null
															// item
					continue;
				else
					full++;
				remainder = this.handler.insertItem(2, remainder, false);
				if (remainder == ItemStack.EMPTY)// If it is a null
															// item
					continue;
				else
					full++;
				remainder = this.handler.insertItem(3, remainder, false);
				if (remainder == ItemStack.EMPTY)// If it is a null
															// item
					continue;
				else
					full++;
				remainder = this.handler.insertItem(4, remainder, false);
				if (remainder == ItemStack.EMPTY)// If it is a null
															// item
					continue;
				else
					full++;
				remainder = this.handler.insertItem(5, remainder, false);
				if (remainder == ItemStack.EMPTY)// If it is a null
															// item
					continue;
				else
					full++;
				remainder = this.handler.insertItem(6, remainder, false);
				if (remainder == ItemStack.EMPTY)// If it is a null
															// item
					continue;
				else
					full++;
				remainder = this.handler.insertItem(7, remainder, false);
				if (remainder == ItemStack.EMPTY)// If it is a null
															// item
					continue;
				else
					full++;
				remainder = this.handler.insertItem(8, remainder, false);
				if (remainder == ItemStack.EMPTY)// If it is a null
															// item
					continue;
				else
					full++;
			}
			if(full < handler.getSlots() - 1) {
				this.world.playEvent(2001, pos, Block.getStateId(state));
				this.world.playSound(null, pos, block.getSoundType(state, world, newPos, player).getBreakSound(), SoundCategory.BLOCKS, 1, 1); //Plays the block breaking sound
				this.world.setBlockToAir(newPos); //Makes the block air
				if(block == Blocks.ICE) //If the block was ice it will place flowing water there instead
					this.world.setBlockState(newPos, Blocks.FLOWING_WATER.getDefaultState());
			}
		}
	}

	/**
	 * The packet which is used to update the tile entity which holds all of the tileentities data
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
			return (T) this.handler; //Makes it so that you can get the capability from our tile entity
		return super.getCapability(capability, facing);
	}

	/**
	 * Says what our block is capable of
	 */
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return true;
		return super.hasCapability(capability, facing);
	}

	/**
	 * Says whether the player can interact with the block - used for our {@link GuiBlockBreaker}
	 * @param player The player to test
	 * @return If the player can interact with the block
	 */
	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.world.getTileEntity(this.getPos()) == this
				&& player.getDistanceSq(this.pos.add(0.5, 0.5, 0.5)) <= 64;
	}

	/**
	 * Says that if the block state updates, the tile entity shouldn't get destroyed but should not refresh
	 */
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
		return false;
	}

}
