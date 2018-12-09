package utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import models.GameInformation;

public class LoadSaveGame
{
	public static GameInformation loadGame(String fileName) throws FileNotFoundException, IOException
	{
		File file = new File(fileName);

		GameInformation game = null;

		try (InputStream inputStream = new FileInputStream(file);
				ObjectInputStream object = new ObjectInputStream(inputStream);)
		{
			game = (GameInformation) object.readObject();
		}
		catch (ClassNotFoundException e)
		{
			throw new IOException("File could not be loaded into the game");
		}

		return game;
	}

	public static void saveGame(BufferedReader br, GameInformation game) throws IOException
	{
		System.out.println("File Name to be saved? (Extension by default would be added: .stc)");
		String fileName = "";

		try
		{
			fileName = br.readLine();
		}
		catch (IOException e)
		{
			throw new IOException("\"Could not read file name: \" + e.getMessage()");
		}

		try (FileOutputStream fs = new FileOutputStream(new File(fileName + ".stc"));
				ObjectOutputStream objectStream = new ObjectOutputStream(fs);)
		{
			objectStream.writeObject(game);
		}
		catch (IOException e)
		{
			throw new IOException("Counld Not save file:" + e.getMessage());
		}

	}

}
