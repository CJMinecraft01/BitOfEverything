package cjminecraft.bitofeverything.init;

import cjminecraft.bitofeverything.BitOfEverything;
import cjminecraft.bitofeverything.Reference;
import cjminecraft.bitofeverything.blocks.BlockBreaker;
import cjminecraft.bitofeverything.blocks.BlockCanvas;
import cjminecraft.bitofeverything.blocks.BlockCotton;
import cjminecraft.bitofeverything.blocks.BlockEnergyCell;
import cjminecraft.bitofeverything.blocks.BlockGamemodeDetector;
import cjminecraft.bitofeverything.blocks.BlockMachineFrame;
import cjminecraft.bitofeverything.blocks.BlockTinBlock;
import cjminecraft.bitofeverything.blocks.BlockTinButton;
import cjminecraft.bitofeverything.blocks.BlockTinDoor;
import cjminecraft.bitofeverything.blocks.BlockTinFence;
import cjminecraft.bitofeverything.blocks.BlockTinFenceGate;
import cjminecraft.bitofeverything.blocks.BlockTinOre;
import cjminecraft.bitofeverything.blocks.BlockTinPressurePlate;
import cjminecraft.bitofeverything.blocks.BlockTinSlabDouble;
import cjminecraft.bitofeverything.blocks.BlockTinSlabHalf;
import cjminecraft.bitofeverything.blocks.BlockTinStairs;
import cjminecraft.bitofeverything.blocks.item.ItemBlockBreaker;
import cjminecraft.bitofeverything.blocks.item.ItemBlockDoor;
import cjminecraft.bitofeverything.blocks.item.ItemBlockMeta;
import cjminecraft.bitofeverything.handlers.EnumHandler;
import cjminecraft.bitofeverything.tileentity.TileEntityCanvas;
import cjminecraft.bitofeverything.util.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * This class handles the registration of our blocks and also the rendering of
 * them
 * 
 * @author CJMinecraft
 *
 */
public class ModBlocks {

	/**
	 * State our blocks
	 */
	public static Block tinOre;
	public static Block tinBlock;
	public static Block gamemodeDetector;
	public static Block machineFrame;
	public static BlockTinSlabHalf tinSlabHalf;
	public static BlockTinSlabDouble tinSlabDouble;
	public static BlockTinStairs tinStairs;
	public static BlockTinFence tinFence;
	public static BlockTinFenceGate tinFenceGate;
	public static Block tinButton;
	public static Block tinPressurePlate;
	public static BlockTinDoor tinDoor;
	public static Block canvas;
	public static Block cotton;

	/*
	 * Energy Blocks
	 */
	public static Block energyCell;
	public static Block breaker;
	public static Block furnaceGenerator;
	public static Block networkController;
	public static Block networkTransmitter;
	public static Block networkReceiver;
	public static Block energyBuffer;
	public static Block blockPlacer;
	public static Block waterMill;

	/**
	 * Initialize the blocks
	 */
	public static void init() {
		tinOre = new BlockTinOre("tin_ore", "tin_ore");
		breaker = new BlockBreaker("block_breaker");
		gamemodeDetector = new BlockGamemodeDetector("gamemode_detector");
		machineFrame = new BlockMachineFrame("machine_frame");
		tinBlock = new BlockTinBlock("tin_block");
		tinSlabHalf = new BlockTinSlabHalf("tin_slab_half");
		tinSlabDouble = new BlockTinSlabDouble("tin_slab_double");
		tinStairs = new BlockTinStairs("tin_stairs", tinBlock.getDefaultState());
		tinFence = new BlockTinFence("tin_fence");
		tinFenceGate = new BlockTinFenceGate("tin_fence_gate");
		tinButton = new BlockTinButton("tin_button");
		tinPressurePlate = new BlockTinPressurePlate("tin_pressure_plate");
		tinDoor = new BlockTinDoor("tin_door");
		canvas = new BlockCanvas("canvas");
		cotton = new BlockCotton("cotton");
		
		/*
		 * Energy Blocks
		 */
		energyCell = new BlockEnergyCell("energy_cell");
	}

	/**
	 * Register the blocks
	 */
	public static void register() {
		registerBlock(tinOre, new ItemBlockMeta(tinOre)); // Says that the block uses the ItemBlockMeta as the item block
		registerBlock(breaker, new ItemBlockBreaker(breaker));
		registerBlock(gamemodeDetector);
		registerBlock(machineFrame, new ItemBlockMeta(machineFrame));
		registerBlock(tinBlock);
		registerBlock(tinSlabHalf, new ItemSlab(tinSlabHalf, tinSlabHalf, tinSlabDouble));
		GameRegistry.register(tinSlabDouble); // Doesn't need an item
		registerBlock(tinStairs);
		registerBlock(tinFence);
		registerBlock(tinFenceGate);
		registerBlock(tinButton);
		registerBlock(tinPressurePlate);
		registerBlock(tinDoor, new ItemBlockDoor(tinDoor));
		registerBlock(canvas);
		GameRegistry.register(cotton);
		
		/*
		 * Energy Blocks
		 */
		registerBlock(energyCell, new ItemBlockMeta(energyCell));
	}

	/**
	 * Register the renders for the block
	 */
	public static void registerRenders() {
		for (int i = 0; i < EnumHandler.OreType.values().length; i++) {
			registerRender(tinOre, i, "tin_ore_" + EnumHandler.OreType.values()[i].getName());
		}
		for (int i = 0; i < EnumHandler.ChipTypes.values().length; i++) {
			registerRender(breaker, i, "block_breaker_" + EnumHandler.ChipTypes.values()[i].getName());
			registerRender(machineFrame, i, "machine_frame_" + EnumHandler.ChipTypes.values()[i].getName());
			registerRender(energyCell, i, "energy_cell_" + EnumHandler.ChipTypes.values()[i].getName());
		}
		registerRender(gamemodeDetector);
		registerRender(tinBlock);
		registerRender(tinSlabHalf);
		registerRender(tinStairs);
		registerRender(tinFence);
		registerRender(tinFenceGate);
		registerRender(tinButton);
		registerRender(tinPressurePlate);
		registerRender(tinDoor);
		registerRender(canvas);
	}

	/**
	 * Register blocks which will have a colour
	 */
	@SideOnly(Side.CLIENT)
	public static void registerBlockColours() {
		Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(new IBlockColor() {

			@Override
			public int colorMultiplier(IBlockState state, IBlockAccess world, BlockPos pos, int tintIndex) {
				TileEntityCanvas canvas = (TileEntityCanvas) world.getTileEntity(pos);
				if (canvas != null)
					return canvas.getColour();
				return 0xFFFFFF;
			}
		}, canvas);
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor() {

			@Override
			public int getColorFromItemstack(ItemStack stack, int tintIndex) {
				if (stack.hasTagCompound())
					if (stack.getTagCompound().hasKey("colour"))
						return stack.getTagCompound().getInteger("colour");
				return 0xFFFFFF;
			}
		}, canvas);
		Utils.getLogger().info("Registered block colours!");
	}

	/**
	 * Creates state mappers for ignoring properties etc.
	 */
	@SideOnly(Side.CLIENT)
	public static void createStateMappers() {
		ModelLoader.setCustomStateMapper(gamemodeDetector, new StateMapperBase() { // Ignores all of the block's properties

			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
				return new ModelResourceLocation(gamemodeDetector.getRegistryName(), "normal");
			}
		});
		ModelLoader.setCustomStateMapper(tinDoor, (new StateMap.Builder().ignore(BlockDoor.POWERED)).build()); // Ignores only the powered property
		ModelLoader.setCustomStateMapper(tinFenceGate, (new StateMap.Builder().ignore(BlockFenceGate.POWERED)).build());
		Utils.getLogger().info("Created the state mappers!");
	}

	/**
	 * Registers the block
	 * 
	 * @param block
	 *            The block to register
	 */
	public static void registerBlock(Block block) {
		block.setCreativeTab(BitOfEverything.blocks);
		GameRegistry.register(block);
		GameRegistry.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
		Utils.getLogger().info("Registered Block: " + block.getUnlocalizedName().substring(5));
	}

	/**
	 * Registers the block with a custom {@link ItemBlock}
	 * 
	 * @param block
	 *            The block
	 * @param itemBlock
	 *            The {@link ItemBlock}
	 */
	public static void registerBlock(Block block, ItemBlock itemBlock) {
		block.setCreativeTab(BitOfEverything.blocks);
		GameRegistry.register(block);
		GameRegistry.register(itemBlock.setRegistryName(block.getRegistryName()));
		Utils.getLogger().info("Registered Block: " + block.getUnlocalizedName().substring(5));
	}

	/**
	 * Registers the blocks renders
	 * 
	 * @param block
	 *            The block
	 */
	public static void registerRender(Block block) {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(
				new ResourceLocation(Reference.MODID, block.getUnlocalizedName().substring(5)), "inventory"));
		Utils.getLogger().info("Registered render for " + block.getUnlocalizedName().substring(5));
	}

	/**
	 * Registers the blocks renders even if it has meta data
	 * 
	 * @param block
	 *            The block
	 * @param meta
	 *            The blocks meta data
	 * @param fileName
	 *            The file name
	 */
	public static void registerRender(Block block, int meta, String fileName) {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), meta,
				new ModelResourceLocation(new ResourceLocation(Reference.MODID, fileName), "inventory"));
		Utils.getLogger().info("Registered render for " + block.getUnlocalizedName().substring(5));
	}

}
