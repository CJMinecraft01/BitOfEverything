package cjminecraft.bitofeverything.items;

import cjminecraft.bitofeverything.Reference;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

/**
 * Basic item
 * @author CJMinecraft
 *
 */
public class ItemTinIngot extends Item {
	
	public ItemTinIngot(String unlocalizedName, String registryName) {
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Reference.MODID, registryName));
	}

}
