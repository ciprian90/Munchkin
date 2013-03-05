package Server;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

import utility.Constants;
import utility.Utility;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.*;	// DON'T REMOVE THIS! (import com.mysql.jdbc.*;)

/**
 * WebMunchkin Login Server. Nimmt SSL (Secure Sockets Layer) gesicherte Verbindungen von
 * WebMunchkinClients an.
 * 
 * @author Marius Kleiner, Dirk Kleiner
 * @version 0.1a
 *
 */
public class Server {
	
	// Klassenvariablen
	static Connection sqlConnection = null;	// Speichert die SQL Datenbank Verbindung
	static ArrayList<Thread> threadList = new ArrayList<Thread>();
	
	/**
	 * Main/Konstruktor
	 * @param arstring
	 */
    public static void main(String[] arstring) {
    	// Überprüfe Parameter
    	checkParameters(arstring);
    	
    	// Baue eine Verbindung zur SQL Datenbank auf...
    	connectToSqlDatabase();
    	
    	// Nehme Serverarbeiten auf...
    	serverTasks();
    }
    
    /**
     * 
     *
    private static void test() {
    	try {
			sqlStatement = sqlConnection.createStatement();
			if (sqlStatement.execute("SELECT * FROM `account` WHERE `login` = 'deadjack' LIMIT 0 , 30")) {
				sqlResult = sqlStatement.getResultSet();
				Utility.debugMsg(sqlResult.toString());
				if (sqlResult.next()) {
					Utility.debugMsg(sqlResult.getNString("username"));
				}
		    }
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
    	    // Alle gebundenen Ressourcen wieder releasen (bzgl. MySQL Datenbank)
			// Release statement
    	    if (sqlStatement != null) {
    	        try {
    	        	sqlStatement.close();
    	        } catch (SQLException sqlEx) { } // ignore
    	        sqlStatement = null;
    	    }
    	    // Release result
    	    if (sqlResult != null) {
    	        try {
    	        	sqlResult.close();
    	        } catch (SQLException sqlEx) { } // ignore
    	        sqlResult = null;
    	    }
		}
	}
	*/

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
    
    /**
     * Baue eine Verbindung zur SQL Datenbank auf...
     */
    private static void connectToSqlDatabase() {
    	Utility.debugMsg("Trying to connect to SQL database");
    	try {
    		// Lade SQL Verbindungstreiber
    		Class.forName("com.mysql.jdbc.Driver");
    		// Establish SQL connection
    		sqlConnection = DriverManager.getConnection("jdbc:mysql://" + Constants.sql_server + ":3306/" + Constants.sql_db_name, 
					Constants.sql_db_username, Constants.sql_db_pw);
			// Konsolenausgabe, wenn die Verbindung erfolgreich aufgebaut wurde
			Utility.debugMsg("SQL-Connection successfully established");
		} catch (SQLException e) {
			Utility.errorMsg("Could not establish connection to sql database. SQL-State: " + e.getSQLState() + 
					"\nCheck http://www.postgresql.org/docs/8.2/static/errcodes-appendix.html", e);
		} catch (ClassNotFoundException e) {
			Utility.errorMsg("Could not load database connection driver", e);
		}
    }
    
    /**
     * Nehme Serverarbeiten auf...
     */
    private static void serverTasks() {
        	// Setze SystemProperties für das Laden und öffnen der Zertifikatdatei
        	// Bekanntgabe des Zertifikatpfades (relativ) an das System
            System.setProperty("javax.net.ssl.keyStore", Constants.certificate_path);
            // Bekanntgabe des Zertifikatpassworts an das System
            System.setProperty("javax.net.ssl.keyStorePassword", Constants.certificate_pw);
            
            // Erstelle einen SSL Socket für die Web-/Netz-Kommunikation
            SSLServerSocketFactory sslserversocketfactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            SSLServerSocket sslserversocket = null;
			try {
				sslserversocket = (SSLServerSocket) sslserversocketfactory.createServerSocket(Constants.port);
			} catch (IOException e) {
				Utility.errorMsg("Couldn't initialize ssl connection", e);
			}
            
            // Horche unaufhörlich auf eingehende Verbindungen
            while (true) {
				try {
					// Warte bis eine Verbindung eingeht und akzeptiere diese...
	            	Utility.debugMsg("Server waiting for incoming connection...");
	            	SSLSocket sslsocket = (SSLSocket) sslserversocket.accept();
					Utility.debugMsg("Connection established");
	            	// Starte einen Thread für die akzeptierte Verbindung
	            	Thread tempThread = new Thread(new ServerInstance(sslsocket, sqlConnection));
	            	threadList.add(tempThread);
	            	tempThread.start();
				} catch (IOException e) {
					// Verwerfe diese Verbindung, da sie nicht aufgebaut werden konnte
					Utility.debugMsg("Could not establish connection to a single client");
				}
            	
            }
    }
}