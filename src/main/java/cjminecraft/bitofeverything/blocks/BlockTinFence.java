package cjminecraft.bitofeverything.blocks;

import cjminecraft.bitofeverything.Reference;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;

public class BlockTinFence extends BlockFence {

	public BlockTinFence(String unlocalizedName) {
		super(Material.IRON, Material.IRON.getMaterialMapColor());
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
		this.setHardness(3);
		this.setResistance(20);
		this.useNeighborBrightness = true;
	}

}
