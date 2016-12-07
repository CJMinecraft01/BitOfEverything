package cjminecraft.bitofeverything.proxy;

import cjminecraft.bitofeverything.worldgen.OreGen;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {
	
	public void init() {
		GameRegistry.registerWorldGenerator(new OreGen(), 0);
	}
	
	public void registerRenders() {
		
	}
	
	public void registerModelBakeryStuff() {
		
	}

}
