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

/**
 * A message holding the energy difference requested by the client and now
 * giving the information back to the client from the server
 * 
 * @author CJMinecraft
 *
 */
public class PacketReturnEnergyDifference implements IMessage {

	/**
	 * Is the message valid?
	 */
	private boolean messageValid;

	private long energyDifference;
	private String className;
	private String energyDifferenceFieldName;

	/**
	 * Default Constructor (for registration)
	 */
	public PacketReturnEnergyDifference() {
		this.messageValid = false;
	}

	/**
	 * Create a packet giving the data provided to the client
	 * 
	 * @param energyDifference2
	 *            The energy difference data
	 * @param className
	 *            The name of the class which holds the field to set
	 * @param energyDifferenceFieldName
	 *            The name of the field to set the data onto
	 */
	public PacketReturnEnergyDifference(long energyDifference2, String className, String energyDifferenceFieldName) {
		this.energyDifference = energyDifference2;
		this.className = className;
		this.energyDifferenceFieldName = energyDifferenceFieldName;
		this.messageValid = true;
	}

	/**
	 * Read the data from the byte buffer (CLIENT SIDE)
	 */
	@Override
	public void fromBytes(ByteBuf buf) {
		try {
			this.energyDifference = buf.readLong();
			this.className = ByteBufUtils.readUTF8String(buf);
			this.energyDifferenceFieldName = ByteBufUtils.readUTF8String(buf);
		} catch (IndexOutOfBoundsException ioe) {
			Utils.getLogger().catching(ioe);
			return;
		}
		this.messageValid = true;
	}

	/**
	 * Write the data to the byte buffer (SERVER SIDE)
	 */
	@Override
	public void toBytes(ByteBuf buf) {
		if (!this.messageValid)
			return;
		buf.writeLong(this.energyDifference);
		ByteBufUtils.writeUTF8String(buf, this.className);
		ByteBufUtils.writeUTF8String(buf, this.energyDifferenceFieldName);
	}

	/**
	 * Handle the {@link PacketReturnEnergyDifference} message
	 * 
	 * @author CJMinecraft
	 *
	 */
	public static class Handler implements IMessageHandler<PacketReturnEnergyDifference, IMessage> {

		/**
		 * Handle the message when received
		 */
		@Override
		public IMessage onMessage(PacketReturnEnergyDifference message, MessageContext ctx) {
			if (!message.messageValid && ctx.side != Side.CLIENT)
				return null;
			Minecraft.getMinecraft().addScheduledTask(() -> processMessage(message));
			return null;
		}

		/**
		 * Process the message
		 * 
		 * @param message
		 *            The message to process
		 */
		void processMessage(PacketReturnEnergyDifference message) {
			try {
				Class clazz = Class.forName(message.className);
				Field energyDifferenceField = clazz.getDeclaredField(message.energyDifferenceFieldName);
				energyDifferenceField.setLong(clazz, message.energyDifference);
			} catch (Exception e) {
				Utils.getLogger().catching(e);
			}
		}

	}

}
