package cjminecraft.bitofeverything.events;

import cjminecraft.bitofeverything.init.ModBlocks;
import cjminecraft.bitofeverything.init.ModItems;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Handles all of our tooltip events
 * @author CJMinecraft
 *
 */
public class TooltipEvents {
	
	/**
	 * Used to make sure every items have the correct tooltip
	 * @param event The tooltip event
	 */
	@SubscribeEvent
	public void onEvent(ItemTooltipEvent event) {
		ItemStack stack = event.getItemStack();
		if(stack.getItem() == ModItems.paintBrush || stack.getItem() == Item.getItemFromBlock(ModBlocks.canvas)) { //For updating the colour tag of paint brushes if they don't have the colour tag
			boolean setTag = false;
			if(!stack.hasTagCompound())
				setTag = true;
			else if(!stack.getTagCompound().hasKey("colour"))
				setTag = true;
			if(setTag) {
				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setInteger("colour", 0xFFFFFF);
				stack.setTagCompound(nbt);
			}
			if(event.getToolTip().size() == 0) { //Make sure they have the tooltip
				event.getToolTip().add(TextFormatting.GRAY + I18n.format(stack.getItem().getUnlocalizedName() + ".tooltip", (0xFFFFFF & stack.getTagCompound().getInteger("colour"))));
			}
		}
	}

}
