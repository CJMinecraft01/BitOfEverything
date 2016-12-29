package cjminecraft.bitofeverything.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cjminecraft.bitofeverything.Reference;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.items.ItemStackHandler;
/**
 * This is where useful methods will be
 * @author CJMinecraft
 *
 */
public class Utils {
	
	/**
	 * Makes the variables which will be initialized when there getter method is called
	 */
	private static Logger logger;
	private static Lang lang;
	
	/**
	 * Returns the logger. This makes System.our.println look shabby
	 * @return The {@link Logger}
	 */
	public static Logger getLogger() {
		if(logger == null) {
			logger = LogManager.getFormatterLogger(Reference.MODID);
		}
		return logger;
	}
	
	/**
	 * Returns the language for the mod.
	 * @return the {@link Lang}
	 */
	public static Lang getLang() {
		if(lang == null) {
			lang = new Lang(Reference.MODID); //Change Reference.MODID to whatever you feel necessary notice that when in the language file it will be what ever you put in . what you asked it for
		}
		return lang;
	}
	
	public static int calculateRedstone(ItemStackHandler handler) {
		int i = 0;
		float f = 0.0F;
		for(int j = 0; j < handler.getSlots(); j++) {
			ItemStack stack = handler.getStackInSlot(j);
			if(!stack.isEmpty()) {
				f += (float)stack.getCount() / (float)Math.min(handler.getSlotLimit(j), stack.getMaxStackSize());
				i++;
			}
		}
		f = f / (float) handler.getSlots();
		return MathHelper.floor(f * 14.0F) + (i > 0 ? 1 : 0);
	}

}
