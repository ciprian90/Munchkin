package utility;

import java.io.File;

/**
 * 
 * Static constant class
 * 
 * @author Marius Kleiner
 * @version 0.1a
 * 
 */
public final class Constants
{

	// Parameters
	public final static String parameter_debug = "debug";

	// Notifier
	public final static String debug_notifier = "Debug";
	public final static String error_notifier = "Error";
	
	// Update Server
	public final static String updaterHost = "http://webmunchkin.freehostingcloud.com";
	public final static String versionURL = updaterHost + "/version.html";
	public final static String historyURL = updaterHost + "/history.html";
	
	public final static String updateDataFolder = "data";
	public final static String updateHost = "webmunchkin.freehostingcloud.com";
	public final static String updateFTPuser = "munchkin@webmunchkin.freehostingcloud.com";
	public final static String updateFTPpw = "munchkin";
	
	// Local Data Folder
	public final static String path_data_folder = "data" + File.separator;
	public final static String path_temp_folder = "temp" + File.separator;
	public final static String path_temp_unpack_folder = "temp" + File.separator + "unpacked" + File.separator;
	
	// Remote Data Folder
	public final static String[] remote_data_folder_ignorelist = {".",".."};
	public final static String[] remote_data_valid_types = {"zip"};

}
