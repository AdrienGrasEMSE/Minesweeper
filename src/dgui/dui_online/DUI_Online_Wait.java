// Pakcage declaration
package dgui.dui_online;

// Import
import ddialog.DDialogBinary;
import deminer.DController;
import dgraphics.DButton;
import dgraphics.DFont;
import dgraphics.DLabel;
import dgraphics.dtheme.DTheme;
import dgui.DGUI;
import donline.DPlayer;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


/**
 * Class Default Online UI
 * 
 * @author  AdrienG
 * @version 0.0
 * 
 * 
 * This class represent the default screen when the user play in multiplayer mode.
 */
public class DUI_Online_Wait extends JPanel implements DUI_Updatable {

    /**
     * UI main objects
     */
    private final   DController controller;
    private final   DGUI        gui;
    private final   DUI_Online  uiOnline;
    private         boolean     serverOwner;


    /**
     * UI main panels
     */
    private final   JPanel      northPanel          = new JPanel();
    private final   JPanel      southPanel          = new JPanel();
    private final   JPanel      centerPanel         = new JPanel();
    private final   JPanel      eastPanel           = new JPanel();


    /**
     * South panels elements
     */
    private final   DButton     backButton          = new DButton("Back",           DFont.JOST_SEMIBOLD,    24, DTheme.BTN_RED);
    private final   DButton     launchGameButton    = new DButton("Lauch the game", DFont.JOST_SEMIBOLD,    24, DTheme.BTN_GRN);


    /**
     * Player list
     */
    private         String      ownerUUI;
    private         Map<String, DPlayer> playerList;


    /**
     * UI Updater
     */
    private final   DUI_Online_Updater  updater;


    /**
     * All dialog
     */
    
    private         DDialogBinary       leaveDialog;



    
    /**
     * Constructor
     * 
     * @param gui in order to transmit data or action performed
     */
    public DUI_Online_Wait(DGUI gui, DUI_Online uiOnline, DController controller) {

        // Getting the gui and the controller
        this.controller = controller;
        this.gui        = gui;
        this.uiOnline   = uiOnline;


        // Setting up updater
        updater         = new DUI_Online_Updater(this);


        // Setting up layout and VFX
        this.setLayout(new BorderLayout());


        // Pannel setup
        this.northPanelSetup    ();
        this.southPanelSetup    ();
        this.centerPanelSetup   ();
        this.eastPanelSetup     ();


        // Setting up the leavDialog
        leaveDialog = new DDialogBinary(gui, "Confirm ?", DTheme.DLG_DRK);

    }




    /**
     * Setting up north panel
    */
    private void northPanelSetup() {

        // Plotting panel and set VFX
        this                .add            (northPanel,BorderLayout.NORTH);
        northPanel          .setLayout      (new BorderLayout());
        northPanel          .setBackground  (DTheme.GUI_VAR.BCK_N);


        // Creating the title panel
        JPanel titlePanel   = new JPanel    (new FlowLayout());
        titlePanel          .setBackground  (DTheme.GUI_VAR.BCK_N);
        northPanel          .add            (titlePanel, BorderLayout.CENTER);

        
        // Creating title label
        DLabel titleLabel   = new DLabel    ("Waiting for player...", DFont.JOST_SEMIBOLD, 40, DTheme.LAB_NTL);
        titleLabel          .setAlignmentX  (Component.CENTER_ALIGNMENT);
        titlePanel          .add            (titleLabel);

    }




    /**
     * Setting up south panel
    */
    private void southPanelSetup() {

        // Plotting panel and set VFX
        this                .add            (southPanel,BorderLayout.SOUTH);
        southPanel          .setLayout      (new FlowLayout());
        southPanel          .setBackground  (DTheme.GUI_DRK.BCK_N);


        // Plotting elements inside
        southPanel          .add(backButton);


        // Setting up listeners
        backButton          .addActionListener(this);

    }




    /**
     * Setting up center panel
     */
    private void centerPanelSetup() {

        // Plotting panel and set VFX
        this        .add            (centerPanel,   BorderLayout.CENTER);
        centerPanel .setLayout      (new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel .setBorder(new EmptyBorder(30, 30, 30, 30));
        centerPanel .setBackground  (DTheme.GUI_NTL.BCK_N);

    }




    /**
     * Setting up east panel
     */
    private void eastPanelSetup() {

        // Only if the client is the server owner
        if (this.serverOwner) {

            // Plotting panel and set VFX
            this                .add            (eastPanel, BorderLayout.EAST);
            eastPanel           .setLayout      (new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
            eastPanel           .setBorder      (new EmptyBorder(30, 30, 30, 30));
            eastPanel           .setBackground  (DTheme.GUI_NTL.BCK_D);


            // Plotting the launch game button
            eastPanel           .add            (launchGameButton);


            // Adding the listener
            launchGameButton    .addActionListener(this);

        }

    }




    /**
     * Setter : to change the client state (server owner or not)
     * 
     * @param serverOwner
     */
    public void setServerOwner(boolean serverOwner) {

        // Change the client state
        this.serverOwner = serverOwner;


        // Refresh the esat panel display
        if (this.isAncestorOf(eastPanel)) {
            this.remove(eastPanel);
        }


        // East panel setup
        this.eastPanelSetup();

    }




    /**
     * To update the player list and the server owner
     * 
     * @param playerList
     * @param ownerUUID
     */
    public void updatePlayerList(Map<String, DPlayer> playerList, String ownerUUID) {

        // Getting player list and owner
        this.playerList = playerList;
        this.ownerUUI   = ownerUUID;

    }




    /**
     * To display player list, and the server owner
     */
    @Override
    public synchronized void updatableAction() {

        // Checking if the user want to leave
        if (leaveDialog.getUserChoice()) {

            // Disconnexion
            this.controller.disconnect();

            
            // Reset the dialog
            leaveDialog = new DDialogBinary(gui, "Confirm ?", DTheme.DLG_DRK);

        }


        // If the player list is not null
        if (playerList != null && !playerList.isEmpty()) {


            // Clearing the center panel
            centerPanel.removeAll();


            // Creating the title pabel
            JPanel titlePanel   = new JPanel    (new GridLayout(1, 2));
            titlePanel          .setBorder      (new EmptyBorder(0, 0, 10, 0));
            titlePanel          .setBackground  (DTheme.GUI_NTL.BCK_N);
            titlePanel          .setMaximumSize (new Dimension(Integer.MAX_VALUE, 50));


            // Creating the title label
            DLabel titleLabel   = new DLabel("Player list", DFont.JOST_SEMIBOLD, 24, DTheme.LAB_TRS);
            titlePanel          .add        (titleLabel);
            titlePanel          .add        (new JLabel(""));
            centerPanel         .add(titlePanel);


            // Traverse player list
            for (String key : playerList.keySet()) {

                // Creating the player panel
                JPanel playerPanel  = new JPanel    (new GridLayout(1, 2));
                playerPanel         .setBorder      (new EmptyBorder(10, 10, 10, 10));
                playerPanel         .setBackground  (DTheme.GUI_NTL.BCK_D);


                // In case of owner uuid
                if (key.equals(ownerUUI)) {

                    // Plotting the player name
                    playerPanel.add(new DLabel(playerList.get(key).getPlayerName(), DFont.JOST_SEMIBOLD,   18, DTheme.LAB_NTL));
                    playerPanel.add(new DLabel("Game owner",   DFont.JOST_LIGHT,      18, DTheme.LAB_TRS));


                } else {

                    // Plotting the player name
                    playerPanel.add(new DLabel(playerList.get(key).getPlayerName(), DFont.JOST_SEMIBOLD,   18, DTheme.LAB_NTL));
                    playerPanel.add(new JLabel(""));

                }

                // Calculing the size of the panem
                playerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));


                // Plotting the player panel
                centerPanel.add(playerPanel);

            }


            // Updating the center panel
            centerPanel.revalidate();
            centerPanel.repaint();

        }

    }




    /**
     * Action after a trigger from any event listener
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        // Actions according to the source
        if (e.getSource() == backButton) {

            // Confirm dialog
            leaveDialog.setModal(false);
            leaveDialog.setVisible(true);
            return;


        } else if (e.getSource() == launchGameButton) {

            // Lauching a new game
            this.controller.launchGame();
            return;

        }


        // Default action
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }

}
