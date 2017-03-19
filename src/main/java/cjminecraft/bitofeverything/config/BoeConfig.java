package cjminecraft.bitofeverything.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cjminecraft.bitofeverything.Reference;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * The main class for your configuration.
 * Get all of your customized values here
 * @author CJMinecraft
 *
 */
public class BoeConfig {
	
	/**
	 * The object which holds the actual config file
	 */
	private static Configuration config = null;
	
	/**
	 * The name of the category for our blocks
	 */
	public static final String CATEGORY_NAME_BLOCKS = "blocks";
	
	/**
	 * The values which are loaded from the config file
	 */
	public static int machineCooldownBasic;
	public static int machineCooldownAdvanced;
	
	/**
	 * Called on the server and the client setting up the config file
	 */
	public static void preInit() {
		File configFile = new File(Loader.instance().getConfigDir(), "BitOfEverything.cfg");
		config = new Configuration(configFile);
		syncFromFiles();
	}
	
	/**
	 * Receive the config object for use in the gui factory
	 * @return the config element
	 */
	public static Configuration getConfig() {
		return config;
	}
	
	/**
	 * Register our event which handles the gui factory saving the config
	 */
	public static void clientPreInit() {
		MinecraftForge.EVENT_BUS.register(new ConfigEventHandler());
	}
	
	/**
	 * Sync the config from a change in the file
	 */
	public static void syncFromFiles() {
		syncConfig(true, true);
	}
	
	/**
	 * Sync the config from a change in the gui
	 */
	public static void syncFromGui() {
		syncConfig(false, true);
	}
	
	/**
	 * Sync the config from a change in the fields (i.e changing the machineCooldownBasic property using BoeConfig.machineCooldownBasic = ...)
	 */
	public static void syncFromFields() {
		syncConfig(false, false);
	}
	
	/**
	 * Used internally to sync all of our properties and fields
	 * @param loadFromConfigFile Should load the actual config file?
	 * @param readFieldsFromConfig Should read the values from the config?
	 */
	private static void syncConfig(boolean loadFromConfigFile, boolean readFieldsFromConfig) {
		if(loadFromConfigFile)
			config.load();
		
		Property propertyMachineCooldownBasic = config.get(CATEGORY_NAME_BLOCKS, "machine_cooldown_basic", 100);
		propertyMachineCooldownBasic.setLanguageKey("gui.config.blocks.machine_cooldown_basic.name");
		propertyMachineCooldownBasic.setComment(I18n.format("gui.config.blocks.machine_cooldown_basic.comment"));
		propertyMachineCooldownBasic.setMinValue(10);
		propertyMachineCooldownBasic.setMaxValue(200);
		Property propertyMachineCooldownAdvanced = config.get(CATEGORY_NAME_BLOCKS, "machine_cooldown_advanced", 50);
		propertyMachineCooldownAdvanced.setLanguageKey("gui.config.blocks.machine_cooldown_advanced.name");
		propertyMachineCooldownAdvanced.setComment(I18n.format("gui.config.blocks.machine_cooldown_advanced.comment"));
		propertyMachineCooldownAdvanced.setMinValue(10);
		propertyMachineCooldownAdvanced.setMaxValue(200);
		
		List<String> propertyOrderBlocks = new ArrayList<String>();
		propertyOrderBlocks.add(propertyMachineCooldownBasic.getName());
		propertyOrderBlocks.add(propertyMachineCooldownAdvanced.getName());
		config.setCategoryPropertyOrder(CATEGORY_NAME_BLOCKS, propertyOrderBlocks);
		
		if(readFieldsFromConfig) {
			machineCooldownBasic = propertyMachineCooldownBasic.getInt();
			machineCooldownAdvanced = propertyMachineCooldownAdvanced.getInt();
		}
		
		propertyMachineCooldownBasic.set(machineCooldownBasic);
		propertyMachineCooldownAdvanced.set(machineCooldownAdvanced);
		
		if(config.hasChanged())
			config.save();
	}
	
	/**
	 * Handles the updating of the config from the gui factory
	 * @author CJMinecraft
	 *
	 */
	public static class ConfigEventHandler {
		
		/**
		 * Syncs the update from the gui factory
		 */
		@SubscribeEvent(priority = EventPriority.LOWEST)
		public void onEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
			if(event.getModID().equals(Reference.MODID)) {
				syncFromGui();
			}
		}
		
	}

}
