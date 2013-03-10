package gui;
import java.awt.Dimension;
import java.awt.Graphics;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import error.CouldNotLoadFileException;


import utility.Constants;
import utility.Utility;

@SuppressWarnings("serial")
public class SplashScreen extends JFrame implements Runnable{

	private ImageIcon icon;
	
    public SplashScreen() {
        // Lade Image
    	URL img = null;
		try {
			img = Utility.getFileURL(Constants.img_splashscreen);
		} catch (CouldNotLoadFileException e) {
			Utility.errorMsg("Could not load file from path " + e.path, e);
		}
        icon = new ImageIcon(img);
        
        // Setze SplashScreengröße(n)
        this.setPreferredSize(new Dimension(icon.getIconWidth(),icon.getIconHeight()));
        this.setSize(new Dimension(icon.getIconWidth(),icon.getIconHeight()));
        this.setMinimumSize(new Dimension(icon.getIconWidth(),icon.getIconHeight()));

        // Window Einstellungen
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setUndecorated(true);
        pack();

        // Fensterposition mittig setzen
        setLocationRelativeTo(null);
        
        // Sichtbar machen
        this.setVisible(true);
        
        
        for (int i = 0; i < 2; i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.dispose();
		
        // Starte Thread
        //Thread t = new Thread(this);
        //t.start();
    }

    /**
     * Zeichne den Splashscreen
     */
    public void paint(Graphics g){
    	g.drawImage(icon.getImage(), 0, 0, null, this);
    }

	@Override
	public void run() {
		for (int i = 0; i < 2; i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.dispose();
		System.out.println("DISPOSE");
	}
}