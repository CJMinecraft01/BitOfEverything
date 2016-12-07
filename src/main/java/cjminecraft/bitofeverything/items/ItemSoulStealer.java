package cjminecraft.bitofeverything.items;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;

import cjminecraft.bitofeverything.Reference;
import cjminecraft.bitofeverything.events.SoulStealerEvents;
import cjminecraft.bitofeverything.util.Utils;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.util.EnumHelper;

/**
 * This is a custom tool which when you hit a mob causes them to drop a {@link ItemHeart}
 * Refer to {@link SoulStealerEvents}
 * @author CJMinecraft
 *
 */
public class ItemSoulStealer extends ItemTool {

	/**
	 * Says that the tool is not effective on any blocks
	 */
	private static final Set<Block> EFFECTIVE_BLOCKS = Sets.newHashSet(new Block[] {});
	
	public ItemSoulStealer(String unlocalizedName) {
		super(EnumHelper.addToolMaterial(Reference.MODID + ":soul_stealer", 0, 100, 0, 1, 0), EFFECTIVE_BLOCKS);
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
	}
	
	/**
	 * Tool tip
	 */
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		super.addInformation(stack, playerIn, tooltip, advanced);
		tooltip.add(TextFormatting.BLUE + Utils.getLang().localize("soul_stealer.tooltip"));
	}

}
