package cjminecraft.bitofeverything.blocks;

import cjminecraft.bitofeverything.Reference;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;

public class BlockTinStairs extends BlockStairs {
	
	public BlockTinStairs(String unlocalizedName, IBlockState state) {
		super(state);
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
		this.setHardness(3);
		this.setResistance(20);
		this.useNeighborBrightness = true;
	}

}
