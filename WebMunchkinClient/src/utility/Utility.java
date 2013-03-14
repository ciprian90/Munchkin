package utility;

import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import error.CouldNotLoadFileException;

/**
 * Die Utility-Klasse stellt nützliche Funktionen für das Programm bereit
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
	 * Hasht die Eingabe als SHA-512 Hash
	 * 
	 * @param input
	 * @return
	 */
	public static String sha512(String input, String salt)
	{
		String sha512 = null;
		if (null == input)
			return null;

		try
		{
			// get Instance of the Hasher
			MessageDigest digest = MessageDigest.getInstance("SHA-512");
			// hash the text+salt
			digest.update((input + salt).getBytes());
			
			// get the hashed byte-array
			byte[] bytes = digest.digest();
			sha512 = "";

			// since we need a Hex-formatted String we have to convert the
			// byte-array into it
			for (int i = 0; i < bytes.length; i++)
			{
				String s = Integer.toHexString(new Byte(bytes[i]));
				while (s.length() < 2)
				{
					s = "0" + s;
				}
				s = s.substring(s.length() - 2);
				sha512 += s;
			}
		}
		catch (NoSuchAlgorithmException e)
		{
			errorMsg("Error while trying to SHA-512 hash", e);
		}

		return sha512;
	}

	/**
	 * Lade eine Datei aus dem jar Archiv
	 * 
	 * @param path
	 * @return URL des Files
	 * @throws CouldNotLoadFileException
	 */
	public static URL getFileURL(String path) throws CouldNotLoadFileException
	{
		URL url = ClassLoader.getSystemResource(path);
		if (url == null)
		{
			throw new CouldNotLoadFileException(path);
		}
		return url;

	}
}
