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

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class BitOfEverything {
	
	public static final CreativeTabs blocks = new TabBOEBlocks();
	public static final CreativeTabs items = new TabBOEItems();
	
	cjminecraft.bitofeverything.handlers.EventHandler eventHandler = new cjminecraft.bitofeverything.handlers.EventHandler();
	
	@Mod.Instance(Reference.MODID)
	public static BitOfEverything instance;
	
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
		
		AchievementHandler.registerAchievements();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
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
