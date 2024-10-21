// Pakcage declaration
package dgui.dui_online;

// Import
import ddialog.DDialogBinary;
import ddialog.DDialogInfo;
import deminer.DController;
import dgraphics.DButton;
import dgraphics.DFont;
import dgraphics.DIntegerField;
import dgraphics.DLabel;
import dgraphics.DStringField;
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
import javax.swing.Box;
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


    /**
     * East panels elements
     */
    private final   DButton     launchGameButton    = new DButton("Lauch the game", DFont.JOST_SEMIBOLD,    24, DTheme.BTN_GRN);
    private final   DIntegerField    fdLength       = new DIntegerField(DFont.JOST_LIGHT, 14, DTheme.FLD_NTL);
    private final   DIntegerField    fdHeigth       = new DIntegerField(DFont.JOST_LIGHT, 14, DTheme.FLD_NTL);
    private final   DIntegerField    fdNbMines      = new DIntegerField(DFont.JOST_LIGHT, 14, DTheme.FLD_NTL);
    private final   DIntegerField    nbMaxPlayer    = new DIntegerField(DFont.JOST_LIGHT, 14, DTheme.FLD_NTL);
    private final   DButton     applyParamButton    = new DButton("Apply parameters", DFont.JOST_SEMIBOLD,    24, DTheme.BTN_GRN);


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

        // Clearing all
        eastPanel.removeAll();


        // Only if the client is the server owner
        if (this.serverOwner) {

            // Plotting panel and set VFX
            this                .add            (eastPanel, BorderLayout.EAST);
            eastPanel           .setLayout      (new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
            eastPanel           .setBorder      (new EmptyBorder(30, 30, 30, 30));
            eastPanel           .setBackground  (DTheme.GUI_NTL.BCK_D);


            // Plotting the launch game button
            JPanel lauchPanel   = new JPanel    (new FlowLayout());
            lauchPanel          .setBackground  (DTheme.TRSPCOL);
            lauchPanel          .add            (launchGameButton);
            eastPanel           .add            (lauchPanel);


            // Creating the parameters panel
            JPanel paramPanel   = new JPanel    ();
            paramPanel          .setLayout      (new BoxLayout(paramPanel, BoxLayout.Y_AXIS));
            paramPanel          .setBackground  (DTheme.GUI_DRK.BCK_D);
            paramPanel          .setBorder      (new EmptyBorder(10, 0, 10, 0));
            eastPanel           .add            (paramPanel);


            // Adding parameters input : Length
            JPanel lengthPanel  = new JPanel    (new GridLayout(2, 1));
            lengthPanel         .setBackground  (DTheme.TRSPCOL);
            DLabel fdLength_    = new DLabel    ("Field Length",    DFont.JOST_SEMIBOLD, 16, DTheme.LAB_TRS);
            lengthPanel         .add            (fdLength_);
            lengthPanel         .add            (fdLength);
            eastPanel           .add            (lengthPanel);
            eastPanel           .add            (Box.createRigidArea(new Dimension(10, 15)));


            // Adding parameters input : Heigth
            JPanel heigthPanel  = new JPanel    (new GridLayout(2, 1));
            heigthPanel         .setBackground  (DTheme.TRSPCOL);
            DLabel fdHeigth_    = new DLabel    ("Field Heigth",    DFont.JOST_SEMIBOLD, 16, DTheme.LAB_TRS);
            heigthPanel         .add            (fdHeigth_);
            heigthPanel         .add            (fdHeigth);
            eastPanel           .add            (heigthPanel);
            eastPanel           .add            (Box.createRigidArea(new Dimension(10, 15)));


            // Adding parameters input : Number of Mines
            JPanel minePanel    = new JPanel    (new GridLayout(2, 1));
            minePanel           .setBackground  (DTheme.TRSPCOL);
            DLabel fdNbMines_   = new DLabel    ("Number of mines", DFont.JOST_SEMIBOLD, 16, DTheme.LAB_TRS);
            minePanel           .add            (fdNbMines_);
            minePanel           .add            (fdNbMines);
            eastPanel           .add            (minePanel);
            eastPanel           .add            (Box.createRigidArea(new Dimension(10, 15)));


            // Adding parameters input : Max player
            JPanel maxPlayerPanel = new JPanel    (new GridLayout(2, 1));
            maxPlayerPanel      .setBackground  (DTheme.TRSPCOL);
            DLabel nbMaxPlayer_ = new DLabel    ("Player limit",      DFont.JOST_SEMIBOLD, 16, DTheme.LAB_TRS);
            maxPlayerPanel      .add            (nbMaxPlayer_);
            maxPlayerPanel      .add            (nbMaxPlayer);
            eastPanel           .add            (maxPlayerPanel);
            eastPanel           .add            (Box.createRigidArea(new Dimension(10, 15)));


            // Adding the submit parameters button
            JPanel applyPanel   = new JPanel    (new FlowLayout());
            applyPanel          .setBackground  (DTheme.TRSPCOL);
            applyPanel          .add            (applyParamButton);
            eastPanel           .add            (applyPanel);


            // Adding listeners
            launchGameButton    .addActionListener(this);
            applyParamButton    .addActionListener(this);

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
     * Setter : to set the default value to the length parameter
     * 
     * @param length
     */
    public void setLength(int length) {
        this.fdLength.setIntegerValue(length);
    }




    /**
     * Setter : to set the default value to the heigth parameter
     * 
     * @param heigth
     */
    public void setHeigth(int heigth) {
        this.fdHeigth.setIntegerValue(heigth);
    }




    /**
     * Setter : to set the default value to the number of mines
     * 
     * @param nbMine
     */
    public void setNbMine(int nbMine) {
        this.fdNbMines.setIntegerValue(nbMine);
    }




    /**
     * Setter : to set the default value to the player limit
     * 
     * @param nbMaxPlayer
     */
    public void setNMaxPlayer(int nbMaxPlayer) {
        this.nbMaxPlayer.setIntegerValue(nbMaxPlayer);
    }




    /**
     * Display a popup when the game lauch is refused
     */
    public void gameLauchRefused() {

        // Display info
        DDialogInfo info = new DDialogInfo(gui, "Unable to launch the game", new String[]{"There are not enough player..."}, DTheme.DLG_DRK);
        info.setModal(false);
        info.setVisible(true);

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


        } else if (e.getSource() == applyParamButton) {

            // Verify parameter
            if (fdLength.getIntegerValue() < 5 || fdLength.getIntegerValue() > 20) {
            
                // Invalid parameters
                DDialogInfo info = new DDialogInfo(gui, "Invalid field length", new String[]{"Length must be between", "5 and 20"}, DTheme.DLG_DRK);
                info.setModal(false);
                info.setVisible(true);
                return;
    

            } else if (fdHeigth.getIntegerValue() < 5 || fdHeigth.getIntegerValue() > 20) {
    
                // Invalid parameters
                DDialogInfo info = new DDialogInfo(gui, "Invalid field height", new String[]{"Height must be between", "5 and 20"}, DTheme.DLG_DRK);
                info.setModal(false);
                info.setVisible(true);
                return;
    

            } else if (fdNbMines.getIntegerValue() < 1 || fdNbMines.getIntegerValue() > ((fdHeigth.getIntegerValue() * fdHeigth.getIntegerValue()) / 4)) {
    
                // Invalid parameters
                DDialogInfo info = new DDialogInfo(gui, "Invalid number of mine", new String[]{"Number of mine must be between", "1 and 1/4 of (height * length)"}, DTheme.DLG_DRK);
                info.setModal(false);
                info.setVisible(true);
                return;
                
    
            } else if (nbMaxPlayer.getIntegerValue() < 2 || nbMaxPlayer.getIntegerValue() > 10) {

                // Invalid parameters
                DDialogInfo info = new DDialogInfo(gui, "Invalid player limit", new String[]{"Player limit must be between", "1 and 10"}, DTheme.DLG_DRK);
                info.setModal(false);
                info.setVisible(true);
                return;

            }


            // Valid parameters
            DDialogInfo info = new DDialogInfo(gui, "Parameters applyied", new String[]{"Parameters has been", "successfully applied"}, DTheme.DLG_DRK);
            info.setModal(false);
            info.setVisible(true);
            this.controller.applyLength     (fdLength.getIntegerValue());
            this.controller.applyHeigth     (fdHeigth.getIntegerValue());
            this.controller.applyNbMine     (fdNbMines.getIntegerValue());
            this.controller.applyNMaxPlayer (nbMaxPlayer.getIntegerValue());
            return;

        }


        // Default action
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }

}
