package cjminecraft.bitofeverything.container;

import cjminecraft.bitofeverything.tileentity.TileEntityBlockBreaker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerBlockBreaker extends Container {

	private TileEntityBlockBreaker te;

	public ContainerBlockBreaker(IInventory playerInv, TileEntityBlockBreaker te) {
		this.te = te;
		IItemHandler handler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

		this.addSlotToContainer(new SlotItemHandler(handler, 0, 62, 17));
		this.addSlotToContainer(new SlotItemHandler(handler, 1, 80, 17));
		this.addSlotToContainer(new SlotItemHandler(handler, 2, 98, 17));
		this.addSlotToContainer(new SlotItemHandler(handler, 3, 62, 35));
		this.addSlotToContainer(new SlotItemHandler(handler, 4, 80, 35));
		this.addSlotToContainer(new SlotItemHandler(handler, 5, 98, 35));
		this.addSlotToContainer(new SlotItemHandler(handler, 6, 62, 53));
		this.addSlotToContainer(new SlotItemHandler(handler, 7, 80, 53));
		this.addSlotToContainer(new SlotItemHandler(handler, 8, 98, 53));

		int xPos = 8;
		int yPos = 84;

		for (int y = 0; y < 3; ++y) {
			for (int x = 0; x < 9; ++x) {
				this.addSlotToContainer(new Slot(playerInv, x + y * 9 + 9, xPos + x * 18, yPos + y * 18));
			}
		}

		for (int x = 0; x < 9; ++x) {
			this.addSlotToContainer(new Slot(playerInv, x, xPos + x * 18, yPos + 58));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.te.isUseableByPlayer(player);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int fromSlot) {
		ItemStack previous = ItemStack.field_190927_a;
		Slot slot = (Slot) this.inventorySlots.get(fromSlot);

		if (slot != null && slot.getHasStack()) {
			ItemStack current = slot.getStack();
		        previous = current.copy();

		        if (current.func_190916_E() == 0)
		            slot.putStack(ItemStack.field_190927_a);
		        else
		            slot.onSlotChanged();

		        if (current.func_190916_E() == previous.func_190916_E()) //Use func_190916_E to get the stack size
		            return null;
			slot.func_190901_a(playerIn, current); //Use instead of slot.onPickupFromSlot(playerIn, current);
		}
		return previous;
	}

}
