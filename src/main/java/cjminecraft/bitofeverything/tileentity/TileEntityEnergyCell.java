package cjminecraft.bitofeverything.tileentity;

import cjminecraft.bitofeverything.handlers.EnumHandler.ChipTypes;
import cjminecraft.bitofeverything.util.Utils;
import cjminecraft.core.energy.CustomForgeEnergyStorage;
import cjminecraft.core.energy.EnergyUnits;
import cjminecraft.core.energy.EnergyUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityEnergyCell extends TileEntity implements ITickable {

	private ItemStackHandler handler;
	private CustomForgeEnergyStorage storage;
	public int energyDifference = 0;

	public TileEntityEnergyCell() {
		this.handler = new ItemStackHandler(2);
		this.storage = new CustomForgeEnergyStorage(1000000, 0);
	}

	public TileEntityEnergyCell(ChipTypes type) {
		this.handler = new ItemStackHandler(2);
		this.storage = new CustomForgeEnergyStorage(type == ChipTypes.BASIC ? 1000000 : 5000000, 0);
	}

	@Override
	public void update() {
		if (this.world != null) {
			if (!this.world.isRemote) {
				int before = this.storage.getEnergyStored();
				int receive = this.storage.getMaxEnergyStored() - this.storage.getEnergyStored() < 1000
						? this.storage.getMaxEnergyStored() - this.storage.getEnergyStored() : 1000;
				int extract = this.storage.getEnergyStored() > 1000 ? 1000 : this.storage.getEnergyStored();
				if (this.storage.getEnergyStored() < this.storage.getMaxEnergyStored()) {
					this.storage.receiveEnergyInternal((int) EnergyUtils.takeEnergyAllFaces(this.world, this.pos,
							receive, EnergyUnits.FORGE_ENERGY, false), false);
					this.storage.receiveEnergyInternal((int) EnergyUtils.takeEnergy(this.handler.getStackInSlot(1),
							receive, EnergyUnits.FORGE_ENERGY, false, null), false);
				}
				this.storage.extractEnergyInternal((int) EnergyUtils.giveEnergyAllFaces(this.world, this.pos, extract,
						EnergyUnits.FORGE_ENERGY, false), false);
				this.storage.extractEnergyInternal((int) EnergyUtils.giveEnergy(this.handler.getStackInSlot(0), extract,
						EnergyUnits.FORGE_ENERGY, false, null), false);
				this.energyDifference = this.storage.getEnergyStored() - before;
			}
		}
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return (T) this.handler;
		if (capability == CapabilityEnergy.ENERGY)
			return (T) this.storage;
		return super.getCapability(capability, facing);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return true;
		if (capability == CapabilityEnergy.ENERGY)
			return true;
		return super.hasCapability(capability, facing);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setTag("Inventory", this.handler.serializeNBT());
		this.storage.writeToNBT(nbt);
		return super.writeToNBT(nbt);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		this.handler.deserializeNBT(nbt.getCompoundTag("Inventory"));
		this.storage.readFromNBT(nbt);
		super.readFromNBT(nbt);
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
