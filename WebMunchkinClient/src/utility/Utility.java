package utility;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class Utility {

	// Tempor�re Variablen
	public static boolean debug = false;
	
	/**
	 * Gebe Konsolenausgabe aus, wenn im Debug-Modus
	 * @param str
	 */
	public static void debugMsg(String str) {
		if (debug) {
			System.out.println(Constants.debug_notifier + ": " + str);
		}
	}
	
	/**
	 * Gebe eine Fehermeldung auf der Konsole aus und beende das Programm
	 */
	public static void errorMsg(String str, Exception e) {
		// Fehlerausgabe
		System.out.println(Constants.error_notifier + ": " + str);
		// Pr�fe Debug-Status
		if (debug) {
			// Gebe java Fehlermeldung aus, wenn im Debug-Modus
			e.printStackTrace();
		}
		System.exit(0);
	}
	
	/**
	 * Hasht die Eingabe als MD5 Hash
	 * @param input
	 * @return
	 */
	public static String md5(String input) {
        String md5 = null;
        if(null == input) return null;
         
        try {  
        //Create MessageDigest object for MD5
        MessageDigest digest = MessageDigest.getInstance("MD5");
        //Update input string in message digest
        digest.update(input.getBytes(), 0, input.length());
        //Converts message digest value in base 16 (hex)
        md5 = new BigInteger(1, digest.digest()).toString(16);
 
        } catch (NoSuchAlgorithmException e) {
            errorMsg("Error while trying to MD5 hash",e);
        }
        return md5;
    }
}
