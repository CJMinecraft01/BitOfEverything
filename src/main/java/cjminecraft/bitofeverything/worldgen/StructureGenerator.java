package cjminecraft.bitofeverything.worldgen;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import cjminecraft.bitofeverything.Reference;
import cjminecraft.bitofeverything.util.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.event.terraingen.BiomeEvent.GetVillageBlockID;

/**
 * Generate a structure with a random rotation, and automatically filled loot
 * tabled contains (if set up correctly using data structure blocks)
 * 
 * @author CJMinecraft
 *
 */
public class StructureGenerator extends WorldGenerator {

	/**
	 * Creates a range from the ground Y level where the structure can spawn
	 */
	private static final int VARIATION = 2;

	/**
	 * The name of the structure
	 */
	private String structureName;

	/**
	 * Allows the user to generate a structure from the template at the
	 * structure name
	 * 
	 * @param structureName
	 *            The name of the structure to generate
	 */
	public StructureGenerator(String structureName) {
		this.structureName = structureName;
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos position) {
		WorldServer worldServer = (WorldServer) world;
		MinecraftServer minecraftServer = world.getMinecraftServer();
		TemplateManager templateManager = worldServer.getStructureTemplateManager();
		Template template = templateManager.get(minecraftServer,
				new ResourceLocation(Reference.MODID, this.structureName));

		if (template == null) {
			// The template does not exist
			Utils.getLogger().error("The structre template: " + this.structureName + " did not exist!");
			return false;
		}

		if (canSpawnHere(template, worldServer, position)) {

			Rotation rotation = Rotation.values()[rand.nextInt(3)];

			PlacementSettings settings = new PlacementSettings().setMirror(Mirror.NONE).setRotation(rotation)
					.setIgnoreStructureBlock(false);

			template.addBlocksToWorld(world, position, settings);

			Map<BlockPos, String> dataBlocks = template.getDataBlocks(position, settings);

			for (Entry<BlockPos, String> entry : dataBlocks.entrySet()) {
				try {
					String[] data = entry.getValue().split(" ");
					if (data.length < 2)
						continue;
					Block block = Block.getBlockFromName(data[0]);
					IBlockState state = null;
					if (data.length == 3)
						state = block.getStateFromMeta(Integer.parseInt(data[2]));
					else
						state = block.getDefaultState();
					for (Entry<IProperty<?>, Comparable<?>> entry2 : block.getDefaultState().getProperties()
							.entrySet()) {
						if (entry2.getKey().getValueClass().equals(EnumFacing.class)
								&& entry2.getKey().getName().equals("facing")) {
							state = state.withRotation(rotation.add(Rotation.CLOCKWISE_180));
							break;
						}
					}
					world.setBlockState(entry.getKey(), state, 3);
					TileEntity te = world.getTileEntity(entry.getKey());
					if (te == null)
						continue;
					if (te instanceof TileEntityLockableLoot)
						((TileEntityLockableLoot) te).setLootTable(new ResourceLocation(data[1]), rand.nextLong());
				} catch (Exception e) {
					Utils.getLogger().catching(e);
					continue;
				}
			}
			return true;
		}

		return false;
	}

	/**
	 * Checks whether the given template can spawn at the given position in the
	 * world
	 * 
	 * @param template
	 *            The template / structure to check
	 * @param world
	 *            The world which the structure will be spawned in
	 * @param pos
	 *            The position that the structure will be spawned at
	 * @return Whether the given template can spawn at the given position in the
	 *         world
	 */
	public static boolean canSpawnHere(Template template, World world, BlockPos pos) {
		return isCornerValid(world, pos) && isCornerValid(world, pos.add(template.getSize().getX(), 0, 0))
				&& isCornerValid(world, pos.add(template.getSize().getX(), 0, template.getSize().getZ()))
				&& isCornerValid(world, pos.add(0, 0, template.getSize().getZ()));
	}

	/**
	 * Checks whether the corner is valid (i.e. the position is with the
	 * variation range of the ground level
	 * 
	 * @param world
	 *            The world which contains the position
	 * @param pos
	 *            The position of the corner
	 * @return Whether the corner is valid
	 */
	public static boolean isCornerValid(World world, BlockPos pos) {
		int groundY = getGroundFromAbove(world, pos.getX(), pos.getZ());
		return groundY > pos.getY() - VARIATION && groundY < pos.getY() + VARIATION;
	}

	/**
	 * Gets the position of the ground going from build height downwards. <br>
	 * <i>Note: This will not check for water!</i>
	 * 
	 * @param world
	 *            The world to get the blocks from
	 * @param x
	 *            The x position of the block to get the ground y value
	 * @param z
	 *            The z position of the block to get the ground y value
	 * @return The ground y level (or -1 if the ground was not found - this
	 *         should never happen)
	 */
	public static int getGroundFromAbove(World world, int x, int z) {
		int y = 255;
		boolean foundGround = false;
		while (!foundGround && y-- > 0) {
			Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();
			if (block == Blocks.WATER || block == Blocks.FLOWING_WATER) {
				y = -1;
				break;
			}
			foundGround = block == Blocks.GRASS || block == Blocks.SAND || block == Blocks.SNOW
					|| block == Blocks.SNOW_LAYER || block == Blocks.MYCELIUM || block == Blocks.STONE;
		}
		return y;
	}

}
