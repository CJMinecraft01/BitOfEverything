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

public class PacketGetEnergyDifference implements IMessage {

	private boolean messageValid;

	private BlockPos pos;
	private String className;
	private String energyDifferenceFieldName;

	public PacketGetEnergyDifference() {
		this.messageValid = false;
	}

	public PacketGetEnergyDifference(BlockPos pos, String className, String energyDifferenceFieldName) {
		this.pos = pos;
		this.className = className;
		this.energyDifferenceFieldName = energyDifferenceFieldName;
		this.messageValid = true;
	}

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

	@Override
	public void toBytes(ByteBuf buf) {
		if (!this.messageValid)
			return;
		NetworkUtils.writeBlockPos(buf, this.pos);
		ByteBufUtils.writeUTF8String(buf, this.className);
		ByteBufUtils.writeUTF8String(buf, this.energyDifferenceFieldName);
	}

	public static class Handler implements IMessageHandler<PacketGetEnergyDifference, IMessage> {

		@Override
		public IMessage onMessage(PacketGetEnergyDifference message, MessageContext ctx) {
			if (!message.messageValid && ctx.side != Side.SERVER)
				return null;
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler)
					.addScheduledTask(() -> processMessage(message, ctx));
			return null;
		}

		void processMessage(PacketGetEnergyDifference message, MessageContext ctx) {
			TileEntity te = ctx.getServerHandler().playerEntity.getServerWorld().getTileEntity(message.pos);
			if (te == null)
				return;
			if (!(te instanceof TileEntityEnergyCell))
				return;
			PacketHandler.INSTANCE.sendTo(new PacketReturnEnergyDifference(((TileEntityEnergyCell) te).energyDifference,
					message.className, message.energyDifferenceFieldName), ctx.getServerHandler().playerEntity);
		}

	}

}
