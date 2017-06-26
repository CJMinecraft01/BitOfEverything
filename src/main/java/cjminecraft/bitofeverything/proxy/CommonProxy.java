package cjminecraft.bitofeverything.proxy;

import cjminecraft.bitofeverything.BitOfEverything;
import cjminecraft.bitofeverything.Reference;
import cjminecraft.bitofeverything.client.gui.GuiHandler;
import cjminecraft.bitofeverything.network.PacketHandler;
import cjminecraft.bitofeverything.tileentity.TileEntityBlockBreaker;
import cjminecraft.bitofeverything.tileentity.TileEntityCanvas;
import cjminecraft.bitofeverything.tileentity.TileEntityEnergyCell;
import net.minecraftforge.fml.common.network.NetworkRegistry;
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
		PacketHandler.registerMessages(Reference.MODID);
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
		GameRegistry.registerTileEntity(TileEntityEnergyCell.class, Reference.MODID + ":energy_cell");
	}
	
	/**
	 * Registers the renders - refer to the {@link ClientProxy#registerRenders()}
	 */
	public void registerRenders() {
		
	}
	
	public void registerModelBakeryStuff() {
		
	}

}
