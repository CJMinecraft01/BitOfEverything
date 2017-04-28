package cjminecraft.bitofeverything.blocks;

import cjminecraft.bitofeverything.Reference;
import cjminecraft.bitofeverything.init.ModItems;
import net.minecraft.block.BlockCrops;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class BlockCotton extends BlockCrops {
	
	public BlockCotton(String unloclaizedName) {
		this.setUnlocalizedName(unloclaizedName);
		this.setRegistryName(new ResourceLocation(Reference.MODID, unloclaizedName));
	}
	
	@Override
	protected Item getSeed() {
		return ModItems.cotton;
	}
	
	@Override
	protected Item getCrop() {
		return ModItems.cotton;
	}

}
