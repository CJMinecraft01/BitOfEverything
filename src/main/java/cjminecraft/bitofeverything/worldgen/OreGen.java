package cjminecraft.bitofeverything.worldgen;

import java.util.Random;

import cjminecraft.bitofeverything.blocks.BlockTinOre;
import cjminecraft.bitofeverything.handlers.EnumHandler.OreType;
import cjminecraft.bitofeverything.init.ModBlocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class OreGen implements IWorldGenerator {
	
	//World Generators
	private WorldGenerator tin_overworld;
	private WorldGenerator tin_nether;
	private WorldGenerator tin_end;
	
	public OreGen() {
		tin_overworld = new WorldGenMinable(ModBlocks.tinOre.getDefaultState().withProperty(BlockTinOre.TYPE, OreType.OVERWORLD), 8);
		tin_nether = new WorldGenMinable(ModBlocks.tinOre.getDefaultState().withProperty(BlockTinOre.TYPE, OreType.NETHER), 8, new NetherGenPredicate());
		tin_end = new WorldGenMinable(ModBlocks.tinOre.getDefaultState().withProperty(BlockTinOre.TYPE, OreType.END), 8, new EndGenPredicate());
	}
	
	private void runGenerator(WorldGenerator generator, World world, Random rand, int chunk_X, int chunk_Z, int chancesToSpawn, int minHeight, int maxHeight) {
		if (minHeight < 0 || maxHeight > 256 || minHeight > maxHeight)
			throw new IllegalArgumentException("Illegal Height Arguments for WorldGenerator");

		int heightDiff = maxHeight - minHeight + 1;
		for (int i = 0; i < chancesToSpawn; i ++) {
			int x = chunk_X * 16 + rand.nextInt(16);
		        int y = minHeight + rand.nextInt(heightDiff);
		        int z = chunk_Z * 16 + rand.nextInt(16);
		        generator.generate(world, rand, new BlockPos(x, y, z));
		}
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
		switch(world.provider.getDimension()) {
		case 0: //Overworld
			this.runGenerator(tin_overworld, world, random, chunkX, chunkZ, 20, 0, 64);
			break;
		case 1: //End
			this.runGenerator(tin_end, world, random, chunkX, chunkZ, 20, 0, 256);
			break;
		case -1: //Nether
			this.runGenerator(tin_nether, world, random, chunkX, chunkZ, 20, 0, 64);
			break;
		}
	}

}
