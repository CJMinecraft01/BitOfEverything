package cjminecraft.bitofeverything.items;

import cjminecraft.bitofeverything.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ItemModFood extends ItemFood {

	private PotionEffect[] effects;
	
	public ItemModFood(String unlocalizedName, int amount, boolean isWolfFood, PotionEffect...potionEffects) {
		super(amount, isWolfFood);
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
		this.effects = potionEffects;
	}
	
	public ItemModFood(String unlocalizedName, int amount, float saturation, boolean isWolfFood, PotionEffect...potionEffects) {
		super(amount, saturation, isWolfFood);
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
		this.effects = potionEffects;
	}
	
	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		for(PotionEffect effect : effects) {
			player.addPotionEffect(effect);
		}
	}

}
