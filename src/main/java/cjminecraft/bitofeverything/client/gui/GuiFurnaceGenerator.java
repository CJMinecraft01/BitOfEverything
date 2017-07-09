package cjminecraft.bitofeverything.client.gui;

import cjminecraft.bitofeverything.Reference;
import cjminecraft.bitofeverything.client.gui.ProgressBar.ProgressBarDirection;
import cjminecraft.bitofeverything.container.ContainerFurnaceGenerator;
import cjminecraft.bitofeverything.network.PacketGetWorker;
import cjminecraft.bitofeverything.network.PacketHandler;
import cjminecraft.bitofeverything.tileentity.TileEntityFurnaceGenerator;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiFurnaceGenerator extends GuiContainer {
	
	public static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/container/furnace_generator.png");
	
	private TileEntityFurnaceGenerator te;
	private IInventory playerInv;
	
	public static int cooldown, maxCooldown;
	
	public static int sync = 0;
	
	private ProgressBar progressBar;
	
	public GuiFurnaceGenerator(IInventory playerInv, TileEntityFurnaceGenerator te) {
		super(new ContainerFurnaceGenerator(playerInv, te));
		
		this.xSize = 176;
		this.ySize = 166;
		
		this.te = te;
		this.playerInv = playerInv;
		
		this.progressBar = new ProgressBar(TEXTURE, ProgressBarDirection.UP_TO_DOWN, 14, 13, 81, 53, 176, 0);
	}
	
	@Override
	public void initGui() {
		super.initGui();
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(TEXTURE);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String s = I18n.format("container.furnace_generator");
		this.mc.fontRendererObj.drawString(s, this.xSize / 2 - this.mc.fontRendererObj.getStringWidth(s) / 2, 6, 4210752); //Draws the block breaker name in the center on the top of the gui
		this.mc.fontRendererObj.drawString(this.playerInv.getDisplayName().getFormattedText(), 8, 73, 4210752); //The player's inventory name
		
		if(cooldown == 0)
			cooldown = maxCooldown;
		this.progressBar.setMin(cooldown).setMax(maxCooldown);
		this.progressBar.draw(this.mc);
		sync++;
		sync %= 10;
		if(sync == 0) {
			PacketHandler.INSTANCE.sendToServer(new PacketGetWorker(this.te.getPos(), this.mc.player.getAdjustedHorizontalFacing(), "cjminecraft.bitofeverything.client.gui.GuiFurnaceGenerator", "cooldown", "maxCooldown"));
		}
	}

}
