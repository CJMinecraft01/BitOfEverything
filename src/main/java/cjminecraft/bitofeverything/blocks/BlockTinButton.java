package cjminecraft.bitofeverything.blocks;

import cjminecraft.bitofeverything.Reference;
import cjminecraft.bitofeverything.handlers.BoeSoundHandler;
import net.minecraft.block.BlockButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * A block which is like a button
 * @author CJMinecraft
 *
 */
public class BlockTinButton extends BlockButton {

	/**
	 * Default constructor
	 * @param unlocalizedName The unlocalized name of the block
	 */
	public BlockTinButton(String unlocalizedName) {
		super(false); //Says that the block is not a wooden button
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
		this.setHardness(3);
		this.setResistance(20);
	}

	/**
	 * Plays our custom sound when you press it
	 */
	@Override
	protected void playClickSound(EntityPlayer player, World worldIn, BlockPos pos) {
		worldIn.playSound(player, pos, BoeSoundHandler.TIN_BUTTON_CLICK_ON, SoundCategory.BLOCKS, 1.0F, 1.0F);
	}

	/**
	 * Plays our custom sound when you press it
	 */
	@Override
	protected void playReleaseSound(World worldIn, BlockPos pos) {
		worldIn.playSound((EntityPlayer)null, pos, BoeSoundHandler.TIN_BUTTON_CLICK_OFF, SoundCategory.BLOCKS, 1.0F, 1.0F);
	}
	
	/**
	 * How long the button emits a redstone signal
	 */
	@Override
	public int tickRate(World worldIn) {
		return 15;
	}

}
