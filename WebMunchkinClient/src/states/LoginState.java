package states;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import client.Client;

import utility.ComMsg;
import utility.Utility;

/**
 * Diese Klasse repr�sentiert den Zustand des Programms w�hrend dem Einloggen
 * 
 * @author Dirk Kleiner, Karsten Schatz, Marius Kleiner
 * @version 0.1a
 * 
 */
public class LoginState implements Runnable
{

	// Klassenvariablen
	private ObjectOutputStream objOutStream = null;
	private ObjectInputStream objInStream = null;

	/**
	 * Konstruktor
	 * 
	 * @param objOutStream
	 * @param objInStream
	 */
	public LoginState(ObjectOutputStream objOutStream,
			ObjectInputStream objInStream)
	{
		// Setze Variablen
		this.objInStream = objInStream;
		this.objOutStream = objOutStream;
	}

	/**
	 * F�hre eine LoginAnfrage durch
	 * 
	 * @param login
	 * @param pw
	 * @return Erfolg des Loginversuchs
	 */
	public boolean doLoginRequest(String login, String pw)
	{
		try
		{
			ComMsg loginMsg = new ComMsg(ComMsg.com_authentication_request,
					login + "|" + pw);
			Utility.debugMsg("Sending authentication login data...");
			objOutStream.writeObject(loginMsg);
			return true;
		}
		catch (IOException e)
		{
			Utility.errorMsg("Couldn't connect to Server", e);
		}
		return false;
	}

	/**
	 * Run H�re auf Antwort(en)
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
			Client.closeClient();
		}
	}

	/**
	 * Verarbeite eingegangene Nachricht
	 * 
	 * @param inObject
	 */
	private void parseMsg(Object obj)
	{
		// Pr�fe Objekttyp und verfahre dementsprechend
		if (obj instanceof utility.ComMsg)
		{
			// Caste zu einer comMsg
			ComMsg msg = (ComMsg) obj;
			// Pr�fe Art der Nachricht
			if (msg.getType().equals(ComMsg.com_authentication_ack))
			{
				Utility.debugMsg("Login has been accepted");
			}
			else if (msg.getType().equals(ComMsg.com_authentication_nack))
			{
				Utility.debugMsg("Login has been declined");
			}
		}
		else
		{
			Utility.debugMsg("Received unknown MessageObject");
		}
	}

}
