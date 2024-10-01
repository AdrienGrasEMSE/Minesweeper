// Package declaration
package deminer_dialog;


// Import
import deminer_graphic.DTheme;
import deminer_graphic.DeminerButton;
import deminer_graphic.DeminerFont;
import deminer_graphic.DeminerLabel;
import java.awt.BorderLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;


/**
 * Class End Game Dialog : popup that occurs at the end of the game after every type of end
 * 
 * @author  AdrienG
 * @version 0.0
 */
public class DeminerDialogEndGame extends JDialog {

    /**
     * Attributes
     */
    private boolean userChoice;




    /**
     * Constructor
     * 
     * @param parent            JFrame that own the dialog
     * @param message           Dialog title
     */
    public DeminerDialogEndGame(JFrame parent, EndGame endGameType) {

        // Herited constructor
        super(parent, "", true);
        this.setUndecorated(true);


        // Creating the main JPanel
        JPanel panel = new JPanel();
        panel.setBorder     (new EmptyBorder(10, 10, 10, 10));
        panel.setBackground (DTheme.GUI_DRK_L);
        panel.setLayout     (new BorderLayout());


        // Setting up the title and the message
        String title = "";
        switch (endGameType) {
            case EndGame.WIN                -> title   = "You win !";
            case EndGame.MINES_CLIKED       -> title   = "You has explosed yourself...";
            case EndGame.MAX_TIME_REACHED   -> title   = "Time limit reached : you lost...";
        }


        // Creating the label for the title
        DeminerLabel titleLabel = new DeminerLabel(title, DeminerFont.JOST_SEMIBOLD, 18, DTheme.FNT_NTL_N);
        titleLabel  .setHorizontalAlignment (SwingConstants.CENTER);
        panel       .add                    (titleLabel, BorderLayout.NORTH);


        // Creating the panel for the message
        JPanel messagPanel = new JPanel();
        messagPanel.setBorder       (new EmptyBorder(15, 0, 15, 0));
        messagPanel.setBackground   (DTheme.GUI_DRK_L);


        // Creating the label
        DeminerLabel messageLabel = new DeminerLabel("Do you want to start a new game ?", DeminerFont.JOST_LIGHT, 16, DTheme.FNT_NTL_D);
        messageLabel.setHorizontalAlignment (SwingConstants.CENTER);
        messagPanel .add                    (messageLabel);
        panel       .add                    (messagPanel, BorderLayout.CENTER); 


        // Creating the panel for the yes and no buttons
        JPanel buttonPanel          = new JPanel();
        buttonPanel.setBorder       (new EmptyBorder(0, 0, 0, 0));
        buttonPanel.setBackground   (DTheme.GUI_DRK_L);


        // Creating the yes and no buttons
        DeminerButton quitButton    = new DeminerButton("Quit",     DeminerFont.JOST_SEMIBOLD, 18, DTheme.FNT_NTL_D, DTheme.BTN_RED_D, DTheme.BTN_RED_N, DTheme.BTN_RED_L);
        DeminerButton newGameButton = new DeminerButton("New game", DeminerFont.JOST_SEMIBOLD, 18, DTheme.FNT_NTL_D, DTheme.BTN_GRN_D, DTheme.BTN_GRN_N, DTheme.BTN_GRN_L);
        
        
        // No button action
        quitButton.addActionListener(e -> {
            userChoice = false;
            this.dispose();
        });


        // Yes button action
        newGameButton.addActionListener(e -> {
            userChoice = true;
            this.dispose();
        });
        
        
        // Plotting the buttons
        buttonPanel.add(quitButton);
        buttonPanel.add(newGameButton);


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
