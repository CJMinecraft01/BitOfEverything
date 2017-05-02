package cjminecraft.bitofeverything.blocks;

import cjminecraft.bitofeverything.Reference;
import cjminecraft.bitofeverything.init.ModItems;
import net.minecraft.block.BlockCrops;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

/**
 * A crop which will craft to make fabric
 * @author CJMinecraft
 *
 */
public class BlockCotton extends BlockCrops {
	
	/**
	 * Initialize the crop
	 * @param unloclaizedName The unlocalized name of the block
	 */
	public BlockCotton(String unloclaizedName) {
		this.setUnlocalizedName(unloclaizedName);
		this.setRegistryName(new ResourceLocation(Reference.MODID, unloclaizedName));
	}
	
	/**
	 * Get the seed. e.g {@link Items#WHEAT_SEEDS}
	 */
	@Override
	protected Item getSeed() {
		return ModItems.cotton;
	}
	
	/**
	 * Get the crop. e.g. {@link Items#WHEAT}
	 */
	@Override
	protected Item getCrop() {
		return ModItems.cotton;
	}

}
