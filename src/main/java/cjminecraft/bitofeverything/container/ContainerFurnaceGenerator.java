package cjminecraft.bitofeverything.container;

import cjminecraft.bitofeverything.container.slots.SlotFurnaceFuel;
import cjminecraft.bitofeverything.tileentity.TileEntityFurnaceGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

/**
 * The container for the furnace generator
 * 
 * @author CJMinecraft
 *
 */
public class ContainerFurnaceGenerator extends Container {

	/**
	 * The inventories
	 */

	private TileEntityFurnaceGenerator te;
	private IItemHandler handler;

	/**
	 * Initialise the container for the furnace generator
	 * 
	 * @param playerInv
	 *            The player's inventory
	 * @param te
	 *            The {@link TileEntityFurnaceGenerator} with the inventory
	 */
	public ContainerFurnaceGenerator(IInventory playerInv, TileEntityFurnaceGenerator te) {
		this.te = te;
		this.handler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

		this.addSlotToContainer(new SlotFurnaceFuel(handler, 0, 81, 35));

		// The player's inventory slots
		int xPos = 8; // The x position of the top left player inventory slot on
						// our texture
		int yPos = 84; // The y position of the top left player inventory slot
						// on our texture

		// Player slots
		for (int y = 0; y < 3; ++y) {
			for (int x = 0; x < 9; ++x) {
				this.addSlotToContainer(new Slot(playerInv, x + y * 9 + 9, xPos + x * 18, yPos + y * 18));
			}
		}

		for (int x = 0; x < 9; ++x) {
			this.addSlotToContainer(new Slot(playerInv, x, xPos + x * 18, yPos + 58));
		}
	}

	/**
	 * Allow for SHIFT click transfers
	 */
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int fromSlot) {
		ItemStack previous = ItemStack.EMPTY;
		Slot slot = (Slot) this.inventorySlots.get(fromSlot);

		if (slot != null && slot.getHasStack()) {
			ItemStack current = slot.getStack();
			previous = current.copy();

			if (fromSlot < this.handler.getSlots()) {
				// From the energy cell inventory to the player's inventory
				if (!this.mergeItemStack(current, this.handler.getSlots(), handler.getSlots() + 36, true))
					return ItemStack.EMPTY;
			} else {
				// From the player's inventory to the block breaker's inventory
				if (!this.mergeItemStack(current, 0, this.handler.getSlots(), false))
					return ItemStack.EMPTY;
			}

			if (current.getCount() == 0)
				slot.putStack(ItemStack.EMPTY);
			else
				slot.onSlotChanged();

			if (current.getCount() == previous.getCount())
				return ItemStack.EMPTY;
			slot.onTake(playerIn, current);
		}
		return previous;
	}

	/**
	 * Say we can interact with the player
	 */
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return player.getDistanceSq(this.te.getPos().add(0.5, 0.5, 0.5)) <= 64;
	}

}
