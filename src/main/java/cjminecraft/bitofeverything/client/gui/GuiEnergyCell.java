package cjminecraft.bitofeverything.client.gui;

import java.text.NumberFormat;
import java.util.Arrays;

import cjminecraft.bitofeverything.Reference;
import cjminecraft.bitofeverything.container.ContainerEnergyCell;
import cjminecraft.bitofeverything.network.PacketGetEnergyDifference;
import cjminecraft.bitofeverything.network.PacketHandler;
import cjminecraft.bitofeverything.tileentity.TileEntityEnergyCell;
import cjminecraft.core.client.gui.EnergyBar;
import cjminecraft.core.config.CJCoreConfig;
import cjminecraft.core.energy.EnergyUnits;
import cjminecraft.core.energy.EnergyUtils;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

public class GuiEnergyCell extends GuiContainer {

	public static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID,
			"textures/gui/container/energy_cell.png");

	public static int sync = 0;

	public static int energyDifference = 0;

	private TileEntityEnergyCell te;
	private IInventory playerInv;

	private EnergyBar energyBar;

	public GuiEnergyCell(IInventory playerInv, TileEntityEnergyCell te) {
		super(new ContainerEnergyCell(playerInv, te));

		this.xSize = 176;
		this.ySize = 166;

		this.te = te;
		this.playerInv = playerInv;
	}

	@Override
	public void initGui() {
		// Energy Bar x = (this.width / 2 - this.xSize / 2) + 79, y =
		// (this.height / 2 - this.ySize / 2) + 12
		// width = 18, height = 60
		this.energyBar = new EnergyBar(0, (this.width / 2 - this.xSize / 2) + 79,
				(this.height / 2 - this.ySize / 2) + 12, 18, 60, 0, 0);
		this.buttonList.add(energyBar);
		super.initGui();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(TEXTURE);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

		sync++;
		sync %= 10;
		if (sync == 0) {
			this.energyBar.syncData(this.te.getPos(), EnumFacing.NORTH);
			PacketHandler.INSTANCE.sendToServer(new PacketGetEnergyDifference(this.te.getPos(),
					"cjminecraft.bitofeverything.client.gui.GuiEnergyCell", "energyDifference"));
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		if (this.energyBar.isMouseOver()) {
			if (CJCoreConfig.MULTIMETER_SIMPLIFY_ENERGY) {
				this.drawHoveringText(
						Arrays.asList(
								EnergyUtils.getEnergyAsString(this.energyBar.energy, CJCoreConfig.DEFAULT_ENERGY_UNIT)
										+ (CJCoreConfig.MULTIMETER_SHOW_CAPACITY
												? " / " + EnergyUtils.getEnergyAsString(this.energyBar.capacity,
														CJCoreConfig.DEFAULT_ENERGY_UNIT)
												: ""),
								this.energyDifference < 0
										? TextFormatting.RED
												+ String.valueOf(EnergyUtils.convertEnergy(EnergyUnits.FORGE_ENERGY,
														CJCoreConfig.DEFAULT_ENERGY_UNIT, this.energyDifference))
										: this.energyDifference > 0 ? TextFormatting.GREEN + "+"
												+ String.valueOf(EnergyUtils.convertEnergy(EnergyUnits.FORGE_ENERGY,
														CJCoreConfig.DEFAULT_ENERGY_UNIT, this.energyDifference))
												: "0"),
						mouseX - ((this.width - this.xSize) / 2), mouseY - ((this.height - this.ySize) / 2));
			} else {
				this.drawHoveringText(Arrays.asList(
						NumberFormat.getInstance().format(this.energyBar.energy) + " "
								+ CJCoreConfig.DEFAULT_ENERGY_UNIT.getSuffix()
								+ (CJCoreConfig.MULTIMETER_SHOW_CAPACITY
										? " / " + NumberFormat.getInstance().format(this.energyBar.capacity) + " "
												+ CJCoreConfig.DEFAULT_ENERGY_UNIT.getSuffix()
										: ""),
						this.energyDifference < 0 ? TextFormatting.RED
								+ String.valueOf(EnergyUtils.convertEnergy(EnergyUnits.FORGE_ENERGY,
										CJCoreConfig.DEFAULT_ENERGY_UNIT, this.energyDifference))
								: this.energyDifference > 0
										? TextFormatting.GREEN + "+"
												+ String.valueOf(EnergyUtils.convertEnergy(EnergyUnits.FORGE_ENERGY,
														CJCoreConfig.DEFAULT_ENERGY_UNIT, this.energyDifference))
										: "0"),
						mouseX - ((this.width - this.xSize) / 2), mouseY - ((this.height - this.ySize) / 2));
			}
		}
	}

}
