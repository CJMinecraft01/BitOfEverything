package cjminecraft.bitofeverything.blocks;

import java.util.Random;

import cjminecraft.bitofeverything.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * This block updates with a redstone signal and changes state based on the nearest player's gamemode
 * @author CJMinecraft
 *
 */
public class BlockGamemodeDetector extends Block {

	/**
	 * The property.
	 * Refer to {@link BlockBreaker} for the other properties
	 */
	public static final PropertyInteger GAMEMODE = PropertyInteger.create("gamemode", 0, 3);
	
	/**
	 * Default constructor
	 * @param unlocalizedName
	 */
	public BlockGamemodeDetector(String unlocalizedName) {
		super(Material.WOOD);
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
		//We don't have a default version of our block
	}
	
	/**
	 * Makes it so that a comparator can get the gamemode and emit it as a redstone signal
	 */
	@Override
	public boolean hasComparatorInputOverride(IBlockState state) {
		return true;
	}
	
	/**
	 * The redstone signal strength when a comparator is connected
	 */
	@Override
	public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos) {
		return state.getValue(GAMEMODE) + 1;
	}
	
	/**
	 * Adds the properties to the block
	 */
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {GAMEMODE});
	}
	
	/**
	 * Gets the correct meta version from the {@link IBlockState}
	 */
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(GAMEMODE);
	}
	
	/**
	 * Gets the correct state from the meta data
	 */
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(GAMEMODE, meta);
	}
	
	/**
	 * Update block when powered.
	 * I recommend using a {@link TileEntity} for this
	 */
	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos p_189540_5_) {
		if(world.isBlockPowered(pos)) {
			EntityPlayer player = world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 8, false);
			if(player != null) {
				if(player.isCreative()) {
					world.setBlockState(pos, this.getDefaultState().withProperty(GAMEMODE, Integer.valueOf(1)), 2);
				}
				else if(player.isSpectator()) {
					world.setBlockState(pos, this.getDefaultState().withProperty(GAMEMODE, Integer.valueOf(3)), 2);
				}
				else if(player.capabilities.allowEdit) {
					world.setBlockState(pos, this.getDefaultState().withProperty(GAMEMODE, Integer.valueOf(0)), 2);
				}
				else {
					world.setBlockState(pos, this.getDefaultState().withProperty(GAMEMODE, Integer.valueOf(2)), 2);
				}
			}
		}
	}

}
