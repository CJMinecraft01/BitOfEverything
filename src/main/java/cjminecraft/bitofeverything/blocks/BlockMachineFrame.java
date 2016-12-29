package cjminecraft.bitofeverything.blocks;

import cjminecraft.bitofeverything.Reference;
import cjminecraft.bitofeverything.blocks.item.IMetaBlockName;
import cjminecraft.bitofeverything.handlers.EnumHandler.ChipTypes;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

/**
 * Simple instance of the {@link BlockMachine}
 * Used for crafting only
 * I have this class just in case I want to make it have a tooltip
 * @author CJMinecraft
 *
 */
public class BlockMachineFrame extends BlockMachine {
	
	/**
	 * Default constructor
	 * @param unlocalizedName The block's unlocalizedName
	 */
	public BlockMachineFrame(String unlocalizedName) {
		super(unlocalizedName);
	}

}
