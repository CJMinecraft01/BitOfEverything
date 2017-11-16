package cjminecraft.bitofeverything.handlers;

import cjminecraft.bitofeverything.Reference;
import cjminecraft.bitofeverything.util.Utils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

/**
 * A handler for all of BitOfEverything's sounds
 * 
 * @author CJMinecraft
 *
 */
public class BoeSoundHandler {

	/**
	 * The number of sounds registered in total
	 */
	private static int size = 0;

	public static SoundEvent TIN_FENCE_GATE_OPEN;
	public static SoundEvent TIN_FENCE_GATE_CLOSE;
	public static SoundEvent TIN_BUTTON_CLICK_ON;
	public static SoundEvent TIN_BUTTON_CLICK_OFF;

	/**
	 * Initialise all of our sounds
	 */
	public static void init() {
		size = SoundEvent.REGISTRY.getKeys().size();

		TIN_FENCE_GATE_OPEN = register("block.tin_fence_gate.open");
		TIN_FENCE_GATE_CLOSE = register("block.tin_fence_gate.close");
		TIN_BUTTON_CLICK_ON = register("block.tin_button.click_on");
		TIN_BUTTON_CLICK_OFF = register("block.tin_button.click_off");
	}

	/**
	 * Register a sound event
	 * 
	 * @param name
	 *            The name of the sound (for use in the sounds.json file)
	 * @return The registered sound event
	 */
	public static SoundEvent register(String name) {
		ResourceLocation location = new ResourceLocation(Reference.MODID, name);
		SoundEvent e = new SoundEvent(location);

		SoundEvent.REGISTRY.register(size, location, e);
		size++;
		Utils.getLogger().info("Registered sound: " + name);
		return e;
	}

}
