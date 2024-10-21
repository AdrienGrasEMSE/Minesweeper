// Pakcage declaration
package dgui.dui_online;

// Import
import ddialog.DDialogInfo;
import ddialog.DDialogLoad;
import ddialog.DDialogStringInput;
import deminer.DController;
import dgraphics.DButton;
import dgraphics.DFont;
import dgraphics.DLabel;
import dgraphics.dtheme.DTheme;
import dgui.DGUI;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
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
public class DUI_Online_Default extends JPanel implements ActionListener {

    /**
     * UI main objects
     */
    private final   DController controller;
    private final   DGUI        gui;
    private final   DUI_Online  uiOnline;


    /**
     * UI main panels
     */
    private final   JPanel      northPanel          = new JPanel();
    private final   JPanel      southPanel          = new JPanel();
    private final   JPanel      centerPanel         = new JPanel();


    /**
     * South panels elements
     */
    private final   DButton     backButton          = new DButton("Back",           DFont.JOST_SEMIBOLD, 24, DTheme.BTN_RED);
    private final   DButton     createGameButton    = new DButton("Create a game",  DFont.JOST_SEMIBOLD, 24, DTheme.BTN_GRN);
    private final   DButton     joinGameButton      = new DButton("Join a game",    DFont.JOST_SEMIBOLD, 24, DTheme.BTN_GRN);


    /**
     * Load dialog
     */
    private final   DDialogLoad serverCreation;
    private final   DDialogLoad serverJoin;



    /**
     * Constructor
     * 
     * @param gui in order to transmit data or action performed
     */
    public DUI_Online_Default(DGUI gui, DUI_Online uiOnline, DController controller) {

        // Getting the gui and the controller
        this.controller = controller;
        this.gui        = gui;
        this.uiOnline   = uiOnline;


        // Setting up layout and VFX
        this.setLayout(new BorderLayout());


        // Pannel setup
        this.northPanelSetup    ();
        this.southPanelSetup    ();
        this.centerPanelSetup   ();


        // Load dialog setup
        serverCreation  = new DDialogLoad(gui, "Creating server", DTheme.DLG_DRK);
        serverJoin      = new DDialogLoad(gui, "Joinning server", DTheme.DLG_DRK);

    }




    /**
     * Setting up north panel
    */
    private void northPanelSetup() {

        // Plotting panel and set VFX
        this                .add(northPanel,BorderLayout.NORTH);
        northPanel          .setLayout      (new FlowLayout());
        northPanel          .setBackground  (DTheme.GUI_VAR.BCK_N);

        
        // Creaing title label
        DLabel titleLabel   = new DLabel("Multiplayer", DFont.JOST_SEMIBOLD, 40, DTheme.LAB_NTL);
        titleLabel          .setAlignmentX(Component.CENTER_ALIGNMENT);
        northPanel          .add(titleLabel);

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
        this                    .add            (centerPanel,   BorderLayout.CENTER);
        centerPanel             .setLayout      (new GridBagLayout());
        centerPanel             .setBackground  (DTheme.GUI_NTL.BCK_N);


        // Constraint creation
        GridBagConstraints gbc  = new GridBagConstraints();
        gbc.gridx               = 0;
        gbc.gridy               = 0;
        gbc.weightx             = 1.0;
        gbc.weighty             = 1.0;
        gbc.anchor              = GridBagConstraints.CENTER;


        // Centered panel
        JPanel centeredPanel    = new JPanel();
        centeredPanel           .setLayout      (new BoxLayout(centeredPanel, BoxLayout.Y_AXIS));
        centeredPanel           .setBorder      (new EmptyBorder(30, 30, 30, 30));
        centeredPanel           .setBackground  (DTheme.GUI_NTL.BCK_D);


        // Game creation panel
        JPanel createPanel      = new JPanel    (new FlowLayout());
        createPanel             .setBackground  (DTheme.GUI_NTL.BCK_D);
        createPanel             .add            (createGameButton);


        // Game join panel
        JPanel joinPanel        = new JPanel    (new FlowLayout());
        joinPanel               .setBackground  (DTheme.GUI_NTL.BCK_D);
        joinPanel               .add            (joinGameButton);


        // Plotting panels
        centeredPanel           .add(createPanel);
        centeredPanel           .add(Box.createRigidArea(new Dimension(10, 30)));
        centeredPanel           .add(joinPanel);


        // Plotting the centered panel
        centerPanel             .add(centeredPanel, gbc);


        // Adding button listener
        createGameButton        .addActionListener(this);
        joinGameButton          .addActionListener(this);

    }




    /**
     * Create a new online game
     */
    private void gameCreation() {

        // Dialog to get custom parameters
        DDialogStringInput param = new DDialogStringInput(gui, "Enter your pseudo", "Pseudo :");
        do {

            // Display dialog
            param.setVisible(true);


            // Infos
            if (!param.getParamValid() && param.getUserConfirm()) {
                DDialogInfo info = new DDialogInfo( gui,
                                                    "Invalid parameters",
                                                    new String[]{ "The pseudo must be not",
                                                    "empty..."
                                                    },
                                                    DTheme.DLG_DRK);
                info.setVisible(true);
            }


        }while (!param.getParamValid() && param.getUserConfirm());


        // Cancel operation
        if (!param.getUserConfirm()) {
            return;
        }


        // Server creation
        controller.newServer(param.getInputString());


        // Loading : waiting for the server to be online
        serverCreation.setVisible(true);

    }




    /**
     * Make an action on the server creation
     * 
     * @param succeed indicate if the server creation is succesful
     */
    public void gameCreated(boolean succeed, String failInfo) {

        // Accoding to the sucess status
        if (succeed) {

            // Display the wait screen
            this.uiOnline.switchSubUIWait();


        } else {

            // Infos
            DDialogInfo info = new DDialogInfo( gui,
                                                    "Server creation aborted",
                                                    new String[]{failInfo},
                                                    DTheme.DLG_DRK);
            info.setVisible(true);

        }


        /**
         * In today's episod of wtf is going on there :
         * 
         * I think that the serverJoin dialog is displayed after
         * the fail or succes to connect.
         * 
         * WHY IS THAT ? I DON'T FUCKING KNOW
         * 
         * 
         * That's why the dialog close is at the end of this...
         */
        
        // Close the dialog and connexion
        SwingUtilities.invokeLater(() -> {
            serverCreation.dispose();
        });

    }




    /**
     * Joinning game phase
     */
    private void joinGame() {

        // Dialog to get custom parameters
        DDialogStringInput pseudo = new DDialogStringInput(gui, "Enter your pseudo", "Pseudo :");
        do {

            // Display dialog
            pseudo.setVisible(true);


            // Infos
            if (!pseudo.getParamValid() && pseudo.getUserConfirm()) {
                DDialogInfo info = new DDialogInfo( gui,
                                                    "Invalid parameters",
                                                    new String[]{ "The pseudo must be not",
                                                    "empty..."
                                                    },
                                                    DTheme.DLG_DRK);
                info.setVisible(true);
            }


        }while (!pseudo.getParamValid() && pseudo.getUserConfirm());


        // Cancel operation
        if (!pseudo.getUserConfirm()) {
            return;
        }


        // Join game
        controller.joinGame(pseudo.getInputString());


        // Loading : waiting for the client to be connected
        serverJoin.setVisible(true);

    }




    /**
     * Game joinned phase
     * 
     * @param succeed
     * @param failInfo
     */
    public void gameJoinned(boolean succeed, String failInfo) {

        // Accoding to the sucess status
        if (succeed) {

            // Display the wait screen
            this.uiOnline.switchSubUIWait();


        } else {

            // Infos
            DDialogInfo info = new DDialogInfo( gui,
                                                    "Connexion to the server aborted",
                                                    new String[]{failInfo},
                                                    DTheme.DLG_DRK);
            info.setVisible(true);

        }


        /**
         * In today's episod of wtf is going on there :
         * 
         * I think that the serverJoin dialog is displayed after
         * the fail or succes to connect.
         * 
         * WHY IS THAT ? I DON'T FUCKING KNOW
         * 
         * 
         * That's why the dialog close is at the end of this...
         */
        
        // Close the dialog and connexion
        SwingUtilities.invokeLater(() -> {
            serverJoin.dispose();
        });

    }




    /**
     * Game disconnexion info (when there is a reason)
     * 
     * @param reason
     */
    public void gameDisconnexion(String reason) {

        // Infos
        DDialogInfo info = new DDialogInfo( gui, "Disconnected from the server", new String[]{reason}, DTheme.DLG_DRK);
        info.setVisible(true);
        
    }




    /**
     * Action after a trigger from any event listener
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        // Actions according to the source
        if (e.getSource() == backButton) {

            // Getting back to the previous screen
            gui.switchUIPrevious();
            return;


        } else if (e.getSource() == createGameButton) {

            // Lauching a new game
            gameCreation();
            return;

        } else if (e.getSource() == joinGameButton) {

            // Lauching a new game
            joinGame();
            return;

        }

        // Default action
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }

}
