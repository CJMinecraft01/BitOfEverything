package cjminecraft.bitofeverything.handlers;

import cjminecraft.bitofeverything.init.ModArmour;
import cjminecraft.bitofeverything.init.ModBlocks;
import cjminecraft.bitofeverything.init.ModItems;
import cjminecraft.bitofeverything.init.ModTools;
import cjminecraft.bitofeverything.util.Utils;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

/**
 * This class handles all of our mods block and item recipes
 * @author CJMinecraft
 *
 */
public class RecipeHandler {
	
	/**
	 * Register the crafting reciepes
	 */
	public static void registerCraftingRecipes() {
		GameRegistry.addRecipe(new ItemStack(ModTools.soulStealer), new Object[] { "SAS", "ADA", "SAS", 'S', Items.STICK, 'A', Items.STRING, 'D', Items.DIAMOND });
		registerToolRecipe(ModItems.tinIngot, ModTools.tinPickaxe, ModTools.tinAxe, ModTools.tinShovel, ModTools.tinHoe, ModTools.tinSword);
		registerArmourRecipe("ingotTin", ModArmour.tinHelmet, ModArmour.tinChestplate, ModArmour.tinLeggings, ModArmour.tinBoots);
		GameRegistry.addRecipe(new ItemStack(ModBlocks.tinBlock), new Object[] { "TTT", "TTT", "TTT", 'T', ModItems.tinIngot });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.tinIngot, 9), new Object[] { ModBlocks.tinBlock });
		GameRegistry.addRecipe(new ItemStack(ModItems.tinIngot), new Object[] { "NNN", "NNN", "NNN", 'N', ModItems.tinNugget });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.tinNugget, 9), new Object[] { ModItems.tinIngot });
		GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.tinApple, new Object[] { "III", "IAI", "III", 'I', "ingotTin", 'A', Items.APPLE }));
		GameRegistry.addRecipe(new ItemStack(ModItems.infinityFlame), new Object[] { "CCC", "CDC", "CCC", 'C', Blocks.COAL_BLOCK, 'D', Blocks.DIAMOND_BLOCK });
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.niceBiscuit, 4), new Object[] { "cropWheat", "cropWheat" }));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.chip, 1, 0), new Object[] { "TRT", "RGR", "TRT", 'T', "ingotTin", 'R', "dustRedstone", 'G', "dyeGreen" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.chip, 1, 1), new Object[] { "TRT", "RCR", "TRT", 'T', "ingotTin", 'R', "dustRedstone", 'C', "chipBasic" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.machineFrame, 1, 0), new Object[] { "TRT", "TCT", "RIR", 'R', "dustRedstone", 'T', "ingotTin", 'C', "chipBasic", 'I', Blocks.IRON_BLOCK }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.machineFrame, 1, 1), new Object[] { "TRT", "TCT", "RIR", 'R', "dustRedstone", 'T', "ingotTin", 'C', "chipAdvanced", 'I', Blocks.IRON_BLOCK }));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.breaker, 1, 0), new Object[] { " P ", "RMR", "TTT", 'P', Items.IRON_PICKAXE, 'R', "dustRedstone", 'M', new ItemStack(ModBlocks.machineFrame, 1, 0), 'T', "ingotTin" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.breaker, 1, 1), new Object[] { " P ", "RMR", "TTT", 'P', Items.IRON_PICKAXE, 'R', "dustRedstone", 'M', new ItemStack(ModBlocks.machineFrame, 1, 1), 'T', "ingotTin" }));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.tinSlabHalf, 2), new Object[] { "   ", "TTT", "   ", 'T', "ingotTin" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.tinSlabHalf, 2), new Object[] { "TTT", "   ", "   ", 'T', "ingotTin" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.tinSlabHalf, 2), new Object[] { "   ", "   ", "TTT", 'T', "ingotTin" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.tinStairs, 4), new Object[] { "T  ", "TT ", "TTT", 'T', "ingotTin" }));
		Utils.getLogger().info("Registered Crafting Recipes!");
	}
	
	/**
	 * Register the furnace recipes
	 */
	public static void registerFurnaceRecipes() {
		GameRegistry.addSmelting(ModBlocks.tinOre, new ItemStack(ModItems.tinIngot), 0.7f);
		Utils.getLogger().info("Registered Furnace Recipes!");
	}
	
	/**
	 * Registers a tool using the ingot
	 * @param ingot The ingot
	 * @param pickaxe The pickaxe
	 * @param axe The axe
	 * @param shovel The shovel
	 * @param hoe The hoe
	 * @param sword The sword
	 */
	private static void registerToolRecipe(Item ingot, Item pickaxe, Item axe, Item shovel, Item hoe, Item sword) {
		GameRegistry.addRecipe(new ItemStack(pickaxe), new Object[] { "III", " S ", " S ", 'I', ingot, 'S', Items.STICK });
		GameRegistry.addRecipe(new ItemStack(axe), new Object[] { "II ", "IS ", " S ", 'I', ingot, 'S', Items.STICK });
		GameRegistry.addRecipe(new ItemStack(axe), new Object[] { " II", " SI", " S ", 'I', ingot, 'S', Items.STICK });
		GameRegistry.addRecipe(new ItemStack(shovel), new Object[] { " I ", " S ", " S ", 'I', ingot, 'S', Items.STICK });
		GameRegistry.addRecipe(new ItemStack(hoe), new Object[] { " II", " S ", " S ", 'I', ingot, 'S', Items.STICK });
		GameRegistry.addRecipe(new ItemStack(hoe), new Object[] { "II ", " S ", " S ", 'I', ingot, 'S', Items.STICK });
		GameRegistry.addRecipe(new ItemStack(sword), new Object[] { " I ", " I ", " S ", 'I', ingot, 'S', Items.STICK });
	}
	
	/**
	 * Registers a tool using the ingot from the {@link OreDictionary}
	 * @param ingotOD The ingot from the {@link OreDictionary}
	 * @param pickaxe The pickaxe
	 * @param axe The axe
	 * @param shovel The shovel
	 * @param hoe The hoe
	 * @param sword The sword
	 * @param stickOD The stick from the {@link OreDictionary}
	 */
	private static void registerToolRecipe(String ingotOD, Item pickaxe, Item axe, Item shovel, Item hoe, Item sword, String stickOD) {
		GameRegistry.addRecipe(new ShapedOreRecipe(pickaxe, new Object[] { "III", " S ", " S ", 'I', ingotOD, 'S', stickOD }));
		GameRegistry.addRecipe(new ShapedOreRecipe(axe, new Object[] { "II ", "IS ", " S ", 'I', ingotOD, 'S', stickOD }));
		GameRegistry.addRecipe(new ShapedOreRecipe(axe, new Object[] { " II", " SI", " S ", 'I', ingotOD, 'S', stickOD }));
		GameRegistry.addRecipe(new ShapedOreRecipe(shovel, new Object[] { " I ", " S ", " S ", 'I', ingotOD, 'S', stickOD }));
		GameRegistry.addRecipe(new ShapedOreRecipe(hoe, new Object[] { " II", " S ", " S ", 'I', ingotOD, 'S', stickOD }));
		GameRegistry.addRecipe(new ShapedOreRecipe(hoe, new Object[] { "II ", " S ", " S ", 'I', ingotOD, 'S', stickOD }));
		GameRegistry.addRecipe(new ShapedOreRecipe(sword, new Object[] { " I ", " I ", " S ", 'I', ingotOD, 'S', stickOD }));
	}
	
	/**
	 * Registers armour using the ingot
	 * @param ingot The ingot
	 * @param helmet The helmet
	 * @param chestplate The chestplate
	 * @param leggings The leggings
	 * @param boots The boots
	 */
	public static void registerArmourRecipe(Item ingot, Item helmet, Item chestplate, Item leggings, Item boots) {
		GameRegistry.addRecipe(new ItemStack(helmet), new Object[] { "III","I I","   ",'I',ingot});
		GameRegistry.addRecipe(new ItemStack(helmet), new Object[] { "   ","III","I I",'I',ingot});
		GameRegistry.addRecipe(new ItemStack(chestplate), new Object[] { "I I","III","III",'I',ingot});
		GameRegistry.addRecipe(new ItemStack(leggings), new Object[] { "III","I I","I I",'I',ingot});
		GameRegistry.addRecipe(new ItemStack(boots), new Object[] { "I I","I I","   ",'I',ingot});
		GameRegistry.addRecipe(new ItemStack(boots), new Object[] { "   ","I I","I I",'I',ingot});
	}
	
	/**
	 * Registers armour using the ingot from the {@link OreDictionary}
	 * @param ingotOD The ingot from the {@link OreDictionary}
	 * @param helmet The helmet
	 * @param chestplate The chestplate
	 * @param leggings The leggings
	 * @param boots The boots
	 */
	public static void registerArmourRecipe(String ingotOD, Item helmet, Item chestplate, Item leggings, Item boots) {
		GameRegistry.addRecipe(new ShapedOreRecipe(helmet, new Object[] { "III","I I","   ",'I',ingotOD}));
		GameRegistry.addRecipe(new ShapedOreRecipe(helmet, new Object[] { "   ","III","I I",'I',ingotOD}));
		GameRegistry.addRecipe(new ShapedOreRecipe(chestplate, new Object[] { "I I","III","III",'I',ingotOD}));
		GameRegistry.addRecipe(new ShapedOreRecipe(leggings, new Object[] { "III","I I","I I",'I',ingotOD}));
		GameRegistry.addRecipe(new ShapedOreRecipe(boots, new Object[] { "I I","I I","   ",'I',ingotOD}));
		GameRegistry.addRecipe(new ShapedOreRecipe(boots, new Object[] { "   ","I I","I I",'I',ingotOD}));
	}

}
