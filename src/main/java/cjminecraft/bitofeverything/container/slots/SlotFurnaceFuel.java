package cjminecraft.bitofeverything.container.slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotFurnaceFuel extends SlotItemHandler {
	
	public SlotFurnaceFuel(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
		super(itemHandler, index, xPosition, yPosition);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return TileEntityFurnace.isItemFuel(stack) || net.minecraft.inventory.SlotFurnaceFuel.isBucket(stack);
	}
	
	@Override
	public int getItemStackLimit(ItemStack stack) {
		return net.minecraft.inventory.SlotFurnaceFuel.isBucket(stack) ? 1 : super.getItemStackLimit(stack);
	}

}
