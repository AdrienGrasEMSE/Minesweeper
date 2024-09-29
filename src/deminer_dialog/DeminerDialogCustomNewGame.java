// Package declaration
package deminer_dialog;

// Import
import deminer_graphic.DeminerButton;
import deminer_graphic.DeminerFont;
import deminer_graphic.DeminerIntegerField;
import deminer_graphic.DeminerLabel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


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
     * Neutral bouton colors
     */
    private final static Color BTN_NTL_DEFAULT  = new Color(0x2C3E50);
    private final static Color BTN_NTL_FLYOVER  = new Color(0x34495E);


    /**
     * Font color
     */
    private final static Color FONT_DEFAULT     = new Color(0xEAECEE);


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
        // this.setSize        (new Dimension(500, 300));
        this.setUndecorated (true);
        this.setLayout      (new GridLayout(5, 1));


        // Creating the label for the message
        JPanel              msgPanel        = new JPanel();
        DeminerLabel        messageLabel    = new DeminerLabel("Enter the custom game parameters :", DeminerFont.JOST_SEMIBOLD, 18, FONT_DEFAULT);
        msgPanel    .setBorder              (new EmptyBorder(0, 0, 5, 0));
        msgPanel    .setBackground          (GUI_DARK_GREY);
        msgPanel    .add                    (messageLabel);


        // Creating the panel for the width parameters
        JPanel              widthPanel      = new JPanel                (new GridLayout(1, 2));
        DeminerLabel        widthLab        = new DeminerLabel          ("Custom width :", DeminerFont.JOST_LIGHT, 18, FONT_DEFAULT);
        DeminerIntegerField widthInput      = new DeminerIntegerField   (DeminerFont.JOST_LIGHT, 18, FONT_DEFAULT, BTN_NTL_DEFAULT, BTN_NTL_FLYOVER);
        widthPanel.setBorder                (new EmptyBorder(2, 10, 2, 10));
        widthPanel.setBackground            (GUI_DARK_GREY);
        widthPanel.add                      (widthLab);
        widthPanel.add                      (widthInput);


        // Creating the panel for the height parameters
        JPanel              heightPanel     = new JPanel                (new GridLayout(1, 2));
        DeminerLabel        heightLab       = new DeminerLabel          ("Custom height :", DeminerFont.JOST_LIGHT, 18, FONT_DEFAULT);
        DeminerIntegerField heightInput     = new DeminerIntegerField   (DeminerFont.JOST_LIGHT, 18, FONT_DEFAULT, BTN_NTL_DEFAULT, BTN_NTL_FLYOVER);
        heightPanel.setBorder               (new EmptyBorder(2, 10, 2, 10));
        heightPanel.setBackground           (GUI_DARK_GREY);
        heightPanel.add                     (heightLab);
        heightPanel.add                     (heightInput);


        // Creating the panel for the number of mines
        JPanel              nbMinesPanel    = new JPanel                (new GridLayout(1, 2));
        DeminerLabel        nbMinesLab      = new DeminerLabel          ("Number of mines :", DeminerFont.JOST_LIGHT, 18, FONT_DEFAULT);
        DeminerIntegerField nbMinesInput    = new DeminerIntegerField   (DeminerFont.JOST_LIGHT, 18, FONT_DEFAULT, BTN_NTL_DEFAULT, BTN_NTL_FLYOVER);
        nbMinesPanel.setBorder              (new EmptyBorder(2, 10, 2, 10));
        nbMinesPanel.setBackground          (GUI_DARK_GREY);
        nbMinesPanel.add                    (nbMinesLab);
        nbMinesPanel.add                    (nbMinesInput);


        // Creating the panel for the confirm and cancel buttons
        JPanel buttonPanel          = new JPanel();
        buttonPanel.setBorder       (new EmptyBorder(5, 0, 0, 0));
        buttonPanel.setBackground   (GUI_DARK_GREY);


        // Creating the confirm and cancel buttons
        DeminerButton cancelButton  = new DeminerButton("Cancel",   DeminerFont.JOST_SEMIBOLD, 18, Color.WHITE, BTN_RED_DEFAULT, BTN_RED_FLYOVER, BTN_RED_ACTIVE);
        DeminerButton confirmButton = new DeminerButton("Confirm",  DeminerFont.JOST_SEMIBOLD, 18, Color.WHITE, BTN_GRN_DEFAULT, BTN_GRN_FLYOVER, BTN_GRN_ACTIVE);
        buttonPanel.add(cancelButton);
        buttonPanel.add(confirmButton);


        // Plotting components
        this.add(msgPanel);
        this.add(widthPanel);
        this.add(heightPanel);
        this.add(nbMinesPanel);
        this.add(buttonPanel);


        // Calculing it's size
        this.pack();


        // Displaying the dialog relative to the prarent frame
        this.setLocationRelativeTo(parent);


        // Yes button action
        // TODO make these action usable
        cancelButton.addActionListener(e -> {
            System.out.println("Vous avez choisi 'Confirm'");
            dispose();
        });


        // No button action
        confirmButton.addActionListener(e -> {
            System.out.println("Vous avez choisi 'Cancel'");
            dispose();
        });
    }
}
