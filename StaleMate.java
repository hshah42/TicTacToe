package utilities;

import models.GameInformation;

public class StaleMate
{

	private static final String	ROW		= "row";
	private static final String	COLUMN	= "column";

	public static boolean isWinningPossible(GameInformation game)
	{
		int numberOfMoves = game.getBoardDimensions() * game.getBoardDimensions();
		int movesLeft = numberOfMoves - game.getCurrentMoves();

		if (checkStraightConditions(game, ROW, movesLeft) || checkStraightConditions(game, COLUMN, movesLeft)
				|| checkTopDiagnolCondition(game, movesLeft) || checkBottomDiagnolCondition(game, movesLeft)
				|| checkReversedDiagnolCondition(game, movesLeft)
				|| checkReversedDiagnolBottomCondition(game, movesLeft))
		{
			return true;
		}

		return false;
	}

	private static boolean checkStraightConditions(GameInformation game, String direction, int movesLeft)
	{
		String[][] gameBoard = game.getGameBoard();

		for (int i = 0; i < game.getBoardDimensions(); i++)
		{
			int count = 0;
			int nullCount = 0;
			int nullCountAfterPlayer = 0;

			String previousPointer = null;
			String previousPlayer = null;

			for (int j = 0; j < game.getBoardDimensions(); j++)
			{
				String currentPoint = null;

				if (direction.equals(ROW))
				{
					currentPoint = gameBoard[i][j];
				}
				else
				{
					currentPoint = gameBoard[j][i];
				}

				nullCount = getNullCount(nullCount, currentPoint, previousPlayer, nullCountAfterPlayer);
				count = getCount(count, currentPoint, previousPointer, previousPlayer, nullCountAfterPlayer);
				nullCountAfterPlayer = getNullCountAfterPlayer(currentPoint, previousPlayer, nullCountAfterPlayer);

				if (isWinningPossible(count, game, movesLeft, nullCount))
				{
					return true;
				}

				previousPointer = currentPoint;

				if (currentPoint != null)
				{
					previousPlayer = currentPoint;
				}
			}
		}

		return false;
	}

	private static boolean checkTopDiagnolCondition(GameInformation game, int movesLeft)
	{
		int end = game.getBoardDimensions();
		String previousPointer = null;
		String[][] gameBoard = game.getGameBoard();

		for (int i = 0; i < end; i++)
		{
			int x = i;
			int y = 0;
			int count = 0;
			int nullCount = 0;
			int nullCountAfterPlayer = 0;
			String previousPlayer = null;

			while (x >= 0)
			{
				String currentPointer = gameBoard[x][y];

				nullCount = getNullCount(nullCount, currentPointer, previousPlayer, nullCountAfterPlayer);
				count = getCount(count, currentPointer, previousPointer, previousPlayer, nullCountAfterPlayer);
				nullCountAfterPlayer = getNullCountAfterPlayer(currentPointer, previousPlayer, nullCountAfterPlayer);

				if (isWinningPossible(count, game, movesLeft, nullCount))
				{
					return true;
				}

				previousPointer = currentPointer;

				if (currentPointer != null)
				{
					previousPlayer = currentPointer;
				}

				x--;
				y++;
			}
		}

		return false;
	}

	private static boolean checkBottomDiagnolCondition(GameInformation game, int movesLeft)
	{
		int end = game.getBoardDimensions() - 1;
		String previousPointer = null;
		String[][] gameBoard = game.getGameBoard();

		for (int i = end; i > 0; i--)
		{
			int x = end;
			int y = i;
			int nullCount = 0;
			int count = 0;
			int nullCountAfterPlayer = 0;
			String previousPlayer = null;

			while (y <= end)
			{
				String currentPointer = gameBoard[x][y];

				nullCount = getNullCount(nullCount, currentPointer, previousPlayer, nullCountAfterPlayer);
				count = getCount(count, currentPointer, previousPointer, previousPlayer, nullCountAfterPlayer);
				nullCountAfterPlayer = getNullCountAfterPlayer(currentPointer, previousPlayer, nullCountAfterPlayer);

				if (isWinningPossible(count, game, movesLeft, nullCount))
				{
					return true;
				}

				previousPointer = currentPointer;

				if (currentPointer != null)
				{
					previousPlayer = currentPointer;
				}

				x--;
				y++;
			}
		}

		return false;
	}

	private static boolean checkReversedDiagnolCondition(GameInformation game, int movesLeft)
	{
		int end = 0;
		String previousPointer = null;
		String[][] gameBoard = game.getGameBoard();

		for (int i = game.getBoardDimensions() - 1; i >= end; i--)
		{
			int x = 0;
			int y = i;
			int nullCount = 0;
			int count = 0;
			int nullCountAfterPlayer = 0;
			String previousPlayer = null;

			while (y <= game.getBoardDimensions() - 1)
			{
				String currentPointer = gameBoard[x][y];

				nullCount = getNullCount(nullCount, currentPointer, previousPlayer, nullCountAfterPlayer);
				count = getCount(count, currentPointer, previousPointer, previousPlayer, nullCountAfterPlayer);
				nullCountAfterPlayer = getNullCountAfterPlayer(currentPointer, previousPlayer, nullCountAfterPlayer);

				if (isWinningPossible(count, game, movesLeft, nullCount))
				{
					return true;
				}

				previousPointer = currentPointer;

				if (currentPointer != null)
				{
					previousPlayer = currentPointer;
				}

				x++;
				y++;

			}
		}

		return false;
	}

	private static boolean checkReversedDiagnolBottomCondition(GameInformation game, int movesLeft)
	{
		int end = 0;
		String previousPointer = null;

		String[][] gameBoard = game.getGameBoard();

		for (int i = game.getBoardDimensions() - 1; i > end; i--)
		{
			int x = i;
			int y = 0;
			int nullCount = 0;
			int count = 0;
			int nullCountAfterPlayer = 0;
			String previousPlayer = null;

			while (x < game.getBoardDimensions())
			{
				String currentPointer = gameBoard[x][y];

				nullCount = getNullCount(nullCount, currentPointer, previousPlayer, nullCountAfterPlayer);
				count = getCount(count, currentPointer, previousPointer, previousPlayer, nullCountAfterPlayer);
				nullCountAfterPlayer = getNullCountAfterPlayer(currentPointer, previousPlayer, nullCountAfterPlayer);

				if (isWinningPossible(count, game, movesLeft, nullCount))
				{
					return true;
				}

				previousPointer = currentPointer;

				if (currentPointer != null)
				{
					previousPlayer = currentPointer;
				}

				x++;
				y++;
			}
		}

		return false;
	}

	private static int getCount(int count, String currentPointer, String previousPointer, String previousPlayer,
			int nullCountAfterPlayer)
	{
		if (currentPointer == null || previousPlayer == null)
		{
			count++;
		}
		else if (previousPointer == null && (previousPlayer != null && !previousPlayer.equals(currentPointer)))
		{
			count = nullCountAfterPlayer;
			count++;
		}
		else if (currentPointer.equals(previousPointer))
		{
			count++;
		}
		else
		{
			count = 0;
		}

		return count;
	}

	private static int getNullCount(int nullCount, String currentPointer, String previousPlayer,
			int nullCountAfterPlayer)
	{
		if (currentPointer == null)
		{
			nullCount++;
		}
		else if (currentPointer != null && previousPlayer != null && !previousPlayer.equals(currentPointer))
		{
			nullCount = nullCountAfterPlayer;
		}
		else if (previousPlayer == null
				|| (currentPointer != null && previousPlayer != null && previousPlayer.equals(currentPointer)))
		{
			return nullCount;
		}
		else
		{
			nullCount = 0;
		}

		return nullCount;
	}

	private static int getNullCountAfterPlayer(String currentPointer, String previousPlayer, int nullCountAfterPlayer)
	{
		if (currentPointer != null)
		{
			nullCountAfterPlayer = 0;
		}
		else if (previousPlayer != null && currentPointer == null)
		{
			nullCountAfterPlayer++;
		}

		return nullCountAfterPlayer;
	}

	private static boolean isWinningPossible(int count, GameInformation game, int movesLeft, int nullCount)
	{
		if (count == game.getWinSequence())
		{
			int numberOfTurnsLeftPerPlayer = (int) Math.ceil((double) movesLeft / game.getNumberOfPlayers());

			if (nullCount > numberOfTurnsLeftPerPlayer && game.getPlayerNumber() == 1)
			{
				return false;
			}

			return true;
		}

		return false;
	}

}
