package cjminecraft.bitofeverything.blocks.item;

import java.util.List;

import cjminecraft.core.energy.EnergyUtils;
import cjminecraft.core.energy.compat.ItemBlockEnergy;
import cjminecraft.core.energy.compat.forge.CustomForgeEnergyStorage;
import cjminecraft.core.energy.compat.forge.ForgeEnergyCapabilityProvider;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class ItemBlockMachine extends ItemBlockEnergy {
	
	/**
	 * Default {@link ItemBlock} constructor
	 * @param block The original block
	 */
	public ItemBlockMachine(Block block) {
		super(block, 100000, 1000, 1000);
		if(!(block instanceof IMetaBlockName)) { //Makes sure that the block implements IMetaBlockName
			throw new IllegalArgumentException(String.format("The given Block %s is not an instance of IMetaBlockName!", block.getUnlocalizedName()));
		}
		this.setHasSubtypes(true); //Says the block has meta data
		this.setMaxDamage(0);
	}
	
	/**
	 * Changes the unlocalized name
	 */
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName() + "." + ((IMetaBlockName) this.block).getSpecialName(stack);
	}

	/**
	 * Fixes a bug with not placing the correct variant of the block
	 * THIS IS NEEDED
	 */
	public int getMetadata(int damage) {
		return damage;
	}

}
