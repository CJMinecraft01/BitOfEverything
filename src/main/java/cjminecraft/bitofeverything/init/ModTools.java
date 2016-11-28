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

public class ModTools {
	
	public static final ToolMaterial tinMaterial = EnumHelper.addToolMaterial(Reference.MODID + ":tin", 2, 300, 5.0F, 2.0F, 12);
	
	public static ItemPickaxe tinPickaxe;
	public static ItemModAxe tinAxe;
	public static ItemHoe tinHoe;
	public static ItemSpade tinShovel;
	public static ItemSword tinSword;
	
	public static ItemSoulStealer soulStealer;
	
	public static void init() {
		tinPickaxe = new ItemModPickaxe(tinMaterial, "tin_pickaxe");
		tinAxe = new ItemModAxe(tinMaterial, "tin_axe");
		tinHoe = new ItemModHoe(tinMaterial, "tin_hoe");
		tinShovel = new ItemModShovel(tinMaterial, "tin_shovel");
		tinSword = new ItemModSword(tinMaterial, "tin_sword");
		
		soulStealer = new ItemSoulStealer("soul_stealer");
	}
	
	public static void register() {
		registerItem(tinPickaxe);
		registerItem(tinAxe);
		registerItem(tinHoe);
		registerItem(tinShovel);
		registerItem(tinSword);
		registerItem(soulStealer);
	}
	
	public static void registerRenders() {
		registerRender(tinPickaxe);
		registerRender(tinAxe);
		registerRender(tinHoe);
		registerRender(tinShovel);
		registerRender(tinSword);
		registerRender(soulStealer);
	}
	
	public static void registerItem(Item item) {
		item.setCreativeTab(BitOfEverything.items);
		GameRegistry.register(item);
		Utils.getLogger().info("Registered item: " + item.getUnlocalizedName().substring(5));
	}
	
	public static void registerRender(Item item) {
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(new ResourceLocation(Reference.MODID, item.getUnlocalizedName().substring(5)), "inventory"));
		Utils.getLogger().info("Register render for " + item.getUnlocalizedName().substring(5));
	}

}
