package cjminecraft.bitofeverything.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * The canvas tile entity which stores the blocks colour
 * @author CJMinecraft
 *
 */
public class TileEntityCanvas extends TileEntity {
	
	//The colour as an int
	private int colour;
	
	/**
	 * Initialise the colour to be white
	 */
	public TileEntityCanvas() {
		this.colour = 0xFFFFFF;
	}
	
	/**
	 * Read the colour from NBT data
	 */
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.colour = nbt.getInteger("colour");
	}
	
	/**
	 * Write the correct NBT data
	 */
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("colour", this.colour);
		return super.writeToNBT(nbt);
	}
	
	/**
	 * Get the colour of the canvas
	 * @return The colour of the canvas
	 */
	public int getColour() {
		return colour;
	}
	
	/**
	 * Set the colour of the canvas
	 * @param colour The new colour of the canvas
	 */
	public void setColour(int colour) {
		this.colour = colour;
	}
	
	/**
	 * The packet which is used to update the tile entity which holds all of the
	 * tileentities data
	 */
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		int metadata = getBlockMetadata();
		return new SPacketUpdateTileEntity(this.pos, metadata, nbt);
	}

	/**
	 * Reads the nbt when it receives a packet
	 */
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		this.readFromNBT(pkt.getNbtCompound());
	}

	/**
	 * Gets the nbt for a new packet
	 */
	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		return nbt;
	}

	/**
	 * Handles when you get an update
	 */
	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		this.readFromNBT(tag);
	}

	/**
	 * Gets the tile entities nbt with all of the data stored in it
	 */
	@Override
	public NBTTagCompound getTileData() {
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		return nbt;
	}

}
