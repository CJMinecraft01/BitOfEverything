package cjminecraft.bitofeverything.creativetabs;

import cjminecraft.bitofeverything.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TabBOEItems extends CreativeTabs {

	public TabBOEItems() {
		super("boeitems");
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(ModItems.tinIngot);
	}

}
