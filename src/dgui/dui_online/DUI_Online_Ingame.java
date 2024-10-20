// Pakcage declaration
package dgui.dui_online;

// Import
import ddialog.DDialogBinary;
import ddialog.DDialogInfo;
import deminer.DController;
import deminer.DSprite;
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
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.Timer;
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
public class DUI_Online_Ingame extends JPanel implements DUI_Updatable {

    /**
     * UI main objects
     */
    private final   DController controller;
    private final   DGUI        gui;
    private final   DUI_Online  uiOnline;
    private         DSprite[][] spriteMesh;
    private         boolean     sizeAdaptation      = false;


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
    private final   DButton     leaveButton         = new DButton("Leave game",     DFont.JOST_SEMIBOLD, 24, DTheme.BTN_RED);


    /**
     * Player list
     */
    private         Map<String, DPlayer> playerList = new HashMap<>();

    
    /**
     * Component listener
     */
    private         ComponentAdapter    centerPanelSizeCheck;


    /**
     * UI Updater
     */
    private final   DUI_Online_Updater  updater;


    /**
     * All dialog
     */
    private         DDialogInfo         playerLost;
    private         DDialogInfo         gameLost;
    private         DDialogInfo         gameResult;
    private         DDialogBinary       leaveDialog;


    /**
     * Index for the start counter
     */
    private         int                 counterIndex;



    
    /**
     * Constructor
     * 
     * @param gui in order to transmit data or action performed
     */
    public DUI_Online_Ingame(DGUI gui, DUI_Online uiOnline, DController controller) {

        // Getting the gui and the controller
        this.controller = controller;
        this.gui        = gui;
        this.uiOnline   = uiOnline;


        // Setting up layout and VFX
        this.setLayout(new BorderLayout());

        // Setting up updater
        updater         = new DUI_Online_Updater(this);


        // Pannel setup
        this.northPanelSetup    ();
        this.southPanelSetup    ();
        this.centerPanelSetup   ();
        this.eastPanelSetup     ();


        // Listener init
        this.listenerInit       ();


        // Setting up the leavDialog
        leaveDialog = new DDialogBinary(gui, "Confirm ?", DTheme.DLG_DRK);

    }



    
    /**
     * Listener initialisation
     */
    private void listenerInit() {

        // Listener that check if if the centerPanel change size
        centerPanelSizeCheck = new ComponentAdapter() {
                    
            // On size change
            @Override
            public void componentResized(ComponentEvent e) {

                System.out.println("dejifiu");

                // If size adaptation enable
                if (sizeAdaptation) {
                    displayMesh();
                }

            }
            
        };

    }




    /**
     * Setting up north panel
    */
    private void northPanelSetup() {

        // Plotting panel and set VFX
        this                .add            (northPanel,BorderLayout.NORTH);
        northPanel          .setLayout      (new FlowLayout());
        northPanel          .setBackground  (DTheme.GUI_VAR.BCK_N);

        
        // Creaing title label
        DLabel titleLabel   = new DLabel    ("Multiplayer game", DFont.JOST_SEMIBOLD, 40, DTheme.LAB_NTL);
        titleLabel          .setAlignmentX  (Component.CENTER_ALIGNMENT);
        northPanel          .add            (titleLabel);

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
        southPanel          .add(leaveButton);


        // Setting up listeners
        leaveButton         .addActionListener(this);

    }




    /**
     * Setting up center panel
     */
    private void centerPanelSetup() {

        // Plotting panel and set VFX
        this        .add                    (centerPanel,   BorderLayout.CENTER);
        centerPanel .removeComponentListener(centerPanelSizeCheck);
        centerPanel .setLayout              (new FlowLayout());
        centerPanel .setBackground          (DTheme.GUI_NTL.BCK_N);


        // Adding the listener to check size changement
        centerPanel .addComponentListener   (centerPanelSizeCheck);

    }




    /**
     * Setting up east panel
     */
    private void eastPanelSetup() {

        // Plotting panel and set VFX
        this                .add            (eastPanel, BorderLayout.EAST);
        eastPanel           .setLayout      (new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
        eastPanel           .setBorder      (new EmptyBorder(30, 30, 30, 30));
        eastPanel           .setBackground  (DTheme.GUI_NTL.BCK_D);
    }




    /**
     * Setter : Enable or disable size adaptation
     * 
     * @param enable
     */
    public void setSizeAdaptation(boolean enable) {
        this.sizeAdaptation = enable;
    }




    /**
     * Setter : to set the new sprite mesh
     * 
     * @param spriteMesh
     */
    public void setSpriteMesh(DSprite[][] spriteMesh) {

        // Getting the mesh
        this.spriteMesh = spriteMesh;


        // Displaying the mesh
        this.displayMesh();

    }




    /**
     * 
     */
    public void gameStartCount() {

        // Deactivate player click capcity
        JPanel glassPane    = new JPanel();
        glassPane           .setOpaque(false);
        glassPane           .addMouseListener(new MouseAdapter() {});
        gui                 .setGlassPane(glassPane);
        glassPane           .setVisible(true);


        // Dialogs
        DDialogInfo[] coutList = new DDialogInfo[5];
        coutList[0] = new DDialogInfo(gui, "The game start in 5sec", new String[]{"Are you ready ?"}, DTheme.DLG_DRK, false);
        coutList[1] = new DDialogInfo(gui, "The game start in 4sec", new String[]{"Tip : Be ready"}, DTheme.DLG_DRK, false);
        coutList[2] = new DDialogInfo(gui, "The game start in 3sec", new String[]{"Tip : Do not explose yourself"}, DTheme.DLG_DRK, false);
        coutList[3] = new DDialogInfo(gui, "The game start in 2sec", new String[]{"Tip : Do not read these tip"}, DTheme.DLG_DRK, false);
        coutList[4] = new DDialogInfo(gui, "The game start in 1sec", new String[]{"Tip : Have a life"}, DTheme.DLG_DRK, false);


        // 
        this.counterIndex = 0;
        Timer timer = new Timer(1000, (ActionEvent e) -> {
            
            // Close the previous dialog
            if (this.counterIndex > 0) {
                coutList[this.counterIndex - 1].setVisible(false);
            }


            // Display the current dialog
            if (this.counterIndex < 5) {
                coutList[this.counterIndex].setVisible(true);
                this.counterIndex++;
            } else {
                glassPane.setVisible(false);
                ((Timer) e.getSource()).stop();
            }


        });


        // Start timer
        timer.start();

    }




    /**
     * Calcul the sprite size according to the size of the centerPanel
     * 
     * @return calcSize, the calculed size
     */
    private int spriteSizeCalcul() {

        // Calculed size
        int calcSize;


        // Getting the right coefficient
        /**Explanation :
         * 
         * This calcul is not too crazy when you know what's going on here
         * 
         * Each sprite is plotted in a JPanel. I do not know why, but these JPanel
         * create a border. These border have their thickness fixed and non relative
         * to the sprite.
         * 
         * So i must consider it in my calcul of height and width available.
         * 
         * I found out that 11px might the best controllerroximation for these border
         */
        int heightCalcul    = (int) ((centerPanel.getHeight()   - spriteMesh    .length * 11 )      / spriteMesh      .length);
        int widthCalcul     = (int) ((centerPanel.getWidth()    - spriteMesh[0] .length * 11 )      / spriteMesh[0]   .length);


        // Saving the smaller one
        if (heightCalcul < widthCalcul) {
            calcSize = heightCalcul;
        } else {
            calcSize = widthCalcul;
        }


        // Min and max condition
        if (calcSize < 20) {
            calcSize = 20;
        }


        // Returning the size
        return calcSize;

    }




    /**
     * Displaying the mine field using the sprite class
     */
    public void displayMesh() {

        // In cas of a null mesh
        if (spriteMesh != null) {
       
            // Removing everything from the center panel and reset the score
            centerPanel.removeAll();


            // Creating the grid to display mines and coefficient
            JPanel minesPanel   = new JPanel    ();
            minesPanel          .setLayout      (new GridLayout(spriteMesh.length, spriteMesh[0].length));
            minesPanel          .setBackground  (DTheme.GUI_NTL.BCK_N);


            // Getting the size of the new sprite
            int sqSize = this.spriteSizeCalcul();


            // Filling up the grid
            for (DSprite[] spriteMeshLine : spriteMesh) {
                for (DSprite sprite : spriteMeshLine) {

                    // Setting up sprite size
                    sprite.setSpriteSize(sqSize);


                    // Plotting the sprite in a JPanel to get border
                    JPanel spriteHolder = new JPanel();
                    spriteHolder        .setBackground(DTheme.GUI_NTL.BCK_N);
                    spriteHolder        .add(sprite);
                    minesPanel          .add(spriteHolder);

                }

            }


            // Rafraîchir l'affichage
            centerPanel.add(minesPanel);
            centerPanel.revalidate();
            centerPanel.repaint();

        }
        
    }




    /**
     * Update the player list
     * 
     * @param playerList
     */
    public void updatePlayerList(Map<String, DPlayer> playerList) {
        this.playerList = playerList;
    }




    /**
     * Display the current
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


        // Usually, the list is not empty BUT
        if (playerList != null && !playerList.isEmpty()) {

            // Clearing the side panel
            this.eastPanel.removeAll();


            // Creating the player alive title panel
            JPanel playerAliveTitlePanel    = new JPanel    (new GridLayout(1, 1));
            playerAliveTitlePanel           .setBorder      (new EmptyBorder(0, 0, 0, 0));
            playerAliveTitlePanel           .setBackground  (DTheme.GUI_NTL.BCK_D);
            playerAliveTitlePanel           .setMaximumSize (new Dimension(Integer.MAX_VALUE, 50));


            // Creating the player alive title
            DLabel playerAliveTitle         = new DLabel    ("Player alive", DFont.JOST_SEMIBOLD, 24, DTheme.LAB_TRS);
            playerAliveTitlePanel           .add            (playerAliveTitle);
            eastPanel                       .add            (playerAliveTitlePanel);


            // Creating the player alive list panel
            JPanel playerAliveListPanel     = new JPanel    ();
            playerAliveListPanel            .setLayout      (new BoxLayout(playerAliveListPanel, BoxLayout.Y_AXIS));
            playerAliveListPanel            .setBorder      (new EmptyBorder(10, 0, 10, 0));
            playerAliveListPanel            .setBackground  (DTheme.GUI_NTL.BCK_D);
            eastPanel                       .add            (playerAliveListPanel);


            // Creating the looser list panel
            JPanel looserListPanel          = new JPanel    ();
            looserListPanel                 .setLayout      (new BoxLayout(looserListPanel, BoxLayout.Y_AXIS));
            looserListPanel                 .setBorder      (new EmptyBorder(10, 0, 10, 0));
            looserListPanel                 .setBackground  (DTheme.GUI_NTL.BCK_D);


            // Running through the player list
            int nbLooser = 0;
            for (DPlayer player : playerList.values()) {

                // Creating the player panel
                JPanel playerPanel  = new JPanel    (new GridLayout(1, 2));
                playerPanel         .setBorder      (new EmptyBorder(10, 10, 10, 10));
                playerPanel         .setBackground  (DTheme.GUI_NTL.BCK_D);


                // Plotting the player name
                playerPanel.add(new DLabel(player.getPlayerName(),              DFont.JOST_SEMIBOLD,   18, DTheme.LAB_NTL));
                playerPanel.add(new DLabel(String.valueOf(player.getScore()),   DFont.JOST_LIGHT,      18, DTheme.LAB_TRS));


                // Calculing the size of the panem
                playerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));


                // Plotting the player panel according to the player state (ingame or looser)
                if (player.isAlive()) {

                    // Plotting panel
                    playerAliveListPanel.add(playerPanel);


                } else {

                    // Plotting title only if not already plotted
                    if (nbLooser == 0) {

                        // Creating the player exploded panel
                        JPanel lostPanel    = new JPanel    (new GridLayout(1, 1));
                        lostPanel           .setBorder      (new EmptyBorder(15, 0, 10, 0));
                        lostPanel           .setBackground  (DTheme.GUI_NTL.BCK_D);
                        lostPanel           .setMaximumSize (new Dimension(Integer.MAX_VALUE, 50));


                        // Creating the player exploded label
                        DLabel lostLabel    = new DLabel    ("Player exploded", DFont.JOST_SEMIBOLD, 24, DTheme.LAB_TRS);
                        lostPanel           .add            (lostLabel);
                        eastPanel           .add            (lostPanel);


                        // Plotting the looser panel
                        eastPanel           .add            (looserListPanel);

                    }


                    // Plotting panel
                    looserListPanel.add(playerPanel);


                    // Incrementing
                    nbLooser ++;

                }

            }


            // Updating the center panel
            eastPanel.revalidate();
            eastPanel.repaint();

        }

    }




    /**
     * In case of game lost
     */
    public void playerLost() {

        // Displaying the info
        playerLost = new DDialogInfo(   
                                        gui,
                                        "You have exploded...", 
                                        new String[]{
                                                        "You cannot play anymore.",
                                                    },
                                        DTheme.DLG_DRK,
                                        false
                                    );
        playerLost.setVisible(true);


        // 5sec Timer
        Timer timer = new Timer(5000, (ActionEvent e) -> {

            // Hidding the popup
            playerLost.setVisible(false);

        });
        timer.setRepeats(false);
        timer.start();

    }




    /**
     * In case of game lost
     */
    public void gameLost() {

        // Displaying the info
        gameLost = new DDialogInfo  (   
                                        gui,
                                        "Everyone have exploded...", 
                                        new String[]{
                                                        "The game is lost",
                                                    },
                                        DTheme.DLG_DRK,
                                        false
                                    );
        gameLost.setVisible(true);


        // 5sec Timer
        Timer timer = new Timer(5000, (ActionEvent e) -> {

            // Hidding the popup
            gameLost.setVisible(false);


            // Game end phase
            this.gameEnd();

        });
        timer.setRepeats(false);
        timer.start();

    }




    /**
     * End of a game phase
     */
    public void gameEnd() {

        // Building the results
        String[]    result  = new String[playerList.size() + 2];
        int         i       = 0;
        DPlayer     winner  = null;
        for (DPlayer player : playerList.values()) {
            if (player != null) {

                // Adding the player and its score the the result
                result[i] = player.getPlayerName() + " : " + player.getScore();


                // Getting the higher score
                if (winner == null || player.getScore() > winner.getScore()) {
                    winner = player;
                }


                // Incrementing
                i++;

            }

        }
        

        // Adding the winner
        result[i] = " ";
        if (winner != null) {
            result[i + 1] = "Winner : " + winner.getPlayerName();
        } else {
            result[i + 1] = "There are no winner";
        }
        

        // Displaying the less worst player
        gameResult = new DDialogInfo   (   
                                            gui,
                                            "Game results", 
                                            result,
                                            DTheme.DLG_DRK,
                                            false
                                        );
        gameResult.setVisible(true);


        // 5sec Timer
        Timer timer = new Timer(5000, (ActionEvent e) -> {

            // Hidding the popup
            gameResult.setVisible(false);


            // Getting back to the wait screen
            this.uiOnline.switchSubUIWait();

        });
        timer.setRepeats(false);
        timer.start();

    }




    /**
     * Action after a trigger from any event listener
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        // Actions according to the source
        if (e.getSource() == leaveButton) {

            // Confirm dialog
            leaveDialog.setModal(false);
            leaveDialog.setVisible(true);
            return;


        }

        // Default action
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }

}
