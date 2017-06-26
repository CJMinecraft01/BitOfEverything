package cjminecraft.bitofeverything;

/**
 * This class handles all of our constants about the mod and its details
 * 
 * @author CJMinecraft
 *
 */
public class Reference {

	/**
	 * Generic modid used for models and registering textures
	 */
	public static final String MODID = "boe";
	/**
	 * The mod's name
	 */
	public static final String NAME = "BitOfEverything";
	/**
	 * Current version TODO Update version
	 */
	public static final String VERSION = "0.0.1";
	/**
	 * Where the server proxy is found
	 */
	public static final String SERVER_PROXY_CLASS = "cjminecraft.bitofeverything.proxy.ServerProxy";
	/**
	 * Where the client proxy is found
	 */
	public static final String CLIENT_PROXY_CLASS = "cjminecraft.bitofeverything.proxy.ClientProxy";
	
	/**
	 * Where our gui factory if found
	 */
	public static final String GUI_FACTORY = "cjminecraft.bitofeverything.config.BoeConfigGuiFactory";
	
	public static final String VERSION_CHECKER_URL = "https://raw.githubusercontent.com/CJMinecraft01/BitOfEverything/master/update.json";

}
