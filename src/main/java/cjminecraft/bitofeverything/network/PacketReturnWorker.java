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
 * A packet which will update fields in a class based on the information
 * provided
 * 
 * @author CJMinecraft
 *
 */
public class PacketReturnWorker implements IMessage {

	/**
	 * States whether the message is valid or not
	 */
	private boolean messageValid;

	/**
	 * The variables we want to send
	 */
	private int cooldown;
	private int maxCooldown;

	private String className;
	private String cooldownFieldName;
	private String maxCooldownFieldName;

	/**
	 * Default constructor used for registration
	 */
	public PacketReturnWorker() {
		this.messageValid = false;
	}

	/**
	 * Create a packet which will update the cooldown and maxCooldown fields in
	 * the given class using Java Reflection
	 * 
	 * @param cooldown
	 *            The cooldown
	 * @param maxCooldown
	 *            The maxCooldown
	 * @param className
	 *            The name of the class which holds the cooldown and max
	 *            cooldown fields
	 * @param cooldownFieldName
	 *            The name of the cooldown field
	 * @param maxCooldownFieldName
	 *            The name of the max cooldown field
	 */
	public PacketReturnWorker(int cooldown, int maxCooldown, String className, String cooldownFieldName,
			String maxCooldownFieldName) {
		this.cooldown = cooldown;
		this.maxCooldown = maxCooldown;
		this.className = className;
		this.cooldownFieldName = cooldownFieldName;
		this.maxCooldownFieldName = maxCooldownFieldName;
		this.messageValid = true;
	}

	/**
	 * Reads all of the data from the provided {@link ByteBuf}
	 */
	@Override
	public void fromBytes(ByteBuf buf) {
		try {
			this.cooldown = buf.readInt();
			this.maxCooldown = buf.readInt();
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
	 * Writes all of the data to the provided {@link ByteBuf}
	 */
	@Override
	public void toBytes(ByteBuf buf) {
		if (!this.messageValid)
			return;
		buf.writeInt(this.cooldown);
		buf.writeInt(this.maxCooldown);
		ByteBufUtils.writeUTF8String(buf, this.className);
		ByteBufUtils.writeUTF8String(buf, this.cooldownFieldName);
		ByteBufUtils.writeUTF8String(buf, this.maxCooldownFieldName);
	}

	/**
	 * The message handler for the {@link PacketReturnWorker} message
	 * 
	 * @author CJMinecraft
	 *
	 */
	public static class Handler implements IMessageHandler<PacketReturnWorker, IMessage> {

		/**
		 * Schedules the task on the client which will process the message
		 */
		@Override
		public IMessage onMessage(PacketReturnWorker message, MessageContext ctx) {
			if (!message.messageValid && ctx.side != Side.CLIENT)
				return null;
			Minecraft.getMinecraft().addScheduledTask(() -> processMessage(message));
			return null;
		}

		/**
		 * Update the fields in the class provided using the data from the
		 * message
		 * 
		 * @param message
		 *            The message which holds the data
		 */
		void processMessage(PacketReturnWorker message) {
			try {
				Class clazz = Class.forName(message.className);
				Field cooldownField = clazz.getDeclaredField(message.cooldownFieldName);
				Field maxCooldownField = clazz.getDeclaredField(message.maxCooldownFieldName);
				cooldownField.setInt(clazz, message.cooldown);
				maxCooldownField.setInt(clazz, message.maxCooldown);
			} catch (Exception e) {
				Utils.getLogger().catching(e);
			}
		}

	}

}
