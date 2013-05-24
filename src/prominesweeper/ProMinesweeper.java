package prominesweeper;

import processing.core.PApplet;
import processing.core.PFont;

import java.util.*;

public class ProMinesweeper extends PApplet
{
	public static int ROWS = 8, COLS = 8, BUTTONSIZE = 20;
	public static final int bombs = 10;

	private PFont font1 = loadFont("ArialMT-12.vlw");
	private Button[][] button;
	private boolean gameOver, gameStarted;
	private int blankSpaces, marked;
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
		blankSpaces = ROWS * COLS;

		SetAllBombs();

		size((BUTTONSIZE * ROWS) + 1, (BUTTONSIZE * COLS) + 1 + 40);
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

		fill(64);
		rect(width / 2 - 15, 5, 30, 30);
		fill(255, 0, 0);
		ellipse(width / 2, 20, 25, 25);

		DisplayUseless();
		WinGame();
	}
	public void mouseReleased()
	{
		if(mouseButton == LEFT)
		{
			if(mouseX >= width / 2 - 15 && mouseX <= width / 2 - 15 + 30)
			{
				if(mouseY >= 5 && mouseY <= 5 + 30)
				{
					gameOver = false;
					marked = 0;
					timer = 0;
					gameStarted = false;
					SetAllBombs();
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
						if(mouseX >= row * BUTTONSIZE && mouseX <= (row * BUTTONSIZE) + BUTTONSIZE)
						{
							if(mouseY >= col * BUTTONSIZE + 40 && mouseY <= (col * BUTTONSIZE + 40) + BUTTONSIZE)
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
							if(mouseY >= col * BUTTONSIZE + 40 && mouseY <= (col * BUTTONSIZE + 40) + BUTTONSIZE)
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
				if(button[row][col].GetClicked() && !button[row][col].GetBomb())
				{
					count++;
				}
			}
		}
		if(count == blankSpaces)
		{
			fill(255, 0, 0);
			text("YOU WIN! NOW GET A LIFE.", 1, 20);
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

				if(Math.random() < .1)
				{
					button[row][col].SetBomb();
					blankSpaces--;
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
