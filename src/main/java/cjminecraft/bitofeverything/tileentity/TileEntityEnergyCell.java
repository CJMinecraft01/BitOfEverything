package cjminecraft.bitofeverything.tileentity;

import cjminecraft.bitofeverything.handlers.EnumHandler.ChipTypes;
import cjminecraft.core.energy.EnergyUnit;
import cjminecraft.core.energy.EnergyUtils;
import cjminecraft.core.energy.compat.TileEntityEnergyStorage;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

/**
 * A {@link TileEntity} which can hold power
 * 
 * @author CJMinecraft
 *
 */
public class TileEntityEnergyCell extends TileEntityEnergyStorage implements ITickable {

	private ItemStackHandler handler;
	public long energyDifference = 0;
	private long transfer;

	/**
	 * A {@link TileEntity} which can hold power
	 */
	public TileEntityEnergyCell() {
		super(1000000, 0);
		this.handler = new ItemStackHandler(2);
		this.transfer = 1000L;
	}

	/**
	 * A {@link TileEntity} which can hold power
	 * 
	 * @param type
	 *            The type of the {@link TileEntity}
	 */
	public TileEntityEnergyCell(ChipTypes type) {
		super(type == ChipTypes.BASIC ? 1000000L : 5000000L, 0);
		this.transfer = type == ChipTypes.BASIC ? 1000L : 5000L;
		this.handler = new ItemStackHandler(2);
	}

	/**
	 * Transfer power
	 */
	@Override
	public void update() {
		if (this.world != null) {
			if (!this.world.isRemote) {
				long before = this.storage.getEnergyStored();
				long receive = this.storage.getMaxEnergyStored() - this.storage.getEnergyStored() < this.transfer
						? this.storage.getMaxEnergyStored() - this.storage.getEnergyStored() : this.transfer;
				long extract = this.storage.getEnergyStored() > this.transfer ? this.transfer
						: this.storage.getEnergyStored();
				if (this.storage.getEnergyStored() < this.storage.getMaxEnergyStored()) {
					this.storage.receiveEnergyInternal((int) EnergyUtils.takeEnergyAllFaces(this.world, this.pos,
							receive, EnergyUnit.FORGE_ENERGY, false), false);
					this.storage.receiveEnergyInternal((int) EnergyUtils.takeEnergy(this.handler.getStackInSlot(1),
							receive, EnergyUnit.FORGE_ENERGY, false, null), false);
				}
				this.storage.extractEnergyInternal((int) EnergyUtils.giveEnergyAllFaces(this.world, this.pos, extract,
						EnergyUnit.FORGE_ENERGY, false), false);
				this.storage.extractEnergyInternal((int) EnergyUtils.giveEnergy(this.handler.getStackInSlot(0), extract,
						EnergyUnit.FORGE_ENERGY, false, null), false);
				this.energyDifference = this.storage.getEnergyStored() - before;
			}
		}
	}

	/**
	 * Get all of the capabilities
	 */
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return (T) this.handler;
		return super.getCapability(capability, facing);
	}

	/**
	 * Say which capabilities the {@link TileEntity} has
	 */
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return true;
		return super.hasCapability(capability, facing);
	}

	/**
	 * Write data to nbt
	 */
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setTag("Inventory", this.handler.serializeNBT());
		return super.writeToNBT(nbt);
	}

	/**
	 * Read data from nbt
	 */
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		this.handler.deserializeNBT(nbt.getCompoundTag("Inventory"));
		this.storage.setMaxTransfer(0);
		super.readFromNBT(nbt);
	}

}
