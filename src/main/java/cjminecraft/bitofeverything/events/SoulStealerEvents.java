package cjminecraft.bitofeverything.events;

import cjminecraft.bitofeverything.init.ModItems;
import cjminecraft.bitofeverything.init.ModTools;
import cjminecraft.bitofeverything.items.ItemHeart;
import cjminecraft.bitofeverything.items.ItemSoulStealer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * This class just drops an {@link ItemHeart} when you hit an {@link Entity} with the {@link ItemSoulStealer}
 * @author CJMinecraft
 *
 */
public class SoulStealerEvents {
	
	/**
	 * Says that this will be called when the {@link LivingHurtEvent} is called
	 * @param event The {@link LivingHurtEvent}
	 */
	@SubscribeEvent
	public void onEvent(LivingHurtEvent event) {
		if(event.getSource().getEntity() instanceof EntityPlayer) { //Checks the person who dealt the damage is a player
			EntityPlayer player = (EntityPlayer) event.getSource().getEntity(); //Gets the player who dealt the damage
			if(player.getHeldItemMainhand().getItem() == ModTools.soulStealer) { //Checks they were holding a soul stealer
				BlockPos pos = event.getEntity().getPosition(); //Get the entity they hurt's position
				EntityItem item = new EntityItem(player.getEntityWorld(), pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ModItems.heart)); //Creates a new EntityItem
				player.getEntityWorld().spawnEntity(item); //Spawns the item in the world. THIS METHOD WAS RENAMED IN 1.11.2 to spawnEntity not spawnEntityInWorld
			}
		}
	}

}
