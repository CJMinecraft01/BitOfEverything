package cjminecraft.bitofeverything.tileentity;

import cjminecraft.bitofeverything.blocks.BlockFurnaceGenerator;
import cjminecraft.bitofeverything.blocks.BlockMachine;
import cjminecraft.bitofeverything.capabilties.Worker;
import cjminecraft.bitofeverything.handlers.EnumHandler.ChipTypes;
import cjminecraft.bitofeverything.init.ModCapabilities;
import cjminecraft.bitofeverything.util.Utils;
import cjminecraft.core.energy.CustomForgeEnergyStorage;
import cjminecraft.core.energy.EnergyUnits;
import cjminecraft.core.energy.EnergyUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityFurnaceGenerator extends TileEntity implements ITickable {

	private ItemStackHandler handler;
	private Worker worker;
	private CustomForgeEnergyStorage storage;
	private ChipTypes type;

	public TileEntityFurnaceGenerator() {
		this.type = this.world.getBlockState(this.pos).getValue(BlockMachine.TYPE);
		this.handler = new ItemStackHandler(1);
		this.storage = new CustomForgeEnergyStorage(type == ChipTypes.BASIC ? 100000 : 500000, 0, type == ChipTypes.BASIC ? 1000 : 5000);
		this.worker = new Worker(1, () -> {
			//Do work
			if(this.worker.getMaxWork() != 1)
				if(this.storage.getMaxEnergyStored() - this.storage.getEnergyStored() > (type == ChipTypes.BASIC ? 40 : 60))
					this.storage.receiveEnergyInternal((type == ChipTypes.BASIC ? 40 : 60), false);
		}, () -> {
			//Work done
			this.worker.setMaxCooldown(1);
			if(this.handler.getStackInSlot(0).getItem() == Items.BUCKET)
				return;
			ItemStack fuel = this.handler.extractItem(0, 1, false);
			if(fuel != ItemStack.EMPTY)
				if(this.storage.getMaxEnergyStored() - this.storage.getEnergyStored() > (type == ChipTypes.BASIC ? 40 : 60))
					this.worker.setMaxCooldown(TileEntityFurnace.getItemBurnTime(fuel));
			if(fuel.getItem() == Items.LAVA_BUCKET)
				this.handler.setStackInSlot(0, new ItemStack(Items.BUCKET));
		});
	}

	public TileEntityFurnaceGenerator(ChipTypes type) {
		this.type = type;
		this.handler = new ItemStackHandler(1);
		this.storage = new CustomForgeEnergyStorage(type == ChipTypes.BASIC ? 100000 : 500000, 0, type == ChipTypes.BASIC ? 1000 : 5000);
		this.worker = new Worker(1, () -> {
			//Do work
			if(this.worker.getMaxWork() != 1)
				if(this.storage.getMaxEnergyStored() - this.storage.getEnergyStored() > (type == ChipTypes.BASIC ? 40 : 60))
					this.storage.receiveEnergyInternal((type == ChipTypes.BASIC ? 40 : 60), false);
		}, () -> {
			//Work done
			this.worker.setMaxCooldown(1);
			if(this.handler.getStackInSlot(0).getItem() == Items.BUCKET)
				return;
			ItemStack fuel = this.handler.extractItem(0, 1, false);
			if(fuel != ItemStack.EMPTY)
				if(this.storage.getMaxEnergyStored() - this.storage.getEnergyStored() > (type == ChipTypes.BASIC ? 40 : 60))
					this.worker.setMaxCooldown(TileEntityFurnace.getItemBurnTime(fuel));
			if(fuel.getItem() == Items.LAVA_BUCKET)
				this.handler.setStackInSlot(0, new ItemStack(Items.BUCKET));
		});
	}

	@Override
	public void update() {
		if (this.world != null) {
			if (!this.world.isRemote) {
				int extract = this.storage.getEnergyStored() > (this.type == ChipTypes.BASIC ? 1000 : 5000) ? (this.type == ChipTypes.BASIC ? 1000 : 5000) : this.storage.getEnergyStored();
				this.storage.extractEnergyInternal((int) EnergyUtils.giveEnergyAllFaces(this.world, this.pos, extract,
						EnergyUnits.FORGE_ENERGY, false), false);
				if(this.storage.getMaxEnergyStored() - this.storage.getEnergyStored() > (this.type == ChipTypes.BASIC ? 40 : 60)) {
					this.worker.doWork();
					this.markDirty();
					this.world.setBlockState(this.pos, this.world.getBlockState(this.pos).withProperty(BlockFurnaceGenerator.ACTIVATED, true), 2);
				} else {
					this.world.setBlockState(this.pos, this.world.getBlockState(this.pos).withProperty(BlockFurnaceGenerator.ACTIVATED, false), 2);
				}
				if(this.worker.getMaxWork() == 1)
					this.world.setBlockState(this.pos, this.world.getBlockState(this.pos).withProperty(BlockFurnaceGenerator.ACTIVATED, false), 2);
			}
		}
	}
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		return oldState.getBlock() != newState.getBlock();
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		this.handler.deserializeNBT(nbt.getCompoundTag("Inventory"));
		this.worker.deserializeNBT(nbt.getCompoundTag("Worker"));
		this.storage.readFromNBT(nbt);
		super.readFromNBT(nbt);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setTag("Inventory", this.handler.serializeNBT());
		nbt.setTag("Worker", this.worker.serializeNBT());
		this.storage.writeToNBT(nbt);
		return super.writeToNBT(nbt);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || capability == ModCapabilities.CAPABILITY_WORKER || capability == CapabilityEnergy.ENERGY)
			return true;
		return super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return (T) this.handler;
		if(capability == ModCapabilities.CAPABILITY_WORKER)
			return (T) this.worker;
		if(capability == CapabilityEnergy.ENERGY)
			return (T) this.storage;
		return super.getCapability(capability, facing);
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

}
