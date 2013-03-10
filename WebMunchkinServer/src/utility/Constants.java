package utility;

import java.io.File;

/**
 * 
 * Konstantenklasse. 
 * Sollte nicht initialisiert werden!
 * 
 * @author Dirk Kleiner, Karsten Schatz, Marius Kleiner
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
	
	// SQL Datenbank
	public static String sql_server = "127.0.0.1";
	public static String sql_db_name = "munchkin";
	public static String sql_db_username = "munchkin";
	public static String sql_db_pw = "munchkin";
	public static String sql_limit_standard = "0 , 30";
	
	// Pfade für Jar-Dateien
	public static String jar_path = "jars";
	public static String jar_sql_database_driver = jar_path + File.separator + "postgresql-9.2-1002.jdbc4.jar";
	
	// Port für Socketverbindung
	public static int port = 4444;

}
