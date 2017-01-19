package cjminecraft.bitofeverything.blocks;

/**
 * A half slab which gets its properties from the tin slab
 * @author CJMinecraft
 *
 */
public class BlockTinSlabHalf extends BlockTinSlab {

	public BlockTinSlabHalf(String unlocalizedName) {
		super(unlocalizedName);
	}

	/**
	 * Says that it isn't the double version of the block
	 */
	@Override
	public boolean isDouble() {
		return false;
	}

}
