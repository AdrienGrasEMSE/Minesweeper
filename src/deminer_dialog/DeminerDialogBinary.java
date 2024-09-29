// Package declaration
package deminer_dialog;

// Import
import deminer_graphic.DeminerButton;
import deminer_graphic.DeminerFont;
import deminer_graphic.DeminerLabel;
import java.awt.BorderLayout;
import java.awt.Color;
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
public class DeminerDialogBinary extends JDialog {

    /**
     * Background color
     */
    private final static Color GUI_DARK_GREY    = new Color(0x17202A);


    /**
     * Red bouton colors
     */
    private final static Color BTN_RED_DEFAULT  = new Color(0xCB4335);
    private final static Color BTN_RED_FLYOVER  = new Color(0xE74C3C);
    private final static Color BTN_RED_ACTIVE   = new Color(0xEA5E50);


    /**
     * Green bouton colors
     */
    private final static Color BTN_GRN_DEFAULT  = new Color(0x239B56);
    private final static Color BTN_GRN_FLYOVER  = new Color(0x28B463);
    private final static Color BTN_GRN_ACTIVE   = new Color(0x2ECC71);


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
    public DeminerDialogBinary(JFrame parent, String message) {

        // Herited constructor
        super(parent, "", true);
        this.setUndecorated(true);


        // Creating the main JPanel
        JPanel panel = new JPanel();
        panel.setBorder     (new EmptyBorder(10, 10, 10, 10));
        panel.setBackground (GUI_DARK_GREY);
        panel.setLayout     (new BorderLayout());


        // Creating the label for the message
        DeminerLabel messageLabel = new DeminerLabel(message, DeminerFont.JOST_SEMIBOLD, 18, Color.WHITE);
        messageLabel.setHorizontalAlignment (SwingConstants.CENTER);
        panel       .add                    (messageLabel, BorderLayout.CENTER);


        // Creating the panel for the yes and no buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder       (new EmptyBorder(15, 0, 0, 0));
        buttonPanel.setBackground   (GUI_DARK_GREY);


        // Creating the yes and no buttons
        DeminerButton noButton  = new DeminerButton("No",   DeminerFont.JOST_SEMIBOLD, 18, Color.WHITE, BTN_RED_DEFAULT, BTN_RED_FLYOVER, BTN_RED_ACTIVE);
        DeminerButton yesButton = new DeminerButton("Yes",  DeminerFont.JOST_SEMIBOLD, 18, Color.WHITE, BTN_GRN_DEFAULT, BTN_GRN_FLYOVER, BTN_GRN_ACTIVE);
        
        
        // Yes button action
        noButton.addActionListener(e -> {
            userChoice = false;
            this.dispose();
        });


        // No button action
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
