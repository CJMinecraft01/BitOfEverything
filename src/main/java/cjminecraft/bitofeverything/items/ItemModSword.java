package cjminecraft.bitofeverything.items;

import cjminecraft.bitofeverything.Reference;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ResourceLocation;

/**
 * A simple instance of a sword class
 * 
 * @author CJMinecraft
 *
 */
public class ItemModSword extends ItemSword {

	/**
	 * Initialise a custom sword
	 * 
	 * @param material
	 *            The tool material of the sword
	 * @param unlocalizedName
	 *            The unlocalized name of the sword
	 */
	public ItemModSword(ToolMaterial material, String unlocalizedName) {
		super(material);
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
	}

}
