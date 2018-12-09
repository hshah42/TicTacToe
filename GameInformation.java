package models;

import java.io.IOException;
import java.io.Serializable;

public class GameInformation implements Serializable
{
	private static final long	serialVersionUID	= 1702275322784186455L;

	private int					numberOfPlayers		= 0;
	private int					boardDimensions		= 0;
	private int					winSequence			= 3;
	private int					currentMoves		= 0;
	private int					playerNumber		= 1;
	private String[][]			gameBoard			= null;

	public int getCurrentMoves()
	{
		return currentMoves;
	}

	public void setCurrentMoves(int currentMoves)
	{
		this.currentMoves = currentMoves;
	}
	
	public int getPlayerNumber()
	{
		return playerNumber;
	}

	public void setPlayerNumber(int playerNumber)
	{
		this.playerNumber = playerNumber;
	}

	public int getNumberOfPlayers()
	{
		return numberOfPlayers;
	}

	public void setNumberOfPlayers(int numberOfPlayers) throws IOException
	{
		if (numberOfPlayers > 26)
		{
			throw new IOException("Number Of players should be less than 26");
		}

		this.numberOfPlayers = numberOfPlayers;
	}

	public int getBoardDimensions()
	{
		return boardDimensions;
	}

	public void setBoardDimensions(int boardDimensions) throws IOException
	{
		if (boardDimensions > 999)
		{
			throw new IOException("Board Dimension should be less than 1000");
		}

		int numberOfMoves = boardDimensions * boardDimensions;

		if (numberOfMoves < numberOfPlayers)
		{
			throw new IOException("Board dimension will not let any player win!");
		}

		this.boardDimensions = boardDimensions;
	}

	public int getWinSequence()
	{
		return winSequence;
	}

	public void setWinSequence(int winSequence) throws IOException
	{
		if (boardDimensions < winSequence)
		{
			throw new IOException("Win Sequence not possible as max sequence is " + boardDimensions
					+ " and win sequence is " + winSequence);
		}
		
		int numberOfTurnsPerPlayer = (int) Math.ceil((double) (boardDimensions * boardDimensions) / numberOfPlayers);
		
		if(winSequence > numberOfTurnsPerPlayer)
		{
			throw new IOException("Win sequence is greater than the number of turns per player hence no one will win.");
		}

		this.winSequence = winSequence;
	}

	public String[][] getGameBoard()
	{
		return gameBoard;
	}

	public void setGameBoard(String[][] gameBoard)
	{
		this.gameBoard = gameBoard;
	}
}
