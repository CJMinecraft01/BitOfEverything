package cjminecraft.bitofeverything.items;

import cjminecraft.bitofeverything.Reference;
import net.minecraft.item.ItemSpade;
import net.minecraft.util.ResourceLocation;

/**
 * A simple instance of a spade
 * 
 * @author CJMinecraft
 *
 */
public class ItemModShovel extends ItemSpade {

	/**
	 * Initialise a spade / shovel
	 * 
	 * @param material
	 *            The tool material of the shovel
	 * @param unlocalizedName
	 *            The unlocalized name of the shovel
	 */
	public ItemModShovel(ToolMaterial material, String unlocalizedName) {
		super(material);
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
	}

}
