package cjminecraft.bitofeverything.items;

import cjminecraft.bitofeverything.Reference;
import net.minecraft.item.ItemHoe;
import net.minecraft.util.ResourceLocation;

/**
 * A simple instance of a hoe
 * 
 * @author CJMinecraft
 *
 */
public class ItemModHoe extends ItemHoe {

	/**
	 * Initialise a hoe
	 * 
	 * @param material
	 *            The tool material of the hoe
	 * @param unlocalizedName
	 *            The unlocalized name of the hoe
	 */
	public ItemModHoe(ToolMaterial material, String unlocalizedName) {
		super(material);
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
	}

}
