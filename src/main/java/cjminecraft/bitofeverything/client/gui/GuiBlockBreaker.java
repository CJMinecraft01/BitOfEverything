package cjminecraft.bitofeverything.client.gui;

import cjminecraft.bitofeverything.Reference;
import cjminecraft.bitofeverything.container.ContainerBlockBreaker;
import cjminecraft.bitofeverything.tileentity.TileEntityBlockBreaker;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiBlockBreaker extends GuiContainer {

	private TileEntityBlockBreaker te;
	private IInventory playerInv;
	
	public GuiBlockBreaker(IInventory playerInv, TileEntityBlockBreaker te) {
		super(new ContainerBlockBreaker(playerInv, te));
		
		this.xSize = 176;
		this.ySize = 166;
		
		this.te = te;
		this.playerInv = playerInv;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MODID, "textures/gui/container/block_breaker.png"));
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String s = I18n.format("container.block_breaker");
		this.mc.fontRendererObj.drawString(s, this.xSize / 2 - this.mc.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
		this.mc.fontRendererObj.drawString(this.playerInv.getDisplayName().getFormattedText(), 8, 72, 4210752);
	}

}
