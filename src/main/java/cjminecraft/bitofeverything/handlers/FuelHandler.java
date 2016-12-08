package cjminecraft.bitofeverything.handlers;

import cjminecraft.bitofeverything.init.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;

public class FuelHandler implements IFuelHandler {

	@Override
	public int getBurnTime(ItemStack fuel) {
		if(fuel.getItem() == ModItems.niceBiscuit)
			return 100;
		if(fuel.getItem() == ModItems.infinityFlame) {
			if(System.getProperty("os.arch") == "x86") {
				return (int) Math.pow(2, 31);
			} else {
				return (int) Math.pow(2, 63);
			}
		}
		return 0;
	}

}
