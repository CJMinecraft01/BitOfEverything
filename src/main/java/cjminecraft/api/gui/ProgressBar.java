package cjminecraft.api.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.Minecraft;

/**
 * This is used to create a progress bar for your GUI
 * @author CJMinecraft
 *
 */
public class ProgressBar {
	
	/**
	 * The progress bar will move from left to right
	 */
	public static final int LEFT_TO_RIGHT = 0;
	/**
	 * The progress bar will move from right to left
	 */
	public static final int RIGHT_TO_LEFT = 1;
	/**
	 * The progress bar will move top to bottom
	 */
	public static final int TOP_TO_BOTTOM= 2;
	/**
	 * The progress bar will move bottom to top
	 */
	public static final int BOTTOM_TO_TOP = 3;
	
	private int min, max;
	
	private int type;
	
	private int progressBarWidth, progressBarHeight;

	/**
	 * This is used to create a progress bar for your GUI
	 * @param min the minimum value (used to calculate the progress in the progress bar)
	 * @param max the maximum value (used to calculate the progress in the progress bar)
	 * @param type the type of progress bar
	 */
	public ProgressBar(int min, int max, int type) {
		this.min = min;
		this.max = max;
	}
	
	/**
	 * This calculates the progress out of the progress bar and is used for drawing the progress bar
	 * @param progressIndicatorPixelWidth the width of the progress bar in pixels from the texture
	 * @param progressIndicatorPixelHeight the height of the progress bar in pixels from the texture
	 * @return the level of progress
	 */
	public int getProgressLevel(int progressIndicatorPixelWidth, int progressIndicatorPixelHeight) {
		this.progressBarWidth = progressIndicatorPixelWidth;
		this.progressBarHeight = progressIndicatorPixelHeight;
		switch(type) {
		case 0:
			return max != 0 && min != 0 ? (min * progressIndicatorPixelWidth) / max : 0;
		case 1:
			return max != 0 && min != 0 ? (min * progressIndicatorPixelWidth) / max : 0;
		case 2:
			return max != 0 && min != 0 ? (min * progressIndicatorPixelHeight) / max : 0;
		case 3:
			return max != 0 && min != 0 ? (min * progressIndicatorPixelHeight) / max : 0;
		}
		return 0;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getProgressBarWidth() {
		return progressBarWidth;
	}

	public void setProgressBarWidth(int progressBarWidth) {
		this.progressBarWidth = progressBarWidth;
	}

	public int getProgressBarHeight() {
		return progressBarHeight;
	}

	public void setProgressBarHeight(int progressBarHeight) {
		this.progressBarHeight = progressBarHeight;
	}
	
}