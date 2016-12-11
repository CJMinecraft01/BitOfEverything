package cjminecraft.api.gui;

/**
 * Used to get the mouse location relative to the texture coordinates
 * @author CJMinecraft
 *
 */
public class GUIMouse {
	
	private int width, height, textureXSize, textureYSize, mouseX, mouseY;

	/**
	 * Used to get the mouse location relative the texture coordinates
	 * @param width the width of the gui
	 * @param height the height of the gui
	 * @param textureXSize the texture's X size
	 * @param textureYSize the texture's Y size
	 * @param mouseX the mouse's X position
	 * @param mouseY the mouse's Y position
	 */
	public GUIMouse(int width, int height, int textureXSize, int textureYSize, int mouseX, int mouseY) {
		this.width = width;
		this.height = height;
		this.textureXSize = textureXSize;
		this.textureYSize = textureYSize;
		this.mouseX = mouseX;
		this.mouseY = mouseY;
	}
	
	/**
	 * This will calculate the X position of the mouse in relation to the texture
	 * @return The mouse X position in relation to the texture
	 */
	public int getActualMouseX() {
		int width = (this.width - this.textureXSize) / 2;
		int actualMouseX = mouseX - width;
		return actualMouseX;
	}
	
	/**
	 * This will calculate the Y position of the mouse in relation to the texture
	 * @return The mouse Y position in relation to the texture
	 */
	public int getActualMouseY() {
		int height = (this.height - this.textureYSize) / 2;
		int actualMouseY = mouseY - height;
		return actualMouseY;
	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getTextureXSize() {
		return textureXSize;
	}

	public void setTextureXSize(int textureXSize) {
		this.textureXSize = textureXSize;
	}

	public int getTextureYSize() {
		return textureYSize;
	}

	public void setTextureYSize(int textureYSize) {
		this.textureYSize = textureYSize;
	}

	public int getMouseX() {
		return mouseX;
	}

	public void setMouseX(int mouseX) {
		this.mouseX = mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}

	public void setMouseY(int mouseY) {
		this.mouseY = mouseY;
	}
	
}
