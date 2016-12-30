package cjminecraft.bitofeverything.worldgen;

import java.util.Random;

import cjminecraft.bitofeverything.blocks.BlockTinOre;
import cjminecraft.bitofeverything.handlers.EnumHandler.OreType;
import cjminecraft.bitofeverything.init.ModBlocks;
import cjminecraft.bitofeverything.util.Utils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

/**
 * This class handles all of our ore generation
 * 
 * @author CJMinecraft
 *
 */
public class OreGen implements IWorldGenerator {

	// World Generators
	private WorldGenerator tin_overworld;
	private WorldGenerator tin_nether;
	private WorldGenerator tin_end;

	/**
	 * The constructor initializes our fields
	 */
	public OreGen() {
		tin_overworld = new WorldGenMinable(
				ModBlocks.tinOre.getDefaultState().withProperty(BlockTinOre.TYPE, OreType.OVERWORLD), 8); //Here we are saying that our ore spawns in veins of up to 8
		tin_nether = new WorldGenMinable(
				ModBlocks.tinOre.getDefaultState().withProperty(BlockTinOre.TYPE, OreType.NETHER), 8,
				new NetherGenPredicate()); //Uses the nether predicate so that when it is being generated it replaces the netherrack
		tin_end = new WorldGenMinable(ModBlocks.tinOre.getDefaultState().withProperty(BlockTinOre.TYPE, OreType.END), 8,
				new EndGenPredicate());
	}

	/**
	 * Will generate our ore using the correct fields on where to generate
	 * @param generator The actual world generator which holds which block to place
	 * @param world The world to spawn the block in
	 * @param rand The random object used for randomisation
	 * @param chunk_X The chunks X position in the world
	 * @param chunk_Z The chunks Z position in the world
	 * @param chancesToSpawn How many times it will spawn per chunk
	 * @param minHeight The minimum height the ore can spawn
	 * @param maxHeight The maximum height the ore can spawn
	 */
	private void runGenerator(WorldGenerator generator, World world, Random rand, int chunk_X, int chunk_Z,
			int chancesToSpawn, int minHeight, int maxHeight) {
		if (minHeight < 0 || maxHeight > 256 || minHeight > maxHeight)
			throw new IllegalArgumentException("Illegal Height Arguments for WorldGenerator");

		int heightDiff = maxHeight - minHeight + 1;
		for (int i = 0; i < chancesToSpawn; i++) {
			int x = chunk_X * 16 + rand.nextInt(16);
			int y = minHeight + rand.nextInt(heightDiff);
			int z = chunk_Z * 16 + rand.nextInt(16);
			generator.generate(world, rand, new BlockPos(x, y, z));
		}
	}

	/**
	 * Actually runs the world generator when called
	 */
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
		switch (world.provider.getDimension()) {
		case 0: // Overworld
			this.runGenerator(tin_overworld, world, random, chunkX, chunkZ, 30, 0, 64);
			break;
		case 1: // End
			this.runGenerator(tin_end, world, random, chunkX, chunkZ, 30, 0, 256);
			break;
		case -1: // Nether
			this.runGenerator(tin_nether, world, random, chunkX, chunkZ, 30, 0, 64);
			break;
		}
	}

}
