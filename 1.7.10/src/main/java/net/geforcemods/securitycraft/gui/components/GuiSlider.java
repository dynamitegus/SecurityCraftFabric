package net.geforcemods.securitycraft.gui.components;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.config.GuiButtonExt;
import net.minecraft.client.Minecraft;

/**
 * This class is blatantly stolen from iChunUtils with permission.
 *
 * and blatantly edited by bl4ckscor3 to fit SC's needs
 *
 * @author iChun
 */
public class GuiSlider extends GuiButtonExt
{
	/** The value of this slider control. */
	public double sliderValue;

	public String dispString = "";

	/** Is this slider control being dragged. */
	public boolean dragging = false;
	public boolean showDecimal = true;

	public double minValue = 0.0D;
	public double maxValue = 5.0D;
	public int precision = 1;

	public ISlider parent = null;

	public String suffix = "";

	public boolean drawString = true;

	private String blockName;

	public GuiSlider(int id, int xPos, int yPos, int width, int height, String prefix, String suf, double minVal, double maxVal, double currentVal, boolean showDec, boolean drawStr)
	{
		this("whyareyoudoingthis", "seriouslywhy", id, xPos, yPos, width, height, prefix, suf, minVal, maxVal, currentVal, showDec, drawStr, null);
	}

	public GuiSlider(String initialString, String bN, int id, int xPos, int yPos, int width, int height, String prefix, String suf, double minVal, double maxVal, double currentVal, boolean showDec, boolean drawStr, ISlider par)
	{
		super(id, xPos, yPos, width, height, prefix);
		minValue = minVal;
		maxValue = maxVal;
		dispString = prefix;
		parent = par;
		suffix = suf;
		showDecimal = showDec;
		blockName = bN;
		String val;
		sliderValue = (currentVal - minVal) / (maxVal - minVal);

		if (showDecimal)
		{
			val = Double.toString(getValue());
			precision = Math.min(val.substring(val.indexOf(".") + 1).length(), 4);
		}
		else
		{
			val = Integer.toString(getValueInt());
			precision = 0;
		}

		displayString = initialString;

		drawString = drawStr;
		if(!drawString)
			displayString = "";
	}

	public GuiSlider(int id, int xPos, int yPos, String displayStr, double minVal, double maxVal, double currentVal, ISlider par)
	{
		this("whyareyoudoingthis", "seriouslywhy", id, xPos, yPos, 150, 20, displayStr, "", minVal, maxVal, currentVal, true, true, par);
	}

	/**
	 * Returns 0 if the button is disabled, 1 if the mouse is NOT hovering over this button and 2 if it IS hovering over
	 * this button.
	 */
	@Override
	public int getHoverState(boolean mouseOver)
	{
		return 0;
	}

	/**
	 * Fired when the mouse button is dragged. Equivalent of MouseListener.mouseDragged(MouseEvent e).
	 */
	@Override
	protected void mouseDragged(Minecraft mc, int mouseX, int mouseY)
	{
		if (visible)
		{
			if (dragging)
			{
				sliderValue = (mouseX - (xPosition + 4)) / (double)(width - 8);
				updateSlider();
			}

			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			drawTexturedModalRect(xPosition + (int)(sliderValue * (width - 8)), yPosition, 0, 66, 4, 20);
			drawTexturedModalRect(xPosition + (int)(sliderValue * (width - 8)) + 4, yPosition, 196, 66, 4, 20);
		}
	}

	/**
	 * Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent
	 * e).
	 */
	@Override
	public boolean mousePressed(Minecraft mc, int mouseX, int mouseY)
	{
		if (super.mousePressed(mc, mouseX, mouseY))
		{
			sliderValue = (double)(mouseX - (xPosition + 4)) / (double)(width - 8);
			updateSlider();
			dragging = true;
			return true;
		}
		else
			return false;
	}

	public void updateSlider()
	{
		if (sliderValue < 0.0F)
			sliderValue = 0.0F;

		if (sliderValue > 1.0F)
			sliderValue = 1.0F;

		String val;

		if (showDecimal)
		{
			val = Double.toString(getValue());

			if (val.substring(val.indexOf(".") + 1).length() > precision)
			{
				val = val.substring(0, val.indexOf(".") + precision + 1);

				if (val.endsWith("."))
					val = val.substring(0, val.indexOf(".") + precision);
			}
			else
				while (val.substring(val.indexOf(".") + 1).length() < precision)
					val = val + "0";
		}
		else
			val = Integer.toString(getValueInt());

		if (parent != null)
			parent.onChangeSliderValue(this, blockName, id);
	}

	/**
	 * Fired when the mouse button is released. Equivalent of MouseListener.mouseReleased(MouseEvent e).
	 */
	@Override
	public void mouseReleased(int mouseX, int mouseY)
	{
		dragging = false;
		parent.onMouseRelease(id);
	}

	public int getValueInt()
	{
		return (int)Math.round(getValue());
	}

	public double getValue()
	{
		return sliderValue * (maxValue - minValue) + minValue;
	}

	public void setValue(double d)
	{
		sliderValue = (d - minValue) / (maxValue - minValue);
	}

	public static interface ISlider
	{
		void onChangeSliderValue(GuiSlider slider, String blockName, int id);

		void onMouseRelease(int id);
	}
}
