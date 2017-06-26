package cjminecraft.bitofeverything;

import cjminecraft.bitofeverything.client.gui.GuiHandler;
import cjminecraft.bitofeverything.config.BoeConfig;
import cjminecraft.bitofeverything.creativetabs.TabBOEBlocks;
import cjminecraft.bitofeverything.creativetabs.TabBOEItems;
import cjminecraft.bitofeverything.handlers.AchievementHandler;
import cjminecraft.bitofeverything.handlers.BoeSoundHandler;
import cjminecraft.bitofeverything.handlers.FuelHandler;
import cjminecraft.bitofeverything.handlers.OreDictionaryHandler;
import cjminecraft.bitofeverything.handlers.RecipeHandler;
import cjminecraft.bitofeverything.init.ModArmour;
import cjminecraft.bitofeverything.init.ModBlocks;
import cjminecraft.bitofeverything.init.ModCapabilities;
import cjminecraft.bitofeverything.init.ModItems;
import cjminecraft.bitofeverything.init.ModTools;
import cjminecraft.bitofeverything.proxy.CommonProxy;
import cjminecraft.bitofeverything.worldgen.OreGen;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.CustomProperty;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * The main class. This class registers the blocks and items and makes sure
 * everything our mod does works
 * 
 * @author CJMinecraft
 */
@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION, guiFactory = Reference.GUI_FACTORY, useMetadata = true, customProperties = {
		@CustomProperty(k = "useVersionChecker", v = "true") })
public class BitOfEverything {

	/**
	 * Our creative tabs
	 */
	public static final CreativeTabs blocks = new TabBOEBlocks();
	public static final CreativeTabs items = new TabBOEItems();

	/**
	 * Handles the events
	 */
	cjminecraft.bitofeverything.handlers.EventHandler eventHandler = new cjminecraft.bitofeverything.handlers.EventHandler();

	/**
	 * Used for GUI stuff
	 */
	@Mod.Instance(Reference.MODID)
	public static BitOfEverything instance;

	/**
	 * Proxy so that we register the correct things on server and client side.
	 * Client side handles the model bakery Server side handles tile entities
	 * and world generation
	 */
	@SidedProxy(serverSide = Reference.SERVER_PROXY_CLASS, clientSide = Reference.CLIENT_PROXY_CLASS)
	public static CommonProxy proxy;

	/**
	 * Called first. Should initialize everything and register everything
	 * 
	 * @param event
	 *            The event (you probably wont use this)
	 */
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ModBlocks.init();
		ModItems.init();
		ModTools.init();
		ModArmour.init();
		ModItems.register();
		ModTools.register();
		ModArmour.register();
		ModBlocks.register();

		BoeConfig.preInit();
		proxy.preInit();
		proxy.registerRenders();
		proxy.registerTileEntities();

		NetworkRegistry.INSTANCE.registerGuiHandler(BitOfEverything.instance, new GuiHandler());
		ModCapabilities.registerCapabilities();

		AchievementHandler.registerAchievements();
	}

	/**
	 * Called to register recipes and events
	 * 
	 * @param event
	 *            The event (you probably wont use this)
	 */
	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init();
		GameRegistry.registerWorldGenerator(new OreGen(), 0);
		GameRegistry.registerFuelHandler(new FuelHandler());
		OreDictionaryHandler.registerOreDictionary();
		proxy.registerModelBakeryStuff();
		eventHandler.registerEvents();
		RecipeHandler.registerCraftingRecipes();
		RecipeHandler.registerFurnaceRecipes();
		BoeSoundHandler.init();
	}

	/**
	 * Called after everything. Should be used for mod integration
	 * 
	 * @param event
	 *            The event (you probably wont use this)
	 */
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
	}

}
