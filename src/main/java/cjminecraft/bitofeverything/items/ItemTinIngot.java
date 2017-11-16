package cjminecraft.bitofeverything.items;

import cjminecraft.bitofeverything.Reference;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

/**
 * A basic item
 * 
 * @author CJMinecraft
 *
 */
public class ItemTinIngot extends Item {

	/**
	 * Initialise a simple item
	 * 
	 * @param unlocalizedName
	 *            The unlocalized name of the item
	 * @param registryName
	 *            The registry name of the item
	 */
	public ItemTinIngot(String unlocalizedName, String registryName) {
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Reference.MODID, registryName));
	}

}
