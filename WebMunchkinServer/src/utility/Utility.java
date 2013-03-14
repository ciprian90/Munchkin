package utility;

import java.util.Random;

/**
 * 
 * Stellt nützliche Hilfsfunktionen bereit.
 * 
 * @author Dirk Kleiner, Karsten Schatz, Marius Kleiner
 * @version 0.1a
 * 
 */
public final class Utility
{

	// Temporäre Variablen
	public static boolean debug = false;

	/**
	 * Gebe Konsolenausgabe aus, wenn im Debug-Modus
	 * 
	 * @param str
	 */
	public static void debugMsg(String str)
	{
		if (debug)
		{
			System.out.println(Constants.debug_notifier + ": " + str);
		}
	}

	/**
	 * Gebe eine Fehermeldung auf der Konsole aus und beende das Programm
	 * 
	 * @param str
	 * @param e
	 */
	public static void errorMsg(String str, Exception e)
	{
		// Fehlerausgabe
		System.out.println(Constants.error_notifier + ": " + str);
		// Prüfe Debug-Status
		if (debug)
		{
			// Gebe java Fehlermeldung aus, wenn im Debug-Modus
			e.printStackTrace();
		}
		System.exit(0);
	}
	
	/**
	 * Generates a Salt-String of length 16
	 * 
	 * @return
	 */
	public static String generateSalt()
	{
		// initialize Randomizer
		Random random = new Random(System.currentTimeMillis());
		// list possible characters
		String text = "abcdefghijklmnopqrstuvwxyz"
				+ "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "!§$%&/()=?" + "1234567890";
		char[] chars = text.toCharArray();

		// build String of length 16
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 16; i++)
		{
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}

		return sb.toString();
	}
}
