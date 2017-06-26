package cjminecraft.bitofeverything.client.gui;

import cjminecraft.bitofeverything.container.ContainerBlockBreaker;
import cjminecraft.bitofeverything.container.ContainerEnergyCell;
import cjminecraft.bitofeverything.tileentity.TileEntityBlockBreaker;
import cjminecraft.bitofeverything.tileentity.TileEntityEnergyCell;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

/**
 * Handles all of the gui's
 * @author CJMinecraft
 *
 */
public class GuiHandler implements IGuiHandler {

	/**
	 * Each gui needs an ID
	 */
	public static final int BLOCK_BREAKER = 0;
	public static final int ENERGY_CELL = 1;
	
	/**
	 * Should return the container for that gui. This is called server side because servers handle items in guis
	 */
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		switch(ID) {
		case BLOCK_BREAKER:
			return new ContainerBlockBreaker(player.inventory, (TileEntityBlockBreaker) world.getTileEntity(pos));
		case ENERGY_CELL:
			return new ContainerEnergyCell(player.inventory, (TileEntityEnergyCell) world.getTileEntity(pos));
		}
		return null;
	}

	/**
	 * Should return the actual gui. This is called client side as thats where guis are rendered
	 */
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		switch(ID) {
		case BLOCK_BREAKER:
			return new GuiBlockBreaker(player.inventory, (TileEntityBlockBreaker) world.getTileEntity(pos));
		case ENERGY_CELL:
			return new GuiEnergyCell(player.inventory, (TileEntityEnergyCell)world.getTileEntity(pos));
		}
		return null;
	}

}
