package cjminecraft.bitofeverything.client.gui;

import cjminecraft.bitofeverything.Reference;
import cjminecraft.bitofeverything.container.ContainerEnergyCell;
import cjminecraft.bitofeverything.tileentity.TileEntityEnergyCell;
import cjminecraft.core.client.gui.GuiBase;
import cjminecraft.core.client.gui.element.ElementEnergyBar;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiEnergyCell extends GuiBase {

	public static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID,
			"textures/gui/container/energy_cell.png");

	private TileEntityEnergyCell te;

	public GuiEnergyCell(IInventory playerInv, TileEntityEnergyCell te) {
		super(new ContainerEnergyCell(playerInv, te), TEXTURE);
		setGuiSize(176, 166);
		this.te = te;
		this.name = "container.energy_cell";
	}

	@Override
	public void initGui() {
		super.initGui();
		addElement(new ElementEnergyBar(this, 79, 16, 18, 56).shouldSync(this.te.getPos(), null));
	}

}
