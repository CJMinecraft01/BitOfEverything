package cjminecraft.bitofeverything.blocks;

import java.util.ArrayList;
import java.util.List;

import cjminecraft.bitofeverything.Reference;
import cjminecraft.bitofeverything.init.ModBlocks;
import cjminecraft.bitofeverything.init.ModItems;
import cjminecraft.bitofeverything.tileentity.TileEntityCanvas;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * A block which can be coloured when clicked on by a paint brush
 * @author CJMinecraft
 *
 */
public class BlockCanvas extends BlockContainer implements ITileEntityProvider {

	/**
	 * Default block constructor
	 * @param unlocalizedName The unlocalised name of the block
	 */
	public BlockCanvas(String unlocalizedName) {
		super(Material.CLOTH);
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
		this.setHardness(1);
		this.setResistance(5);
		this.isBlockContainer = true; //Says it is a block container
	}
	
	/**
	 * This will change the blocks colour when clicked on by a paint brush
	 */
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(player.getHeldItem(hand).getItem() == ModItems.paintBrush) {
			TileEntityCanvas canvas = (TileEntityCanvas) world.getTileEntity(pos);
			canvas.setColour(player.getHeldItem(hand).getTagCompound().getInteger("colour"));
			world.markBlockRangeForRenderUpdate(pos, pos);
		}
		return false;
	}
	
	/**
	 * Says what colour the block is
	 */
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
		if(stack.hasTagCompound())
			if(stack.getTagCompound().hasKey("colour"))
				tooltip.add(TextFormatting.GRAY + I18n.format(getUnlocalizedName() + ".tooltip", String.format("#%06X", (0xFFFFFF & stack.getTagCompound().getInteger("colour")))));
	}
	
	/**
	 * Makes it so that when you pick block, you get the correct block
	 */
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos,
			EntityPlayer player) {
		TileEntityCanvas canvas = (TileEntityCanvas) world.getTileEntity(pos);
		ItemStack stack = new ItemStack(ModBlocks.canvas);
		NBTTagCompound nbt = new NBTTagCompound();
		if(canvas != null) 
			nbt.setInteger("colour", canvas.getColour());
		else
			nbt.setInteger("colour", 0xFFFFFF);
		stack.setTagCompound(nbt);
		return stack;
	}
	
	/**
	 * Needed to make sure our block is rendered correctly
	 */
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	/**
	 * Create the tile entity
	 */
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityCanvas();
	}
	
	/**
	 * Also create the tile entity
	 */
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityCanvas();
	}
	
	/**
	 * When placed it will update the colour to that of the tile entity which the tile entity inherits from the item block
	 */
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		if(!world.isRemote) {
			TileEntityCanvas canvas = (TileEntityCanvas) world.getTileEntity(pos);
			canvas.setColour(stack.getTagCompound().getInteger("colour"));
		}
		world.markBlockRangeForRenderUpdate(pos, pos); //Update the blocks render
	}
	
	/**
	 * Not fully functioning. Works with <code> /setblock <x> <y> <z> air 0 destroy </code> but not when breaking normally with your hand
	 */
	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		List<ItemStack> drops = new ArrayList<ItemStack>();
		TileEntityCanvas canvas = (TileEntityCanvas) world.getTileEntity(pos);
		ItemStack stack = new ItemStack(ModBlocks.canvas);
		NBTTagCompound nbt = new NBTTagCompound();
		if(canvas != null) //When you break it with your hand
			nbt.setInteger("colour", canvas.getColour());
		else //When you break it with a command
			nbt.setInteger("colour", 0xFFFFFF);
		stack.setTagCompound(nbt);
		drops.add(stack);
		return drops;
	}

}
