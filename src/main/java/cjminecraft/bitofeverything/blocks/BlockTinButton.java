package cjminecraft.bitofeverything.blocks;

import cjminecraft.bitofeverything.Reference;
import cjminecraft.bitofeverything.handlers.BoeSoundHandler;
import net.minecraft.block.BlockButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockTinButton extends BlockButton {

	public BlockTinButton(String unlocalizedName) {
		super(false);
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
		this.setHardness(3);
		this.setResistance(20);
	}

	@Override
	protected void playClickSound(EntityPlayer player, World worldIn, BlockPos pos) {
		worldIn.playSound(player, pos, BoeSoundHandler.TIN_BUTTON_CLICK_ON, SoundCategory.BLOCKS, 1.0F, 1.0F);
	}

	@Override
	protected void playReleaseSound(World worldIn, BlockPos pos) {
		worldIn.playSound((EntityPlayer)null, pos, BoeSoundHandler.TIN_BUTTON_CLICK_OFF, SoundCategory.BLOCKS, 1.0F, 1.0F);
	}
	
	@Override
	public int tickRate(World worldIn) {
		return 15;
	}

}
