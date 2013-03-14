package utility;

/**
 * 
 * This class provides useful tools.
 * 
 * @author Marius Kleiner
 * @version 0.1a
 * 
 */
public final class Utility
{

	// Runtime variables
	public static boolean debug = false;

	/**
	 * Print debug messages on console while in debug mode
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
	 * Print error message on console
	 * 
	 * @param str
	 * @param e
	 */
	public static void errorMsg(String str, Exception e)
	{
		System.out.println(Constants.error_notifier + ": " + str);
		if (debug)
		{
			e.printStackTrace();
		}
		System.exit(0);
	}
	
	/**
	 * Check program parameters
	 * 
	 * @param arstring
	 */
	public static void checkParameters(String[] arstring)
	{
		// Check known parameters in arstring
		for (String val : arstring)
		{
			if (val.equals(Constants.parameter_debug))
			{
				Utility.debug = true;
				Utility.debugMsg("Running in debug-mode");
			}

		}
	}
}
