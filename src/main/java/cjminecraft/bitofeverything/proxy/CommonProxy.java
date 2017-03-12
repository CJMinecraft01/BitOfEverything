package cjminecraft.bitofeverything.proxy;

import cjminecraft.bitofeverything.Reference;
import cjminecraft.bitofeverything.handlers.FuelHandler;
import cjminecraft.bitofeverything.tileentity.TileEntityBlockBreaker;
import cjminecraft.bitofeverything.util.Utils;
import cjminecraft.bitofeverything.worldgen.OreGen;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * This class handles everything on the server side
 * @author CJMinecraft
 *
 */
public class CommonProxy {
	
	public void preInit() {
		
	}
	
	/**
	 * Called on the initialization phase of the game loading
	 */
	public void init() {
		//MOVED THESE TO MAIN CLASS - FIX FOR MC 1.11.2
	}
	
	/**
	 * Registers our tile entities
	 */
	public void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntityBlockBreaker.class, Reference.MODID + ":block_breaker");
	}
	
	/**
	 * Registers the renders - refer to the {@link ClientProxy}
	 */
	public void registerRenders() {
		
	}
	
	public void registerModelBakeryStuff() {
		
	}

}
