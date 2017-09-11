package cjminecraft.bitofeverything.blocks;

import java.util.ArrayList;
import java.util.List;

import cjminecraft.bitofeverything.Reference;
import cjminecraft.bitofeverything.blocks.item.IMetaBlockName;
import cjminecraft.bitofeverything.blocks.item.ItemBlockMachine;
import cjminecraft.bitofeverything.handlers.EnumHandler.ChipTypes;
import cjminecraft.bitofeverything.init.ModItems;
import cjminecraft.bitofeverything.items.ItemChip;
import cjminecraft.bitofeverything.util.Utils;
import cjminecraft.core.CJCore;
import cjminecraft.core.energy.CustomForgeEnergyStorage;
import cjminecraft.core.energy.EnergyUtils;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;

/**
 * Class to be used by all machines
 * 
 * @author CJMinecraft
 *
 */
public abstract class BlockMachine extends BlockContainer implements IMetaBlockName, ITileEntityProvider {

	protected int energy = 0;
	protected int capacity = 100000;
	protected int maxReceive = 1000, maxExtract = 1000;

	/**
	 * The type which indicates the tier of block. Basic and advanced versions
	 * of each machine
	 */
	public static final PropertyEnum<ChipTypes> TYPE = PropertyEnum.<ChipTypes>create("type", ChipTypes.class);

	/**
	 * Sets the default state, hardness and resistance for machines
	 * 
	 * @param unlocalizedName
	 *            The unlocalized name of the block
	 */
	public BlockMachine(String unlocalizedName) {
		super(Material.IRON); // The blocks material
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
		this.setHardness(3);
		this.setResistance(20);
		this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, ChipTypes.BASIC));
		this.isBlockContainer = true;
	}

	public BlockMachine setEnergy(int energy, int capacity, int maxReceive, int maxExtract) {
		this.energy = energy;
		this.capacity = capacity;
		this.maxReceive = maxReceive;
		this.maxExtract = maxExtract;
		return this;
	}

	/**
	 * Makes sure the block works with meta data
	 */
	@Override
	public String getSpecialName(ItemStack stack) {
		return ChipTypes.values()[stack.getItemDamage() % ChipTypes.values().length].getName();
	}

	/**
	 * Makes sure that in the creative tab there are items for all the different
	 * chip types
	 */
	@Override
	public void getSubBlocks(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> list) {
		for (int i = 0; i < ChipTypes.values().length; i++) {
			NBTTagCompound nbt = new NBTTagCompound();
			CustomForgeEnergyStorage storage = new CustomForgeEnergyStorage(i == 0 ? this.capacity : this.capacity * 5,
					i == 0 ? 1000 : 5000, i == 0 ? 1000 : 5000,
					i == 0 ? this.energy : this.energy * 5);
			storage.writeToNBT(nbt);
			ItemStack stack = new ItemStack(itemIn, 1, i, nbt);
			stack.setTagCompound(nbt);
			list.add(stack);
			nbt = new NBTTagCompound();
			storage = new CustomForgeEnergyStorage(i == 0 ? this.capacity : this.capacity * 5,
					i == 0 ? 1000 : 5000, i == 0 ? 1000 : 5000,
					i == 0 ? this.capacity : this.capacity * 5);
			storage.writeToNBT(nbt);
			stack = new ItemStack(itemIn, 1, i, nbt);
			stack.setTagCompound(nbt);
			list.add(stack);
		}
	}

	/**
	 * Makes sure that when you pick block you get the correct block
	 */
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos,
			EntityPlayer player) {
		TileEntity te = world.getTileEntity(pos);
		world.notifyBlockUpdate(pos, state, state, 2);
		if (te == null)
			return new ItemStack(Item.getItemFromBlock(this), 1, (int) (getMetaFromState(world.getBlockState(pos))));
		NBTTagCompound nbt = te.getUpdateTag();
		nbt.setInteger("MaxReceive", state.getValue(TYPE).getID() == 0 ? 1000 : 5000);
		nbt.setInteger("MaxExtract", state.getValue(TYPE).getID() == 0 ? 1000 : 5000);
		ItemStack stack = new ItemStack(Item.getItemFromBlock(this), 1,
				(int) (getMetaFromState(world.getBlockState(pos))), nbt);
		stack.setTagCompound(nbt);
		return stack;
	}

	/**
	 * Makes sure the block drops the correct damage. If the block has the
	 * {@link PropertyDirection} then this needs to be overridden
	 */
	@Override
	public int damageDropped(IBlockState state) {
		return getMetaFromState(state);
	}

	/**
	 * Returns the meta data from the blockstate. If the block has the
	 * {@link PropertyDirection} then this needs to be overridden
	 */
	@Override
	public int getMetaFromState(IBlockState state) {
		return ((ChipTypes) state.getValue(TYPE)).getID();
	}

	/**
	 * Returns the state from the meta data. If the block has the
	 * {@link PropertyDirection} then this needs to be overridden
	 */
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(TYPE, ChipTypes.values()[meta % ChipTypes.values().length]);
	}

	/**
	 * Says the block has multiple types. If the block has the
	 * {@link PropertyDirection} then this needs to be overridden
	 */
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { TYPE });
	}

	/**
	 * Called by the {@link ItemChip} meaning that when you right click a
	 * machine with a chip, the machine will upgrade
	 * 
	 * @param world
	 *            The world the block is in
	 * @param player
	 *            The player who right clicked
	 * @param hand
	 *            The player's hand (in game) which did the clicking
	 * @param pos
	 *            The position of the block
	 * @param stack
	 *            The {@link ItemStack} in the player's hand
	 */
	public void updateMachineTier(World world, EntityPlayer player, EnumHand hand, BlockPos pos, ItemStack stack) {
		if (stack.getItem() == ModItems.chip) {
			ChipTypes newType = ChipTypes.values()[stack.getItemDamage() % ChipTypes.values().length];
			ChipTypes currentType = (ChipTypes) world.getBlockState(pos).getValue(TYPE);
			IBlockState newState = world.getBlockState(pos).withProperty(TYPE, newType);
			if (newType.getID() > currentType.getID()) {
				world.setBlockState(pos, newState, 2);
			}
			ItemStack newStack = stack.copy();
			newStack.shrink(1);
			player.setHeldItem(hand, newStack);
			if (player.getHeldItem(hand).getCount() <= 0)
				player.setHeldItem(hand, ItemStack.EMPTY); // Sets the stack to
															// nothing if there
															// stack size is 0
															// or smaller
			// stack.shrink(1); //NEW IN 1.11.2 - Before it would be
			// .stackSize--
		}
	}

	/**
	 * Says that the block should use a model to be rendered
	 */
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	/**
	 * Default method to create the tile entity. You probably want to override
	 * this
	 */
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return null;
	}

	/**
	 * Default method to create the tile entity. You probably want to override
	 * this
	 */
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return null;
	}

	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		List<ItemStack> drops = new ArrayList<ItemStack>();
		ItemStack stack = new ItemStack(new ItemBlockMachine(this));
		TileEntity te = world.getTileEntity(pos);
		if (te != null && EnergyUtils.hasSupport(te, null)) {
			stack = new ItemStack(new ItemBlockMachine(this), 1, world.getBlockState(pos).getValue(TYPE).getID(),
					te.getUpdateTag());
			stack.setTagCompound(te.getUpdateTag());
		}
		drops.add(stack);
		return drops;
	}

	@Override
	public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te,
			ItemStack stack) {
		super.harvestBlock(world, player, pos, state, te, stack);
		world.setBlockToAir(pos);
		world.removeTileEntity(pos);
	}

	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player,
			boolean willHarvest) {
		if (willHarvest)
			return true;
		return super.removedByPlayer(state, world, pos, player, willHarvest);
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		super.onBlockPlacedBy(world, pos, state, placer, stack);
		TileEntity te = world.getTileEntity(pos);
		if (te != null && EnergyUtils.hasSupport(te, null) && stack.hasTagCompound()) {
			if (EnergyUtils.getEnergySupport(te, null).getContainer(te, null) instanceof CustomForgeEnergyStorage) {
				CustomForgeEnergyStorage storage = (CustomForgeEnergyStorage) EnergyUtils.getEnergySupport(te, null)
						.getContainer(te, null);
				storage.readFromNBT(stack.getTagCompound());
				storage.setMaxReceive(state.getValue(TYPE).getID() == 0  ? this.maxReceive : this.maxReceive * 5);
				storage.setMaxExtract(state.getValue(TYPE).getID() == 0  ? this.maxExtract : this.maxExtract * 5);
				world.notifyBlockUpdate(pos, state, state, 2);
			}
		}
	}

}
