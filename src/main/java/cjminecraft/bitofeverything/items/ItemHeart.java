package cjminecraft.bitofeverything.items;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;

import cjminecraft.bitofeverything.Reference;
import cjminecraft.bitofeverything.init.ModItems;
import cjminecraft.bitofeverything.util.Utils;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;

/**
 * This item heals the player by one heart when right clicked
 * @author CJMinecraft
 *
 */
public class ItemHeart extends Item {
	
	/**
	 * As with every item
	 */
	public ItemHeart(String unlocalizedName) {
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
	}
	
	/**
	 * Increases the player health when right clicked if they aren't at full health
	 */
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		if(player.getHealth() < player.getMaxHealth()) {
			player.heal(2); //Heals them 2 HP or 1 heart
			player.inventory.decrStackSize(player.inventory.currentItem, 1); //NEW 1.11
		}
		return super.onItemRightClick(world, player, hand);
	}
	
	/**
	 * Adds a tool tip to the item
	 */
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		super.addInformation(stack, playerIn, tooltip, advanced);
		tooltip.add(TextFormatting.AQUA + Utils.getLang().localize("heart.tooltip"));
	}

}
