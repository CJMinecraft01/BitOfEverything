package cjminecraft.bitofeverything.events;

import cjminecraft.bitofeverything.Reference;
import cjminecraft.core.util.VersionChecker;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class PlayerEvents {
	
	@SubscribeEvent
	public void onPlayerJoinEvent(PlayerLoggedInEvent event) {
		VersionChecker.checkForUpdate(Reference.VERSION_CHECKER_URL, Reference.MODID, Reference.VERSION, event.player);
	}

}
