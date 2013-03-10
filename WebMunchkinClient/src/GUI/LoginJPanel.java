package gui;
import com.cloudgarden.layout.AnchorConstraint;
import com.cloudgarden.layout.AnchorLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

import javax.swing.WindowConstants;
import javax.swing.JFrame;
import javax.swing.JTextField;

import states.LoginState;
import utility.Utility;


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
public class LoginJPanel extends javax.swing.JPanel {
	@SuppressWarnings("unused")
	private JFrame frame;
	private LoginState state;
	private JTextField username;
	private JButton loginButton;
	private JTextField password;

	public LoginJPanel(JFrame frame, LoginState state) {
		super();
		// Variablen
		this.frame = frame;
		this.state = state;
		//
		frame.getContentPane().add(this);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	private void initGUI() {
		try {
			AnchorLayout thisLayout = new AnchorLayout();
			this.setLayout(thisLayout);
			this.setPreferredSize(new java.awt.Dimension(400, 300));
			this.setSize(new java.awt.Dimension(400, 300));
			this.setBackground(new java.awt.Color(0,0,0));
			{
				loginButton = new JButton();
				this.add(loginButton, new AnchorConstraint(418, 896, 495, 646, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				loginButton.setText("Login");
				loginButton.setPreferredSize(new java.awt.Dimension(100, 23));
				loginButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						Utility.debugMsg("Sending a login request...");
						state.doLoginRequest(username.getText(), password.getText());
					}
				});
			}
			{
				password = new JTextField();
				this.add(password, new AnchorConstraint(418, 616, 495, 338, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				password.setText("password");
				password.setPreferredSize(new java.awt.Dimension(111, 23));
			}
			{
				username = new JTextField();
				this.add(username, new AnchorConstraint(418, 308, 495, 31, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
				username.setText("username");
				username.setPreferredSize(new java.awt.Dimension(111, 23));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
