package cjminecraft.bitofeverything.handlers;

import cjminecraft.bitofeverything.events.SoulStealerEvents;
import net.minecraftforge.common.MinecraftForge;

/**
 * This class handles the registration of our events
 * @author CJMinecraft
 *
 */
public class EventHandler {
	
	/**
	 * Registers our events
	 */
	public void registerEvents() {
		MinecraftForge.EVENT_BUS.register(new SoulStealerEvents());
	}

}
