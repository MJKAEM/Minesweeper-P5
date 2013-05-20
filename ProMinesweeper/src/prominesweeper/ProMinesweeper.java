package prominesweeper;

import processing.core.PApplet;
import java.util.*;

public class ProMinesweeper extends PApplet
{
	public final static int ROWS = 10, COLS = 10;
	
	private Button[][] button;
	
	public void setup()
	{
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
		
		size((20 * ROWS) + 1, (20 * COLS) + 1);
	}

	public void draw()
	{
		for(int row = 0; row < ROWS; row++)
		{
			for(int col = 0; col < COLS; col++)
			{
				button[row][col].Show();
			}
		}
	}
}
