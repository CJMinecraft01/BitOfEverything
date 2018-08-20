package cjminecraft.bitofeverything.tileentity;

import cjminecraft.bitofeverything.blocks.BlockDoubleFurnace;
import cjminecraft.bitofeverything.capabilties.Worker;
import cjminecraft.bitofeverything.init.ModCapabilities;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;

public class TileEntityDoubleFurnace extends TileEntitySidedInventory implements ITickable {
	
	private Worker burnWorker;
	private Worker cookWorker;
	
	public TileEntityDoubleFurnace() {
		super(new int[] { 4, 5 }, new int[] { 0, 1 }, new int[] { 2, 3 }, new int[] { 2, 3 }, new int[] { 2, 3 }, new int[] { 2, 3 });
		this.burnWorker = new Worker(0, () -> {
			if (!this.world.isRemote)
				if (this.cookWorker.getMaxWork() > 0)
					this.cookWorker.doWork();
		}, () -> {
			if (!this.world.isRemote) {
				this.burnWorker.setMaxCooldown(0);
				this.world.setBlockState(this.pos, this.world.getBlockState(this.pos).withProperty(BlockDoubleFurnace.ACTIVATED, false), 3);
				this.cookWorker.setMaxCooldown(0);
			}
		});
		this.cookWorker = new Worker(0, () -> {
			if (!this.world.isRemote) {
				if (!canSmelt())
					this.cookWorker.setReversed(true);
				else
					this.cookWorker.setReversed(false);
			}
		}, () -> {
			if (!this.world.isRemote) {
				this.cookWorker.setMaxCooldown(0);
				ItemStack toSmelt = this.handler.extractItem(0, 1, false);
				ItemStack result = FurnaceRecipes.instance().getSmeltingResult(this.handler.getStackInSlot(0)).copy();
				ItemStack leftOver = this.handler.insertItem(4, result, false);
				if (!leftOver.isEmpty())
					this.handler.insertItem(5, leftOver, false);
			}
		});
	}
	
	@Override
	public void update() {
		if (!this.world.isRemote) {
			if (this.burnWorker.getMaxWork() > 0)
				this.burnWorker.doWork();
			if (canSmelt()) {
				if (this.cookWorker.getMaxWork() == 0 && this.burnWorker.getMaxWork() != 0) {
					this.cookWorker.setMaxCooldown(200);
					this.cookWorker.setReversed(false);
				}
				if (TileEntityFurnace.isItemFuel(this.handler.getStackInSlot(2)) && this.burnWorker.getMaxWork() == 0) {
					this.world.setBlockState(this.pos, this.world.getBlockState(this.pos).withProperty(BlockDoubleFurnace.ACTIVATED, true), 3);
					this.burnWorker.setMaxCooldown(TileEntityFurnace.getItemBurnTime(this.handler.getStackInSlot(2)));
					this.handler.extractItem(2, 1, false);
				}
			}
		}
	}
	
	@Override
	protected void onSlotChanged(int slot) {
		if (slot == 0 || slot == 1)
			shiftSlot(1, 0);
		if (slot == 2 || slot == 3)
			shiftSlot(3, 2);
		if (slot == 4 || slot == 5)
			shiftSlot(4, 5);
	}
	
	 /**
	 * Shifts items from slot A to slot B
	 * 
	 * @param slotA
	 *            The index of the slot to shift from
	 * @param slotB
	 *            The index of the slot to shift to
	 */
	private void shiftSlot(int slotA, int slotB) {
		if (this.handler.getStackInSlot(slotB).isEmpty()) {
			if (!this.handler.getStackInSlot(slotA).isEmpty()) {
				this.handler.setStackInSlotInternal(slotB, this.handler.getStackInSlot(slotA));
				this.handler.setStackInSlotInternal(slotA, ItemStack.EMPTY);
			}
		} else {
			if (this.handler.getStackInSlot(slotA).isItemEqual(this.handler.getStackInSlot(slotB))) {
				int toExtract = this.handler.getStackInSlot(slotB).getMaxStackSize()
						- this.handler.getStackInSlot(slotB).getCount();
				if (toExtract > 0)
					this.handler.insertItemInternal(slotB, this.handler.extractItemInternal(slotA, toExtract, false), false);
			}
		}
	}
	
	@Override
	protected boolean isStackValid(int slot, ItemStack stack) {
		if (slot == 2 || slot == 3)
			return TileEntityFurnace.isItemFuel(stack) || SlotFurnaceFuel.isBucket(stack) && stack.getItem() != Items.BUCKET;
		if (slot == 4 || slot == 5)
			return false;
		return super.isStackValid(slot, stack);
	}
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		return oldState.getBlock() != newState.getBlock();
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setTag("burnWorker", this.burnWorker.serializeNBT());
		nbt.setTag("cookWorker", this.cookWorker.serializeNBT());
		return super.writeToNBT(nbt);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.burnWorker.deserializeNBT(nbt.getCompoundTag("burnWorker"));
		this.cookWorker.deserializeNBT(nbt.getCompoundTag("cookWorker"));
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == ModCapabilities.CAPABILITY_WORKER)
			return true;
		return super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == ModCapabilities.CAPABILITY_WORKER) {
			if (facing == EnumFacing.NORTH)
				return (T) this.burnWorker;
			else if (facing == EnumFacing.SOUTH)
				return (T) this.cookWorker;
		}
		return super.getCapability(capability, facing);
	}
	
	private boolean canSmelt() {
		ItemStack result = FurnaceRecipes.instance().getSmeltingResult(this.handler.getStackInSlot(0)).copy();
		if (result.isEmpty())
			return false;
		if (this.handler.getStackInSlot(4).isEmpty())
			return true;
		if (!this.handler.getStackInSlot(4).isItemEqual(result)) {
			if (this.handler.getStackInSlot(5).isEmpty())
				return true;
			if (!this.handler.getStackInSlot(5).isItemEqual(result))
				return false;
			int count = this.handler.getStackInSlot(5).getCount() + result.getCount();
			return count <= this.handler.getSlotLimit(5) && count <= this.handler.getStackInSlot(5).getMaxStackSize();
		}
		int count = this.handler.getStackInSlot(4).getCount() + result.getCount();
		return count <= this.handler.getSlotLimit(4) && count <= this.handler.getStackInSlot(4).getMaxStackSize();
	}

}
