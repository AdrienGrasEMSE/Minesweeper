// Package declaration
package ddialog;

import java.awt.BorderLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import dgraphics.DFont;
import dgraphics.DLabel;
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
    private final   DLabel      label;
    private final   String[]    labelPhase = new String[4];
    private         int         labelIndex = 0;
    private final   Timer       animationService;




    /**
     * Constructor
     * 
     * @param parent            JFrame that own the dialog
     * @param message           Dialog message
     * @param listInfos         list of info that will be displayed on different line
     */
    public DDialogLoad(JFrame parent, String title, DColors_UI colorSet) {

        // Herited constructor
        super(parent, "", false);
        this.setUndecorated (true);
        this.setBackground  (DTheme.TRSPCOL);


        // Creating the main JPanel
        JPanel mainPanel = new JPanel();
        mainPanel.setBorder     (new EmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground (colorSet.BCK_N);
        mainPanel.setLayout     (new BorderLayout());


        // Creating the label for the wait animation
        this.label           = new DLabel            (title + "...", DFont.JOST_SEMIBOLD, 18, DTheme.LAB_TRS);
        label                .setHorizontalAlignment (SwingConstants.CENTER);
        mainPanel           .add                    (label, BorderLayout.NORTH);


        // Adding the whole to the dialog frame
        this.setContentPane (mainPanel);
        this.pack           ();


        // Displaying the dialog relative to the prarent frame
        this.setLocationRelativeTo(parent);


        // Creating animation phase
        labelPhase[0] = title;
        labelPhase[1] = title + ".";
        labelPhase[2] = title + "..";
        labelPhase[3] = title + "...";


        // Creating timer
        animationService = new Timer(500, e -> {

            // Mettre à jour le texte du JLabel avec l'étape actuelle
            label.setText(labelPhase[labelIndex]);


            // Incrementing
            if (labelIndex < 3) {
                labelIndex ++;
            } else {
                labelIndex = 0;
            }

        });


        // Start animation service
        this.animationService.start();

    }

}
