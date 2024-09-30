// Import
import deminer_dialog.DeminerDialogBinary;
import deminer_dialog.DeminerDialogCustomNewGame;
import deminer_dialog.DeminerDialogEndGame;
import deminer_dialog.DeminerDialogInfo;
import deminer_graphic.DeminerButton;
import deminer_graphic.DTheme;
import deminer_graphic.DeminerFont;
import deminer_graphic.DeminerLabel;
import deminer_graphic.combo_box.DeminerComboBox;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * GUI : Graphical User Interface
 * 
 * @author  AdrienG
 * @version 0.0
 */
public class GUI extends JPanel implements ActionListener{

    /**
     * GUI main objects
     */ 
    private final   App         app;
    private         Square[][]  squareMesh;
    private         int         previousLevelIndex;

    
    /**
     * GUI main panels
     */
    private final JPanel northPanel   = new JPanel(new FlowLayout());
    private final JPanel eastPanel    = new JPanel();
    private final JPanel southPanel   = new JPanel(new FlowLayout());
    private final JPanel westPanel    = new JPanel();
    private final JPanel centerPanel  = new JPanel();


    /**
     * 
     */
    private boolean manualLevelChange           = false;
    private boolean sizeAdaptation              = false;




    /**
     * North panels elements
     */
    private final DeminerLabel labScore         = new DeminerLabel("Score", DeminerFont.JOST_LIGHT,     24, DTheme.FNT_NTL_D);
    private final DeminerLabel labLevel         = new DeminerLabel("Level", DeminerFont.JOST_REGULAR,   24, DTheme.FNT_NTL_N);
    private final DeminerLabel valScore         = new DeminerLabel("",      DeminerFont.JOST_REGULAR,   24, DTheme.FNT_NTL_N);
    private final DeminerComboBox<Level> valLevel     = new DeminerComboBox<>(  Level.values(),
                                                                                24,
                                                                                DTheme.FNT_NTL_D,
                                                                                DTheme.FNT_NTL_N,
                                                                                DTheme.BTN_VAR_D,
                                                                                DTheme.BTN_VAR_N,
                                                                                DTheme.BTN_VAR_L);




    /**
     * South panels elements
     */
    private final DeminerButton quitButton      = new DeminerButton("Quit",     DeminerFont.JOST_SEMIBOLD, 24, Color.WHITE, DTheme.BTN_RED_D, DTheme.BTN_RED_N, DTheme.BTN_RED_L);
    private final DeminerButton newGameButton   = new DeminerButton("New game", DeminerFont.JOST_SEMIBOLD, 24, Color.WHITE, DTheme.BTN_GRN_D, DTheme.BTN_GRN_D, DTheme.BTN_GRN_D);




    /**
     * GUI
     */
    public GUI(App app) {

        // Getting application
        this.app    = app;


        // Setting up layout and VFX
        setLayout(new BorderLayout());
        setBackground(Color.black);


        // Setting up panels
        panelSetup();
        northPanelSetup();
        southPanelSetup();
        centerPanelSetup();

    }



    /**
     * Setting up main panels : north, east, south, west and center
     */
    private void panelSetup() {

        // Plotting panels
        add(eastPanel,      BorderLayout.EAST);
        add(westPanel,      BorderLayout.WEST);

        // Panels vfx
        
        eastPanel           .setBackground  (DTheme.GUI_NTL_D);
        westPanel           .setBackground  (DTheme.GUI_NTL_D);

        // TEST
        JLabel east     = new JLabel("EAST");
        JLabel west     = new JLabel("WEST");
        eastPanel       .add(east);
        westPanel       .add(west);

    }



    
    /**
     * Setting up north panel
    */
    private void northPanelSetup() {

        // Plotting panel and set VFX
        add(northPanel,     BorderLayout.NORTH);
        northPanel          .setBackground(DTheme.GUI_VAR_N);


        // Plotting elements inside
        northPanel          .add(labScore);
        northPanel          .add(valScore);
        northPanel          .add(labLevel);
        northPanel          .add(valLevel);


        // Elements VFX
        labScore            .setForeground(Color.white);
        valScore            .setForeground(Color.white);
        labLevel            .setForeground(Color.white);
        valLevel            .setSelectedItem(app.getLevel());
        valLevel            .addItemListener((ItemEvent e) -> {

            // Making an action only on item selection
            if (e.getStateChange()          == ItemEvent.SELECTED   &&
                valLevel.getSelectedIndex() != -1                   &&
                valLevel.getSelectedIndex() != previousLevelIndex   &&
                !manualLevelChange) {

                // Changing game level
                levelChange(false);

            }
        });

    }




    /**
     * Setting up south panel
    */
    private void southPanelSetup() {

        // Plotting panel and set VFX
        add(southPanel,     BorderLayout.SOUTH);
        southPanel          .setBackground  (DTheme.GUI_DRK_N);


        // Plotting elements inside
        southPanel          .add(quitButton);
        southPanel          .add(newGameButton);


        // Setting up listeners
        quitButton          .addActionListener(this);
        newGameButton       .addActionListener(this);

    }




    /**
     * Setting up center panel
     */
    private void centerPanelSetup() {

        // Plotting panel and set VFX
        this.add(centerPanel,   BorderLayout.CENTER);
        centerPanel             .setBackground(DTheme.GUI_NTL_N);


        // Adding the listener to check size changement
        centerPanel.addComponentListener(new ComponentAdapter() {
            
            // On size change
            @Override
            public void componentResized(ComponentEvent e) {

                // If size adaptating is enable
                if (sizeAdaptation) {
                    displayMesh();
                }
                
            }
            
        });

    }




    /**
     * Setter : to enable or disable size adaptation
     * 
     * @param enable
     */
    public void setSizeAdaptation(boolean enable) {
        sizeAdaptation = enable;
    }




    /**
     * Update the displayed level according to the app one
     */
    public void updateLevel() {

        // Disable ItemListener
        manualLevelChange   = true;
        valLevel            .setSelectedItem(app.getLevel());
        manualLevelChange   = false;

    }




    /**
     * Action performed on level change
     */
    private void levelChange(boolean lightDisplay) {

        // Changing game level
        previousLevelIndex = valLevel.getSelectedIndex();
        switch (valLevel.getSelectedIndex()) {
            case 0 -> app.setLevel(Level.EASY);
            case 1 -> app.setLevel(Level.MEDIUM);
            case 2 -> app.setLevel(Level.HARD);
            case 3 -> app.setLevel(Level.CUSTOM);
                
        }


        // If light display off
        if (!lightDisplay) {

            // Closing the popup
            valLevel.setPopupVisible(false);


            // Dialog to start a new game
            DeminerDialogBinary newGame = new DeminerDialogBinary(app, "Do you want to start a new game ?");
            newGame.setVisible(true);


            // Getting the answer
            boolean userChoice = newGame.getUserChoice();
            if (userChoice && app.getLevel() != Level.CUSTOM) {
                
                // New game 
                this.newClassicGame(true);


            } else if (userChoice && app.getLevel() == Level.CUSTOM) {

                // New game 
                this.newCustomGame(true);

            }

        }

    }




    /**
     * Action performed when the user want a new classic game
     */
    private void newClassicGame(boolean lightDisplay) {

        // If light display on
        if (lightDisplay) {

            // New classic game
            app.newClassicGame();


        } else {

            // Dialog to endGame
            DeminerDialogBinary confirm = new DeminerDialogBinary(app, "Confirm ?");
            confirm.setVisible(true);


            // Getting the answer
            boolean userChoice = confirm.getUserChoice();
            if (userChoice) {
                app.newClassicGame();
            }

        }

    }




    /**
     * Action performed when the user want a new custom game
     */
    private void newCustomGame(boolean lightDisplay) {

        // Dialog to get custom parameters
        DeminerDialogCustomNewGame param = new DeminerDialogCustomNewGame(app);
        do {

            // Display dialog
            param.setVisible(true);


            // Infos
            if (!param.getParamValid() && param.getUserConfirm()) {
                DeminerDialogInfo info = new DeminerDialogInfo(app, "Invalid parameters", new String[]{ "- Width and Height must be between",
                                                                                                                "5 and 20",
                                                                                                                "",
                                                                                                                "- Number of mines must be between",
                                                                                                                "1 and 75% of (Width * Height)"
                                                                                                            });
                info.setVisible(true);
            }


        }while (!param.getParamValid() && param.getUserConfirm());


        // Confirm
        if (param.getUserConfirm()) {
            
            // If light display on
            if (lightDisplay) {

                // New custom game
                app.newCustomGame(param.getCustomHeight(), param.getCustomWidth(), param.getCustomNbMines());


            } else {

                // Dialog to endGame
                DeminerDialogBinary confirm = new DeminerDialogBinary(app, "Confirm ?");
                confirm.setVisible(true);


                // Getting the answer
                boolean userChoice = confirm.getUserChoice();
                if (userChoice) {

                    // New custom game
                    app.newCustomGame(param.getCustomHeight(), param.getCustomWidth(), param.getCustomNbMines());
                }

            }

        }

    }




    public void endGamePhase(boolean gameWon) {

        // Dialog
        DeminerDialogEndGame endGame = new DeminerDialogEndGame(app, gameWon);
        endGame.setVisible(true);


        // Getting the answer
        boolean userChoice = endGame.getUserChoice();
        if (userChoice) {

            // New classic or custom game
            if (app.getLevel() == Level.CUSTOM) {

                // New custom game phase
                newCustomGame(true);


            } else {

                // New classic game phase
                newClassicGame(true);

            }

        } else {

            // Quit app
            app.quit();

        }
        
    }



    /**
     * Setter : to get from the app the square mesh
     * 
     * @param squareMesh
     */
    public void setSquareMesh(Square[][] squareMesh) {
        this.squareMesh = squareMesh;
    }




    /**
     * Displaying the mine field using the square class
     */
    public void displayMesh() {
        
        // Removing everything from the center panel and reset the score
        centerPanel.removeAll();
        valScore.setText("0");


        // Creating the grid to display mines and coefficient
        JPanel minesPanel   = new JPanel();
        minesPanel          .setLayout(new GridLayout(squareMesh.length, squareMesh[0].length));
        minesPanel          .setBackground(DTheme.GUI_NTL_N);


        // Getting the size of the new square
        int sqSize = this.squareSizeCalcul();


        // Filling up the grid
        for (Square[] squareMeshLine : squareMesh) {
            for (Square square : squareMeshLine) {

                // Setting up square size
                square.setSquareSize(sqSize);


                // Plotting the square in a JPanel to get border
                JPanel squareHolder = new JPanel();
                squareHolder        .setBackground(DTheme.GUI_NTL_N);
                squareHolder        .add(square);
                minesPanel          .add(squareHolder);

            }

        }


        // Rafraîchir l'affichage
        centerPanel.add(minesPanel);
        centerPanel.revalidate();
        centerPanel.repaint();
        
    }




    /**
     * 
     * 
     * @return
     */
    private int squareSizeCalcul() {

        // Calculed size
        int calcSize;


        // Getting the right coefficient
        /**Explanation :
         * 
         * This calcul is not too crazy when you know what's going on here
         * 
         * Each square is plotted in a JPanel. I do not know why, but these JPanel
         * create a border. These border have their thickness fixed and non relative
         * to the square.
         * 
         * So i must consider it in my calcul of height and width available
         */
        int heightCalcul    = (int) ((centerPanel.getHeight()   - squareMesh    .length * 11 )      / squareMesh      .length);
        int widthCalcul     = (int) ((centerPanel.getWidth()    - squareMesh[0] .length * 11 )      / squareMesh[0]   .length);


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
     * Update the score tab
     * 
     * @param score
     */
    public void updateScore(int score) {
        valScore.setText(String.valueOf(score));
    }




    /**
     * Action after a trigger from any event listener
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        // Actions according to the source
        if (e.getSource() == quitButton) {

            // Quitting the application
            app.quit();
            return;


        } else if (e.getSource() == newGameButton && app.getLevel().getNbLevel() != 3) {

            // New classic game phase
            newClassicGame(false);
            return;


        } else if (e.getSource() == newGameButton && app.getLevel().getNbLevel() == 3) {

            // New custom game phase
            newCustomGame(false);
            return;

        }

        // Default action
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
    
}
