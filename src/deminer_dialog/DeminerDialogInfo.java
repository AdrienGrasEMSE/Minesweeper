// Package declaration
package deminer_dialog;

// Import
import deminer_graphic.DeminerButton;
import deminer_graphic.DeminerFont;
import deminer_graphic.DeminerLabel;
import java.awt.BorderLayout;
import java.awt.Color;
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
     * Background color
     */
    private final static Color GUI_DARK_GREY    = new Color(0x17202A);


    /**
     * Neutral bouton colors
     */
    private final static Color BTN_NTL_DEFAULT  = new Color(0x2C3E50);
    private final static Color BTN_NTL_FLYOVER  = new Color(0x34495E);
    private final static Color BTN_NTL_ACTIVE   = new Color(0x566573);


    /**
     * Font color
     */
    private final static Color FONT_DEFAULT     = new Color(0xEAECEE);


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
        mainPanel.setBackground (GUI_DARK_GREY);
        mainPanel.setLayout     (new BorderLayout());


        // Creating the label for the title
        DeminerLabel titleLabel             = new DeminerLabel(title, DeminerFont.JOST_SEMIBOLD, 18, FONT_DEFAULT);
        titleLabel  .setHorizontalAlignment (SwingConstants.CENTER);
        mainPanel   .add                    (titleLabel, BorderLayout.NORTH);


        // Displaying infos
        this.infoDisplay(listInfos);

       
        // Creating the ok button
        DeminerButton okButton  = new DeminerButton("Ok",   DeminerFont.JOST_SEMIBOLD, 18, FONT_DEFAULT, BTN_NTL_DEFAULT, BTN_NTL_FLYOVER, BTN_NTL_ACTIVE);
         
        
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
        infoPanel.setBackground (GUI_DARK_GREY);


        // Crossing listInfo
        for (String info : listInfo) {

            // Creating the label for the message
            DeminerLabel infoLabel              = new DeminerLabel(info, DeminerFont.JOST_LIGHT, 16, FONT_DEFAULT);
            infoLabel   .setHorizontalAlignment (SwingConstants.CENTER);
            infoPanel   .add                    (infoLabel);

        }


        // Plotting infos
        mainPanel.add(infoPanel, BorderLayout.CENTER);

    }

}

