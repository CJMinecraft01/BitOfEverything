package cjminecraft.bitofeverything.handlers;

import cjminecraft.bitofeverything.init.ModBlocks;
import cjminecraft.bitofeverything.init.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictionaryHandler {
	
	public static void registerOreDictionary() {
		OreDictionary.registerOre("oreTin", ModBlocks.tinOre);
		OreDictionary.registerOre("ingotTin", ModItems.tinIngot);
		OreDictionary.registerOre("chipBasic", new ItemStack(ModItems.chip, 1, 0));
		OreDictionary.registerOre("chipAdvanced", new ItemStack(ModItems.chip, 1, 1));
	}

}
