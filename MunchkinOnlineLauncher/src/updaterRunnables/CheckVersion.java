package updaterRunnables;

import standard.Updater;
import standard.UpdaterFrame;

/**
 * This class checks if the client is up to date.
 * (Running in background)
 * 
 * @author Marius Kleiner
 * @version 0.1
 *
 */
public class CheckVersion implements Runnable
{
	
	/**
	 * Runnable
	 */
	public void run()
	{
		// Retrieve up-to-date status
		int a = Updater.checkUpToDate();
		// Hand over retrieved info to UpdaterFrame
		UpdaterFrame.getInstance().checkVersionOutcome(a);
	}

}
