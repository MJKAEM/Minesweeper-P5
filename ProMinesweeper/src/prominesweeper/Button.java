package prominesweeper;

import java.awt.Color;

import processing.core.PApplet;

public class Button
{
	private PApplet p;
	
	private boolean isBomb, isClicked;
	private int posX, posY;
	private Color c;
	
	public Button(PApplet America, int x, int y)
	{
		p = America;
		isBomb = false;
		posX = x * 20;
		posY = y * 20;
		c = Color.GRAY;
	}
	
	public void Show()
	{
		p.fill(c.getRed(), c.getGreen(), c.getBlue());
		p.rect(posX, posY, 20, 20);
	}
	
	public boolean GetBomb() { return isBomb; }
	public void SetBomb() { isBomb = true; }
	public void SetColor(Color colour) { c = colour; }
}
