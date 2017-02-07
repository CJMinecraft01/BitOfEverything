package cjminecraft.bitofeverything.init;

import cjminecraft.bitofeverything.BitOfEverything;
import cjminecraft.bitofeverything.Reference;
import cjminecraft.bitofeverything.items.ItemModAxe;
import cjminecraft.bitofeverything.items.ItemModHoe;
import cjminecraft.bitofeverything.items.ItemModPickaxe;
import cjminecraft.bitofeverything.items.ItemModShovel;
import cjminecraft.bitofeverything.items.ItemModSword;
import cjminecraft.bitofeverything.items.ItemSoulStealer;
import cjminecraft.bitofeverything.util.Utils;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.RecipesTools;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Handles the tool registration and render
 * @author CJMinecraft
 *
 */
public class ModTools {
	
	/**
	 * The tool material (Ours is like Iron)
	 */
	public static final ToolMaterial tinMaterial = EnumHelper.addToolMaterial(Reference.MODID + ":tin", 2, 300, 5.0F, 2.0F, 12);
	
	/**
	 * State the individual tools
	 */
	public static ItemPickaxe tinPickaxe;
	public static ItemModAxe tinAxe; //NOTICE WE USE THE ITEMMODAXE NOT ITEMAXE
	public static ItemHoe tinHoe;
	public static ItemSpade tinShovel;
	public static ItemSword tinSword;
	
	public static ItemSoulStealer soulStealer; //Custom tool
	
	/**
	 * Initialize the tools
	 */
	public static void init() {
		tinPickaxe = new ItemModPickaxe(tinMaterial, "tin_pickaxe");
		tinAxe = new ItemModAxe(tinMaterial, "tin_axe");
		tinHoe = new ItemModHoe(tinMaterial, "tin_hoe");
		tinShovel = new ItemModShovel(tinMaterial, "tin_shovel");
		tinSword = new ItemModSword(tinMaterial, "tin_sword");
		
		soulStealer = new ItemSoulStealer("soul_stealer");
	}
	
	/**
	 * Register the tools
	 */
	public static void register() {
		registerItem(tinPickaxe);
		registerItem(tinAxe);
		registerItem(tinHoe);
		registerItem(tinShovel);
		registerItem(tinSword);
		registerItem(soulStealer);
	}
	
	/**
	 * Register the tools render
	 */
	public static void registerRenders() {
		registerRender(tinPickaxe);
		registerRender(tinAxe);
		registerRender(tinHoe);
		registerRender(tinShovel);
		registerRender(tinSword);
		registerRender(soulStealer);
	}
	
	/**
	 * Register the item
	 * @param item The item
	 */
	public static void registerItem(Item item) {
		item.setCreativeTab(BitOfEverything.items);
		GameRegistry.register(item);
		Utils.getLogger().info("Registered Item: " + item.getUnlocalizedName().substring(5));
	}
	
	/**
	 * Register the items render
	 * @param item The item
	 */
	public static void registerRender(Item item) {
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(new ResourceLocation(Reference.MODID, item.getUnlocalizedName().substring(5)), "inventory"));
		Utils.getLogger().info("Registered render for " + item.getUnlocalizedName().substring(5));
	}

}
