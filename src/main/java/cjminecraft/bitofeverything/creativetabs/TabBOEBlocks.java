package cjminecraft.bitofeverything.creativetabs;

import cjminecraft.bitofeverything.init.ModBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TabBOEBlocks extends CreativeTabs {

	public TabBOEBlocks() {
		super("boeblocks");
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(ModBlocks.tin_ore);
	}

}
