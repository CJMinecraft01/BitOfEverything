package cjminecraft.bitofeverything.tileentity;

import com.mojang.authlib.GameProfile;

import cjminecraft.bitofeverything.BitOfEverything;
import cjminecraft.bitofeverything.blocks.BlockBreaker;
import cjminecraft.bitofeverything.handlers.EnumHandler.ChipTypes;
import cjminecraft.bitofeverything.util.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityBlockBreaker extends TileEntity implements ITickable, ICapabilityProvider {

	private ItemStackHandler handler;
	private int cooldown;

	public TileEntityBlockBreaker() {
		this.cooldown = 0;
		this.handler = new ItemStackHandler(9);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		this.cooldown = nbt.getInteger("Cooldown");
		this.handler.deserializeNBT(nbt.getCompoundTag("ItemStackHandler"));
		super.readFromNBT(nbt);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("Cooldown", this.cooldown);
		nbt.setTag("ItemStackHandler", this.handler.serializeNBT());
		return super.writeToNBT(nbt);
	}

	@Override
	public void update() {
		if (this.worldObj != null) {
			if (!this.worldObj.isRemote && this.worldObj.isBlockPowered(pos)) {
				IBlockState currentState = this.worldObj.getBlockState(pos);
				this.worldObj.setBlockState(pos,
						currentState.withProperty(BlockBreaker.ACTIVATED, Boolean.valueOf(true)));
				this.cooldown++;
				if (this.worldObj.getBlockState(pos).getValue(BlockBreaker.TYPE) == ChipTypes.BASIC)
					this.cooldown %= 100;
				else
					this.cooldown %= 50;
				if (this.cooldown == 0) {
					IBlockState state = this.worldObj.getBlockState(pos);
					EnumFacing facing = (EnumFacing) state.getValue(BlockBreaker.FACING);
					breakBlock(facing);
				}
			} else if (!this.worldObj.isBlockPowered(pos)) {
				if (!this.worldObj.isAirBlock(pos)) {
					if (this.worldObj.getBlockState(pos).getValue(BlockBreaker.ACTIVATED)) {
						IBlockState currentState = this.worldObj.getBlockState(pos);
						this.worldObj.setBlockState(pos,
								currentState.withProperty(BlockBreaker.ACTIVATED, Boolean.valueOf(false)));
					}
				}
			}
		}
	}

	public void breakBlock(EnumFacing facing) {
		BlockPos newPos = pos.offset(facing, 1);
		IBlockState state = this.worldObj.getBlockState(newPos);
		Block block = state.getBlock();
		if (!block.isAir(state, this.worldObj, newPos) && block.getBlockHardness(state, this.worldObj, newPos) >= 0) {
			Utils.getLogger().info("RAN");
			EntityPlayer player = new EntityPlayer(worldObj, new GameProfile(null, "BlockBreaker")) {

				@Override
				public boolean isSpectator() {
					return true;
				}

				@Override
				public boolean isCreative() {
					return false;
				}
			};
			// block.harvestBlock(worldObj, player, newPos, state,
			// worldObj.getTileEntity(newPos), new ItemStack(block)); SPAWNS BLOCK
			for (ItemStack stack : block.getDrops(worldObj, newPos, state, 0)) {
				ItemStack remainder = this.handler.insertItem(0, stack, false);
				if (remainder == ItemStack.field_190927_a)// If it is a null
															// item
					continue;
				remainder = this.handler.insertItem(1, remainder, false);
				if (remainder == ItemStack.field_190927_a)// If it is a null
															// item
					continue;
				remainder = this.handler.insertItem(2, remainder, false);
				if (remainder == ItemStack.field_190927_a)// If it is a null
															// item
					continue;
				remainder = this.handler.insertItem(3, remainder, false);
				if (remainder == ItemStack.field_190927_a)// If it is a null
															// item
					continue;
				remainder = this.handler.insertItem(4, remainder, false);
				if (remainder == ItemStack.field_190927_a)// If it is a null
															// item
					continue;
				remainder = this.handler.insertItem(5, remainder, false);
				if (remainder == ItemStack.field_190927_a)// If it is a null
															// item
					continue;
				remainder = this.handler.insertItem(6, remainder, false);
				if (remainder == ItemStack.field_190927_a)// If it is a null
															// item
					continue;
				remainder = this.handler.insertItem(7, remainder, false);
				if (remainder == ItemStack.field_190927_a)// If it is a null
															// item
					continue;
				remainder = this.handler.insertItem(8, remainder, false);
				if (remainder == ItemStack.field_190927_a)// If it is a null
															// item
					continue;
			}
			this.worldObj.playSound(null, pos, block.getSoundType().getBreakSound(), SoundCategory.BLOCKS, 1, 1);
			this.worldObj.setBlockToAir(newPos);
		}
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		int metadata = getBlockMetadata();
		return new SPacketUpdateTileEntity(this.pos, metadata, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		this.readFromNBT(pkt.getNbtCompound());
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		return nbt;
	}

	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		this.readFromNBT(tag);
	}

	@Override
	public NBTTagCompound getTileData() {
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		return nbt;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return (T) this.handler;
		return super.getCapability(capability, facing);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return true;
		return super.hasCapability(capability, facing);
	}

	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.worldObj.getTileEntity(this.getPos()) == this
				&& player.getDistanceSq(this.pos.add(0.5, 0.5, 0.5)) <= 64;
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
		return false;
	}

}
