package cjminecraft.api.pipes;

import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public interface IItemPipe extends ITransferPipeBase {
	
	/** 
	 * This method is used to check if the item pipe can insert an item stack into an inventory
	 * 
	 * @param inventory
	 * 			Used to check if the item stack can be put in to the inventory
	 * 
	 * @param stack
	 * 			Used to test whether the stack is valid to go in a specific slot
	 * 
	 * @param slot
	 * 			The slot number in which the item stack is going into
	 * 
	 * @param side
	 * 			The side in which the item will be inserted into
	 */
	
	boolean canInsertItemInSlot(IInventory inventory, ItemStack stack, int slot, EnumFacing side);
	
	/** 
	 * This method is used to check if the item pipe can extract an item stack from an inventory
	 * 
	 * @param inventory
	 * 			Used to check if the item stack can be pulled out of the inventory
	 * 
	 * @param stack
	 * 			Used to test whether the stack is valid to come out of in a specific slot
	 * 
	 * @param slot
	 * 			The slot number in which the item stack is coming out of
	 * 
	 * @param side
	 * 			To test if the Item Pipe is connected on that side
	 */
	
	boolean canExtractItemFromSlot(IInventory inventory, ItemStack stack, int slot, EnumFacing side);
	
	/** 
	 * This method is used to insert an item stack into an inventory
	 * 
	 * @param inventory
	 * 			The inventory the item stack is going to be inserted into
	 * 
	 * @param stack
	 * 			The item stack to insert
	 * 
	 * @param slot
	 * 			The slot in which the item stack is going into
	 * 
	 * @param side
	 * 			The side that the stack will be inserted into
	 */
	
	ItemStack insertStack(IInventory inventory, ItemStack stack, int slot, EnumFacing side);
	
	/**
	 * This method is used to transfer the items out of the item pipe
	 */
	boolean transferItemsOut();
	
	/**
     * Returns false if the inventory has any room to place items in
     * 
     * @param inventory
     * 			The inventory to test
     * 
     * @param side
     * 			The side in which the inventory is being tested from
     */
	boolean isInventoryFull(IInventory inventory, EnumFacing side);
	
	/**
     * Returns false if the specified IInventory contains any items
     * 
     * @param inventory
     * 			The inventory to test
     * 
     * @param side
     * 			The side in which the inventory is being tested from
     */
	boolean isInventoryEmpty(IInventory inventoryIn, EnumFacing side);
	
	/**
     * Pulls from the specified slot in the inventory and places in any available slot in the item pipe. Returns true if
     * the entire stack was moved
     * 
     * @param pipe
     * 			Used to put the items in the item pipe
     * 
     * @param inventory
     * 			Used to get the item out of the chosen inventory
     * 
     * @param slot
     * 			Let's the inventory know what slot to pull from
     * 
     * @param facing
     * 			The face in which the item is being pulled from
     */
	boolean pullItemFromSlot(IItemPipe pipe, IInventory inventory, int slot, EnumFacing side);
	
	/**
	 * Attempts to place the passed stack in the inventory, using as many slots as required. Returns leftover items
	 * @param inventory
	 * 			The inventory to be placed into
	 * @param stack
	 * 			The stack that is being transfered
	 * @param facing
	 * 			The face of the block in which the stack is going into
	 */
	ItemStack putStackInInventoryAllSlots(IInventory inventory, ItemStack stack, EnumFacing side);
	
	/**
     * Returns the IInventory that this item pipe is pushing into
     */
	IInventory getInventoryForPipeTransfer();
	
	/**
     * Returns the IInventory for the specified item pipe
     */
	IInventory getItemPipeInventory(IItemPipe pipe);
	
	/**
     * Returns the IInventory (if applicable) of the TileEntity at the specified position
     * 
     * @param world
     * 			Used to get the tile entity's inventory
     * 
     * @param x
     * 			The position 'x'
     * 
     * @param y
     * 			The position 'y'
     * 
     * @param z
     * 			The position 'z'
     */
	IInventory getInventoryAtPosition(World world, double x, double y, double z);
	
	/**
	 * Tests whether the two stacks can combine
	 * @param stack1
	 * 			The first item stack
	 * 
	 * @param stack2
	 * 			The second item stack
	 */
	boolean canCombine(ItemStack stack1, ItemStack stack2);
	
	/**
	 * Sets the transfer cooldown to the number of ticks
	 * @param ticks
	 * 			The ticks in which the item duct cooldown is going to be
	 */
	void setTransferCooldown(int ticks);
	
	/**
	 * Tests if the item pipe is on cooldown
	 */
	boolean isOnTransferCooldown();
	
	/**
	 * Returns true if the item duct can transfer
	 */
	boolean mayTransfer();
	
}
