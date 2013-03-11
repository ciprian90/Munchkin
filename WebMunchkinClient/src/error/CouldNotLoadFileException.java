package error;

/**
 * Dieser Fehler wird geworfen, wenn ein File aus dem JAR Datenarchiv nicht
 * geladen werden konnte.
 * 
 * @author Dirk Kleiner, Karsten Schatz, Marius Kleiner
 * @version 0.1a
 * 
 */
@SuppressWarnings("serial")
public class CouldNotLoadFileException extends Exception
{

	// Klassenvariablen
	public String path; // Speichert den Pfad zu der Datei,
						// welche hätte geladen werden sollen

	/**
	 * Konstruktor
	 * 
	 * @param path
	 */
	public CouldNotLoadFileException(String path)
	{
		this.path = path;
	}
}
