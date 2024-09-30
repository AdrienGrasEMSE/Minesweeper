// Import
import deminer_dialog.DeminerDialogBinary;
import deminer_dialog.DeminerDialogCustomNewGame;
import deminer_dialog.DeminerDialogInfo;
import deminer_graphic.DeminerButton;
import deminer_graphic.DeminerFont;
import deminer_graphic.DeminerLabel;
import deminer_graphic.combo_box.DeminerComboBox;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import javax.swing.*;


/**
 * GUI : Graphical User Interface
 * 
 * @author  AdrienG
 * @version 0.0
 */
public class GUI extends JPanel implements ActionListener{

    /**
     * Main colors
     */
    private final static Color GUI_DARK_CYAN      = new Color(0x117864);
    private final static Color GUI_LIGHT_GREY     = new Color(0x212F3D);
    private final static Color GUI_MAIN_GREY      = new Color(0x1C2833);
    private final static Color GUI_DARK_GREY      = new Color(0x17202A);


    /**
     * Red bouton colors
     */
    private final static Color BTN_RED_DEFAULT  = new Color(0xCB4335);
    private final static Color BTN_RED_FLYOVER  = new Color(0xE74C3C);
    private final static Color BTN_RED_ACTIVE   = new Color(0xEA5E50);


    /**
     * Green bouton colors
     */
    private final static Color BTN_GRN_DEFAULT  = new Color(0x239B56);
    private final static Color BTN_GRN_FLYOVER  = new Color(0x28B463);
    private final static Color BTN_GRN_ACTIVE   = new Color(0x2ECC71);


    /**
     * Cyan bouton colors
     */
    private final static Color BTN_CYN_DEFAULT  = new Color(0x148F77);
    private final static Color BTN_CYN_FLYOVER  = new Color(0x17A589);
    private final static Color BTN_CYN_ACTIVE   = new Color(0x1ABC9C);


    /**
     * Font color
     */
    private final static Color FONT_DEFAULT     = new Color(0xEAECEE);
    private final static Color FONT_ACTIVE      = new Color(0xD5D8DC);




    /**
     * GUI main objects
     */
    private final   App         app;
    private final   Minefield   field;
    // private         Square[][]  squareIndex;
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
     * North panels elements
     */
    private final DeminerLabel labScore         = new DeminerLabel("Score", DeminerFont.JOST_LIGHT,     18, FONT_DEFAULT);
    private final DeminerLabel labLevel         = new DeminerLabel("Level", DeminerFont.JOST_REGULAR,   18, FONT_ACTIVE);
    private final DeminerLabel valScore         = new DeminerLabel("",      DeminerFont.JOST_REGULAR,   18, FONT_ACTIVE);
    private final DeminerComboBox<Level> valLevel     = new DeminerComboBox<>(  Level.values(),
                                                                                18,
                                                                                FONT_DEFAULT,
                                                                                FONT_ACTIVE,
                                                                                BTN_CYN_DEFAULT,
                                                                                BTN_CYN_FLYOVER,
                                                                                BTN_CYN_ACTIVE);




    /**
     * South panels elements
     */
    private final DeminerButton quitButton      = new DeminerButton("Quit",     DeminerFont.JOST_SEMIBOLD, 16, Color.WHITE, BTN_RED_DEFAULT, BTN_RED_FLYOVER, BTN_RED_ACTIVE);
    private final DeminerButton newGameButton   = new DeminerButton("New game", DeminerFont.JOST_SEMIBOLD, 16, Color.WHITE, BTN_GRN_DEFAULT, BTN_GRN_FLYOVER, BTN_GRN_ACTIVE);




    /**
     * GUI
     */
    public GUI(App app, Minefield field) {

        // Getting application
        this.app    = app;
        this.field  = field;


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
        
        eastPanel           .setBackground  (GUI_MAIN_GREY);
        westPanel           .setBackground  (GUI_MAIN_GREY);

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
        northPanel          .setBackground(GUI_DARK_CYAN);


        // Plotting elements inside
        northPanel          .add(labScore);
        northPanel          .add(valScore);
        northPanel          .add(labLevel);
        northPanel          .add(valLevel);


        // Elements VFX
        labScore            .setForeground(Color.white);
        valScore            .setForeground(Color.white);
        labLevel            .setForeground(Color.white);
        valLevel            .setBackground(GUI_DARK_GREY);
        valLevel            .setForeground(Color.black);
        valLevel            .setSelectedItem(app.getLevel());
        valLevel            .addItemListener((ItemEvent e) -> {

            // Making an action only on item selection
            if (e.getStateChange()          == ItemEvent.SELECTED   &&
                valLevel.getSelectedIndex() != -1                   &&
                valLevel.getSelectedIndex() != previousLevelIndex) {

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
        southPanel          .setBackground  (GUI_DARK_GREY);


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
        add(centerPanel,    BorderLayout.CENTER);
        centerPanel         .setBackground  (GUI_LIGHT_GREY);

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

            // Dialog to confirm
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
                                                                                                                "5 and 150",
                                                                                                                "",
                                                                                                                "- Number of mines must be between",
                                                                                                                "1 and (Width * Height - 2)"
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

                // Dialog to confirm
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





    /**
     * Displaying the mine field using the square class
     */
    public void displayField() {
        field.displayPrettyMines();
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
