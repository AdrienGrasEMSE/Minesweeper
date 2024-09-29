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


/**
 * Class Binary Dialog (binary question popup, with yes/no answer)
 * 
 * @author  AdrienG
 * @version 0.0
 */
public class DeminerDialogCustomNewGame extends JDialog {

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
     * Attributes : answer
     */
    private int customWidth;
    private int customHeight;
    private int customNbMines;




    /**
     * Constructor
     * 
     * @param parent            JFrame that own the dialog
     */
    public DeminerDialogCustomNewGame(JFrame parent) {

        // Herited constructor
        super(parent, "Custom game parameters", true);


        // Creating the main JPanel
        JPanel panel = new JPanel();
        panel.setBackground (GUI_DARK_GREY);
        panel.setLayout     (new BorderLayout());


        // Creating the label for the message
        DeminerLabel messageLabel = new DeminerLabel("Enter the custom game parameters :", DeminerFont.JOST_SEMIBOLD, 18, Color.WHITE);
        messageLabel.setHorizontalAlignment (SwingConstants.CENTER);
        panel       .add                    (messageLabel, BorderLayout.NORTH);


        // Creating the panel for the parameters
        JPanel paramPanel = new JPanel();
        paramPanel.setBackground(GUI_DARK_GREY);


        // Creating labels
        DeminerLabel customWidthLab     = new DeminerLabel("Custom width :",    DeminerFont.JOST_LIGHT, 18, Color.WHITE);
        DeminerLabel customHeightLab    = new DeminerLabel("Custom Height :",   DeminerFont.JOST_LIGHT, 18, Color.WHITE);
        DeminerLabel customNbMinesLab   = new DeminerLabel("Number of Mines :", DeminerFont.JOST_LIGHT, 18, Color.WHITE);
        paramPanel.add(customWidthLab);
        paramPanel.add(customHeightLab);
        paramPanel.add(customNbMinesLab);
        panel.add(paramPanel, BorderLayout.CENTER);


        // Creating the panel for the confirm and cancel buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(GUI_DARK_GREY);


        // Creating the confirm and cancel buttons
        DeminerButton confirmButton = new DeminerButton("Confirm",  DeminerFont.JOST_SEMIBOLD, 18, Color.WHITE, BTN_RED_DEFAULT, BTN_RED_FLYOVER, BTN_RED_ACTIVE);
        DeminerButton cancelButton  = new DeminerButton("Cancel",   DeminerFont.JOST_SEMIBOLD, 18, Color.WHITE, BTN_GRN_DEFAULT, BTN_GRN_FLYOVER, BTN_GRN_ACTIVE);
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);


        // Adding the button pane to the dialog
        panel.add(buttonPanel, BorderLayout.SOUTH);


        // Adding the whole to the dialog frame
        setContentPane          (panel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack                    ();


        // Displaying the dialog relative to the prarent frame
        setLocationRelativeTo(parent);


        // Yes button action
        // TODO make these action usable
        confirmButton.addActionListener(e -> {
            System.out.println("Vous avez choisi 'Confirm'");
            dispose();
        });


        // No button action
        cancelButton.addActionListener(e -> {
            System.out.println("Vous avez choisi 'Cancel'");
            dispose();
        });
    }
}
