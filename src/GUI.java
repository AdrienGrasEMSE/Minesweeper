// Import
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import ddialog.DDialogBinary;
import ddialog.DDialogCustomNewGame;
import ddialog.DDialogEndGame;
import ddialog.DDialogInfo;
import ddialog.EndGame;
import dgraphics.DButton;
import dgraphics.DFont;
import dgraphics.DLabel;
import dgraphics.DTheme;
import dgraphics.dcombo.DComboBox;


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
    private final JPanel    northPanel      = new JPanel(new GridLayout(2, 1));
    private final JPanel    southPanel      = new JPanel(new FlowLayout());
    private final JPanel    centerPanel     = new JPanel();


    /**
     * GUI menu
     */
    private final JMenuBar  menuBar         = new JMenuBar();
    private final JMenu     menuGame        = new JMenu("Game");


    /**
     * 
     */
    private boolean manualLevelChange           = false;
    private boolean sizeAdaptation              = false;




    /**
     * North panels elements
     */
    private final DLabel labScore         = new DLabel("Score",         DFont.JOST_REGULAR,   18, DTheme.FNT_NTL_D);
    private final DLabel labLevel         = new DLabel("Level",         DFont.JOST_REGULAR,   18, DTheme.FNT_NTL_D);
    private final DLabel labTime          = new DLabel("Time",          DFont.JOST_REGULAR,   18, DTheme.FNT_NTL_D);
    private final DLabel labTimeMax       = new DLabel("Time limit",    DFont.JOST_REGULAR,   18, DTheme.FNT_NTL_D);
    private final DLabel valScore         = new DLabel("",              DFont.JOST_LIGHT,     18, DTheme.FNT_NTL_N, DTheme.BTN_VAR_N);
    private final DLabel valTime          = new DLabel("00:00",         DFont.JOST_LIGHT,     18, DTheme.FNT_NTL_N, DTheme.BTN_VAR_N);
    private final DLabel valTimeMax       = new DLabel("",              DFont.JOST_LIGHT,     18, DTheme.FNT_NTL_N, DTheme.BTN_VAR_N);
    private final DComboBox<Level> valLevel     = new DComboBox<>(  Level.values(),
                                                                                18,
                                                                                DTheme.FNT_NTL_D,
                                                                                DTheme.FNT_NTL_N,
                                                                                DTheme.BTN_VAR_D,
                                                                                DTheme.BTN_VAR_N,
                                                                                DTheme.BTN_VAR_L);




    /**
     * South panels elements
     */
    private final DButton quitButton      = new DButton("Quit",     DFont.JOST_SEMIBOLD, 24, Color.WHITE, DTheme.BTN_RED_D, DTheme.BTN_RED_N, DTheme.BTN_RED_L);
    private final DButton newGameButton   = new DButton("New game", DFont.JOST_SEMIBOLD, 24, Color.WHITE, DTheme.BTN_GRN_D, DTheme.BTN_GRN_D, DTheme.BTN_GRN_D);




    /**
     * GUI
     */
    public GUI(App app) {

        // Getting application
        this.app    = app;


        // Setting up layout and VFX
        this.setLayout(new BorderLayout());


        // Setting up Menu
        this.menuSetup();


        // Setting up panels
        this.northPanelSetup();
        this.southPanelSetup();
        this.centerPanelSetup();

    }




    /**
     * Menu setup
     */
    private void menuSetup() {
        
        // Adding item to the menuGame
        JMenuItem mQuitter  = new JMenuItem("Quitter", KeyEvent.VK_Q); 
        menuGame            .add(mQuitter);


        // Adding menu to the menu bar
        menuBar.add(menuGame);


        // Plotting the menu bar
        app.add(menuBar);

    }
    



    /**
     * Setting up north panel
    */
    private void northPanelSetup() {

        // Plotting panel and set VFX
        add(northPanel,     BorderLayout.NORTH);
        northPanel          .setBackground  (DTheme.GUI_VAR_N);


        // Upper panel
        JPanel upperPanel   = new JPanel(new FlowLayout());
        upperPanel          .setBorder      (new EmptyBorder(0, 0, 5, 0));
        upperPanel          .setBackground  (DTheme.GUI_VAR_N);
        upperPanel          .add(labScore);
        upperPanel          .add(valScore);
        upperPanel          .add(labLevel);
        upperPanel          .add(valLevel);


        // Lower panel
        JPanel lowerPanel   = new JPanel(new FlowLayout());
        lowerPanel          .setBackground  (DTheme.GUI_VAR_N);
        lowerPanel          .add(labTime);
        lowerPanel          .add(valTime);
        lowerPanel          .add(labTimeMax);
        lowerPanel          .add(valTimeMax);


        // Plotting
        northPanel          .add(upperPanel);
        northPanel          .add(lowerPanel);


        // Setting up combo box
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
     * Update the displayed time according to the app one
     */
    public void updateTime(int timeSpent) {

        // Divise the time to get minute and second
        int min_ = timeSpent / 60;
        int sec_ = (timeSpent % 60);


        // Setting up string for the label
        String min = "00";
        String sec = "00";


        // Attributing minutes
        if (min_ > 0 && min_ < 10) {
            min = "0" + String.valueOf(min_);
        } else if (min_ > 9 && min_ < 100) {
            min = String.valueOf(min_);
        }


        // Attributing sec
        if (sec_ > 0 && sec_ < 10) {
            sec = "0" + String.valueOf(sec_);
        } else if (sec_ > 9 && sec_ < 100) {
            sec = String.valueOf(sec_);
        }


        // Updating the label
        valTime.setText(min + ":" + sec);
    }




    /**
     * Update the displayed time limit
     * 
     * @param timeLimit
     */
    public void updateTimeLimit(int timeLimit) {

        // If there is a time limit
        if (timeLimit == 0) {

            // Setting up content
            valTimeMax.setText("No limit");


        } else {
            
            // Divise the time to get minute and second
            int min_ = timeLimit / 60;
            int sec_ = (timeLimit % 60);


            // Setting up string for the label
            String min = "00";
            String sec = "00";


            // Attributing minutes
            if (min_ > 0 && min_ < 10) {
                min = "0" + String.valueOf(min_);
            } else if (min_ > 9 && min_ < 100) {
                min = String.valueOf(min_);
            }


            // Attributing sec
            if (sec_ > 0 && sec_ < 10) {
                sec = "0" + String.valueOf(sec_);
            } else if (sec_ > 9 && sec_ < 100) {
                sec = String.valueOf(sec_);
            }


            // Updating the label
            valTimeMax.setText(min + ":" + sec);

        }

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
            DDialogBinary newGame = new DDialogBinary(app, "Do you want to start a new game ?");
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
            DDialogBinary confirm = new DDialogBinary(app, "Confirm ?");
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
        DDialogCustomNewGame param = new DDialogCustomNewGame(app);
        do {

            // Display dialog
            param.setVisible(true);


            // Infos
            if (!param.getParamValid() && param.getUserConfirm()) {
                DDialogInfo info = new DDialogInfo(app, "Invalid parameters", new String[]{ "- Width and Height must be between",
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
                DDialogBinary confirm = new DDialogBinary(app, "Confirm ?");
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




    /**
     * End game phase : display a dialog to check if the user want a rematch or quit
     * 
     * @param gameWon in case of win or lost, the message is different
     */
    public void endGamePhase(EndGame endGameType) {

        // Dialog
        DDialogEndGame endGame = new DDialogEndGame(app, endGameType);
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
     * Calcul the square size according to the size of the centerPanel
     * 
     * @return calcSize, the calculed size
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
         * So i must consider it in my calcul of height and width available.
         * 
         * I found out that 11px might the best approximation for these border
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
