package gui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import error.CouldNotLoadFileException;

import utility.Constants;
import utility.Utility;

/**
 * Diese Klasse repräsentiert einen SplashScreen, welcher zu Beginn des
 * Programmstarts gezeigt wird.
 * 
 * @author Dirk Kleiner, Karsten Schatz, Marius Kleiner
 * @version 0.1a
 * 
 */
@SuppressWarnings("serial")
public class SplashScreen extends JFrame
{

	// Klassenvariablen
	private BufferedImage icon;

	/**
	 * Konstruktor
	 */
	public SplashScreen()
	{
		// Lade das SplashScreen Bild
		URL img = null;
		try
		{
			img = Utility.getFileURL(Constants.img_splashscreen);
		}
		catch (CouldNotLoadFileException e)
		{
			Utility.errorMsg("Could not load file from path " + e.path, e);
		}

		try
		{
			icon = ImageIO.read(img);
		}
		catch (IOException e1)
		{
			Utility.errorMsg("Could not open file", e1);
		}

		// Setze SplashScreengröße(n)
		this.setPreferredSize(new Dimension(icon.getWidth(), icon.getHeight()));
		this.setSize(new Dimension(icon.getWidth(), icon.getHeight()));
		this.setMinimumSize(new Dimension(icon.getWidth(), icon.getHeight()));

		// Window Einstellungen
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setUndecorated(true);

		// Setze Hintergrund auf durchsichtig
		this.setBackground(new Color(0.0f, 0.0f, 0.0f, 0.0f));
		pack();

		// Fensterposition mittig setzen
		setLocationRelativeTo(null);

		// Sichtbar machen
		this.setVisible(true);

		// Vorläufiger 2 Sekunden wait-Timer
		for (int i = 0; i < 2; i++)
		{
			try
			{
				Thread.sleep(1000);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}

		// Schließe den Splashscreen
		this.dispose();
	}

	/**
	 * Zeichne den Splashscreen
	 */
	public void paint(Graphics g)
	{
		g.drawImage(icon, 0, 0, null, this);
	}
}