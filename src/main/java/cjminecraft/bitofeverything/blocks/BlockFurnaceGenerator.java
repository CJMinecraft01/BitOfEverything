package cjminecraft.bitofeverything.blocks;

import cjminecraft.bitofeverything.BitOfEverything;
import cjminecraft.bitofeverything.client.gui.GuiHandler;
import cjminecraft.bitofeverything.handlers.EnumHandler.ChipTypes;
import cjminecraft.bitofeverything.tileentity.TileEntityBlockBreaker;
import cjminecraft.bitofeverything.tileentity.TileEntityFurnaceGenerator;
import cjminecraft.core.CJCore;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

/**
 * The block for the furnace generator
 * 
 * @author CJMinecraft
 *
 */
public class BlockFurnaceGenerator extends BlockMachine {

	/**
	 * The direction of the furnace generator
	 */
	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	/**
	 * Whether the generator is active
	 */
	public static final PropertyBool ACTIVATED = PropertyBool.create("activated");

	/**
	 * Initialise the furnace generator
	 * 
	 * @param unlocalizedName
	 *            The unlocalized name of the furnace generator
	 */
	public BlockFurnaceGenerator(String unlocalizedName) {
		super(unlocalizedName);
		this.maxReceive = 0;
		this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, ChipTypes.BASIC)
				.withProperty(FACING, EnumFacing.NORTH).withProperty(ACTIVATED, false));
	}

	/**
	 * For {@link TileEntity} creation
	 */
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return super.createTileEntity(worldIn, getStateFromMeta(meta));
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityFurnaceGenerator(state.getValue(TYPE));
	}

	/**
	 * Create the block state
	 */
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { TYPE, FACING, ACTIVATED });
	}

	/**
	 * Replacement of onBlockPlaced in 1.11.2
	 */
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return this.getDefaultState().withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer))
				.withProperty(ACTIVATED, Boolean.valueOf(false))
				.withProperty(TYPE, getStateFromMeta(meta * EnumFacing.values().length).getValue(TYPE));
	}

	/**
	 * Returns the correct meta for the block I recommend also saving the
	 * EnumFacing to the meta but I haven't
	 */
	@Override
	public int getMetaFromState(IBlockState state) {
		ChipTypes type = (ChipTypes) state.getValue(TYPE);
		EnumFacing facing = (EnumFacing) state.getValue(FACING);
		int meta = type.getID() * EnumFacing.values().length + facing.ordinal();
		return meta;
	}

	/**
	 * Gets the block state from the meta
	 */
	@Override
	public IBlockState getStateFromMeta(int meta) {
		ChipTypes type = ChipTypes.values()[(int) (meta / EnumFacing.values().length) % ChipTypes.values().length];
		EnumFacing facing = EnumFacing.values()[meta % EnumFacing.values().length];
		return this.getDefaultState().withProperty(TYPE, type).withProperty(FACING, facing);
	}

	/**
	 * Makes sure that when you pick block you get the right version of the
	 * block
	 */
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos,
			EntityPlayer player) {
		TileEntity te = world.getTileEntity(pos);
		world.notifyBlockUpdate(pos, state, state, 2);
		if (te == null)
			return new ItemStack(Item.getItemFromBlock(this), 1,
					(int) (getMetaFromState(world.getBlockState(pos)) / EnumFacing.values().length));
		NBTTagCompound nbt = te.getUpdateTag();
		nbt.setInteger("MaxReceive", state.getValue(TYPE).getID() == 0 ? 1000 : 5000);
		nbt.setInteger("MaxExtract", state.getValue(TYPE).getID() == 0 ? 1000 : 5000);
		ItemStack stack = new ItemStack(Item.getItemFromBlock(this), 1,
				(int) (getMetaFromState(world.getBlockState(pos)) / EnumFacing.values().length), nbt);
		stack.setTagCompound(nbt);
		return stack;
	}

	/**
	 * Makes the block drop the right version of the block from meta data
	 */
	@Override
	public int damageDropped(IBlockState state) {
		return (int) (getMetaFromState(state) / EnumFacing.values().length);
	}

	/**
	 * Called when you break the block so that all of the items inside of the
	 * tile entity drop
	 */
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		TileEntityFurnaceGenerator te = (TileEntityFurnaceGenerator) world.getTileEntity(pos);
		IItemHandler handler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		for (int slot = 0; slot < handler.getSlots(); slot++) {
			ItemStack stack = handler.getStackInSlot(slot);
			InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), stack);
		}
		super.breakBlock(world, pos, state);
	}

	/**
	 * Opens our block's gui when the player right clicks on the block
	 */
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing heldItem, float side, float hitX, float hitY) {
		if (!worldIn.isRemote)
			playerIn.openGui(BitOfEverything.instance, GuiHandler.FURNACE_GENERATOR, worldIn, pos.getX(), pos.getY(),
					pos.getZ());
		return true;
	}

}
