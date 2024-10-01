// Package declaration
package ddialog;

// Import
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import dgraphics.DButton;
import dgraphics.DFont;
import dgraphics.DLabel;
import dgraphics.DTheme;


/**
 * Class Info Dialog
 * 
 * @author  AdrienG
 * @version 0.0
 */
public class DDialogInfo extends JDialog {

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
    public DDialogInfo(JFrame parent, String title, String[] listInfos) {

        // Herited constructor
        super(parent, "", true);
        this.setUndecorated(true);


        // Creating the main JPanel
        mainPanel.setBorder     (new EmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground (DTheme.GUI_DRK_L);
        mainPanel.setLayout     (new BorderLayout());


        // Creating the label for the title
        DLabel titleLabel             = new DLabel(title, DFont.JOST_SEMIBOLD, 18, DTheme.FNT_NTL_N);
        titleLabel  .setHorizontalAlignment (SwingConstants.CENTER);
        mainPanel   .add                    (titleLabel, BorderLayout.NORTH);


        // Displaying infos
        this.infoDisplay(listInfos);

       
        // Creating the ok button
        DButton okButton  = new DButton("Ok",   DFont.JOST_SEMIBOLD, 18, DTheme.FNT_NTL_N, DTheme.BTN_NTL_D, DTheme.BTN_NTL_N, DTheme.BTN_NTL_L);
         
        
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
            DLabel infoLabel              = new DLabel(info, DFont.JOST_LIGHT, 16, DTheme.FNT_NTL_D);
            infoLabel   .setHorizontalAlignment (SwingConstants.CENTER);
            infoPanel   .add                    (infoLabel);

        }


        // Plotting infos
        mainPanel.add(infoPanel, BorderLayout.CENTER);

    }

}
