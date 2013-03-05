package utility;

/**
 * 
 * Stellt n�tzliche Hilfsfunktionen bereit.
 *  
 * @author Marius Kleiner, Dirk Kleiner
 * @version 0.1a
 *
 */
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
	 * @param str
	 * @param e
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
}