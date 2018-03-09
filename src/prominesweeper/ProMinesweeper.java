//Martino Kuan, Pro Minesweeper, AP Comp Sci mods 6,7

package prominesweeper;

import processing.core.PApplet;
import processing.core.PFont;

import java.util.*;

public class ProMinesweeper extends PApplet
{
	public static int ROWS = 16, COLS = 16, BUTTONSIZE = 20;
	public static final int bombs = 40;

	private PFont font1 = loadFont("ArialMT-12.vlw");
	private Button[][] button;
	private boolean gameOver, gameStarted, clickOn, lose;
	private int marked;
	private int timer;

	public void setup()
	{
		textFont(font1);
		text("Forced loading of font.", -255, -255);

		button = new Button[ROWS][COLS];
		gameOver = false;
		gameStarted = false;
		marked = 0;
		timer = 0;

		SetAllBombs();

		//size((BUTTONSIZE * ROWS) + 1, (BUTTONSIZE * COLS) + 1 + 40);
		size(321, 361);
	}

	public void draw()
	{
		background(200);
		for(int row = 0; row < ROWS; row++)
		{
			for(int col = 0; col < COLS; col++)
			{
				button[row][col].Show(font1);

				if(gameOver && !gameStarted)
					if(button[row][col].GetShield())
					{
						if(!button[row][col].GetBomb())
						{
							button[row][col].YokuDekiMashita();
						}
					}
			}
		}

		fill(64);
		rect(width / 2 - 15, 5, 30, 30);
		fill(255, 255, 0);
		ellipse(width / 2, 20, 25, 25);
		
		if(gameOver && !gameStarted && !lose)
		{
			fill(0);
			arc(width / 2 - 5, 15, 5, 8, 0, PI);
			arc(width / 2 + 5, 15, 5, 8, 0, PI);
			
			line(width / 2 - 5, 15, width / 2 + 5, 15);
			
			line(width / 2 - 5 - (float)2.5, 15, width / 2 - (float)12.5, 20);
			line(width / 2 + 5 + (float)2.5, 15, width / 2 + (float)12.5, 20);
			
			noFill();
			arc(width / 2, 25, 10, 5, 0, PI);
		}
		else if(gameOver && !gameStarted && lose)
		{
			fill(0);
			line(width / 2 - 5, 15, width / 2 - 3, 18);
			line(width / 2 - 3, 15, width / 2 - 5, 18);
			
			line(width / 2 + 5, 15, width / 2 + 3, 18);
			line(width / 2 + 3, 15, width / 2 + 5, 18);
			
			noFill();
			arc(width / 2, 25, 10, 5, PI, PI * 2);
		}
		else if(clickOn)
		{
			fill(0);
			ellipse(width / 2 - 5, 18, 5, 5);
			ellipse(width / 2 + 5, 18, 5, 5);
			ellipse(width / 2, 25, 5, 5);
		}
		else if(!clickOn)
		{
			fill(0);
			ellipse(width / 2 - 5, 18, 3, 3);
			ellipse(width / 2 + 5, 18, 3, 3);
			noFill();
			arc(width / 2, 25, 10, 5, 0, PI);
		}

		DisplayUseless();
		WinGame();
	}
	public void mousePressed()
	{
		if(!gameOver)
		{
			if(mouseButton == LEFT)
			{
				for(int row = 0; row < ROWS; row++)
				{
					for(int col = 0; col < COLS; col++)
					{
						if(mouseX > row * BUTTONSIZE && mouseX < (row * BUTTONSIZE) + BUTTONSIZE)
						{
							if(mouseY > col * BUTTONSIZE + 40 && mouseY < (col * BUTTONSIZE + 40) + BUTTONSIZE)
							{
								if(!button[row][col].GetClicked())
								{
									if(!button[row][col].GetShield())
									{
										clickOn = true;
									}
								}
							}
						}
					}
				}
			}
		}
	}
	public void mouseReleased()
	{
		if(mouseButton == LEFT)
		{
			if(mouseX > width / 2 - 15 && mouseX < width / 2 - 15 + 30)
			{
				if(mouseY > 5 && mouseY < 5 + 30)
				{
					gameOver = false;
					marked = 0;
					timer = 0;
					gameStarted = false;
					SetAllBombs();
					lose = false;
				}
			}
		}
		if(!gameOver)
		{
			if(mouseButton == LEFT)
			{
				for(int row = 0; row < ROWS; row++)
				{
					for(int col = 0; col < COLS; col++)
					{
						if(mouseX > row * BUTTONSIZE && mouseX < (row * BUTTONSIZE) + BUTTONSIZE)
						{
							if(mouseY > col * BUTTONSIZE + 40 && mouseY < (col * BUTTONSIZE + 40) + BUTTONSIZE)
							{
								gameStarted = true;
								if(!button[row][col].GetClicked())
								{
									if(!button[row][col].GetShield())
									{
										button[row][col].SetClicked();
										button[row][col].SetSurrounding(GetNeighborsBomb(row, col));
										if(button[row][col].GetSurrounding() == 0)
										{
											RecurseUncover(row, col);
										}
										if(button[row][col].GetBomb())
										{
											button[row][col].SetEndClick();
											LoseGame();
										}
									}
								}
								clickOn = false;
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
						if(mouseX > row * BUTTONSIZE && mouseX < (row * BUTTONSIZE) + BUTTONSIZE)
						{
							if(mouseY > col * BUTTONSIZE + 40 && mouseY < (col * BUTTONSIZE + 40) + BUTTONSIZE)
							{
								if(!button[row][col].GetClicked())
								{
									button[row][col].SetShield();
									if(button[row][col].GetShield())
									{
										marked++;
									}
									else
									{
										marked--;
									}
								}
							}
						}
					}
				}
			}
		}
	}
	public ArrayList<Button> GetNeighbors(int r, int c)
	{
		ArrayList<Button> temp = new ArrayList<Button>();

		for(int row = r - 1; row <= r + 1; row++)
		{
			for(int col = c - 1; col <= c + 1; col++)
			{
				if(row >= 0 && col >= 0 && row < ROWS && col < COLS)
				{
					if(button[row][col].GetBomb())
					{
						temp.add(button[row][col]);
					}
				}
			}
		}

		return temp;
	}
	public int GetNeighborsBomb(int r, int c)
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
	public void RecurseUncover(int r, int c)
	{
		for(int row = r - 1; row <= r + 1; row++)
		{
			for(int col = c - 1; col <= c + 1; col++)
			{
				if(row >= 0 && col >= 0 && row < ROWS && col < COLS)
				{
					if(!button[row][col].GetClicked() && !button[row][col].GetBomb())
					{
						button[row][col].SetClicked();
						button[row][col].SetSurrounding(GetNeighborsBomb(row, col));
						if(button[row][col].GetSurrounding() == 0)
						{
							RecurseUncover(row, col);
						}
					}
				}
			}
		}
	}
	public void LoseGame()
	{
		gameOver = true;
		gameStarted = false;
		lose = true;
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
	public void WinGame()
	{
		int count = 0;
		for(int row = 0; row < ROWS; row++)
		{
			for(int col = 0; col < COLS; col++)
			{
				if(!button[row][col].GetClicked())
				{
					count++;
				}
			}
		}
		if(count == bombs)
		{
			lose = false;
			gameOver = true;
			gameStarted = false;
		}
	}
	public void SetAllBombs()
	{
		int count = 0;
		for(int row = 0; row < ROWS; row++)
		{
			for(int col = 0; col < COLS; col++)
			{
				button[row][col] = new Button(this, row, col);

				if(Math.random() < 0.1)
				{
					if(count != bombs)
					{
						button[row][col].SetBomb();
					}
				}

				if(button[row][col].GetBomb())
				{
					count++;
				}
			}
		}
		if(count != bombs)
		{
			SetAllBombs();
		}
	}
	public void DisplayUseless()
	{
		fill(0);
		rect(5, 5, 50, 30);
		rect(width - 5 - 50, 5, 50, 30);
		fill(255, 0, 0);

		if((bombs - marked) >= 10)
		{
			text("0" + (bombs - marked), 10, 20);
		}
		else if((bombs - marked) < 10 && (bombs - marked) > -1)
		{
			text("00" + (bombs - marked), 10, 20);
		}
		else if((bombs - marked) < 0)
		{
			text("000", 10, 20);
		}

		if(timer / 60 < 10)
		{
			text("00" + (timer / 60), width + 5 - 50, 20);
		}
		else if(timer / 60 < 100)
		{
			text("0" + (timer / 60), width + 5 - 50, 20);
		}
		else if(timer / 60 < 1000)
		{
			text(timer / 60, width + 5 - 50, 20);
		}
		else
		{
			text(999, width + 5 - 50, 20);
		}
		if(gameStarted)
		{
			timer++;
		}
	}
}
