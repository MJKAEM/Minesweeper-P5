package prominesweeper;

import java.awt.Color;

import processing.core.PApplet;
import processing.core.PFont;

public class Button
{
	private PApplet p;

	private boolean bomb, clicked, endClick, shield;
	private int posX, posY;
	private int other;

	public Button(PApplet America, int x, int y)
	{
		p = America;
		bomb = false;
		clicked = false;
		endClick = false;
		shield = false;
		posX = x * ProMinesweeper.BUTTONSIZE;
		posY = y * ProMinesweeper.BUTTONSIZE;
	}

	public void Show(PFont f)
	{
		p.textFont(f);
		
		if(shield)
		{
			p.fill(0, 255, 0);
		}
		else if(clicked)
		{
			p.fill(200);
		}
		else
		{
			p.fill(128);
		}
		if(endClick)
		{
			p.fill(255, 0, 0);
		}
		
		p.rect(posX, posY, ProMinesweeper.BUTTONSIZE, ProMinesweeper.BUTTONSIZE);

		if(bomb && clicked)
		{
			p.fill(0);
			p.ellipse(posX + ProMinesweeper.BUTTONSIZE / 2,
					posY + ProMinesweeper.BUTTONSIZE / 2,
					ProMinesweeper.BUTTONSIZE / 2,
					ProMinesweeper.BUTTONSIZE / 2);
		}
		else
		{
			switch(other)
			{
			case 1:
				p.fill(0, 0, 255);
				p.text(1, posX + 7, posY + 15);
				break;
			case 2:
				p.fill(0, 100, 0);
				p.text(2, posX + 7, posY + 15);
				break;
			case 3:
				p.fill(255, 0, 0);
				p.text(3, posX + 7, posY + 15);
				break;
			case 4:
				p.fill(255, 0, 255);
				p.text(4, posX + 7, posY + 15);
				break;
			case 5:
				p.fill(178, 34, 34);
				p.text(5, posX + 7, posY + 15);
				break;
			case 6:
				p.fill(64, 224, 208);
				p.text(6, posX + 7, posY + 15);
				break;
			case 7:
				p.fill(0);
				p.text(7, posX + 7, posY + 15);
				break;
			case 8:
				p.fill(128);
				p.text(8, posX + 7, posY + 15);
				break;
			}
		}
	}

	public boolean GetBomb() { return bomb; }
	public boolean GetClicked() { return clicked; }
	public int GetSurrounding() { return other; }
	public boolean GetShield() { return shield; }
	public void SetBomb() { bomb = true; }
	public void SetClicked() { clicked = true; }
	public void SetSurrounding(int surround) { other = surround; }
	public void SetEndClick() { endClick = true; }
	public void SetShield() { shield = !shield; }
}
