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
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockGamemodeDetector extends Block {

	public static final PropertyInteger GAMEMODE = PropertyInteger.create("gamemode", 0, 3);
	
	public BlockGamemodeDetector(String unlocalizedName) {
		super(Material.WOOD);
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {GAMEMODE});
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(GAMEMODE);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(GAMEMODE, meta);
	}
	
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
