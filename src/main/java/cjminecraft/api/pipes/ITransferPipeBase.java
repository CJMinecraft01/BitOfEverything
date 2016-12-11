package cjminecraft.api.pipes;

import net.minecraft.inventory.IInventory;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;

public interface ITransferPipeBase extends IInventory, ITickable {
	
	/**
	 * This methods returns whether the inventory is empty
	 */
	boolean isEmpty();
	
	/**
	 * This methods returns whether the inventory is full
	 */
	boolean isFull();
	
    /**
     * Returns the worldObj for this tileEntity.
     */
    World getWorld();

    /**
     * Gets the world X position for this pipe entity.
     */
    double getXPos();

    /**
     * Gets the world Y position for this pipe entity.
     */
    double getYPos();

    /**
     * Gets the world Z position for this pipe entity.
     */
    double getZPos();

}
