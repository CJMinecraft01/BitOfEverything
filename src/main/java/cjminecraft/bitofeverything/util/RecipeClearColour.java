package cjminecraft.bitofeverything.util;

import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

/**
 * A custom recipe which allows the player to clear the colour (which it gets
 * from {@link RecipeItemColour}) of the {@link ItemStack} provided and default
 * the colour to white
 * 
 * @author CJMinecraft
 *
 */
public class RecipeClearColour implements IRecipe {

	/**
	 * The {@link ItemStack} to reset the colour
	 */
	private ItemStack targetItemStack;

	/**
	 * A custom recipe which allows the player to clear the colour of the stack
	 * 
	 * @param targetItemStack
	 *            The {@link ItemStack} which will be able to clear its colour
	 */
	public RecipeClearColour(ItemStack targetItemStack) {
		this.targetItemStack = targetItemStack;
	}

	/**
	 * Does the inventory match what is required?
	 */
	@Override
	public boolean matches(InventoryCrafting inv, World world) {
		boolean randomItemDetected = false;
		boolean stackFound = false;
		for (int slot = 0; slot < inv.getSizeInventory(); slot++) {
			if (inv.getStackInSlot(slot).getItem() == targetItemStack.getItem())
				if (inv.getStackInSlot(slot).hasTagCompound()
						&& (inv.getStackInSlot(slot).getTagCompound().hasKey("colour")
								|| inv.getStackInSlot(slot).getTagCompound().hasKey("color")))
					stackFound = true;
			if (inv.getStackInSlot(slot).getItem() != Item.getItemFromBlock(Blocks.AIR)
					&& inv.getStackInSlot(slot).getItem() != targetItemStack.getItem())
				randomItemDetected = true;
		}
		return stackFound && !randomItemDetected;
	}

	/**
	 * Get the crafting result. I.e. the blank coloured item
	 */
	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		ItemStack toClear = ItemStack.EMPTY;
		for (int slot = 0; slot < inv.getSizeInventory(); slot++)
			if (inv.getStackInSlot(slot).getItem() == targetItemStack.getItem())
				if (inv.getStackInSlot(slot).hasTagCompound()
						&& (inv.getStackInSlot(slot).getTagCompound().hasKey("colour")
								|| inv.getStackInSlot(slot).getTagCompound().hasKey("color")))
					toClear = inv.getStackInSlot(slot).copy();
		if (toClear.getTagCompound().hasKey("colour"))
			toClear.getTagCompound().setInteger("colour", 0xFFFFFF);
		if (toClear.getTagCompound().hasKey("color"))
			toClear.getTagCompound().setInteger("color", 0xFFFFFF);
		return toClear;
	}

	/**
	 * How many slots are involved? 10 because the output classes as a slot
	 */
	@Override
	public int getRecipeSize() {
		return 10;
	}

	/**
	 * The recipe output is nothing
	 */
	@Override
	public ItemStack getRecipeOutput() {
		return ItemStack.EMPTY;
	}

	/**
	 * Get the remaining items (which is none)
	 */
	@Override
	public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
		return NonNullList.<ItemStack>withSize(inv.getSizeInventory(), ItemStack.EMPTY);
	}

}
