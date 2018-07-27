package cjminecraft.bitofeverything.events;

import cjminecraft.bitofeverything.Reference;
import cjminecraft.core.util.VersionChecker;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

/**
 * An event handler for all the player related events
 * 
 * @author CJMinecraft
 *
 */
public class PlayerEvents {

	/**
	 * Check for updates when the player logs in on the world
	 * 
	 * @param event
	 *            The event which is called when the player logs in
	 */
	@SubscribeEvent
	public void onPlayerJoinEvent(PlayerLoggedInEvent event) {
		// VersionChecker.checkForUpdate(Reference.VERSION_CHECKER_URL, Reference.MODID, Reference.VERSION, event.player); TODO Update CJCore
	}

}
