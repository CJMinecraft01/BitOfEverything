package cjminecraft.bitofeverything.blocks;

import cjminecraft.bitofeverything.handlers.EnumHandler.ChipTypes;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

/**
 * Simple instance of the {@link BlockMachine} Used for crafting only I have
 * this class just in case I want to make it have a tooltip
 * 
 * @author CJMinecraft
 *
 */
public class BlockMachineFrame extends BlockMachine {

	/**
	 * Default constructor
	 * 
	 * @param unlocalizedName
	 *            The block's unlocalizedName
	 */
	public BlockMachineFrame(String unlocalizedName) {
		super(unlocalizedName);
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, NonNullList<ItemStack> list) {
		for (int i = 0; i < ChipTypes.values().length; i++)
			list.add(new ItemStack(item, 1, i));
	}

}
