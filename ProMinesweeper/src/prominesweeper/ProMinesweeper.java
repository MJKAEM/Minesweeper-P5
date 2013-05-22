package prominesweeper;

import processing.core.PApplet;
import processing.core.PFont;

import java.util.*;

public class ProMinesweeper extends PApplet
{
	public final static int ROWS = 10, COLS = 10, BUTTONSIZE = 20;

	private PFont font1 = loadFont("ArialMT-12.vlw");
	private Button[][] button;
	private boolean gameOver;

	public void setup()
	{
		textFont(font1);
		text("Forced loading of font.", -255, -255);

		button = new Button[ROWS][COLS];
		gameOver = false;

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
		if(!gameOver)
		{
			if(mouseButton == LEFT)
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
									if(!button[row][col].GetShield())
									{
										button[row][col].SetClicked();
										button[row][col].SetSurrounding(GetNeighbors(row, col));
										if(button[row][col].GetBomb())
										{
											button[row][col].SetEndClick();
											LoseGame();
										}
									}
								}
							}
						}
					}
				}
			}
			else if(mouseButton == RIGHT)
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
									button[row][col].SetShield();
								}
							}
						}
					}
				}
			}
		}
	}
	public int GetNeighbors(int r, int c)
	{
		int count = 0;
		for(int row = r - 1; row <= r + 1; row++)
		{
			for(int col = c - 1; col <= c + 1; col++)
			{
				if(row >= 0 && col >= 0 && row < ROWS && col < COLS)
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
	public void LoseGame()
	{
		gameOver = true;
		for(int row = 0; row < ROWS; row++)
		{
			for(int col = 0; col < COLS; col++)
			{
				if(button[row][col].GetBomb())
				{
					button[row][col].SetClicked();
				}
			}
		}
	}
}
