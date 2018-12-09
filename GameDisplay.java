package utilities;

import models.GameInformation;

public class GameDisplay
{
	public static void displayGame(String[][] currentPosition, GameInformation game)
	{
		System.out.print("   ");

		for (int j = 1; j <= game.getBoardDimensions(); j++)
		{
			if (j < 10)
			{
				System.out.print("  " + j + "   ");
			}
			else if (j < 100)
			{
				System.out.print("  " + j + "  ");
			}
			else
			{
				System.out.print(" " + j + "  ");
			}
		}

		System.out.println("\n");

		for (int i = 0; i < game.getBoardDimensions(); i++)
		{
			if ((i + 1) < 10)
			{
				System.out.print(i + 1);
				System.out.print("  ");

			}
			else if ((i + 1) < 100)
			{

				System.out.print(i + 1);
				System.out.print(" ");
			}
			else
			{

				System.out.print(i + 1);
			}

			for (int j = 0; j < game.getBoardDimensions() - 1; j++)
			{
				if (currentPosition[i][j] != null)
				{
					System.out.print("  " + currentPosition[i][j] + "  |");
				}
				else
				{
					System.out.print("     |");
				}
			}

			if (i != game.getBoardDimensions() - 1)
			{
				if (currentPosition[i][game.getBoardDimensions() - 1] != null)
				{
					System.out.print("  " + currentPosition[i][game.getBoardDimensions() - 1]);
				}
				else
				{
					System.out.print("      ");
				}

				System.out.println("\n");

				for (int k = 0; k < game.getBoardDimensions() - 1; k++)
				{
					if (k == 0)
					{
						System.out.print("   - - -+");
					}
					else
					{
						System.out.print("- - -+");
					}
				}

				System.out.print("- - -");
			}
			else
			{
				if (currentPosition[i][game.getBoardDimensions() - 1] != null)
				{
					System.out.print("  " + currentPosition[i][game.getBoardDimensions() - 1]);
				}
				else
				{
					System.out.print("     ");
				}
			}

			System.out.println("\n");

		}
	}
}
