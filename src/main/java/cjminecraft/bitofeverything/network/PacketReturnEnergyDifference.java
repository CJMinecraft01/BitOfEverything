package cjminecraft.bitofeverything.network;

import java.lang.reflect.Field;

import cjminecraft.bitofeverything.util.Utils;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PacketReturnEnergyDifference implements IMessage {

	private boolean messageValid;

	private int energyDifference;
	private String className;
	private String energyDifferenceFieldName;

	public PacketReturnEnergyDifference() {
		this.messageValid = false;
	}

	public PacketReturnEnergyDifference(int energyDifference, String className, String energyDifferenceFieldName) {
		this.energyDifference = energyDifference;
		this.className = className;
		this.energyDifferenceFieldName = energyDifferenceFieldName;
		this.messageValid = true;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		try {
			this.energyDifference = buf.readInt();
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
		buf.writeInt(this.energyDifference);
		ByteBufUtils.writeUTF8String(buf, this.className);
		ByteBufUtils.writeUTF8String(buf, this.energyDifferenceFieldName);
	}

	public static class Handler implements IMessageHandler<PacketReturnEnergyDifference, IMessage> {

		@Override
		public IMessage onMessage(PacketReturnEnergyDifference message, MessageContext ctx) {
			if (!message.messageValid && ctx.side != Side.CLIENT)
				return null;
			Minecraft.getMinecraft().addScheduledTask(() -> processMessage(message));
			return null;
		}

		void processMessage(PacketReturnEnergyDifference message) {
			try {
				Class clazz = Class.forName(message.className);
				Field energyDifferenceField = clazz.getDeclaredField(message.energyDifferenceFieldName);
				energyDifferenceField.setInt(clazz, message.energyDifference);
			} catch (Exception e) {
				Utils.getLogger().catching(e);
			}
		}

	}

}
