package cjminecraft.bitofeverything.handlers;

import cjminecraft.bitofeverything.init.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;

/**
 * This class handles how long our items will burn in a furnace
 * @author CJMinecraft
 *
 */
public class FuelHandler implements IFuelHandler {

	/**
	 * Inherited from {@link IFuelHandler}
	 * This method returns how long an item will burn in ticks
	 * There are 20 ticks per second
	 */
	@Override
	public int getBurnTime(ItemStack fuel) {
		if(fuel.getItem() == ModItems.niceBiscuit)
			return 100; //This will smelt half an item. A full item would be 200 ticks
		if(fuel.getItem() == ModItems.infinityFlame) {
			return (int) Math.pow(2, 31); //This is a very big number which will work on both 32 and 64 bit computers
		}
		return 0;
	}

}
