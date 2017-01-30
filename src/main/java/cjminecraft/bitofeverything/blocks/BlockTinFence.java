package cjminecraft.bitofeverything.blocks;

import cjminecraft.bitofeverything.Reference;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;

/**
 * A tin version of a fence
 * @author CJMinecraft
 *
 */
public class BlockTinFence extends BlockFence {

	public BlockTinFence(String unlocalizedName) {
		super(Material.IRON, Material.IRON.getMaterialMapColor()); //The block fence class wants us to also put in the materials map colour
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
		this.setHardness(3);
		this.setResistance(20);
		this.useNeighborBrightness = true;
	}

}
