package cjminecraft.bitofeverything.client.gui;

import cjminecraft.bitofeverything.Reference;
import cjminecraft.bitofeverything.container.ContainerEnergyCell;
import cjminecraft.bitofeverything.tileentity.TileEntityEnergyCell;
import cjminecraft.core.client.gui.GuiBase;
import cjminecraft.core.client.gui.element.ElementEnergyBar;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

/**
 * The gui for the energy cell
 * 
 * @author CJMinecraft
 *
 */
public class GuiEnergyCell extends GuiBase {

	/**
	 * The background texture of the energy cell
	 */
	public static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID,
			"textures/gui/container/energy_cell.png");

	/**
	 * The {@link TileEntityEnergyCell} for the syncing of the
	 * {@link ElementEnergyBar}
	 */
	private TileEntityEnergyCell te;

	/**
	 * Initialise the gui for the energy cell
	 * 
	 * @param playerInv
	 *            The player's inventory (for the container)
	 * @param te
	 *            The {@link TileEntityEnergyCell} for syncing and the container
	 */
	public GuiEnergyCell(IInventory playerInv, TileEntityEnergyCell te) {
		// Sets the background texture
		super(new ContainerEnergyCell(playerInv, te), TEXTURE);
		setGuiSize(176, 166); // Set the gui size
		this.te = te;
		this.name = "container.energy_cell"; // Will automatically be localised
												// using CJCore
	}

	/**
	 * Where we add all our gui elements
	 */
	@Override
	public void initGui() {
		super.initGui();
		addElement(new ElementEnergyBar(this, 79, 16, 18, 56).shouldSync(this.te.getPos(), null));
	}

}
