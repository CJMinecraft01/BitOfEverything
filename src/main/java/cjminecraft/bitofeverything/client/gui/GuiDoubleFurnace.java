package cjminecraft.bitofeverything.client.gui;

import cjminecraft.bitofeverything.Reference;
import cjminecraft.bitofeverything.container.ContainerDoubleFurnace;
import cjminecraft.bitofeverything.network.PacketGetWorker;
import cjminecraft.bitofeverything.network.PacketHandler;
import cjminecraft.bitofeverything.tileentity.TileEntityDoubleFurnace;
import cjminecraft.core.client.gui.GuiBase;
import cjminecraft.core.client.gui.element.ElementProgressBar;
import cjminecraft.core.client.gui.element.ElementProgressBar.ProgressBarDirection;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

public class GuiDoubleFurnace extends GuiBase {

	public static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/container/double_furnace.png");
	
	private static int sync = 0;
	
	public static int burnMin = 1, burnMax = 1;
	public static int cookMin = 0, cookMax = 1;
	
	private TileEntityDoubleFurnace te;
	
	public GuiDoubleFurnace(EntityPlayer player, TileEntityDoubleFurnace te) {
		super(new ContainerDoubleFurnace(player, te), TEXTURE);
		setGuiSize(176, 166);
		this.te = te;
		this.name = "container.double_furnace";
	}
	
	@Override
	public void initGui() {
		super.initGui();
		addElement(new ElementProgressBar(this, 56, 36, 14, 14).setDirection(ProgressBarDirection.UP_TO_DOWN).setTextureUV(176, 0).setTexture(TEXTURE, 256, 256));
		addElement(new ElementProgressBar(this, 79, 34, 24, 18).setDirection(ProgressBarDirection.LEFT_TO_RIGHT).setTextureUV(176, 14).setTexture(TEXTURE, 256, 256));
	}
	
	@Override
	protected void updateElementInformation() {
		super.updateElementInformation();
		
		if (burnMin == 0)
			burnMin = burnMax;
		
		((ElementProgressBar) this.elements.get(0)).setMin(burnMin).setMax(burnMax);
		((ElementProgressBar) this.elements.get(1)).setMin(cookMin).setMax(cookMax);
		
		sync++;
		sync %= 10;
		if (sync == 0) {
			PacketHandler.INSTANCE.sendToServer(new PacketGetWorker(this.te.getPos(), EnumFacing.NORTH, "cjminecraft.bitofeverything.client.gui.GuiDoubleFurnace", "burnMin", "burnMax"));
			PacketHandler.INSTANCE.sendToServer(new PacketGetWorker(this.te.getPos(), EnumFacing.SOUTH, "cjminecraft.bitofeverything.client.gui.GuiDoubleFurnace", "cookMin", "cookMax"));
		}
	}

}
