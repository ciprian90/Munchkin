package standard;

import updaterRunnables.getClientIsWantedDifference;
import utility.Utility;

/**
 *	MAIN
 */
public class main
{

	/**
	 * 
	 * @param args
	 */
	@SuppressWarnings("all")
	public static void main(String[] args){
		//
		Utility.checkParameters(args);
		//
		Thread t = new Thread(new getClientIsWantedDifference());
    	t.start();
		//
        try {
            @SuppressWarnings("unused")
			UpdaterFrame updateInfo = UpdaterFrame.getInstance();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
