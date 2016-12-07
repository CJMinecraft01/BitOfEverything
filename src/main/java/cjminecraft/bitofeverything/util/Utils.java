package cjminecraft.bitofeverything.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cjminecraft.bitofeverything.Reference;
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

}
