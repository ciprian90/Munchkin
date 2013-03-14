package utility;

/**
 * Versioning of Game Client.
 * 
 * @author Marius Kleiner
 * @version 1.0
 *
 */
public class Version
{

	// Class variables
	public boolean valid = true;
	public int[] versionDigits = null;

	/**
	 * Constructor. Converts incominc String to a valid
	 * version (represented by an int[]). If anything
	 * fails it sets the class variable 'valid' to false.
	 * @param versionAsString
	 */
	public Version(String versionAsString) {
		try {
			String[] split = versionAsString.split("\\.");
			if (split.length != 2) {
				throw new NumberFormatException();
			}
			versionDigits = new int[split[1].length() + 1];
			versionDigits[0] = Integer.parseInt(split[0]);
			char[] c = split[1].toCharArray();
			for (int i = 0; i < c.length; i++) {
				String s = Character.toString(c[i]);
				versionDigits[i+1] = Integer.parseInt(s);
			}
		}catch (NumberFormatException e) {
			versionDigits = new int[1];
			versionDigits[0] = 0;
			valid = false;
		}
	}
	
	/**
	 * Compare this Version to another. Works like Comparable.
	 * @param v
	 * @return
	 */
	public int compareTo(Version v)
	{
		for (int i = 0; i < Math.min(v.versionDigits.length, versionDigits.length); i++) {
			if (versionDigits[i] < v.versionDigits[i]) {
				return -1;
			}else if (versionDigits[i] > v.versionDigits[i]) {
				return 1;
			}
		}
		//
		if (versionDigits.length < v.versionDigits.length) {
			return -1;
		}else if (versionDigits.length > v.versionDigits.length) {
			return 1;
		}
		//
		return 0;
	}

}
