package cjminecraft.bitofeverything.handlers;

import java.util.ArrayList;
import java.util.List;

import cjminecraft.bitofeverything.Reference;
import cjminecraft.bitofeverything.init.ModArmour;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;

/**
 * Handles all of the achievements
 * @author CJMinecraft
 *
 */
public class AchievementHandler {
	
	/**
	 * A list of all of our achievements
	 */
	private static List<Achievement> achievements = new ArrayList<Achievement>();
	
	/**
	 * State each achievement and use the createAchievement method to register the achievement
	 */
	public static Achievement achievementTinMan = createAchievement("tinman", 0, 0, ModArmour.tinHelmet);
	
	/**
	 * Registers the achievements
	 */
	public static void registerAchievements() {
		Achievement[] achievementArray = new Achievement[achievements.size()]; //Converts the list to an array
		for(Achievement achievement : achievements) {
			achievement.registerStat(); //Registers the achievement
			achievementArray[achievements.indexOf(achievement)] = achievement; //Adds it to the array
		}
		AchievementPage.registerAchievementPage(new AchievementPage(Reference.NAME, achievementArray)); //Adds the array to our achievement page
	}
	
	private static Achievement createAchievement(String name, int column, int row, Item item) {
		Achievement acheivement = new Achievement("achievement." + name, name, column, row, item, (Achievement)null); //The null is for the parent this is if one achievement requires another to unlock it
		achievements.add(acheivement);
		return acheivement;
	}
	
	private static Achievement createAchievement(String name, int column, int row, Block block) {
		Achievement acheivement = new Achievement("achievement." + name, name, column, row, block, (Achievement)null);
		achievements.add(acheivement);
		return acheivement;
	}
	
	private static Achievement createAchievement(String name, int column, int row, ItemStack stack) {
		Achievement acheivement = new Achievement("achievement." + name, name, column, row, stack, (Achievement)null);
		achievements.add(acheivement);
		return acheivement;
	}

}
