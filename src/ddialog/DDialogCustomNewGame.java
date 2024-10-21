// Package declaration
package ddialog;

// Import
import java.awt.GridLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import dgraphics.DButton;
import dgraphics.DFont;
import dgraphics.DIntegerField;
import dgraphics.DLabel;
import dgraphics.dtheme.DTheme;


/**
 * Class Custom New Game
 * 
 * @author  AdrienG
 * @version 0.0
 * 
 * 
 * Generate a Dialog that ask the user the parameters for a custom game
 * 
 * Fixed UI
 */
public class DDialogCustomNewGame extends JDialog {

    /**
     * Attributes : answer
     */
    private int     customWidth;
    private int     customHeight;
    private int     customNbMines;
    private boolean paramValid  = false;
    private boolean userConfirm;




    /**
     * Constructor
     * 
     * @param parent            JFrame that own the dialog
     */
    public DDialogCustomNewGame(JFrame parent) {

        // Herited constructor
        super(parent, "Custom game parameters", true);
        this.setUndecorated (true);
        this.setBackground  (DTheme.TRSPCOL);
        this.setLayout      (new GridLayout(5, 1));


        // Creating the label for the message
        JPanel      msgPanel                = new JPanel();
        DLabel      messageLabel            = new DLabel("Enter the custom game parameters :", DFont.JOST_SEMIBOLD, 18, DTheme.LAB_TRS);
        msgPanel    .setBorder              (new EmptyBorder(0, 0, 5, 0));
        msgPanel    .setBackground          (DTheme.DLG_DRK.BCK_N);
        msgPanel    .add                    (messageLabel);


        // Creating the panel for the width parameters
        JPanel        widthPanel            = new JPanel        (new GridLayout(1, 2));
        DLabel        widthLab              = new DLabel        ("Custom width :", DFont.JOST_LIGHT, 18, DTheme.LAB_TRS);
        DIntegerField widthInput            = new DIntegerField (DFont.JOST_LIGHT, 18, DTheme.FLD_DRK);
        widthPanel.setBorder                (new EmptyBorder(2, 10, 2, 10));
        widthPanel.setBackground            (DTheme.DLG_DRK.BCK_N);
        widthPanel.add                      (widthLab);
        widthPanel.add                      (widthInput);


        // Creating the panel for the height parameters
        JPanel        heightPanel           = new JPanel        (new GridLayout(1, 2));
        DLabel        heightLab             = new DLabel        ("Custom height :", DFont.JOST_LIGHT, 18, DTheme.LAB_TRS);
        DIntegerField heightInput           = new DIntegerField (DFont.JOST_LIGHT, 18, DTheme.FLD_DRK);
        heightPanel.setBorder               (new EmptyBorder(2, 10, 2, 10));
        heightPanel.setBackground           (DTheme.DLG_DRK.BCK_N);
        heightPanel.add                     (heightLab);
        heightPanel.add                     (heightInput);


        // Creating the panel for the number of mines
        JPanel        nbMinesPanel          = new JPanel        (new GridLayout(1, 2));
        DLabel        nbMinesLab            = new DLabel        ("Number of mines :", DFont.JOST_LIGHT, 18, DTheme.LAB_TRS);
        DIntegerField nbMinesInput          = new DIntegerField (DFont.JOST_LIGHT, 18, DTheme.FLD_DRK);
        nbMinesPanel.setBorder              (new EmptyBorder(2, 10, 2, 10));
        nbMinesPanel.setBackground          (DTheme.DLG_DRK.BCK_N);
        nbMinesPanel.add                    (nbMinesLab);
        nbMinesPanel.add                    (nbMinesInput);


        // Creating the panel for the confirm and cancel buttons
        JPanel buttonPanel          = new JPanel();
        buttonPanel.setBorder       (new EmptyBorder(5, 0, 0, 0));
        buttonPanel.setBackground   (DTheme.DLG_DRK.BCK_N);


        // Creating the confirm and cancel buttons
        DButton cancelButton  = new DButton("Cancel",   DFont.JOST_SEMIBOLD, 18, DTheme.BTN_RED);
        DButton confirmButton = new DButton("Confirm",  DFont.JOST_SEMIBOLD, 18, DTheme.BTN_GRN);
        

        // Yes button action
        confirmButton.addActionListener(e -> {

            // Saving parameters
            this.customWidth    = widthInput    .getIntegerValue();
            this.customHeight   = heightInput   .getIntegerValue();
            this.customNbMines  = nbMinesInput  .getIntegerValue();


            // Validation
            paramValidation();
            userConfirm = true;
            dispose();
        });


        // No button action
        cancelButton.addActionListener(e -> {
            userConfirm = false;
            dispose();
        });
        
        
        // Plotting the buttons
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

    }




    /**
     * Method to check parameters
     */
    private void paramValidation() {

        // Width validation
        if (customWidth < 5 || customWidth > 20) {
            
            // Invalid parameters
            paramValid = false;
            return;

        }
        

        // Height validation
        if (customHeight < 5 || customHeight > 20) {

            // Invalid parameters
            paramValid = false;
            return;

        }
        

        // NbMines validation
        if (customNbMines < 1 || customNbMines > ((customWidth * customHeight) / 4)) {

            // Invalid parameters
            paramValid = false;
            return;
            

        }
        

        // Valid parameters
        paramValid = true;

    }




    /**
     * Getter : to get if the user has confirm the parameters
     * 
     * @return userConfirm
     */
    public boolean getUserConfirm() {
        return userConfirm;
    }




    /**
     * Getter : to get if the parameters are valid
     * 
     * @return paramValid
     */
    public boolean getParamValid() {
        return paramValid;
    }




    /**
     * Getter : to get the custom width entered by the user
     * 
     * @return customWidth
     */
    public int getCustomWidth() {
        return customWidth;
    }




    /**
     * Getter : to get the custom height entered by the user
     * 
     * @return customHeight
     */
    public int getCustomHeight() {
        return customHeight;
    }




    /**
     * Getter : to get the number of mines height entered by the user
     * 
     * @return customNbMines the number of mines
     */
    public int getCustomNbMines() {
        return customNbMines;
    }


}
