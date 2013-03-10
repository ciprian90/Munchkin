package utility;

import java.io.Serializable;

/**
 * Diese Klasse repräsentiert eine Netzwerknachricht
 * 
 * @author Dirk Kleiner, Karsten Schatz, Marius Kleiner
 * @version 0.1a
 *
 */
@SuppressWarnings("serial")
public class ComMsg implements Serializable{

	// Klassenvariablen
	private String msgType = null;
	private String msg = null;
	
	/** 
	 * Nachrichtenkommunikation
	 */
	public static String com_authentication_request = "auth_req";	// Authentifizierungsanfrage:	<login>|<passwort>
	public static String com_authentication_ack = "auth_ack";		// Acknowledged / angenommen
	public static String com_authentication_nack = "auth_nack";		// Not acknowledged / abgewiesen
	
	/**
	 * Konstruktor
	 */
	public ComMsg(String type, String msg) {
		this.msgType = type;
		this.msg = msg;
	}
	
	/**
	 * Getter Methoden
	 */
	public String getType() {
		return msgType;
	}
	public String getMsg() {
		return msg;
	}
}
