package cjminecraft.bitofeverything.items;

import cjminecraft.bitofeverything.Reference;
import net.minecraft.item.ItemHoe;
import net.minecraft.util.ResourceLocation;

/**
 * A basic hoe
 * @author CJMinecraft
 *
 */
public class ItemModHoe extends ItemHoe {

	public ItemModHoe(ToolMaterial material, String unlocalizedName) {
		super(material);
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
	}

}
