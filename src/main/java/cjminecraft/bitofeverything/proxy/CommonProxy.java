package cjminecraft.bitofeverything.proxy;

import cjminecraft.bitofeverything.handlers.FuelHandler;
import cjminecraft.bitofeverything.worldgen.OreGen;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * This class handles everything on the server side
 * @author CJMinecraft
 *
 */
public class CommonProxy {
	
	/**
	 * Called on the initialization phase of the game loading
	 */
	public void init() {
		GameRegistry.registerWorldGenerator(new OreGen(), 0);
		GameRegistry.registerFuelHandler(new FuelHandler());
	}
	
	/**
	 * Registers the renders - refer to the {@link ClientProxy}
	 */
	public void registerRenders() {
		
	}
	
	public void registerModelBakeryStuff() {
		
	}

}
