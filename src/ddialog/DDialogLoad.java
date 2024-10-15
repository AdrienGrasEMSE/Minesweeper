// Package declaration
package ddialog;

// Import
import java.awt.BorderLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import dgraphics.DFont;
import dgraphics.DLoadLabel;
import dgraphics.dtheme.DColors_UI;
import dgraphics.dtheme.DTheme;


/**
 * Class Wait Dialog
 * 
 * @author  AdrienG
 * @version 0.0
 * 
 * 
 * Generate a loading dialog
 */
public class DDialogLoad extends JDialog {

    /**
     * Wait label
     */
    private final DLoadLabel wait;



    /**
     * Constructor
     * 
     * @param parent            JFrame that own the dialog
     * @param message           Dialog message
     * @param listInfos         list of info that will be displayed on different line
     */
    public DDialogLoad(JFrame parent, String title, DColors_UI colorSet) {

        // Herited constructor
        super(parent, "", true);
        this.setUndecorated (true);
        this.setBackground  (DTheme.TRSPCOL);


        // Creating the main JPanel
        JPanel mainPanel = new JPanel();
        mainPanel.setBorder     (new EmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground (colorSet.BCK_N);
        mainPanel.setLayout     (new BorderLayout());


        // Creating the label for the wait animation
        this.wait           = new DLoadLabel        (title, DFont.JOST_SEMIBOLD, 18, DTheme.LAB_TRS);
        wait                .setHorizontalAlignment (SwingConstants.CENTER);
        mainPanel           .add                    (wait, BorderLayout.NORTH);


        // Adding the whole to the dialog frame
        this.setContentPane (mainPanel);
        this.pack           ();


        // Displaying the dialog relative to the prarent frame
        this.setLocationRelativeTo(parent);

    }





    /**
     * Modigying the dispose method to stop the waitLabel
     */
    @Override
    public void dispose() {

        // Stoping the label
        wait.stop();
        

        // Herited method
        super.dispose();

    }

}
