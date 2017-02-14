package cjminecraft.bitofeverything.blocks;

import cjminecraft.bitofeverything.Reference;
import net.minecraft.block.BlockPressurePlateWeighted;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * A block which acts like a pressure plate
 * @author CJMinecraft
 *
 */
public class BlockTinPressurePlate extends BlockPressurePlateWeighted {

	/**
	 * Default constructor
	 * @param unlocalizedName The unlocalized name of the block
	 */
	public BlockTinPressurePlate(String unlocalizedName) {
		super(Material.IRON, 10);
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
		this.setHardness(3);
		this.setResistance(20);
	}
	
	/**
	 * Change this if you want to emit a redstone signal different to that of a weighted pressure plate
	 */
	@Override
	protected int computeRedstoneStrength(World worldIn, BlockPos pos) {
		return super.computeRedstoneStrength(worldIn, pos);
	}
	
	/**
	 * Change this to play your own click on sound
	 */
	@Override
	protected void playClickOffSound(World worldIn, BlockPos pos) {
		super.playClickOffSound(worldIn, pos);
	}
	
	/**
	 * Change this to play your own click off sound
	 */
	@Override
	protected void playClickOnSound(World worldIn, BlockPos color) {
		super.playClickOnSound(worldIn, color);
	}

}
