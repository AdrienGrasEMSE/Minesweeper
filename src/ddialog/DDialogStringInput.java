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
import dgraphics.DLabel;
import dgraphics.DStringField;
import dgraphics.dtheme.DTheme;


/**
 * Class String Input Dialog
 * 
 * @author  AdrienG
 * @version 0.0
 * 
 * 
 * Generate a dialog which ask a string input
 */
public class DDialogStringInput extends JDialog {

    /**
     * Attributes : answer
     */
    private String  inputString;
    private boolean paramValid  = false;
    private boolean userConfirm;




    /**
     * Constructor
     * 
     * @param parent JFrame that own the dialog
     */
    public DDialogStringInput(JFrame parent, String title, String asked) {

        // Herited constructor
        super(parent, title, true);
        this.setUndecorated (true);
        this.setBackground  (DTheme.TRSPCOL);
        this.setLayout      (new GridLayout(3, 1));


        // Creating the label for the message
        JPanel      msgPanel                = new JPanel();
        DLabel      messageLabel            = new DLabel(title, DFont.JOST_SEMIBOLD, 18, DTheme.LAB_TRS);
        msgPanel    .setBorder              (new EmptyBorder(0, 0, 5, 0));
        msgPanel    .setBackground          (DTheme.DLG_DRK.BCK_N);
        msgPanel    .add                    (messageLabel);


        // Creating the panel for the string parameter
        JPanel          stringPanel         = new JPanel        (new GridLayout(1, 2));
        DLabel          stringLab           = new DLabel        (asked, DFont.JOST_LIGHT, 18, DTheme.LAB_TRS);
        DStringField    stringInput         = new DStringField  (DFont.JOST_LIGHT, 18, DTheme.FLD_NTL);
        stringPanel.setBorder               (new EmptyBorder(2, 10, 2, 10));
        stringPanel.setBackground           (DTheme.DLG_DRK.BCK_N);
        stringPanel.add                     (stringLab);
        stringPanel.add                     (stringInput);


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
            this.inputString    = stringInput   .getText();


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
        this.add(stringPanel);
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

        // If the input is empty
        if (inputString.isEmpty()) {

            // Invalid parameters
            paramValid = false;
            return;

        }
        

        // Valid parameters
        paramValid = true;

    }




    /**
     * Getter : to get if the user has confirm its input
     * 
     * @return userConfirm
     */
    public boolean getUserConfirm() {
        return userConfirm;
    }




    /**
     * Getter : to get if the string is valid
     * 
     * @return paramValid
     */
    public boolean getParamValid() {
        return paramValid;
    }




    /**
     * Getter : to get the user input
     * 
     * @return inputString
     */
    public String getInputString() {
        return inputString;
    }


}
