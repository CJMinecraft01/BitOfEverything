package cjminecraft.bitofeverything.container;

import cjminecraft.bitofeverything.container.slots.SlotFurnaceFuel;
import cjminecraft.bitofeverything.container.slots.SlotFurnaceOutput;
import cjminecraft.bitofeverything.tileentity.TileEntityDoubleFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerDoubleFurnace extends Container {

	private TileEntityDoubleFurnace te;
	private IItemHandler handler;

	public ContainerDoubleFurnace(EntityPlayer player, TileEntityDoubleFurnace te) {
		this.te = te;
		this.handler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

		this.addSlotToContainer(new SlotItemHandler(this.handler, 0, 56, 17));
		this.addSlotToContainer(new SlotItemHandler(this.handler, 1, 39, 17));
		this.addSlotToContainer(new SlotFurnaceFuel(this.handler, 2, 56, 53));
		this.addSlotToContainer(new SlotFurnaceFuel(this.handler, 3, 39, 53));
		this.addSlotToContainer(new SlotFurnaceOutput(player, this.handler, 4, 118, 35));
		this.addSlotToContainer(new SlotFurnaceOutput(player, this.handler, 5, 139, 35));

		// The player's inventory slots
		int xPos = 8; // The x position of the top left player inventory slot on our texture
		int yPos = 84; // The y position of the top left player inventory slot on our texture

		// Player slots
		for (int y = 0; y < 3; ++y) {
			for (int x = 0; x < 9; ++x) {
				this.addSlotToContainer(new Slot(player.inventory, x + y * 9 + 9, xPos + x * 18, yPos + y * 18));
			}
		}

		for (int x = 0; x < 9; ++x) {
			this.addSlotToContainer(new Slot(player.inventory, x, xPos + x * 18, yPos + 58));
		}
	}
	

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int fromSlot) {
		ItemStack previous = ItemStack.EMPTY;
		Slot slot = (Slot) this.inventorySlots.get(fromSlot);

		if (slot != null && slot.getHasStack()) {
			ItemStack current = slot.getStack();
			previous = current.copy();

			if (fromSlot < this.handler.getSlots()) {
				// From the furnace inventory to the player's inventory
				if (!this.mergeItemStack(current, this.handler.getSlots(), this.handler.getSlots() + 36, true))
					return ItemStack.EMPTY;
			} else {
				// From the player's inventory to the furnace's inventory
				if (TileEntityFurnace.isItemFuel(current))
					if (!this.mergeItemStack(current, 2, 4, false))
						return ItemStack.EMPTY;
				if (!this.mergeItemStack(current, 0, 4, false))
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

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return player.getPosition().distanceSq(this.te.getPos().add(0.5, 0.5, 0.5)) <= 64;
	}

}
