package cjminecraft.bitofeverything.proxy;

import cjminecraft.bitofeverything.BitOfEverything;
import cjminecraft.bitofeverything.Reference;
import cjminecraft.bitofeverything.client.gui.GuiHandler;
import cjminecraft.bitofeverything.config.BoeConfig;
import cjminecraft.bitofeverything.init.ModArmour;
import cjminecraft.bitofeverything.init.ModBlocks;
import cjminecraft.bitofeverything.init.ModItems;
import cjminecraft.bitofeverything.init.ModTools;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.NetworkRegistry;

/**
 * This class handles everything on the client side like the {@link ModelBakery} and the render of items
 * @author CJMinecraft
 *
 */
public class ClientProxy extends CommonProxy {
	
	/**
	 * Everything that should be ran client side only in the pre initialization phase
	 */
	@Override
	public void preInit() {
		ModBlocks.createStateMappers();
		BoeConfig.clientPreInit();
	}
	
	/**
	 * Everything that should be ran client side only in the initialization phase
	 */
	@Override
	public void init() {
		ModItems.registerItemColours();
		NetworkRegistry.INSTANCE.registerGuiHandler(BitOfEverything.instance, new GuiHandler());
	}
	
	/**
	 * Registers the renders
	 */
	@Override
	public void registerRenders() {
		ModItems.registerRenders();
		ModBlocks.registerRenders();
		ModTools.registerRenders();
		ModArmour.registerRenders();
	}
	
	/**
	 * Adds the item variant renders
	 */
	@Override
	public void registerModelBakeryStuff() {
		ModelBakery.registerItemVariants(ModItems.chip, new ResourceLocation(Reference.MODID, "chip_basic"), new ResourceLocation(Reference.MODID, "chip_advanced"));
		ModelBakery.registerItemVariants(Item.getItemFromBlock(ModBlocks.breaker), new ResourceLocation(Reference.MODID, "block_breaker_basic"), new ResourceLocation(Reference.MODID, "block_breaker_advanced"));
		ModelBakery.registerItemVariants(Item.getItemFromBlock(ModBlocks.tinOre), new ResourceLocation(Reference.MODID, "tin_ore_overworld"), new ResourceLocation(Reference.MODID, "tin_ore_nether"), new ResourceLocation(Reference.MODID, "tin_ore_end"));
		ModelBakery.registerItemVariants(Item.getItemFromBlock(ModBlocks.machineFrame), new ResourceLocation(Reference.MODID, "machine_frame_basic"), new ResourceLocation(Reference.MODID, "machine_frame_advanced"));
	}

}
