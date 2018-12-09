package utilities;

import models.GameInformation;

public class WinCondition
{
	public static boolean checkWin(String[][] board, int x, int y, String player, GameInformation gameInformation)
	{
		int startIndexForX = determineIndexes(x, gameInformation);
		int startIndexForY = determineIndexes(y, gameInformation);
		int endIndexForX = determineEndIndex(x, gameInformation);
		int endIndexForY = determineEndIndex(y, gameInformation);

		if (diagonalCheck(board, player, startIndexForX, startIndexForY, endIndexForX, endIndexForY, gameInformation))
		{
			return true;
		}

		if (reverseDiagnalCheck(board, player, x - (gameInformation.getWinSequence() - 1),
				y + gameInformation.getWinSequence() - 1, x + (gameInformation.getWinSequence() - 1),
				y - gameInformation.getWinSequence() - 1, gameInformation))
		{
			return true;
		}

		if (checkRowConditions(board, player, x, startIndexForY, endIndexForY, gameInformation))
		{
			return true;
		}

		if (checkColumnConditions(board, player, y, startIndexForX, endIndexForX, gameInformation))
		{
			return true;
		}

		return false;
	}

	private static boolean checkRowConditions(String[][] board, String player, int currentXIndex, int startIndexY,
			int endIndexY, GameInformation gameInformation)
	{
		int count = 0;

		for (int i = startIndexY; i <= endIndexY; i++)
		{
			if (board[currentXIndex][i]!=null && board[currentXIndex][i].equals(player))
			{
				count++;

				if (count == gameInformation.getWinSequence())
				{
					return true;
				}
			}
			else
			{
				count = 0;
			}
		}

		return false;
	}

	private static boolean checkColumnConditions(String[][] board, String player, int currentYIndex, int startIndexX,
			int endIndexY, GameInformation gameInformation)
	{
		int count = 0;

		for (int i = startIndexX; i <= endIndexY; i++)
		{
			if (board[i][currentYIndex]!=null && board[i][currentYIndex].equals(player))
			{
				count++;

				if (count == gameInformation.getWinSequence())
				{
					return true;
				}
			}
			else
			{
				count = 0;
			}
		}

		return false;
	}

	private static boolean diagonalCheck(String[][] board, String player, int startIndexForX, int startIndexForY,
			int endIndexForX, int endIndexForY, GameInformation gameInformation)
	{
		int count = 0;

		while (startIndexForX <= endIndexForX && startIndexForY <= endIndexForY)
		{
			if (board[startIndexForX][startIndexForY]!=null && board[startIndexForX][startIndexForY].equals(player))
			{
				count++;

				if (count == gameInformation.getWinSequence())
				{
					return true;
				}
			}
			else
			{
				count = 0;
			}

			startIndexForX++;
			startIndexForY++;
		}

		return false;
	}

	private static boolean reverseDiagnalCheck(String[][] board, String player, int startIndexForX, int startIndexForY,
			int endIndexForX, int endIndexForY, GameInformation gameInformation)
	{
		int count = 0;

		if (startIndexForY >= gameInformation.getBoardDimensions())
		{
			startIndexForY = gameInformation.getBoardDimensions() - 1;
		}

		if (endIndexForX >= gameInformation.getBoardDimensions())
		{
			endIndexForX = gameInformation.getBoardDimensions() - 1;
		}

		if (endIndexForY < 0)
		{
			endIndexForY = 0;
		}

		if (startIndexForX < 0)
		{
			startIndexForX = 0;
		}

		while (startIndexForX <= endIndexForX && startIndexForY >= endIndexForY)
		{
			if (board[startIndexForX][startIndexForY]!=null && board[startIndexForX][startIndexForY].equals(player))
			{
				count++;

				if (count == gameInformation.getWinSequence())
				{
					return true;
				}
			}
			else
			{
				count = 0;
			}

			startIndexForX++;
			startIndexForY--;
		}

		return false;
	}

	private static int determineIndexes(int pointer, GameInformation gameInformation)
	{
		int index = pointer - (gameInformation.getWinSequence() - 1);

		if (index > 0)
		{
			return index;
		}

		return 0;
	}

	private static int determineEndIndex(int pointer, GameInformation gameInformation)
	{
		int index = pointer + (gameInformation.getWinSequence() - 1);

		if (index < (gameInformation.getBoardDimensions() - 1))
		{
			return index;
		}

		return gameInformation.getBoardDimensions() - 1;
	}

}
