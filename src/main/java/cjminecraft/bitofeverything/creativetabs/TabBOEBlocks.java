package cjminecraft.bitofeverything.creativetabs;

import cjminecraft.bitofeverything.init.ModBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * The creative tab for blocks from our mod
 * @author CJMinecraft
 *
 */
public class TabBOEBlocks extends CreativeTabs {

	/**
	 * Just says the unlocalized name of our creative tab
	 */
	public TabBOEBlocks() {
		super("boeblocks");
	}

	/**
	 * Gets the item that will appear as the tabs icon
	 */
	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(ModBlocks.tinOre);
	}

}
