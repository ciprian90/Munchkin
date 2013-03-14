package standard;

import java.io.InputStream;
import java.net.URL;

import utility.Constants;
import utility.Version;

/**
 * This class provides important update functions.
 * 
 * @author Marius Kleiner
 * @version 0.1
 *
 */
public class Updater {
    
	// Class variables
    private static Version clientVersion = new Version("0.005");
    
    /**
     * Get latest version from update server
     * @return Latest version as String
     * @throws Exception
     */
    public static String getLatestVersion() throws Exception
    {
        String data = getTxtData(Constants.versionURL);
        return data.substring(data.indexOf("[version]")+9,data.indexOf("[/version]"));
    }
    
    /**
     * Get last update history
     * @return
     * @throws Exception
     */
    public static String getWhatsNew() throws Exception
    {
        String data = getTxtData(Constants.historyURL);
        return data.substring(data.indexOf("[history]")+9,data.indexOf("[/history]"));
    }


    
    /**
     * Read out text file from source adress
     * @param address
     * @return
     * @throws Exception
     */
    private static String getTxtData(String address)throws Exception
    {
        URL url = new URL(address);
        InputStream html = null;
        html = url.openStream();
        int c = 0;
        StringBuffer buffer = new StringBuffer("");
        // Read until end of file
        while(c != -1) {
            c = html.read();
            buffer.append((char)c);
        }
        return buffer.toString();
    }

    /**
     * Check if the client is up-to-date
     * @return -1 couldNotConnectOrError, 0 = outdated, 1 = upToDate
     */
    public static int checkUpToDate()
	{
    	try
		{
    		// TODO
			String actualVersionString = getLatestVersion();
			Version actualVersion = new Version(actualVersionString);
			// Check if one of retrieved Versions is invalid
			if (!actualVersion.valid || !clientVersion.valid) {
				return -1;
			}
			// Compare client versions
			int compare = clientVersion.compareTo(actualVersion);
			if (compare == 0) {
				return 1;
			}else if (compare > 0) {
				return -1;
			}else {
				return 0;
			}
		}
		catch (Exception e)
		{
			// Return, that an error occured while trying
			// to retrieve client Version
			return -1;
		}
	}

}