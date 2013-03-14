package standard;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import updaterRunnables.CheckVersion;


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
/**
 *
 * @author Thomas Otero H3R3T1C
 */
@SuppressWarnings("serial")
public class UpdaterFrame extends JFrame{

	//
	private static UpdaterFrame instance;
	
	//
    private JEditorPane infoPane;
    private JScrollPane scp;
    public JButton ok;
    private JButton cancel;
    private JPanel pan1;
    private JPanel pan2;
    private int upToDate = -1;	// -1 couldNotConnectOrError, 0 = outdated, 1 = upToDate

    /**
     * 
     */
    private UpdaterFrame() {
    	//
        initComponents();
        //
        Thread t = new Thread(new CheckVersion());
    	t.start();
    }
    
    /**
     * 
     */
    public static UpdaterFrame getInstance() {
    	//
    	if (instance == null) {
    		instance = new UpdaterFrame();
    	}
    	//
    	return UpdaterFrame.instance;
    }
    
    /**
     * 
     */
	public void checkVersionOutcome(int outcome) {
		//
		upToDate = outcome;
		//
        if (outcome == 1) {
        	infoPane.setText("Client is up to date.");
        	this.ok.setText("Start Game");
        	this.ok.setEnabled(true);
        }else if (outcome == 0){
        	infoPane.setText("Client is outdated!");
        	this.ok.setText("Update");
        	this.ok.setEnabled(true);
        }else{
        	infoPane.setText("Connecting to updateServer...");
        	this.ok.setText("Retry");
        	this.ok.setEnabled(true);
        }
	}

	/**
     * 
     */
    @SuppressWarnings("deprecation")
	private void initComponents() {

        this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        this.setTitle("New Update Found");
        pan1 = new JPanel();
        pan1.setLayout(new BorderLayout());

        pan2 = new JPanel();
        pan2.setLayout(new FlowLayout());

        infoPane = new JEditorPane();
        infoPane.setContentType("text/html");

        scp = new JScrollPane();
        scp.setViewportView(infoPane);

        ok = new JButton("Update");
        ok.addActionListener(new OkActionListener());

        cancel = new JButton("Cancel");
        cancel.addActionListener( new ActionListener(){

            public void actionPerformed(ActionEvent e) {
                UpdaterFrame.this.dispose();
            }
        });
        pan2.add(ok);
        ok.setEnabled(false);
        ok.setText("Start");
        pan2.add(cancel);
        pan1.add(pan2, BorderLayout.SOUTH);
        pan1.add(scp, BorderLayout.CENTER);
        this.add(pan1);
        pack();
        show();
        this.setSize(300, 200);
    }
    
    /**
     * 
     * @author Marius
     *
     */
    public class OkActionListener implements ActionListener {
    	
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			if (upToDate == 1) {
	        	// Start Client
	        }else if (upToDate == 0){
	        	// Start update
	        	ok.setEnabled(false);
	            ok.setText("updating...");
	        }else{
	        	//
	        	ok.setEnabled(false);
	            ok.setText("...");
	            infoPane.setText("Fetching server version data...");
	            //
	        	Thread t = new Thread(new CheckVersion());
	        	t.start();
	        }
			
		}
    	
    }

}

