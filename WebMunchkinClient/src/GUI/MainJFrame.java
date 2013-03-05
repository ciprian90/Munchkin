package GUI;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import utility.Utility;

import Client.Client;

/**
 * Programmhauptfenster
 * 
 * @author Marius Kleiner, Dirk Kleiner
 * @version 0.1a
 *
 */
public class MainJFrame extends JFrame{

	// Klassenvariablen
	
	/**
	 * Konstruktor
	 */
	public MainJFrame() {
		// Folgender Befehl l�scht den Fensterrahmen (LauncherAnzeige)
		// setUndecorated(true);
		
		// Definiere Aktion beim schlie�en des Fensters
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				Utility.debugMsg("Window closed!");
				Client.closeClient();
			}
		});
	}
}
