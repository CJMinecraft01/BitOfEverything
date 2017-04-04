package cjminecraft.bitofeverything.proxy;

import cjminecraft.bitofeverything.Reference;
import cjminecraft.bitofeverything.config.BoeConfig;
import cjminecraft.bitofeverything.handlers.FuelHandler;
import cjminecraft.bitofeverything.tileentity.TileEntityBlockBreaker;
import cjminecraft.bitofeverything.tileentity.TileEntityCanvas;
import cjminecraft.bitofeverything.util.Utils;
import cjminecraft.bitofeverything.worldgen.OreGen;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * This class handles everything on the server side
 * @author CJMinecraft
 *
 */
public class CommonProxy {
	
	/**
	 * Called on the pre initialization phase of the game loading
	 */
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
		GameRegistry.registerTileEntity(TileEntityCanvas.class, Reference.MODID + ":canvas");
	}
	
	/**
	 * Registers the renders - refer to the {@link ClientProxy#registerRenders()}
	 */
	public void registerRenders() {
		
	}
	
	public void registerModelBakeryStuff() {
		
	}

}
