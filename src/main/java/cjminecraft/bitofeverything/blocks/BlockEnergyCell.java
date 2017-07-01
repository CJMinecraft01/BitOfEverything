package cjminecraft.bitofeverything.blocks;

import cjminecraft.bitofeverything.BitOfEverything;
import cjminecraft.bitofeverything.client.gui.GuiHandler;
import cjminecraft.bitofeverything.handlers.EnumHandler.ChipTypes;
import cjminecraft.bitofeverything.handlers.EnumHandler.EnergyConnectionType;
import cjminecraft.bitofeverything.tileentity.TileEntityEnergyCell;
import cjminecraft.core.energy.EnergyUtils;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class BlockEnergyCell extends BlockMachine {

	public static final PropertyEnum<EnergyConnectionType> NORTH = PropertyEnum.<EnergyConnectionType>create("north",
			EnergyConnectionType.class);
	public static final PropertyEnum<EnergyConnectionType> SOUTH = PropertyEnum.<EnergyConnectionType>create("south",
			EnergyConnectionType.class);
	public static final PropertyEnum<EnergyConnectionType> EAST = PropertyEnum.<EnergyConnectionType>create("east",
			EnergyConnectionType.class);
	public static final PropertyEnum<EnergyConnectionType> WEST = PropertyEnum.<EnergyConnectionType>create("west",
			EnergyConnectionType.class);
	public static final PropertyEnum<EnergyConnectionType> UP = PropertyEnum.<EnergyConnectionType>create("up",
			EnergyConnectionType.class);
	public static final PropertyEnum<EnergyConnectionType> DOWN = PropertyEnum.<EnergyConnectionType>create("down",
			EnergyConnectionType.class);

	public BlockEnergyCell(String unlocalizedName) {
		super(unlocalizedName);
		this.useNeighborBrightness = true;
		this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, ChipTypes.BASIC)
				.withProperty(NORTH, EnergyConnectionType.NONE).withProperty(SOUTH, EnergyConnectionType.NONE)
				.withProperty(EAST, EnergyConnectionType.NONE).withProperty(WEST, EnergyConnectionType.NONE)
				.withProperty(UP, EnergyConnectionType.NONE).withProperty(DOWN, EnergyConnectionType.NONE));
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityEnergyCell(ChipTypes.values()[meta % ChipTypes.values().length]);
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityEnergyCell(state.getValue(TYPE));
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		TileEntityEnergyCell te = (TileEntityEnergyCell) world.getTileEntity(pos);
		IItemHandler handler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		for (int slot = 0; slot < handler.getSlots(); slot++) {
			ItemStack stack = handler.getStackInSlot(slot);
			InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), stack);
		}
		super.breakBlock(world, pos, state);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing heldItem, float side, float hitX, float hitY) {
		if (!worldIn.isRemote) {
			playerIn.openGui(BitOfEverything.instance, GuiHandler.ENERGY_CELL, worldIn, pos.getX(), pos.getY(),
					pos.getZ());
		}
		return true;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { TYPE, NORTH, SOUTH, EAST, WEST, UP, DOWN });
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
		return state.withProperty(NORTH,
				EnergyUtils.getEnergyHolderSupport(world.getTileEntity(pos.offset(EnumFacing.NORTH)),
						EnumFacing.SOUTH) != null
								? EnergyConnectionType.NORMAL
								: EnergyUtils.getEnergyProducerSupport(
										world.getTileEntity(pos.offset(EnumFacing.NORTH)), EnumFacing.SOUTH) != null
												? EnergyConnectionType.IN
												: EnergyUtils.getEnergyConsumerSupport(
														world.getTileEntity(pos.offset(EnumFacing.NORTH)),
														EnumFacing.SOUTH) != null ? EnergyConnectionType.OUT
																: EnergyConnectionType.NONE)
				.withProperty(SOUTH, EnergyUtils.getEnergyHolderSupport(
						world.getTileEntity(pos.offset(EnumFacing.SOUTH)), EnumFacing.NORTH) != null
								? EnergyConnectionType.NORMAL
								: EnergyUtils.getEnergyProducerSupport(
										world.getTileEntity(pos.offset(EnumFacing.SOUTH)), EnumFacing.NORTH) != null
												? EnergyConnectionType.IN
												: EnergyUtils.getEnergyConsumerSupport(
														world.getTileEntity(pos.offset(EnumFacing.SOUTH)),
														EnumFacing.NORTH) != null ? EnergyConnectionType.OUT
																: EnergyConnectionType.NONE)
				.withProperty(EAST, EnergyUtils.getEnergyHolderSupport(world.getTileEntity(pos.offset(EnumFacing.EAST)),
						EnumFacing.WEST) != null
								? EnergyConnectionType.NORMAL
								: EnergyUtils.getEnergyProducerSupport(world.getTileEntity(pos.offset(EnumFacing.EAST)),
										EnumFacing.WEST) != null
												? EnergyConnectionType.IN
												: EnergyUtils.getEnergyConsumerSupport(
														world.getTileEntity(pos.offset(EnumFacing.EAST)),
														EnumFacing.WEST) != null ? EnergyConnectionType.OUT
																: EnergyConnectionType.NONE)
				.withProperty(WEST, EnergyUtils.getEnergyHolderSupport(world.getTileEntity(pos.offset(EnumFacing.WEST)),
						EnumFacing.EAST) != null
								? EnergyConnectionType.NORMAL
								: EnergyUtils.getEnergyProducerSupport(world.getTileEntity(pos.offset(EnumFacing.WEST)),
										EnumFacing.EAST) != null
												? EnergyConnectionType.IN
												: EnergyUtils.getEnergyConsumerSupport(
														world.getTileEntity(pos.offset(EnumFacing.WEST)),
														EnumFacing.EAST) != null ? EnergyConnectionType.OUT
																: EnergyConnectionType.NONE)
				.withProperty(UP,
						EnergyUtils.getEnergyHolderSupport(world.getTileEntity(pos.offset(EnumFacing.UP)),
								EnumFacing.DOWN) != null
										? EnergyConnectionType.NORMAL
										: EnergyUtils.getEnergyProducerSupport(
												world.getTileEntity(pos.offset(EnumFacing.UP)), EnumFacing.DOWN) != null
														? EnergyConnectionType.IN
														: EnergyUtils.getEnergyConsumerSupport(
																world.getTileEntity(pos.offset(EnumFacing.UP)),
																EnumFacing.DOWN) != null ? EnergyConnectionType.OUT
																		: EnergyConnectionType.NONE)
				.withProperty(DOWN,
						EnergyUtils.getEnergyHolderSupport(world.getTileEntity(pos.offset(EnumFacing.DOWN)),
								EnumFacing.UP) != null
										? EnergyConnectionType.NORMAL
										: EnergyUtils.getEnergyProducerSupport(
												world.getTileEntity(pos.offset(EnumFacing.DOWN)), EnumFacing.UP) != null
														? EnergyConnectionType.IN
														: EnergyUtils.getEnergyConsumerSupport(
																world.getTileEntity(pos.offset(EnumFacing.DOWN)),
																EnumFacing.UP) != null ? EnergyConnectionType.OUT
																		: EnergyConnectionType.NONE);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}

}
