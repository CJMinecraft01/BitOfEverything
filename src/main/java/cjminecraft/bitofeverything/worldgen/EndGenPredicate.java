package cjminecraft.bitofeverything.worldgen;

import com.google.common.base.Predicate;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

public class EndGenPredicate implements Predicate<IBlockState>{

	@Override
	public boolean apply(IBlockState input) {
		return input != null && input.getBlock() == Blocks.END_STONE;
	}

}
