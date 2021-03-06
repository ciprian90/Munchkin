package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.net.ssl.SSLSocket;

import error.NackLoginException;
import error.WrongMessageException;

import utility.Constants;
import utility.SqlTableNames;
import utility.Utility;
import utility.ComMsg;

/**
 * Diese Klasse verarbeitet die Kommunikation mit (je) einem Client
 * 
 * @author Dirk Kleiner, Karsten Schatz, Marius Kleiner
 * @version 0.1a
 * 
 */
public class ServerInstance implements Runnable
{

	// Klassenvariablen
	public SSLSocket sslsocket = null;
	public InputStream inputstream = null;
	public ObjectInputStream objInStream = null;
	public OutputStream outputstream = null;
	public ObjectOutputStream objOutStream = null;
	// SQL
	public static Connection sqlConnection = null;
	public static java.sql.Statement sqlStatement = null; // Enth�lt SQL
	// Anfragestatements
	public static ResultSet sqlResult = null; // Enth�lt SQL Anfrageergebnisse
	// Authentifizierung
	boolean authentication = false;

	/**
	 * Konstruktor
	 * 
	 * @param sslsocket
	 * @param sqlConnection
	 * 
	 */
	@SuppressWarnings("static-access")
	public ServerInstance(SSLSocket sslsocket, Connection sqlConnection)
			throws IOException
	{
		// Variablen
		this.sslsocket = sslsocket;
		this.sqlConnection = sqlConnection;
		// Erstelle ben�tigte Streams
		inputstream = sslsocket.getInputStream();
		objInStream = new ObjectInputStream(inputstream);
		outputstream = sslsocket.getOutputStream();
		objOutStream = new ObjectOutputStream(outputstream);
	}

	/**
	 * Runnable
	 */
	public void run()
	{
		Object inObject = null;
		// Warte auf eingehende Nachrichten (loop 4ever)
		try
		{
			// Lese eine Zeile, bis es nichts mehr zu lesen gibt....
			while ((inObject = objInStream.readObject()) != null)
			{
				// Interpretiere erhaltene Nachricht
				Utility.debugMsg("Receiving MessageObject");
				parseMsg(inObject);
			}
		}
		catch (IOException | ClassNotFoundException e)
		{
			// Fehler w�hrend aus bufferedReader gelesen wird, oder
			// (wahscheinlicher)
			// Verbindungsabbruch
			Utility.debugMsg("Client disconnected");
		}
		finally
		{
			closeConnection();
		}
	}

	/**
	 * Schlie�e diese Verbindung
	 */
	public void closeConnection()
	{
		// Release alle gebundenen Ressourcen
		try
		{
			objOutStream.close();
		}
		catch (IOException e)
		{
			objOutStream = null;
		}
		try
		{
			outputstream.close();
		}
		catch (IOException e)
		{
			outputstream = null;
		}
		try
		{
			objInStream.close();
		}
		catch (IOException e)
		{
			objInStream = null;
		}
		try
		{
			inputstream.close();
		}
		catch (IOException e)
		{
			inputstream = null;
		}
		try
		{
			sslsocket.close();
		}
		catch (IOException e)
		{
			sslsocket = null;
		}
		if (sqlResult != null)
		{
			try
			{
				sqlResult.close();
			}
			catch (SQLException sqlEx)
			{} // ignore
			sqlResult = null;
		}
		if (sqlStatement != null)
		{
			try
			{
				sqlStatement.close();
			}
			catch (SQLException sqlEx)
			{} // ignore
			sqlStatement = null;
		}
	}

	/**
	 * Verarbeite eingegangene Nachricht
	 * 
	 * @param obj
	 */
	private void parseMsg(Object obj)
	{
		// Pr�fe Objekttyp und verfahre dementsprechend
		if (obj instanceof utility.ComMsg)
		{
			try
			{
				// Caste zu einer comMsg
				ComMsg msg = (ComMsg) obj;
				// Pr�fe Art der Nachricht
				if (msg.getType().equals(ComMsg.com_authentication_request))
				{
					checkAuthentication(msg.getMsg());
				}
			}
			catch (WrongMessageException e)
			{
				Utility.errorMsg("Broken messageObjekt or SQL request error", e);
			}
		}
		else
		{
			Utility.debugMsg("Received unknown MessageObject");
		}

	}

	/**
	 * Pr�fe Logindaten
	 * 
	 * @param string
	 * @throws WrongMessageException
	 */
	private void checkAuthentication(String string)
			throws WrongMessageException
	{
		// String verarbeiten
		String[] stringArray = string.split("\\|");
		if (stringArray.length == 2)
		{
			String login = stringArray[0];
			String passwort = stringArray[1];
			// Pr�fe logindaten in der SQL Datenbank
			// SQL Anfrage
			ResultSet result = sqlRequest("*", "`"
					+ SqlTableNames.sqlt_acc_account + "`", "`"
					+ SqlTableNames.sqlt_acc_login + "` = '" + login + "'",
					Constants.sql_limit_standard);
			// Pr�fe, ob Ergebnis vorhanden
			try
			{
				if (result != null)
				{
					if (result.next())
					{
						if (result.getString(SqlTableNames.sqlt_acc_pw).equals(
								passwort))
						{
							// Login angenommen
							answerLoginRequest(true);
							Utility.debugMsg("Login angenommen!");
						}
						else
						{
							// Keine korrekten Logindaten
							throw new NackLoginException();
						}
					}
					else
					{
						// Keine korrekten Logindaten
						throw new NackLoginException();
					}
				}
				else
				{
					// Keine korrekten Logindaten
					throw new NackLoginException();
				}
			}
			catch (NackLoginException | SQLException e)
			{
				// Die Loginanfrage wird abgewiesen
				answerLoginRequest(false);
				Utility.debugMsg("Login abgewiesen!");
			}
		}
		else
		{
			throw new WrongMessageException();
		}
	}

	/**
	 * Sende eine Antwort auf die Login Anfrage
	 * 
	 * @param answer
	 */
	public void answerLoginRequest(boolean answer)
	{
		try
		{
			ComMsg loginAnsw = null;
			if (answer)
			{
				loginAnsw = new ComMsg(ComMsg.com_authentication_ack, "");
			}
			else
			{
				loginAnsw = new ComMsg(ComMsg.com_authentication_nack, "");
			}
			Utility.debugMsg("Sending login request answer");
			objOutStream.writeObject(loginAnsw);
		}
		catch (IOException e)
		{
			Utility.errorMsg("Error while sending message to client", e);
		}
	}

	/**
	 * F�hre eine Anfrage an die SQL Datenbank aus
	 * 
	 * @return Ergebnis der Anfrage
	 */
	public static ResultSet sqlRequest(String select, String from,
			String where, String limit)
	{
		try
		{
			String request = "SELECT " + select + " FROM " + from + " WHERE "
					+ where + " LIMIT " + limit;
			sqlStatement = sqlConnection.createStatement();
			if (sqlStatement.execute(request))
			{
				sqlResult = sqlStatement.getResultSet();
				Utility.debugMsg("Gathered SQL request: " + request);
				return sqlResult;
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}

}
