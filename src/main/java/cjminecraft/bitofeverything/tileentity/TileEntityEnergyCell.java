package cjminecraft.bitofeverything.tileentity;

import cjminecraft.core.util.GenericTileEntity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityEnergyCell extends GenericTileEntity {

	private ItemStackHandler handler;
	
	public TileEntityEnergyCell() {
		this.handler = new ItemStackHandler(2);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return (T) this.handler;
		return super.getCapability(capability, facing);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return true;
		return super.hasCapability(capability, facing);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setTag("Inventory", this.handler.serializeNBT());
		return super.writeToNBT(nbt);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		this.handler.deserializeNBT(nbt.getCompoundTag("Inventory"));
		super.readFromNBT(nbt);
	}
	
	//============================================================
	//
	//                        CJCore Start
	//
	//============================================================
	
	@Override
	public void writeClientDataToNBT(NBTTagCompound nbt) {
		writeToNBT(nbt);
	}

	@Override
	public void readClientDataFromNBT(NBTTagCompound nbt) {
		readFromNBT(nbt);
	}
	
	//============================================================
	//
	//                        CJCore End
	//
	//============================================================

}
