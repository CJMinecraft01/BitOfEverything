package cjminecraft.bitofeverything.container.slots;

import cjminecraft.bitofeverything.tileentity.TileEntityDoubleFurnace;
import cjminecraft.bitofeverything.util.Utils;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotFurnaceOutput extends SlotItemHandler {

	private TileEntityDoubleFurnace furnace;
	private final EntityPlayer player;
	private int removeCount;

	public SlotFurnaceOutput(TileEntityDoubleFurnace furnace, EntityPlayer player, IItemHandler itemHandler, int index,
			int xPosition, int yPosition) {
		super(itemHandler, index, xPosition, yPosition);
		this.furnace = furnace;
		this.player = player;
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return false;
	}

	@Override
	public ItemStack decrStackSize(int amount) {
		if (this.getHasStack())
			this.removeCount += Math.min(amount, this.getStack().getCount());
		return super.decrStackSize(amount);
	}

	@Override
	public ItemStack onTake(EntityPlayer player, ItemStack stack) {
		this.onCrafting(stack);
		super.onTake(player, stack);
		return stack;
	}

	@Override
	protected void onCrafting(ItemStack stack, int amount) {
		this.removeCount += amount;
		this.onCrafting(stack);
	}

	@Override
	protected void onCrafting(ItemStack stack) {
		stack.onCrafting(this.player.world, this.player, this.removeCount);

		if (!this.player.world.isRemote) {
			int i = this.removeCount;
			float f = FurnaceRecipes.instance().getSmeltingExperience(stack);

			if (f == 0.0F) {
				i = 0;
			} else if (f < 1.0F) {
				int j = MathHelper.floor((float) i * f);
				
				if (j < MathHelper.ceil((float) i * f) && Math.random() < (double) ((float) i * f - (float) j))
					j++;

				i = j;
			}

			while (i > 0) {
				int k = EntityXPOrb.getXPSplit(i);
				i -= k;
				this.player.world.spawnEntity(new EntityXPOrb(this.player.world, this.player.posX,
						this.player.posY + 0.5D, this.player.posZ + 0.5D, k));
			}
		}

		this.removeCount = 0;

		FMLCommonHandler.instance().firePlayerSmeltedEvent(this.player, stack);

		if (stack.getItem() == Items.IRON_INGOT)
			this.player.addStat(AchievementList.ACQUIRE_IRON);
		else if (stack.getItem() == Items.COOKED_FISH)
			this.player.addStat(AchievementList.COOK_FISH);
	}

}
