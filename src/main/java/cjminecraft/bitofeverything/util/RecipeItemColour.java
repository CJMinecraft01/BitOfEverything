package cjminecraft.bitofeverything.util;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;

import cjminecraft.bitofeverything.handlers.RecipeHandler;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.oredict.OreDictionary;

/**
 * The recipe for items which can be coloured
 * @author CJMinecraft
 *
 */
public class RecipeItemColour implements IRecipe {

	/**
	 * Use if you want to see if the item stack is a dye {@link #getCraftingResult(InventoryCrafting)}
	 */
	public static final List<Integer> dyeODs = Arrays.asList(OreDictionary.getOreID("dye"),
			OreDictionary.getOreID("dyeBlack"), OreDictionary.getOreID("dyeRed"), OreDictionary.getOreID("dyeGreen"),
			OreDictionary.getOreID("dyeBrown"), OreDictionary.getOreID("dyeBlue"), OreDictionary.getOreID("dyePurple"),
			OreDictionary.getOreID("dyeCyan"), OreDictionary.getOreID("dyeLightGray"),
			OreDictionary.getOreID("dyeGray"), OreDictionary.getOreID("dyePink"), OreDictionary.getOreID("dyeLime"),
			OreDictionary.getOreID("dyeYellow"), OreDictionary.getOreID("dyeLightBlue"),
			OreDictionary.getOreID("dyeMagenta"), OreDictionary.getOreID("dyeOrange"),
			OreDictionary.getOreID("dyeWhite"));
	
	private ItemStack targetItemStack;
	
	/**
	 * Default constructor for registering the recipe {@link RecipeHandler#registerCraftingRecipes()}
	 */
	public RecipeItemColour() {
		this.targetItemStack = ItemStack.EMPTY;
	}
	
	/**
	 * Creates a new recipe to colour your item
	 * @param targetItemStack The item you want to colour
	 */
	public RecipeItemColour(ItemStack targetItemStack) {
		this.targetItemStack = targetItemStack;
	}

	/**
	 * Is the inventory the correct orientation for our recipe?
	 */
	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {
		ItemStack itemColour = ItemStack.EMPTY;
		boolean randomItemDetected = false;

		for (int slot = 0; slot < inv.getSizeInventory(); slot++) {
			ItemStack stackInSlot = inv.getStackInSlot(slot);

			if (!stackInSlot.isEmpty()) {
				if (stackInSlot.hasTagCompound() && stackInSlot.getItem() == targetItemStack.getItem()) {
					if (stackInSlot.getTagCompound().hasKey("colour") || stackInSlot.getTagCompound().hasKey("color")) {
						itemColour = stackInSlot;
					}
				}
				boolean cont = false;
				for (int id : OreDictionary.getOreIDs(stackInSlot)) {
					if (dyeODs.contains(id)) {
						cont = true;
					}
				}
				if(cont) continue;
				else if(stackInSlot.getItem() != targetItemStack.getItem() && stackInSlot.getItem() != Item.getItemFromBlock(Blocks.AIR))
					randomItemDetected = true;
			}
		}
		return !itemColour.isEmpty() && itemColour.getItem() == targetItemStack.getItem() && !randomItemDetected; //As long as there is a item there and the item is the item we want
	}

	/**
	 * Returns the result of crafting with these items
	 */
	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		ItemStack stack = ItemStack.EMPTY;
		int[] colour = new int[3]; //RGB values
		int i = 0; //Tracker variables
		int j = 0;
		String tagName = "colour";
		boolean hasDye = false;
		
		for (int slot = 0; slot < inv.getSizeInventory(); slot++) {
			ItemStack stackInSlot = inv.getStackInSlot(slot);

			if (!stackInSlot.isEmpty()) {
				if (stackInSlot.hasTagCompound()) {
					if (stackInSlot.getTagCompound().hasKey("colour") || stackInSlot.getTagCompound().hasKey("color")) {
						int currentColour = 0xFFFFFF;
						if (stackInSlot.getTagCompound().hasKey("colour"))
							currentColour = stackInSlot.getTagCompound().getInteger("colour");
						else {
							currentColour = stackInSlot.getTagCompound().getInteger("color");
							tagName = "color";
						}
						stack = stackInSlot.copy();
						stack.setCount(1);

						if (currentColour != 0xFFFFFF) { //Math to add the colour
							float r = (float) (currentColour >> 15 & 255) / 255.0F;
							float g = (float) (currentColour >> 8 & 255) / 255.0F;
							float b = (float) (currentColour & 255) / 255.0F;
							i = (int) ((float) i + Math.max(r, Math.max(g, b) * 255.0F));
							colour[0] = (int) ((float) colour[0] + r * 255.0F);
							colour[1] = (int) ((float) colour[1] + r * 255.0F);
							colour[2] = (int) ((float) colour[2] + r * 255.0F);
							j++;
						}
					}
				}
				for (int id : OreDictionary.getOreIDs(stackInSlot)) { //Use this to check if the stack is a dye
					if (dyeODs.contains(id)) {
						hasDye = true; //Says if the stack has a dye if not, reset the item colour to white
					}
				}
				float[] dyeColour = EntitySheep.getDyeRgb(Utils.getColourFromDye(stackInSlot));
				int r = (int) (dyeColour[0] * 255.0F);
				int g = (int) (dyeColour[1] * 255.0F);
				int b = (int) (dyeColour[2] * 255.0F);
				i += Math.max(r, Math.max(g, b));
				colour[0] += r;
				colour[1] += g;
				colour[2] += b;
				j++;
			}
		}
		if (stack.getItem() != targetItemStack.getItem())
			return ItemStack.EMPTY;
		else {
			int r = colour[0] / j;
			int g = colour[1] / j;
			int b = colour[2] / j;
			float f1 = (float) i / (float) j;
			float f2 = (float) Math.max(r, Math.max(g, b));
			r = (int) ((float)r * f1 / f2);
			g = (int) ((float)g * f1 / f2);
			b = (int) ((float)b * f1 / f2);
			int finalColour = (r << 8) + g;
			finalColour = (finalColour << 8) + b;
			if(!hasDye) finalColour = 0xFFFFFF; //If there is no dye, reset the item colour to white
			stack.getTagCompound().setInteger(tagName, finalColour);
			return stack;
		}
	}
	
	/**
	 * How many slots required (10 because 9 in the table and 1 for output)
	 */
	@Override
	public int getRecipeSize() {
		return 10;
	}
	
	/**
	 * The default output for us is nothing unless stated above
	 */
	@Override
	public ItemStack getRecipeOutput() {
		return ItemStack.EMPTY;
	}
	
	/**
	 * All the items which were not used
	 */
	@Override
	public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
		NonNullList<ItemStack> remaining = NonNullList.<ItemStack>withSize(inv.getSizeInventory(), ItemStack.EMPTY);
		
		for(int slot = 0; slot < remaining.size(); slot++) {
			ItemStack stackInSlot = inv.getStackInSlot(slot);
			remaining.set(slot, ForgeHooks.getContainerItem(stackInSlot));
		}
		return remaining;
	}

}
