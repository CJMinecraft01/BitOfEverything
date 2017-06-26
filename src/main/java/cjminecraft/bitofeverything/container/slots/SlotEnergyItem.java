package cjminecraft.bitofeverything.container.slots;

import cjminecraft.core.energy.EnergyUtils;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotEnergyItem extends SlotItemHandler {

	public SlotEnergyItem(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
		super(itemHandler, index, xPosition, yPosition);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		return super.isItemValid(stack) && EnergyUtils.hasSupport(stack, null);
	}

}
