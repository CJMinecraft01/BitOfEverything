package cjminecraft.bitofeverything.items;

import cjminecraft.bitofeverything.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ItemModFood extends ItemFood {

	/**
	 * This just holds the potion effects our item will give
	 */
	private PotionEffect[] effects;

	/**
	 * This creates a new food item
	 * 
	 * @param unlocalizedName
	 *            The unlocalized and registry name
	 * @param amount
	 *            How many hunger points the food will refill
	 * @param isWolfFood
	 *            If the food can be ate by a wolf
	 * @param potionEffects
	 *            Potion effects that will be given to the player upon eating it
	 */
	public ItemModFood(String unlocalizedName, int amount, boolean isWolfFood, PotionEffect... potionEffects) {
		super(amount, isWolfFood);
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
		this.effects = potionEffects;
	}

	/**
	 * This creates a new food item
	 * 
	 * @param unlocalizedName
	 *            The unlocalized and registry name
	 * @param amount
	 *            How many hunger points the food will refill
	 * @param saturation
	 *            How long the food will last after it has been ate
	 * @param isWolfFood
	 *            If the food can be ate by a wolf
	 * @param potionEffects
	 *            Potion effects that will be given to the player upon eating it
	 */
	public ItemModFood(String unlocalizedName, int amount, float saturation, boolean isWolfFood,
			PotionEffect... potionEffects) {
		super(amount, saturation, isWolfFood);
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
		this.effects = potionEffects;
	}

	/**
	 * Called when the player eats the food.
	 * This just adds all of the potion effects to the player
	 */
	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		for (PotionEffect effect : effects) {
			player.addPotionEffect(new PotionEffect(effect));
		}
	}

}
