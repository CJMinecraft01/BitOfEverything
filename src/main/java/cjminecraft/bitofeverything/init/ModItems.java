package cjminecraft.bitofeverything.init;

import cjminecraft.bitofeverything.BitOfEverything;
import cjminecraft.bitofeverything.Reference;
import cjminecraft.bitofeverything.handlers.EnumHandler;
import cjminecraft.bitofeverything.items.ItemChip;
import cjminecraft.bitofeverything.items.ItemHeart;
import cjminecraft.bitofeverything.items.ItemTinIngot;
import cjminecraft.bitofeverything.util.Utils;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModItems {
	
	public static Item tinIngot;
	public static Item heart;
	public static Item chip;
	
	public static void init() {
		tinIngot = new ItemTinIngot("tin_ingot", "tin_ingot");
		heart = new ItemHeart("heart");
		chip = new ItemChip("chip");
	}
	
	public static void register() {
		registerItem(tinIngot);
		registerItem(heart);
		registerItem(chip);
	}
	
	public static void registerRenders() {
		registerRender(tinIngot);
		registerRender(heart);
		for(int i = 0; i < EnumHandler.ChipTypes.values().length; i++) {
			registerRender(chip, i, "chip_" + EnumHandler.ChipTypes.values()[i].getName());
		}
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
	
	public static void registerRender(Item item, int meta, String fileName) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(new ResourceLocation(Reference.MODID, fileName), "inventory"));
		Utils.getLogger().info("Register render for " + item.getUnlocalizedName().substring(5));
	}

}
