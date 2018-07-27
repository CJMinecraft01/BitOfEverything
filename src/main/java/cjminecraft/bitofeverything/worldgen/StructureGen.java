package cjminecraft.bitofeverything.worldgen;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.IWorldGenerator;

public class StructureGen implements IWorldGenerator {

	private StructureGenerator cabinGenerator;

	public StructureGen() {
		this.cabinGenerator = new StructureGenerator("cabin");
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
		int blockX = chunkX * 16 + random.nextInt(16);
		int blockZ = chunkZ * 16 + random.nextInt(16);

		switch (world.provider.getDimension()) {
		case 0:
			// The overworld
			runGenerator(this.cabinGenerator, world, blockX, blockZ, 50, random);
			break;
		case -1:
			// The nether
			break;
		case 1:
			// The end
			break;
		}
	}

	private void runGenerator(StructureGenerator generator, World world, int blockX, int blockZ, int chance,
			Random random) {
		if ((int) (Math.random() * chance) == 0) {
			generator.generate(world, random,
					new BlockPos(blockX, StructureGenerator.getGroundFromAbove(world, blockX, blockZ), blockZ));
		}
	}

}
