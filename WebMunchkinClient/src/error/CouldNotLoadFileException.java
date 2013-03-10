package error;

@SuppressWarnings("serial")
public class CouldNotLoadFileException extends Exception {

	public String path;
	
	public CouldNotLoadFileException(String path) {
		this.path = path;
	}
}
