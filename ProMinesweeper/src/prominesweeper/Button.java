package prominesweeper;

import java.awt.Color;

import processing.core.PApplet;
import processing.core.PFont;

public class Button
{
	private PApplet p;

	private boolean bomb, clicked;
	private int posX, posY;
	private int other;

	public Button(PApplet America, int x, int y)
	{
		p = America;
		bomb = false;
		clicked = false;
		posX = x * ProMinesweeper.BUTTONSIZE;
		posY = y * ProMinesweeper.BUTTONSIZE;
	}

	public void Show(PFont f)
	{
		p.textFont(f);
		if(clicked)
		{
			p.fill(255);
		}
		else
		{
			p.fill(128);
		}
		p.rect(posX, posY, ProMinesweeper.BUTTONSIZE, ProMinesweeper.BUTTONSIZE);

		switch(other)
		{
		case 1:
			p.fill(0, 0, 255);
			p.text(1, posX, posY);
			break;
		case 2:
			p.fill(0, 255, 0);
			p.text(2, posX, posY);
			break;
		case 3:
			p.fill(255, 0, 0);
			p.text(3, posX, posY);
			break;
		case 4:
			p.fill(255, 0, 255);
			p.text(4, posX, posY);
			break;
		case 5:
			p.fill(0);
			p.text(5, posX, posY);
			break;
		case 6:
			p.fill(0);
			p.text(6, posX, posY);
			break;
		case 7:
			p.fill(0);
			p.text(7, posX, posY);
			break;
		case 8:
			p.fill(0);
			p.text(8, posX, posY);
			break;
		}
	}

	public boolean GetBomb() { return bomb; }
	public boolean GetClicked() { return clicked; }
	public int GetSurrounding() { return other; }
	public void SetBomb() { bomb = true; }
	public void SetClicked() { clicked = true; }
	public void SetSurrounding(int surround) { other = surround; }
}
