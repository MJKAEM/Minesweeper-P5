package prominesweeper;

import processing.core.PApplet;
import processing.core.PFont;

import java.util.*;

public class ProMinesweeper extends PApplet
{
	public final static int ROWS = 10, COLS = 10, BUTTONSIZE = 20;
	
	private PFont font1 = loadFont("ArialMT-12.vlw");
	private Button[][] button;

	public void setup()
	{
		textFont(font1);
		text("Forced loading of font.", -255, -255);
		
		button = new Button[ROWS][COLS];

		for(int row = 0; row < ROWS; row++)
		{
			for(int col = 0; col < COLS; col++)
			{
				button[row][col] = new Button(this, row, col);

				if(Math.random() < .1)
				{
					button[row][col].SetBomb();
				}
			}
		}

		size((BUTTONSIZE * ROWS) + 1, (BUTTONSIZE * COLS) + 1);
	}

	public void draw()
	{
		for(int row = 0; row < ROWS; row++)
		{
			for(int col = 0; col < COLS; col++)
			{
				button[row][col].Show(font1);
			}
		}
	}
	public void mouseReleased()
	{
		for(int row = 0; row < ROWS; row++)
		{
			for(int col = 0; col < COLS; col++)
			{
				if(mouseX >= row * BUTTONSIZE && mouseX <= (row * BUTTONSIZE) + BUTTONSIZE)
				{
					if(mouseY >= col * BUTTONSIZE && mouseY <= (col * BUTTONSIZE) + BUTTONSIZE)
					{
						if(!button[row][col].GetClicked())
						{
							button[row][col].SetClicked();
							//button[row][col].SetSurrounding(GetNeighbors(row, col));
							System.out.println(GetNeighbors(row, col));
						}
					}
				}
			}
		}
	}
	public int GetNeighbors(int r, int c)
	{
		int count = 0;
		for(int row = r - 1; row < ROWS && row <= r + 1; r++)
		{
			for(int col = c - 1; col < COLS && col <= r + 1; c++)
			{
				if(r >= 0 && c >= 0)
				{
					if(button[row][col].GetBomb())
					{
						count++;
					}
				}
			}
		}
		return count;
	}
}
