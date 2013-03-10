package client;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.swing.JPanel;

import gui.LoginJPanel;
import gui.MainJFrame;
import gui.SplashScreen;

import states.LoginState;
import utility.Constants;
import utility.Utility;

import java.io.*;

/**
 * WebMunchkin Login Client. Baut eine SSL (Secure Sockets Layer) gesicherte Verbindung zum
 * WebMunchkinServer auf und startet den Programmablauf.
 * 
 * @author Dirk Kleiner, Karsten Schatz, Marius Kleiner
 * @version 0.1a
 *
 */
public class Client {
	
	// Klassenvariablen
	static InputStream inputstream = null;
	static InputStreamReader inputstreamreader = null;
	static BufferedReader bufferedreader = null;
	static OutputStream outputstream = null;
	static SSLSocket sslsocket = null;
	static ObjectOutputStream objOutStream = null;
	static Object state = null;
	static ObjectInputStream objInStream = null;
	static MainJFrame jFrame = null;
	static JPanel jPanel = null;
	static Thread stateThread = null;
	static boolean close = false;
	
	/**
	 * MAIN
	 * @param arstring
	 */
	public static void main(String[] arstring) {
		// Überprüfe Parameter
    	checkParameters(arstring);
    	
    	// SplashScreen
    	Utility.debugMsg("Create Splashscreen");
    	@SuppressWarnings("unused")
		SplashScreen splashscreen = new SplashScreen();
    	
    	// Fenster öffnen
		Utility.debugMsg("Create Window");
		jFrame = new MainJFrame();
    	
    	// Client Verbindung
    	serverConnection();
    }

	/**
	 * Baue eine Verbindung auf
	 */
	private static void serverConnection() {
		Utility.debugMsg("Trying to establish server connection...");
		try {
			// Setze SystemProperties für das Laden und öffnen der Zertifikatdatei
	    	// Bekanntgabe des Zertifikatpfades (relativ) an das System
			System.setProperty("javax.net.ssl.trustStore", Constants.certificate_path);
			
	        // Bekanntgabe des Zertifikatpassworts an das System
	        System.setProperty("javax.net.ssl.trustStorePassword", Constants.certificate_pw);
	        
	        // Erstelle einen SSL Socket für die Web-/Netz-Kommunikation
	        SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
			sslsocket = (SSLSocket) sslsocketfactory.createSocket("localhost", Constants.port);
			
	        // Erstelle einen Inputstream für Konsoleneingabe
	        inputstream = System.in;
	        inputstreamreader = new InputStreamReader(inputstream);
	        bufferedreader = new BufferedReader(inputstreamreader);
	        // Erstelle Outputstream
	        outputstream = sslsocket.getOutputStream();	
	        objOutStream = new ObjectOutputStream(outputstream);
	        // Erstelle Inputstream
	        inputstream = sslsocket.getInputStream();
			objInStream = new ObjectInputStream(inputstream);
			
			// Verbindung wurde aufgebaut....
			Utility.debugMsg("Server connection established");
			
			
			// Wechsle zum Login Status
	    	setLoginState(true);
	        
		} catch (IOException e) {
			Utility.errorMsg("Couldn't establish connection to server", e);
		}
		// Debugmessage
		Utility.debugMsg("Client has been started successfully");
	}
	
	/**
	 * Wechsle zum Loginbildschirm. State und Panel.
	 */
	public static void setLoginState(boolean serverOnline) {
		Utility.debugMsg("Loading LoginState");
		state = new LoginState(objOutStream, objInStream);
		stateThread = new Thread((Runnable) state);
		stateThread.start();
		jPanel = new LoginJPanel(jFrame, (LoginState)state);
	}
	
	/**
	 * Beende den Client
	 */
	public static void closeClient() {
		if (close) {
			return;
		}else{
			close = true;
		}
					
		Utility.debugMsg("Trying to close the client");
		// State releasen
		if (stateThread != null) {
			stateThread.interrupt();
			stateThread = null;
		}
		// Release alle gebundenen Ressourcen
		try {
			objInStream.close();
		} catch (IOException e2) {
			objInStream = null;
		}
		try {
			objOutStream.close();
		} catch (IOException e2) {
			objOutStream = null;
		}
		try {
			outputstream.close();
		} catch (IOException e1) {
			outputstream = null;
		}
		try {
			inputstream.close();
		} catch (IOException e1) {
			inputstream = null;
		}
		try {
			sslsocket.close();
		} catch (IOException e1) {
			sslsocket = null;
		}
		System.exit(0);
	}

	/**
     * Überprüfe und verarbeite die gesetzten Programmparameter 
     */
    private static void checkParameters(String[] arstring) {
    	// Check known parameters in arstring
    	for (String val : arstring) {
    		if (val.equals(Constants.parameter_debug)) {
    	    	Utility.debug = true;
    	    	Utility.debugMsg("Running in debug-mode");
    		}
    		
    	}
    }
}