package cjminecraft.bitofeverything.util;

import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class RecipeClearColour implements IRecipe {

	private ItemStack targetItemStack;
	
	public RecipeClearColour(ItemStack targetItemStack) {
		this.targetItemStack = targetItemStack;
	}
	
	@Override
	public boolean matches(InventoryCrafting inv, World world) {
		boolean randomItemDetected = false;
		boolean stackFound = false;
		for(int slot = 0; slot < inv.getSizeInventory(); slot++) {
			if(inv.getStackInSlot(slot).getItem() == targetItemStack.getItem())
				if(inv.getStackInSlot(slot).hasTagCompound() && (inv.getStackInSlot(slot).getTagCompound().hasKey("colour") || inv.getStackInSlot(slot).getTagCompound().hasKey("color")))
					stackFound = true;
			if(inv.getStackInSlot(slot).getItem() != Item.getItemFromBlock(Blocks.AIR) && inv.getStackInSlot(slot).getItem() != targetItemStack.getItem())
				randomItemDetected = true;
		}
		return stackFound && !randomItemDetected;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		ItemStack toClear = ItemStack.EMPTY;
		for(int slot = 0; slot < inv.getSizeInventory(); slot++)
			if(inv.getStackInSlot(slot).getItem() == targetItemStack.getItem())
				if(inv.getStackInSlot(slot).hasTagCompound() && (inv.getStackInSlot(slot).getTagCompound().hasKey("colour") || inv.getStackInSlot(slot).getTagCompound().hasKey("color")))
					toClear = inv.getStackInSlot(slot).copy();
		if(toClear.getTagCompound().hasKey("colour"))
			toClear.getTagCompound().setInteger("colour", 0xFFFFFF);
		if(toClear.getTagCompound().hasKey("color"))
			toClear.getTagCompound().setInteger("color", 0xFFFFFF);
		return toClear;
	}

	@Override
	public int getRecipeSize() {
		return 10;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return ItemStack.EMPTY;
	}

	@Override
	public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
		return NonNullList.<ItemStack>withSize(inv.getSizeInventory(), ItemStack.EMPTY);
	}

}
