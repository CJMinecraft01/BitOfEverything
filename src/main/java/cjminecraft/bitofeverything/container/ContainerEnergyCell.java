package cjminecraft.bitofeverything.container;

import cjminecraft.bitofeverything.container.slots.SlotEnergyItem;
import cjminecraft.bitofeverything.tileentity.TileEntityEnergyCell;
import cjminecraft.core.energy.EnergyUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class ContainerEnergyCell extends Container {

	private TileEntityEnergyCell te;
	private IItemHandler handler;

	public ContainerEnergyCell(IInventory playerInv, TileEntityEnergyCell te) {
		this.te = te;
		this.handler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

		//Our tile entity slots
		this.addSlotToContainer(new SlotEnergyItem(handler, 0, 44, 35));
		this.addSlotToContainer(new SlotEnergyItem(handler, 1, 116, 35));
		
		// The player's inventory slots
		int xPos = 8; // The x position of the top left player inventory slot on our texture
		int yPos = 84; // The y position of the top left player inventory slot on our texture

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

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return player.getDistanceSq(this.te.getPos().add(0.5, 0.5, 0.5)) <= 64;
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int fromSlot) {
		ItemStack previous = ItemStack.EMPTY;
		Slot slot = (Slot) this.inventorySlots.get(fromSlot);
		
		if (slot != null && slot.getHasStack()) {
			ItemStack current = slot.getStack();
			previous = current.copy();
			
			if(fromSlot < this.handler.getSlots()) {
				//From the energy cell inventory to the player's inventory
				if(!this.mergeItemStack(current, handler.getSlots(), handler.getSlots() + 36, true))
					return ItemStack.EMPTY;
			} else {
				//From the player's inventory to the block breaker's inventory
				if(EnergyUtils.hasSupport(current, null))
					if(!this.mergeItemStack(current, 0, handler.getSlots(), false))
						return ItemStack.EMPTY;
			}
			
			if(current.getCount() == 0)
				slot.putStack(ItemStack.EMPTY);
			else
				slot.onSlotChanged();
			
			if(current.getCount() == previous.getCount())
				return ItemStack.EMPTY;
			slot.onTake(playerIn, current);
		}
		return previous;
	}

}
