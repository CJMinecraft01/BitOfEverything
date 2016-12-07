package cjminecraft.bitofeverything.creativetabs;

import cjminecraft.bitofeverything.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * The creative tab for items from our mod
 * @author CJMinecraft
 *
 */
public class TabBOEItems extends CreativeTabs {

	/**
	 * Just says the unlocalized name of our creative tab
	 */
	public TabBOEItems() {
		super("boeitems");
	}

	/**
	 * Gets the item that will appear as the tabs icon
	 */
	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(ModItems.tinIngot);
	}

}
