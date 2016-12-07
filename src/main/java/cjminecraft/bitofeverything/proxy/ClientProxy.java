package cjminecraft.bitofeverything.proxy;

import cjminecraft.bitofeverything.Reference;
import cjminecraft.bitofeverything.init.ModArmour;
import cjminecraft.bitofeverything.init.ModBlocks;
import cjminecraft.bitofeverything.init.ModItems;
import cjminecraft.bitofeverything.init.ModTools;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void registerRenders() {
		ModItems.registerRenders();
		ModBlocks.registerRenders();
		ModTools.registerRenders();
		ModArmour.registerRenders();
	}
	
	@Override
	public void registerModelBakeryStuff() {
		ModelBakery.registerItemVariants(ModItems.chip, new ResourceLocation(Reference.MODID, "chip_basic"), new ResourceLocation(Reference.MODID, "chip_advanced"));
		ModelBakery.registerItemVariants(Item.getItemFromBlock(ModBlocks.breaker), new ResourceLocation(Reference.MODID, "block_breaker_basic"), new ResourceLocation(Reference.MODID, "block_breaker_advanced"));
		ModelBakery.registerItemVariants(Item.getItemFromBlock(ModBlocks.tinOre), new ResourceLocation(Reference.MODID, "tin_ore_overworld"), new ResourceLocation(Reference.MODID, "tin_ore_nether"), new ResourceLocation(Reference.MODID, "tin_ore_end"));
	}

}
