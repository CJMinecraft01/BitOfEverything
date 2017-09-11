package cjminecraft.bitofeverything.client.gui;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Arrays;

import cjminecraft.bitofeverything.Reference;
import cjminecraft.bitofeverything.container.ContainerFurnaceGenerator;
import cjminecraft.bitofeverything.network.PacketGetWorker;
import cjminecraft.bitofeverything.network.PacketHandler;
import cjminecraft.bitofeverything.tileentity.TileEntityFurnaceGenerator;
import cjminecraft.bitofeverything.util.Utils;
import cjminecraft.core.CJCore;
import cjminecraft.core.client.gui.GuiBase;
import cjminecraft.core.client.gui.element.ElementEnergyBar;
import cjminecraft.core.client.gui.element.ElementProgressBar;
import cjminecraft.core.client.gui.element.ElementProgressBar.ProgressBarDirection;
import cjminecraft.core.config.CJCoreConfig;
import cjminecraft.core.energy.EnergyUtils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

public class GuiFurnaceGenerator extends GuiBase {

	public static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID,
			"textures/gui/container/furnace_generator.png");

	private TileEntityFurnaceGenerator te;

	public static int cooldown, maxCooldown = 0;

	public static int sync = 0;

	public GuiFurnaceGenerator(IInventory playerInv, TileEntityFurnaceGenerator te) {
		super(new ContainerFurnaceGenerator(playerInv, te), TEXTURE);
		setGuiSize(176, 166);

		this.te = te;
		this.name = "container.furnace_generator";
	}

	@Override
	public void initGui() {
		super.initGui();
		addElement(new ElementEnergyBar(this, 7, 16, 18, 54).shouldSync(this.te.getPos(), null));
		addElement(new ElementProgressBar(this, 81, 53, 14, 13).setDirection(ProgressBarDirection.UP_TO_DOWN)
				.setTextureUV(176, 0).setTexture(TEXTURE, 256, 256));
	}
	
	@Override
	protected void updateElementInformation() {
		super.updateElementInformation();
		if (cooldown == 0)
			cooldown = maxCooldown;
		((ElementProgressBar) this.elements.get(1)).setMin(cooldown).setMax(maxCooldown);
		sync++;
		sync %= 10;
		if (sync == 0) {
			PacketHandler.INSTANCE
					.sendToServer(new PacketGetWorker(this.te.getPos(), this.mc.player.getAdjustedHorizontalFacing(),
							"cjminecraft.bitofeverything.client.gui.GuiFurnaceGenerator", "cooldown", "maxCooldown"));
		}
	}

}
