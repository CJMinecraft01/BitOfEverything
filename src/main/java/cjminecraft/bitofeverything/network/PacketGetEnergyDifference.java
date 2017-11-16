package cjminecraft.bitofeverything.network;

import cjminecraft.bitofeverything.tileentity.TileEntityEnergyCell;
import cjminecraft.bitofeverything.util.Utils;
import cjminecraft.core.util.NetworkUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

/**
 * A packet which will ask to send the energy difference from the server to the
 * client
 * 
 * @author CJMinecraft
 *
 */
public class PacketGetEnergyDifference implements IMessage {

	/**
	 * Is the message valid?
	 */
	private boolean messageValid;

	/**
	 * The data to send
	 */

	private BlockPos pos;
	private String className;
	private String energyDifferenceFieldName;

	/**
	 * Default constructor. Used for registration
	 */
	public PacketGetEnergyDifference() {
		this.messageValid = false;
	}

	/**
	 * Create a packet requesting the server to send the client data about the
	 * energy difference
	 * 
	 * @param pos
	 *            The position of the {@link TileEntity} which holds energy
	 * @param className
	 *            The name of the class with the field to update in
	 * @param energyDifferenceFieldName
	 *            The name of the field to update
	 */
	public PacketGetEnergyDifference(BlockPos pos, String className, String energyDifferenceFieldName) {
		this.pos = pos;
		this.className = className;
		this.energyDifferenceFieldName = energyDifferenceFieldName;
		this.messageValid = true;
	}

	/**
	 * Get the data from the byte buffer (SERVER SIDE)
	 */
	@Override
	public void fromBytes(ByteBuf buf) {
		try {
			this.pos = NetworkUtils.readBlockPos(buf);
			this.className = ByteBufUtils.readUTF8String(buf);
			this.energyDifferenceFieldName = ByteBufUtils.readUTF8String(buf);
		} catch (IndexOutOfBoundsException ioe) {
			Utils.getLogger().catching(ioe);
			return;
		}
		this.messageValid = true;
	}

	/**
	 * Write data to the byte buffer (CLIENT SIDE)
	 */
	@Override
	public void toBytes(ByteBuf buf) {
		if (!this.messageValid)
			return;
		NetworkUtils.writeBlockPos(buf, this.pos);
		ByteBufUtils.writeUTF8String(buf, this.className);
		ByteBufUtils.writeUTF8String(buf, this.energyDifferenceFieldName);
	}

	/**
	 * Handle the {@link PacketGetEnergyDifference} message
	 * 
	 * @author CJMinecraft
	 *
	 */
	public static class Handler implements IMessageHandler<PacketGetEnergyDifference, IMessage> {

		/**
		 * When we receive the message
		 */
		@Override
		public IMessage onMessage(PacketGetEnergyDifference message, MessageContext ctx) {
			if (!message.messageValid && ctx.side != Side.SERVER)
				return null;
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler)
					.addScheduledTask(() -> processMessage(message, ctx));
			return null;
		}

		/**
		 * Process the message
		 * 
		 * @param message
		 *            The message
		 * @param ctx
		 *            The message context
		 */
		void processMessage(PacketGetEnergyDifference message, MessageContext ctx) {
			TileEntity te = ctx.getServerHandler().player.getServerWorld().getTileEntity(message.pos);
			if (te == null)
				return;
			if (!(te instanceof TileEntityEnergyCell))
				return;
			PacketHandler.INSTANCE.sendTo(new PacketReturnEnergyDifference(((TileEntityEnergyCell) te).energyDifference,
					message.className, message.energyDifferenceFieldName), ctx.getServerHandler().player);
		}

	}

}
