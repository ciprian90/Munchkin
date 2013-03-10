package gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import client.Client;

import utility.Utility;

/**
 * Programmhauptfenster
 * 
 * @author Dirk Kleiner, Karsten Schatz, Marius Kleiner
 * @version 0.1a
 *
 */
@SuppressWarnings("serial")
public class MainJFrame extends JFrame{

	// Klassenvariablen
	
	/**
	 * Konstruktor
	 */
	public MainJFrame() {
		// Folgender Befehl löscht den Fensterrahmen (LauncherAnzeige)
		// setUndecorated(true);
		
		// Definiere Aktion beim schließen des Fensters
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				Utility.debugMsg("Window closed!");
				Client.closeClient();
			}
		});
	}
}
