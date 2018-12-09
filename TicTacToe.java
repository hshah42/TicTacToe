package tictactoe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import models.GameInformation;
import utilities.GameDisplay;
import utilities.LoadSaveGame;
import utilities.StaleMate;
import utilities.WinCondition;

public class TicTacToe
{
	private static final String[]	PLAYER_REPRESENTATION	= { "X", "O", "A", "B", "C", "D", "E", "F", "G", "H", "I",
			"J", "K", "L", "M", "N", "P", "Q", "R", "S", "T", "U", "V", "W", "Y", "Z" };
	private static final String		PLAYERS					= "player";
	private static final String		SEQUENCE				= "sequence";
	private static final String		DIMENSION				= "dimension";

	public static void main(String[] args)
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		GameInformation game = initSaveGame(br);

		if (game == null)
		{
			game = initInitialData(br, game);
		}

		playGame(game, br);
	}

	private static GameInformation initSaveGame(BufferedReader br)
	{
		GameInformation game = null;

		System.out.println("Do you want to continue a saved game? Y/N");
		String save = checkYNCondition(br);

		if (save.equalsIgnoreCase("N"))
		{
			return null;
		}

		System.out.println(
				"Enter file name (please include extension. Only allowed is .stc, in the same format it was saved):");

		try
		{
			String fileName = br.readLine();

			if (!fileName.endsWith(".stc"))
			{
				throw new IOException("Incorrect file format, only stc is allowed.");
			}

			game = LoadSaveGame.loadGame(fileName);
		}
		catch (IOException e)
		{
			System.out.println("Error in reading file: " + e.getMessage());
			System.out.println("Do you want to continue from the same file? Y/N");

			String continueIfError = checkYNCondition(br);

			if (continueIfError.equalsIgnoreCase("N"))
			{
				return null;
			}

			return initSaveGame(br);
		}

		return game;

	}

	private static GameInformation initInitialData(BufferedReader br, GameInformation game)
	{
		game = new GameInformation();

		System.out.println("Enter number of players: ");
		getInformationFromUser(game, br, PLAYERS);

		System.out.println("Enter Board Dimension: ");
		getInformationFromUser(game, br, DIMENSION);

		System.out.println("Enter Winning Sequence: ");
		getInformationFromUser(game, br, SEQUENCE);

		return game;
	}

	private static void getInformationFromUser(GameInformation game, BufferedReader br, String inputType)
	{
		int input = 0;

		do
		{
			try
			{
				input = Integer.parseInt(br.readLine());

				switch (inputType)
				{
					case PLAYERS:
						game.setNumberOfPlayers(input);
						break;

					case DIMENSION:
						game.setBoardDimensions(input);
						break;

					case SEQUENCE:
						game.setWinSequence(input);
						break;

					default:
						break;
				}
			}
			catch (NumberFormatException | IOException e)
			{
				System.err.println("Error in input: " + e.getMessage());
				System.out.println("Try Again!");
				input = 0;
			}
		}
		while (input == 0);

	}

	private static void playGame(GameInformation game, BufferedReader br)
	{
		int numberOfMoves = game.getBoardDimensions() * game.getBoardDimensions();
		int minWinMoves = (game.getWinSequence() - 1) * game.getNumberOfPlayers();
		boolean didYouWin = false;

		String[][] gameBoard = game.getGameBoard();

		if (gameBoard == null)
		{
			gameBoard = new String[game.getBoardDimensions()][game.getBoardDimensions()];
		}

		game.setGameBoard(gameBoard);

		GameDisplay.displayGame(gameBoard, game);

		while (game.getCurrentMoves() <= numberOfMoves)
		{
			try
			{
				System.out.println("Player" + game.getPlayerNumber() + " enter position separated by space: ");
				String position = br.readLine();

				if (position.equalsIgnoreCase("Q"))
				{
					quitGame(br, game, gameBoard);
					return;
				}

				String[] positionArray = position.split(" ");

				if (positionArray.length != 2)
				{
					System.err.println("Error in syntax! Input 2 numbers separated by spaces!");
					continue;
				}

				int x = Integer.parseInt(positionArray[0]) - 1;
				int y = Integer.parseInt(positionArray[1]) - 1;

				if (!isValidInput(game, x, y))
				{
					System.err.println("Invalid Input! Try Again!");
					continue;
				}

				gameBoard[x][y] = PLAYER_REPRESENTATION[game.getPlayerNumber() - 1];

				GameDisplay.displayGame(gameBoard, game);

				if (game.getCurrentMoves() >= minWinMoves)
				{
					didYouWin = WinCondition.checkWin(gameBoard, x, y,
							PLAYER_REPRESENTATION[game.getPlayerNumber() - 1], game);
				}

				if (didYouWin)
				{
					System.out.println("Player" + game.getPlayerNumber() + " Won");
					break;
				}

				game.setCurrentMoves(game.getCurrentMoves() + 1);
				game.setGameBoard(gameBoard);

				if (!StaleMate.isWinningPossible(game))
				{
					System.out.println("Game is a tie! No winning condition possible");
					return;
				}
			}
			catch (NumberFormatException | IOException e)
			{
				System.err.println("Error in input. Please enter correct cordinates separated by space.");
			}

			if (game.getPlayerNumber() == game.getNumberOfPlayers())
			{
				game.setPlayerNumber(1);
			}
			else
			{
				game.setPlayerNumber(game.getPlayerNumber() + 1);
			}
		}

		if (!didYouWin)
		{
			System.out.println("Game is a tie!");
		}
	}

	private static boolean isValidInput(GameInformation game, int x, int y)
	{
		String[][] gameBoard = game.getGameBoard();

		if (x > (game.getBoardDimensions() - 1) || y > (game.getBoardDimensions() - 1))
		{
			return false;
		}

		if (gameBoard[x][y] != null)
		{
			return false;
		}

		return true;
	}

	private static void quitGame(BufferedReader br, GameInformation game, String[][] gameBoard)
	{
		System.out.println("Do you want to save the game? Y/N");
		String save = checkYNCondition(br);

		if (save.equalsIgnoreCase("N"))
		{
			return;
		}

		try
		{
			LoadSaveGame.saveGame(br, game);
		}
		catch (Exception e)
		{
			System.out.println("Error: " + e.getMessage());
			System.out.println("Try Again?");
			
			String continueIfError = checkYNCondition(br);

			if (continueIfError.equalsIgnoreCase("N"))
			{
				return;
			}
			
			quitGame(br, game, gameBoard);
			
		}

	}

	private static String checkYNCondition(BufferedReader br)
	{
		String input = null;

		do
		{
			if (input != null)
			{
				System.err.println("Incorrect input, please use Y/N");
			}
			try
			{
				input = br.readLine();
			}
			catch (IOException e)
			{
				System.err.println("Error in input! Try again!");
			}
		}
		while (!input.equalsIgnoreCase("Y") && !input.equalsIgnoreCase("N"));

		return input;
	}

}
