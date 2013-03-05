package utility;

import java.io.File;

/**
 * 
 * Konstantenklasse. 
 * Sollte nicht initialisiert werden!
 * 
 * @author Marius Kleiner, Dirk Kleiner
 * @version 0.1a
 *
 */
public final class Constants {
	
	//Parameters
	public static String parameter_debug = "debug";
	
	// Notifier
	public static String debug_notifier = "Debug";
	public static String error_notifier = "Error";
	
	// Zertifikatinformationen
	public static String certificate_path = "certificates" + File.separator + "mySrvKeystore";
	public static String certificate_pw = "display99";
	
	// Port für Socketverbindung
	public static int port = 4444;
}
