// Package declaration
package deminer_dialog;

// Import
import deminer_graphic.DTheme;
import deminer_graphic.DeminerButton;
import deminer_graphic.DeminerFont;
import deminer_graphic.DeminerLabel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;


/**
 * Class Info Dialog
 * 
 * @author  AdrienG
 * @version 0.0
 */
public class DeminerDialogInfo extends JDialog {

    /**
     * Main panel
     */
    private final JPanel mainPanel = new JPanel();



    /**
     * Constructor
     * 
     * @param parent            JFrame that own the dialog
     * @param message           Dialog message
     * @param listInfos         list of info that will be displayed on different line
     */
    public DeminerDialogInfo(JFrame parent, String title, String[] listInfos) {

        // Herited constructor
        super(parent, "", true);
        this.setUndecorated(true);


        // Creating the main JPanel
        mainPanel.setBorder     (new EmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground (DTheme.GUI_DRK_L);
        mainPanel.setLayout     (new BorderLayout());


        // Creating the label for the title
        DeminerLabel titleLabel             = new DeminerLabel(title, DeminerFont.JOST_SEMIBOLD, 18, DTheme.FNT_NTL_N);
        titleLabel  .setHorizontalAlignment (SwingConstants.CENTER);
        mainPanel   .add                    (titleLabel, BorderLayout.NORTH);


        // Displaying infos
        this.infoDisplay(listInfos);

       
        // Creating the ok button
        DeminerButton okButton  = new DeminerButton("Ok",   DeminerFont.JOST_SEMIBOLD, 18, DTheme.FNT_NTL_N, DTheme.BTN_NTL_D, DTheme.BTN_NTL_N, DTheme.BTN_NTL_L);
         
        
        // Button action
        okButton.addActionListener(e -> {
            this.dispose();
        });


        // Adding the button pane to the dialog
        mainPanel.add(okButton, BorderLayout.SOUTH);


        // Adding the whole to the dialog frame
        this.setContentPane (mainPanel);
        this.pack           ();


        // Displaying the dialog relative to the prarent frame
        this.setLocationRelativeTo(parent);

    }




    /**
     * Plotting infos
     */
    private void infoDisplay(String[] listInfo) {

        // Creating a new panel that will hold all infos
        JPanel infoPanel = new JPanel(new GridLayout(listInfo.length, 1));
        infoPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
        infoPanel.setBackground (DTheme.GUI_DRK_L);


        // Crossing listInfo
        for (String info : listInfo) {

            // Creating the label for the message
            DeminerLabel infoLabel              = new DeminerLabel(info, DeminerFont.JOST_LIGHT, 16, DTheme.FNT_NTL_D);
            infoLabel   .setHorizontalAlignment (SwingConstants.CENTER);
            infoPanel   .add                    (infoLabel);

        }


        // Plotting infos
        mainPanel.add(infoPanel, BorderLayout.CENTER);

    }

}

