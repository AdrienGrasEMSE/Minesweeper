// Package declaration
package dgui;

// Import
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
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
import dgraphics.dcombo.DComboBox;
import dgraphics.dtheme.DTheme;
import deminer.DController;
import deminer.DSprite;
import deminer.DLevel;


/**
 * Class Local UI
 * 
 * @author  AdrienG
 * @version 0.0
 * 
 * 
 * This class represent the UI when the user play in a solo mode.
 */
public class DUI_Local extends JPanel implements ActionListener {

    /**
     * UI main objects
     */ 
    private final   DGUI        gui;
    private final   DController controller;
    private         DSprite[][] spriteMesh;
    private         int         previousLevelIndex;
    private         boolean     sizeAdaptation      = false;


    /**
     * UI main panels
     */
    private final   JPanel      northPanel          = new JPanel();
    private final   JPanel      southPanel          = new JPanel();
    private final   JPanel      centerPanel         = new JPanel();


    /**
     * North panels elements
     */
    private final   DLabel      labScore            = new DLabel("Score",         DFont.JOST_REGULAR,   18, DTheme.LAB_TRS);
    private final   DLabel      labLevel            = new DLabel("Level",         DFont.JOST_REGULAR,   18, DTheme.LAB_TRS);
    private final   DLabel      labTime             = new DLabel("Time",          DFont.JOST_REGULAR,   18, DTheme.LAB_TRS);
    private final   DLabel      labTimeMax          = new DLabel("Time limit",    DFont.JOST_REGULAR,   18, DTheme.LAB_TRS);
    private final   DLabel      valScore            = new DLabel("",              DFont.JOST_LIGHT,     18, DTheme.LAB_NTL);
    private final   DLabel      valTime             = new DLabel("00:00",         DFont.JOST_LIGHT,     18, DTheme.LAB_NTL);
    private final   DLabel      valTimeMax          = new DLabel("",              DFont.JOST_LIGHT,     18, DTheme.LAB_NTL);
    private final   DComboBox<DLevel> valLevel      = new DComboBox<>(  DLevel.values(), 18, DTheme.CBO_VAR);


    /**
     * South panels elements
     */
    private final   DButton     quitButton          = new DButton("Quit",     DFont.JOST_SEMIBOLD, 24, DTheme.BTN_RED);
    private final   DButton     newGameButton       = new DButton("New game", DFont.JOST_SEMIBOLD, 24, DTheme.BTN_GRN);


    /**
     * Component listener
     */
    private         ComponentAdapter    centerPanelSizeCheck;
    private         ItemListener        levelChangeCheck;




    /**
     * Constructor
     * 
     * @param gui
     * @param controller in order to transmit data or action performed
     */
    public DUI_Local(DGUI gui, DController controller) {

        // Getting the gui and the controller
        this.gui        = gui;
        this.controller = controller;


        // Setting up layout and VFX
        this.setLayout(new BorderLayout());


        // Setting up listener
        this.listenerInit();


        // Pannel setup
        this.northPanelSetup    ();
        this.southPanelSetup    ();
        this.centerPanelSetup   ();

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

                // If size adaptating is enable
                if (sizeAdaptation) {
                    displayMesh();
                }
                
            }
            
        };


        // Listener that check if the level has changed on the level selector
        levelChangeCheck = new ItemListener() {

            // On item change
            @Override
            public void itemStateChanged(ItemEvent e) {

                // Making an action only on item selection
                if (e.getStateChange()          == ItemEvent.SELECTED   &&
                    valLevel.getSelectedIndex() != -1                   &&
                    valLevel.getSelectedIndex() != previousLevelIndex) {

                    // Changing game level
                    previousLevelIndex = valLevel.getSelectedIndex();
                    switch (valLevel.getSelectedIndex()) {
                        case 0 -> controller.setLevel(DLevel.EASY);
                        case 1 -> controller.setLevel(DLevel.MEDIUM);
                        case 2 -> controller.setLevel(DLevel.HARD);
                        case 3 -> controller.setLevel(DLevel.CUSTOM);
                            
                    }


                    // Closing the popup
                    valLevel.setPopupVisible(false);


                    // Dialog to start a new game
                    DDialogBinary newGame = new DDialogBinary(gui, "Do you want to start a new game ?", DTheme.DLG_DRK);
                    newGame.setVisible(true);


                    // Getting the answer
                    boolean userChoice = newGame.getUserChoice();
                    if (userChoice && controller.getLevel() != DLevel.CUSTOM) {
                        
                        // New game 
                        newClassicGame(true);


                    } else if (userChoice && controller.getLevel() == DLevel.CUSTOM) {

                        // New game 
                        newCustomGame(true);

                    }

                }
                
            
            }

        };

    }




    /**
     * Setting up north panel
    */
    private void northPanelSetup() {

        // Plotting panel and set VFX
        this                .add(northPanel,BorderLayout.NORTH);
        northPanel          .setLayout      (new GridLayout(1, 2, 100, 0));
        northPanel          .setBackground  (DTheme.GUI_VAR.BCK_N);
        northPanel          .setBorder      (new EmptyBorder(0, 0, 0, 0));


        // Score panel
        JPanel scorePanel   = new JPanel    (new GridLayout(2, 2, 0, 5));
        scorePanel          .setBorder      (new EmptyBorder(10, 10, 10, 10));
        scorePanel          .setBackground  (DTheme.GUI_VAR.BCK_N);
        scorePanel          .add(labLevel);
        scorePanel          .add(valLevel);
        scorePanel          .add(labScore);
        scorePanel          .add(valScore);


        // Time panel
        JPanel timelPanel   = new JPanel    (new GridLayout(2, 2, 0, 5));
        timelPanel          .setBorder      (new EmptyBorder(10, 10, 10, 10));
        timelPanel          .setBackground  (DTheme.GUI_VAR.BCK_N);
        timelPanel          .add(labTime);
        timelPanel          .add(valTime);
        timelPanel          .add(labTimeMax);
        timelPanel          .add(valTimeMax);


        // Plotting
        northPanel          .add(timelPanel);
        northPanel          .add(scorePanel);


        // Setting up combo box
        valLevel            .setSelectedItem(controller.getLevel());
        valLevel            .addItemListener(levelChangeCheck);


        // Adding the listener to check size changement
        northPanel.addComponentListener(new ComponentAdapter() {
            
            // On size change
            @Override
            public void componentResized(ComponentEvent e) {

                /**
                 * Border size change : what's the matter ?
                 * 
                 * By changing the border size, we can keep a fixed size for each side
                 * of the north panel (time side and level/score side). Each side will
                 * be separated by a fix lenght that does not depend on the frame size.
                 * 
                 * Calcul is :
                 * 
                 * ((panelWidth - 2 * side_size) - sides_spacing) / 2 = left_and_right_border_thickeness
                 */
                int         newHGap = ((northPanel.getWidth() - 550) - 100) / 2;


                // Applying the new border
                northPanel          .setBorder(new EmptyBorder(10, newHGap, 10, newHGap));
                northPanel          .revalidate();
                
            }
            
        });

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
        this                    .add            (centerPanel,   BorderLayout.CENTER);
        centerPanel             .removeComponentListener(centerPanelSizeCheck);
        centerPanel             .setLayout      (new FlowLayout());
        centerPanel             .setBackground  (DTheme.GUI_NTL.BCK_N);


        // Adding the listener to check size changement
        centerPanel.addComponentListener(centerPanelSizeCheck);

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
     * Update the displayed level according to the controller one
     */
    public void updateLevel(boolean newGameTrigger) {

        // Saving the previous index
        previousLevelIndex = controller.getLevel().getNbLevel();
        valLevel.setSelectedItem(controller.getLevel());


        // If we maybe want a new game
        if (newGameTrigger) {

            // Change level
            valLevel.setSelectedItem(controller.getLevel());


            // Dialog to start a new game
            DDialogBinary newGame = new DDialogBinary(gui, "Do you want to start a new game ?", DTheme.DLG_DRK);
            newGame.setVisible(true);


            // Getting the answer
            boolean userChoice = newGame.getUserChoice();
            if (userChoice && controller.getLevel() != DLevel.CUSTOM) {
                
                // New game 
                newClassicGame(true);


            } else if (userChoice && controller.getLevel() == DLevel.CUSTOM) {

                // New game 
                newCustomGame(true);

            }


        } else {

            // Update display
            valLevel.removeItemListener(levelChangeCheck);
            valLevel.setSelectedItem(controller.getLevel());
            valLevel.addItemListener(levelChangeCheck);

        }

    }




    /**
     * Update the displayed time according to the controller one
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
     * Action performed when the user want a new classic game
     * 
     * @param lightInteraction specify the type interaction with the user
     */
    public void newClassicGame(boolean lightInteraction) {

        // If light interaction on
        if (lightInteraction) {

            // New classic game
            controller.newClassicGame();


        } else {

            // Dialog to confirm
            DDialogBinary confirm = new DDialogBinary(gui, "Confirm ?", DTheme.DLG_DRK);
            confirm.setVisible(true);


            // Getting the answer
            boolean userChoice = confirm.getUserChoice();
            if (userChoice) {
                controller.newClassicGame();
            }

        }

    }




    /**
     * Action performed when the user want a new custom game
     * 
     * @param lightInteraction specify the type interaction with the user
     */
    public void newCustomGame(boolean lightInteraction) {

        // Dialog to get custom parameters
        DDialogCustomNewGame param = new DDialogCustomNewGame(gui);
        do {

            // Display dialog
            param.setVisible(true);


            // Infos
            if (!param.getParamValid() && param.getUserConfirm()) {
                DDialogInfo info = new DDialogInfo( gui,
                                                    "Invalid parameters",
                                                    new String[]{ "- Width and Height must be between",
                                                    "5 and 20",
                                                    "",
                                                    "- Number of mines must be between",
                                                    "1 and 75% of (Width * Height)"
                                                    },
                                                    DTheme.DLG_DRK);
                info.setVisible(true);
            }

        }while (!param.getParamValid() && param.getUserConfirm());


        // Confirm
        if (param.getUserConfirm()) {
            
            // If light interaction on
            if (lightInteraction) {

                // New custom game
                controller.newCustomGame(param.getCustomHeight(), param.getCustomWidth(), param.getCustomNbMines());


            } else {

                // Dialog to confirm
                DDialogBinary confirm = new DDialogBinary(gui, "Confirm ?", DTheme.DLG_DRK);
                confirm.setVisible(true);


                // Getting the answer
                boolean userChoice = confirm.getUserChoice();
                if (userChoice) {

                    // New custom game
                    controller.newCustomGame(param.getCustomHeight(), param.getCustomWidth(), param.getCustomNbMines());
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
        DDialogEndGame endGame = new DDialogEndGame(gui, endGameType);
        endGame.setVisible(true);


        // Getting the answer
        boolean userChoice = endGame.getUserChoice();
        if (userChoice) {

            // New classic or custom game
            if (controller.getLevel() == DLevel.CUSTOM) {

                // New custom game phase
                newCustomGame(true);


            } else {

                // New classic game phase
                newClassicGame(true);

            }

        } else {

            // Quitting controller
            this.quitConfirm(false);

        }

    }




    /**
     * Start a new dialog phase to confirm controller leaving
     * 
     * @param lightInteraction specify the type interaction with the user
     */
    public void quitConfirm(boolean lightInteraction) {

        // If light interaction on
        if (lightInteraction) {

            // Quit controller
            controller.quit();


        } else {

            // Dialog to confirm
            DDialogBinary confirm = new DDialogBinary(gui, "Confirm ?", DTheme.DLG_DRK);
            confirm.setVisible(true);


            // Getting the answer
            boolean userChoice = confirm.getUserChoice();
            if (userChoice) {
                controller.quit();
            }

        }

    }
    



    /**
     * Setter : to get from the controller the sprite mesh
     * 
     * @param spriteMesh
     */
    public void setSpriteMesh(DSprite[][] spriteMesh) {
        this.spriteMesh = spriteMesh;
    }




    /**
     * Displaying the mine field using the sprite class
     */
    public void displayMesh() {
       
        // Removing everything from the center panel and reset the score
        centerPanel.removeAll();
        valScore.setText("0");


        // Creating the grid to display mines and coefficient
        JPanel minesPanel   = new JPanel();
        minesPanel          .setLayout(new GridLayout(spriteMesh.length, spriteMesh[0].length));
        minesPanel          .setBackground(DTheme.GUI_NTL.BCK_N);


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
     * Action after a trigger from any event listener
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        // Actions according to the source
        if (e.getSource() == quitButton) {

            // Quitting the controller
            this.quitConfirm(false);
            return;


        } else if (e.getSource() == newGameButton && controller.getLevel().getNbLevel() != 3) {

            // New classic game phase
            newClassicGame(false);
            return;


        } else if (e.getSource() == newGameButton && controller.getLevel().getNbLevel() == 3) {

            // New custom game phase
            newCustomGame(false);
            return;


        }

        // Default action
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }

}
