package cjminecraft.bitofeverything.init;

import cjminecraft.bitofeverything.BitOfEverything;
import cjminecraft.bitofeverything.Reference;
import cjminecraft.bitofeverything.items.ItemModArmour;
import cjminecraft.bitofeverything.util.Utils;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModArmour {
	
	public static ArmorMaterial tinMaterial = EnumHelper.addArmorMaterial("tin", Reference.MODID + ":tin", 15, new int[] {2,6,5,2}, 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 2.0F);
	
	public static ItemArmor tinHelmet;
	public static ItemArmor tinChestplate;
	public static ItemArmor tinLeggings;
	public static ItemArmor tinBoots;
	
	public static void init() {
		tinHelmet = new ItemModArmour(tinMaterial, 1, EntityEquipmentSlot.HEAD, "tin_helmet");
		tinChestplate = new ItemModArmour(tinMaterial, 1, EntityEquipmentSlot.CHEST, "tin_chestplate");
		tinLeggings = new ItemModArmour(tinMaterial, 2, EntityEquipmentSlot.LEGS, "tin_leggings");
		tinBoots = new ItemModArmour(tinMaterial, 1, EntityEquipmentSlot.FEET, "tin_boots");
	}
	
	public static void register() {
		registerItem(tinHelmet);
		registerItem(tinChestplate);
		registerItem(tinLeggings);
		registerItem(tinBoots);
	}
	
	public static void registerRenders() {
		registerRender(tinHelmet);
		registerRender(tinChestplate);
		registerRender(tinLeggings);
		registerRender(tinBoots);
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
