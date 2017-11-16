package cjminecraft.bitofeverything.items;

import cjminecraft.bitofeverything.Reference;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.util.ResourceLocation;

/**
 * A simple instance of a pickaxe
 * 
 * @author CJMinecraft
 *
 */
public class ItemModPickaxe extends ItemPickaxe {

	/**
	 * Initialise a new pickaxe
	 * 
	 * @param material
	 *            The tool material of the pickaxe
	 * @param unlocalizedName
	 *            The unlocalized name of the pickaxe
	 */
	public ItemModPickaxe(ToolMaterial material, String unlocalizedName) {
		super(material);
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
	}

}
