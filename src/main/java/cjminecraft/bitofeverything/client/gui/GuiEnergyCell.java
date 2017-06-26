package cjminecraft.bitofeverything.client.gui;

import java.io.IOException;

import cjminecraft.bitofeverything.Reference;
import cjminecraft.bitofeverything.container.ContainerEnergyCell;
import cjminecraft.bitofeverything.tileentity.TileEntityEnergyCell;
import cjminecraft.bitofeverything.util.Utils;
import cjminecraft.core.CJCore;
import cjminecraft.core.client.gui.EnergyBar;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiEnergyCell extends GuiContainer {

	public static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/container/energy_cell.png");
	
	private TileEntityEnergyCell te;
	private IInventory playerInv;
	
	public GuiEnergyCell(IInventory playerInv, TileEntityEnergyCell te) {
		super(new ContainerEnergyCell(playerInv, te));
		
		this.xSize = 176;
		this.ySize = 166;
		
		this.te = te;
		this.playerInv = playerInv;
	}
	
	@Override
	public void initGui() {
		//Energy Bar x = (this.width / 2 - this.xSize / 2) + 79, y = (this.height / 2 - this.ySize / 2)
		//width = 18, height = 60
		super.initGui();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(TEXTURE);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}

}
