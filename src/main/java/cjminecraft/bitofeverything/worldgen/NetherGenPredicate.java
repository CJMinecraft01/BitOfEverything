package cjminecraft.bitofeverything.worldgen;

import com.google.common.base.Predicate;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

/**
 * This class is used for world generation in the nether Refer to {@link OreGen}
 * as a guide
 * 
 * @author CJMinecraft
 *
 */
public class NetherGenPredicate implements Predicate<IBlockState> {

	/**
	 * Says that it will only replace netherrack
	 */
	@Override
	public boolean apply(IBlockState input) {
		return input != null && input.getBlock() == Blocks.NETHERRACK;
	}

}
