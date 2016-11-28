package cjminecraft.bitofeverything.handlers;

import cjminecraft.bitofeverything.events.SoulStealerEvents;
import net.minecraftforge.common.MinecraftForge;

public class EventHandler {
	
	public void registerEvents() {
		MinecraftForge.EVENT_BUS.register(new SoulStealerEvents());
	}

}
