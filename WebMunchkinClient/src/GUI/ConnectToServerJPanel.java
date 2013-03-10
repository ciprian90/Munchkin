package gui;

import java.awt.Dimension;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
@SuppressWarnings("serial")
public class ConnectToServerJPanel extends javax.swing.JPanel {
	private JProgressBar statusBar;
	private JTextField statusField;

	/**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	*/
	public ConnectToServerJPanel() {
		super();
		initGUI();
	}
	
	/**
	 * Initialisiere die GUI für den LoginScreen
	 */
	private void initGUI() {
		try {
			setPreferredSize(new Dimension(400, 300));
			this.setBackground(new java.awt.Color(0,0,0));
			this.setLayout(null);
			{
				statusBar = new JProgressBar();
				this.add(statusBar);
				statusBar.setBounds(128, 192, 148, 14);
			}
			{
				statusField = new JTextField();
				this.add(statusField);
				statusField.setText("...");
				statusField.setBounds(128, 152, 148, 23);
				statusField.setBackground(new java.awt.Color(0,0,0));
				statusField.setEditable(false);
				statusField.setForeground(new java.awt.Color(255,255,255));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
