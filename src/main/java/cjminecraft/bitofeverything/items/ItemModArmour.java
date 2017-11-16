package cjminecraft.bitofeverything.items;

import java.util.Iterator;

import cjminecraft.bitofeverything.Reference;
import cjminecraft.bitofeverything.handlers.AchievementHandler;
import cjminecraft.bitofeverything.init.ModArmour;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

/**
 * A simple instance of armour. Will be used for all different types of armour
 * 
 * @author CJMinecraft
 *
 */
public class ItemModArmour extends ItemArmor {

	/**
	 * Create a new piece of armour.
	 * 
	 * @param materialIn
	 *            The material of the armour
	 * @param renderIndexIn
	 *            The render index (0 for the helmet, chestplate and boots; 1
	 *            for leggings)
	 * @param equipmentSlotIn
	 *            The equipment slot (0 for the helmet, 1 for the chestplate, 2
	 *            for the leggings, 3 for the boots)
	 * @param unlocalizedName
	 *            The unlocalized name of the armour
	 */
	public ItemModArmour(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn,
			String unlocalizedName) {
		super(materialIn, renderIndexIn, equipmentSlotIn);
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
	}

	/**
	 * Triggers the tin man achievement when all four pieces are worn
	 */
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		int tinArmourPeices = 0;
		if (player.getArmorInventoryList() != null) {
			Iterator<ItemStack> iterator = player.getArmorInventoryList().iterator();
			while (iterator.hasNext()) {
				ItemStack stack = iterator.next();
				if (stack != null) {
					if (stack.getItem() instanceof ItemModArmour) {
						ItemModArmour item = (ItemModArmour) stack.getItem();
						if (item.getArmorMaterial() == ModArmour.tinMaterial) {
							tinArmourPeices++;
							continue;
						}
					}
				}
			}
		}
		if (tinArmourPeices == 4)
			if (!player.hasAchievement(AchievementHandler.achievementTinMan))
				player.addStat(AchievementHandler.achievementTinMan);
	}

}
