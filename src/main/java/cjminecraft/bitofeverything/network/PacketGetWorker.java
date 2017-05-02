package cjminecraft.bitofeverything.network;

import cjminecraft.bitofeverything.capabilties.IWork;
import cjminecraft.bitofeverything.init.ModCapabilities;
import cjminecraft.bitofeverything.util.Utils;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

/**
 * A network message which will receive data about a {@link TileEntity} from the
 * {@link Side#SERVER}
 * 
 * @author CJMinecraft
 *
 */
public class PacketGetWorker implements IMessage {

	/**
	 * States whether the message is valid or not
	 */
	private boolean messageValid;

	/**
	 * The variables to send
	 */
	private BlockPos pos;
	private EnumFacing side;

	private String className;
	private String cooldownFieldName;
	private String maxCooldownFieldName;

	/**
	 * Default constructor used for registration
	 */
	public PacketGetWorker() {
		this.messageValid = false;
	}

	/**
	 * Create a packet which will retrieve
	 * {@link ModCapabilities#CAPABILITY_WORKER} details from the
	 * {@link TileEntity} on the {@link Side#SERVER}
	 * 
	 * @param pos
	 *            The position of the {@link TileEntity}
	 * @param side
	 *            The side of the {@link TileEntity} for use with
	 *            {@link Capability}
	 * @param className
	 *            The name of the class the data will be stored in
	 * @param cooldownFieldName
	 *            The name of the cooldown field which will store the cooldown
	 *            data
	 * @param maxCooldownFieldName
	 *            The name of the max cooldown field which will store the max
	 *            cooldown data
	 */
	public PacketGetWorker(BlockPos pos, EnumFacing side, String className, String cooldownFieldName,
			String maxCooldownFieldName) {
		this.pos = pos;
		this.side = side;
		this.className = className;
		this.cooldownFieldName = cooldownFieldName;
		this.maxCooldownFieldName = maxCooldownFieldName;
		this.messageValid = true;
	}

	/**
	 * Read data from the {@link ByteBuf} provided
	 */
	@Override
	public void fromBytes(ByteBuf buf) {
		try {
			this.pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
			this.side = EnumFacing.byName(ByteBufUtils.readUTF8String(buf));
			this.className = ByteBufUtils.readUTF8String(buf);
			this.cooldownFieldName = ByteBufUtils.readUTF8String(buf);
			this.maxCooldownFieldName = ByteBufUtils.readUTF8String(buf);
		} catch (IndexOutOfBoundsException ioe) {
			Utils.getLogger().catching(ioe);
			return;
		}
		this.messageValid = true;
	}

	/**
	 * Write data to the {@link ByteBuf} provided
	 */
	@Override
	public void toBytes(ByteBuf buf) {
		if (!this.messageValid)
			return;
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
		ByteBufUtils.writeUTF8String(buf, this.side.getName2());
		ByteBufUtils.writeUTF8String(buf, this.className);
		ByteBufUtils.writeUTF8String(buf, this.cooldownFieldName);
		ByteBufUtils.writeUTF8String(buf, this.maxCooldownFieldName);
	}

	/**
	 * Handles the {@link PacketGetWorker} message
	 * 
	 * @author CJMinecraft
	 *
	 */
	public static class Handler implements IMessageHandler<PacketGetWorker, IMessage> {

		/**
		 * Adds a task to the server handler to process our message
		 */
		@Override
		public IMessage onMessage(PacketGetWorker message, MessageContext ctx) {
			if (!message.messageValid && ctx.side != Side.SERVER)
				return null;
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler)
					.addScheduledTask(() -> processMessage(message, ctx));
			return null;
		}

		/**
		 * Gets all information required from the {@link TileEntity} at the
		 * given pos
		 * 
		 * @param message
		 *            Which holds the data regarding {@link BlockPos} and side
		 * @param ctx
		 *            The message context
		 */
		void processMessage(PacketGetWorker message, MessageContext ctx) {
			TileEntity te = ctx.getServerHandler().playerEntity.getServerWorld().getTileEntity(message.pos);
			if (te == null)
				return;
			if (!te.hasCapability(ModCapabilities.CAPABILITY_WORKER, message.side))
				return;
			IWork worker = te.getCapability(ModCapabilities.CAPABILITY_WORKER, message.side);
			PacketHandler.INSTANCE.sendTo(
					new PacketReturnWorker(worker.getWorkDone(), worker.getMaxWork(), message.className,
							message.cooldownFieldName, message.maxCooldownFieldName),
					ctx.getServerHandler().playerEntity);
		}
	}

}
