// Package declaration
package deminer_dialog;

// Imports
import deminer_graphic.DTheme;
import deminer_graphic.DButton;
import deminer_graphic.DFont;
import deminer_graphic.DLabel;
import java.awt.BorderLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;


/**
 * Class Binary Dialog (binary question popup, with yes/no answer)
 * 
 * @author  AdrienG
 * @version 0.0
 */
public class DDialogBinary extends JDialog {

    /**
     * Attributes
     */
    private boolean userChoice;




    /**
     * Constructor
     * 
     * @param parent            JFrame that own the dialog
     * @param message           Dialog message
     */
    public DDialogBinary(JFrame parent, String message) {

        // Herited constructor
        super(parent, "", true);
        this.setUndecorated(true);


        // Creating the main JPanel
        JPanel panel = new JPanel();
        panel.setBorder     (new EmptyBorder(10, 10, 10, 10));
        panel.setBackground (DTheme.GUI_DRK_L);
        panel.setLayout     (new BorderLayout());


        // Creating the label for the message
        DLabel messageLabel = new DLabel(message, DFont.JOST_SEMIBOLD, 18, DTheme.FNT_NTL_D);
        messageLabel.setHorizontalAlignment (SwingConstants.CENTER);
        panel       .add                    (messageLabel, BorderLayout.NORTH);


        // Creating the panel for the yes and no buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder       (new EmptyBorder(15, 0, 0, 0));
        buttonPanel.setBackground   (DTheme.GUI_DRK_L);


        // Creating the yes and no buttons
        DButton noButton  = new DButton("No",   DFont.JOST_SEMIBOLD, 18, DTheme.FNT_NTL_D, DTheme.BTN_RED_D, DTheme.BTN_RED_N, DTheme.BTN_RED_L);
        DButton yesButton = new DButton("Yes",  DFont.JOST_SEMIBOLD, 18, DTheme.FNT_NTL_D, DTheme.BTN_GRN_D, DTheme.BTN_GRN_N, DTheme.BTN_GRN_L);
        
        
        // No button action
        noButton.addActionListener(e -> {
            userChoice = false;
            this.dispose();
        });


        // Yes button action
        yesButton.addActionListener(e -> {
            userChoice = true;
            this.dispose();
        });
        
        
        // Plotting the buttons
        buttonPanel.add(noButton);
        buttonPanel.add(yesButton);


        // Adding the button pane to the dialog
        panel.add(buttonPanel, BorderLayout.SOUTH);


        // Adding the whole to the dialog frame
        this.setContentPane (panel);
        this.pack           ();


        // Displaying the dialog relative to the prarent frame
        this.setLocationRelativeTo(parent);

    }




    /**
     * Getter : to get what option did the user choose
     * 
     * @return userChoice : true = yes, false = no
     */
    public boolean getUserChoice() {
        return userChoice;
    }

}
