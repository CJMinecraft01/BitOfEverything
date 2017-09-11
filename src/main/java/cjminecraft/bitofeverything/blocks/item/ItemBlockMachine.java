package cjminecraft.bitofeverything.blocks.item;

import java.text.NumberFormat;
import java.util.List;

import cjminecraft.core.CJCore;
import cjminecraft.core.config.CJCoreConfig;
import cjminecraft.core.energy.CustomForgeEnergyStorage;
import cjminecraft.core.energy.EnergyUtils;
import cjminecraft.core.energy.ForgeEnergyCapabilityProvider;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class ItemBlockMachine extends ItemBlockMeta {

	public ItemBlockMachine(Block block) {
		super(block);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		if (EnergyUtils.hasSupport(stack, null)) {
			if (CJCoreConfig.ENERGY_BAR_SIMPLIFY_ENERGY) {
				tooltip.add(EnergyUtils.getEnergyAsString(
						EnergyUtils.getEnergyStored(stack, null, CJCoreConfig.DEFAULT_ENERGY_UNIT),
						CJCoreConfig.DEFAULT_ENERGY_UNIT)
						+ (CJCoreConfig.ENERGY_BAR_SHOW_CAPACITY ? " / " + EnergyUtils.getEnergyAsString(
								EnergyUtils.getCapacity(stack, null, CJCoreConfig.DEFAULT_ENERGY_UNIT),
								CJCoreConfig.DEFAULT_ENERGY_UNIT) : ""));
			} else {
				tooltip.add(
						NumberFormat.getInstance()
								.format(EnergyUtils.getEnergyStored(stack, null, CJCoreConfig.DEFAULT_ENERGY_UNIT))
								+ " "
								+ CJCoreConfig.DEFAULT_ENERGY_UNIT
										.getSuffix()
								+ (CJCoreConfig.ENERGY_BAR_SHOW_CAPACITY ? " / "
										+ NumberFormat.getInstance().format(
												EnergyUtils.getCapacity(stack, null, CJCoreConfig.DEFAULT_ENERGY_UNIT))
										+ " " + CJCoreConfig.DEFAULT_ENERGY_UNIT.getSuffix() : ""));
			}
		}
	}

	@Override
	public int getRGBDurabilityForDisplay(ItemStack stack) {
		return EnergyUtils.getEnergyRGBDurabilityForDisplay(stack);
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		return EnergyUtils.getEnergyDurabilityForDisplay(stack);
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return EnergyUtils.hasSupport(stack, null);
	}
	
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		if(nbt != null && nbt.hasKey("Energy") && nbt.hasKey("Capacity") && nbt.hasKey("MaxReceive") && nbt.hasKey("MaxExtract"))
			return new ForgeEnergyCapabilityProvider(stack, nbt);
		return new ForgeEnergyCapabilityProvider(stack, 0, 0, 0, 0);
	}
	
	@Override
	public ItemStack getDefaultInstance() {
		NBTTagCompound nbt = new ItemStack(this).serializeNBT();
		CustomForgeEnergyStorage storage = new CustomForgeEnergyStorage(100000, 1000, 1000, 0);
		storage.writeToNBT(nbt);
		return new ItemStack(nbt);
	}

}
