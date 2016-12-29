package cjminecraft.bitofeverything;

import cjminecraft.bitofeverything.creativetabs.TabBOEBlocks;
import cjminecraft.bitofeverything.creativetabs.TabBOEItems;
import cjminecraft.bitofeverything.handlers.AchievementHandler;
import cjminecraft.bitofeverything.handlers.OreDictionaryHandler;
import cjminecraft.bitofeverything.handlers.RecipeHandler;
import cjminecraft.bitofeverything.init.ModArmour;
import cjminecraft.bitofeverything.init.ModBlocks;
import cjminecraft.bitofeverything.init.ModItems;
import cjminecraft.bitofeverything.init.ModTools;
import cjminecraft.bitofeverything.proxy.CommonProxy;
import cjminecraft.bitofeverything.util.Utils;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * The main class. This class registers the blocks and items and makes sure
 * everything our mod does works
 * 
 * @author CJMinecraft
 */
@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
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
	 * Client side handles the model bakery
	 * Server side handles tileentities and world generation
	 */
	@SidedProxy(serverSide = Reference.SERVER_PROXY_CLASS, clientSide = Reference.CLIENT_PROXY_CLASS)
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ModItems.init();
		ModTools.init();
		ModArmour.init();
		ModBlocks.init();
		ModItems.register();
		ModTools.register();
		ModArmour.register();
		ModBlocks.register();

		proxy.registerRenders();
		proxy.registerTileEntities();

		AchievementHandler.registerAchievements();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init();
		OreDictionaryHandler.registerOreDictionary();
		proxy.registerModelBakeryStuff();
		eventHandler.registerEvents();
		RecipeHandler.registerCraftingRecipes();
		RecipeHandler.registerFurnaceRecipes();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
	}

}
