package cjminecraft.bitofeverything.blocks;

import java.util.List;
import java.util.Random;

import cjminecraft.bitofeverything.Reference;
import cjminecraft.bitofeverything.blocks.item.IMetaBlockName;
import cjminecraft.bitofeverything.handlers.EnumHandler.ChipTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * This is where the magic happens.
 * Our blocks details and information is here
 * @author CJMinecraft
 *
 */
public class BlockBreaker extends Block implements IMetaBlockName {

	/**
	 * The different properties our block can have.
	 * For {@link PropertyInteger} refer to {@link BlockGamemodeDetector}
	 */
	public static final PropertyEnum TYPE = PropertyEnum.create("type", ChipTypes.class);
	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	public static final PropertyBool ACTIVATED = PropertyBool.create("activated");
	
	/**
	 * Default block constructor
	 * @param unlocalizedName The block's unlocalized name
	 */
	public BlockBreaker(String unlocalizedName) {
		super(Material.IRON);
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
		//Sets the default version of the block
		this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, ChipTypes.BASIC).withProperty(FACING, EnumFacing.NORTH).withProperty(ACTIVATED, Boolean.valueOf(false)));
	}
	
	/**
	 * Adds the properties to our block
	 */
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {TYPE,FACING,ACTIVATED});
	}
	
	/**
	 * Update the block if it is powered. I recommend using a {@link TileEntity} for this
	 */
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos p_189540_5_) {
		if(worldIn.isBlockPowered(pos)) {
			worldIn.setBlockState(pos, this.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(ACTIVATED, Boolean.valueOf(true)).withProperty(TYPE, state.getValue(TYPE)), 2);
		}
		else {
			worldIn.setBlockState(pos, this.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(ACTIVATED, Boolean.valueOf(false)).withProperty(TYPE, state.getValue(TYPE)), 2);
		}
		worldIn.scheduleBlockUpdate(pos, blockIn, 2, 0);
	}
	
	/**
	 * Says redstone can connect
	 */
	@Override
	public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return side != EnumFacing.UP || side != EnumFacing.DOWN; //Says that as long as its not connected on top or bottom it will connect
	}
	
	/**
	 * Places the block with the correct orientation
	 */
	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ,
			int meta, EntityLivingBase placer) {
		worldIn.scheduleBlockUpdate(pos, worldIn.getBlockState(pos).getBlock(), 2, 0); //Updated BlockPistonFace.getFacingFromEntity to EnumFacing.func_190914_a 1.11 fix
		return this.getDefaultState().withProperty(FACING, EnumFacing.func_190914_a(pos, placer)).withProperty(ACTIVATED, Boolean.valueOf(false)).withProperty(TYPE, getStateFromMeta(meta).getValue(TYPE));
	}
	
	/**
	 * Returns the correct meta for the block
	 * I recommend also saving the EnumFacing to the meta but I haven't
	 * If you don't, upon world reload the block will always face north
	 */
	@Override
	public int getMetaFromState(IBlockState state) {
		ChipTypes type = (ChipTypes) state.getValue(TYPE);
		return type.getID();
	}
	
	/**
	 * Gets the block state from the meta
	 */
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(TYPE, ChipTypes.values()[meta]);
	}
	
	/**
	 * Gets each variant of the block.
	 * Refer to {@link ChipTypes}
	 */
	@Override
	public void getSubBlocks(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> list) {
		for(int i = 0; i < ChipTypes.values().length; i++) {
			list.add(new ItemStack(itemIn, 1, i));
		}
	}

	/**
	 * From {@link IMetaBlockName}
	 */
	@Override
	public String getSpecialName(ItemStack stack) {
		return ChipTypes.values()[stack.getItemDamage()].getName();
	}
	
	/**
	 * Makes sure that when you pick block you get the right version of the block
	 */
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos,
			EntityPlayer player) {
		return new ItemStack(Item.getItemFromBlock(this), 1, getMetaFromState(world.getBlockState(pos)));
	}
	
	/**
	 * Makes the block drop the right version of the block from meta data
	 */
	@Override
	public int damageDropped(IBlockState state) {
		return getMetaFromState(state);
	}
	
}
