// Package declaration
package dgui;

// Import
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import ddialog.EndGame;
import deminer.DController;
import deminer.DLevel;
import deminer.DSprite;
import dgui.dui_online.DUI_Online;


/**
 * GUI : Graphical User Interface
 * 
 * @author  AdrienG
 * @version 0.0
 * 
 * 
 * This is the main graphical component. Its goal is to control evrey other
 * graphical component. In its shape, it is a classic JFrame that hold and
 * display components.
 */
public class DGUI extends JFrame {

    /**
     * GUI main attributes
     */
    private final   DController controller;
    private final   JPanel      mainPanel           = new JPanel();
    private final   CardLayout  mainLayout          = new CardLayout();
    private         DUI_Local   uiLocal;
    private         DUI_Option  uiOption;
    private         DUI_Online  uiOnline;
    private         DUI_Type    currentUI           = DUI_Type.UI_LOCAL;
    private         DUI_Type    previousUI          = DUI_Type.UI_LOCAL;


    /**
     * GUI menu
     */
    private final   JMenuBar    menuBar             = new JMenuBar();
    private final   JMenu       menuGame            = new JMenu("Game");
    private final   JMenu       menuMod             = new JMenu("Game mod");


    /**
     * Menu and submenu
     */
    private final   JMenu       mNewGame            = new JMenu     ("New Game");
    private final   JMenu       mNewClassicGame     = new JMenu     ("Classic Game");
    private final   JMenu       mLevelChange        = new JMenu     ("Change difficulty");
    private final   JMenuItem   mNewEasyGame        = new JMenuItem ("Easy");
    private final   JMenuItem   mNewMediumGame      = new JMenuItem ("Medium");
    private final   JMenuItem   mNewHardGame        = new JMenuItem ("Hard");
    private final   JMenuItem   mNewCustomGame      = new JMenuItem ("Custom Game");
    private final   JMenuItem   mLevelEasy          = new JMenuItem ("Easy");
    private final   JMenuItem   mLevelMedium        = new JMenuItem ("Medium");
    private final   JMenuItem   mLevelHard          = new JMenuItem ("Hard");
    private final   JMenuItem   mOption             = new JMenuItem ("Options");
    private final   JMenuItem   mQuit               = new JMenuItem ("Quit");
    private final   JMenuItem   mSolo               = new JMenuItem ("Solo game");
    private final   JMenuItem   mMultiPlayer        = new JMenuItem ("Multiplayer");


    /**
     * Getting the screen size
     */
    private final   Toolkit     toolkit             = Toolkit.getDefaultToolkit();
    private final   Dimension   screenSize          = toolkit.getScreenSize();
    private final   int         SCREENWIDTH         = screenSize.width;
    private final   int         SCREENHEIGHT        = screenSize.height;



    /**
     * Constructor
     * 
     * @param controller
     */
    public DGUI(DController controller) {

        // Setting up global style
        ImageIcon   logoIcon        = new ImageIcon("./img/mine.png");
        Image       logo            = logoIcon.getImage();
        this        .setIconImage   (logo);
        this        .setName        ("AdrienG's deminer");
        this        .setTitle       ("AdrienG's deminer");


        // Getting the controller
        this.controller         = controller;


        // Setting up menus
        this.menuGameSetup      ();
        this.menuModSetup       ();
        this.setJMenuBar        (menuBar);


        // Calculing size after its content and setting window visible
        this.setSize            (new Dimension(SCREENWIDTH / 2, 2 * SCREENHEIGHT / 3));
        this.setPreferredSize   (new Dimension(SCREENWIDTH / 2, 2 * SCREENHEIGHT / 3));
        this.setMinimumSize     (new Dimension(SCREENWIDTH / 2, 2 * SCREENHEIGHT / 3));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible         (true);


        // Setting up the main panel
        this                    .add(mainPanel);
        this.mainPanel          .setLayout(mainLayout);


        // Setting up UIs
        this.uiLocal            = new DUI_Local(this, this.controller);
        this.uiOption           = new DUI_Option(this);
        this.uiOnline           = new DUI_Online(this, this.controller);


        // Plotting UIs
        mainPanel               .add(this.uiLocal,  "UI_LOCAL");
        mainPanel               .add(this.uiOption, "UI_OPTION");
        mainPanel               .add(this.uiOnline, "UI_ONLINE");


        // Switching to the local UI
        this                    .switchUILocal();

    }




    /**
     * Menu game setup
     */
    private void menuGameSetup() {

        // mNewEasyGame action
        mNewEasyGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Lauching a new easy game
                controller.setLevel     (DLevel.EASY);
                uiLocal.updateLevel     (false);
                uiLocal.newClassicGame  (false);

            }

        });


        // mNewMediumGame action
        mNewMediumGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Lauching a new medium game
                controller.setLevel     (DLevel.MEDIUM);
                uiLocal.updateLevel     (false);
                uiLocal.newClassicGame  (false);

            }

        });


        // mNewHardGame action
        mNewHardGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Lauching a new hard game
                controller.setLevel     (DLevel.HARD);
                uiLocal.updateLevel     (false);
                uiLocal.newClassicGame  (false);

            }

        });


        // mNewHardGame action
        mNewCustomGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Lauching a new custom game
                controller.setLevel     (DLevel.CUSTOM);
                uiLocal.updateLevel     (false);
                uiLocal.newCustomGame   (true);

            }

        });


        // mLevelEasy action
        mLevelEasy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Change to easy difficulty
                controller.setLevel     (DLevel.EASY);
                uiLocal.updateLevel     (true);

            }

        });


        // mLevelMedium action
        mLevelMedium.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Change to medium difficulty
                controller.setLevel     (DLevel.MEDIUM);
                uiLocal.updateLevel     (true);

            }

        });


        // mLevelHard action
        mLevelHard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Change to hard difficulty
                controller.setLevel     (DLevel.HARD);
                uiLocal.updateLevel     (true);

            }

        });


        // mOption action
        mOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                // Switching to the option interface
                switchUIOption();

            }

        });


        // mQuit action
        mQuit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uiLocal.quitConfirm(false);

            }
            
        });


        // Adding sub menu
        mNewClassicGame     .add(mNewEasyGame);
        mNewClassicGame     .add(mNewMediumGame);
        mNewClassicGame     .add(mNewHardGame);
        mNewGame            .add(mNewClassicGame);
        mNewGame            .add(mNewCustomGame);
        mLevelChange        .add(mLevelEasy);
        mLevelChange        .add(mLevelMedium);
        mLevelChange        .add(mLevelHard);


        // Adding items to the menu
        menuGame            .add(mNewGame);
        menuGame            .add(mLevelChange);
        menuGame            .addSeparator();
        menuGame            .add(mOption);
        menuGame            .addSeparator();
        menuGame            .add(mQuit);


        // Adding menu to the menu bar
        menuBar.add(menuGame);

    }




    /**
     * Menu game setup
     */
    private void menuModSetup() {
        
        // mSolo action
        mSolo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Switching to the local interface
                switchUILocal();
                controller.setLevel(DLevel.EASY);
                uiLocal.updateLevel(false);
                uiLocal.newClassicGame(true);

            }

        });


        // mMultiPlayer action
        mMultiPlayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                // TODO : Multiplayer interface
                switchUIOnline();

            }

        });


        // Adding items to the menu
        menuMod.add(mSolo);
        menuMod.addSeparator();
        menuMod.add(mMultiPlayer);


        // Adding menu to the menu bar
        menuBar.add(menuMod);

    }




    /**
     * Apply a theme
     */
    public void updateTheme() {

        // Clearing the frame
        mainPanel.remove             (uiLocal);
        mainPanel.remove             (uiOption);
        mainPanel.remove             (uiOnline);


        // Setting up UIs
        this.uiLocal            = new DUI_Local(this, this.controller);
        this.uiOption           = new DUI_Option(this);
        this.uiOnline           = new DUI_Online(this, this.controller);


        // Plotting UIs
        mainPanel               .add(this.uiLocal,  "UI_LOCAL");
        mainPanel               .add(this.uiOption, "UI_OPTION");
        mainPanel               .add(this.uiOnline, "UI_ONLINE");


        // Switching to the current UI
        DUI_Type buffer_ = previousUI;
        switch (currentUI) {
            case DUI_Type.UI_LOCAL:
                this.switchUILocal();
                break;
        
            case DUI_Type.UI_OPTION:
                this.switchUIOption();
                break;

            case DUI_Type.UI_ONLINE:
                this.switchUIOnline();
                break;
        }
        previousUI = buffer_;

    }




    /**
     * Switch to the local UI
     */
    private void switchUILocal() {

        // Saving previous UI
        previousUI  = currentUI;
        currentUI   = DUI_Type.UI_LOCAL;
        

        // Switching
        mainLayout  .show(mainPanel, "UI_LOCAL");
        mSolo       .setEnabled(false);
        mOption     .setEnabled(true);
        mMultiPlayer.setEnabled(true);

    }




    /**
     * Switch to the option UI
     */
    private void switchUIOption() {

        // Saving previous UI
        previousUI  = currentUI;
        currentUI   = DUI_Type.UI_OPTION;
        

        // Switching
        mainLayout  .show(mainPanel, "UI_OPTION");
        mSolo       .setEnabled(true);
        mOption     .setEnabled(false);
        mMultiPlayer.setEnabled(true);

    }



    
    /**
     * Switch to the online UI
     */
    private void switchUIOnline() {

        // Saving previous UI
        previousUI  = currentUI;
        currentUI   = DUI_Type.UI_ONLINE;
        

        // Switching
        mainLayout  .show(mainPanel, "UI_ONLINE");
        mSolo       .setEnabled(true);
        mOption     .setEnabled(true);
        mMultiPlayer.setEnabled(false);

    }




    /**
     * Switching back to the previous UI
     */
    public void switchUIPrevious() {

        // Exception management
        if (    (currentUI == DUI_Type.UI_ONLINE && previousUI == DUI_Type.UI_OPTION) ||
                (currentUI == previousUI)) {

            // Back to the local UI
            this.switchUILocal();


        } else {

            // Switching back to the previous UI
            currentUI   = previousUI;

        
            // Switching
            switch (currentUI) {
                case DUI_Type.UI_LOCAL:
                    this.switchUILocal();
                    break;
            
                case DUI_Type.UI_OPTION:
                    this.switchUIOption();
                    break;

                case DUI_Type.UI_ONLINE:
                    this.switchUIOnline();
                    break;
            }
        
        }

    }




    /**
     * =====================================================================================================================
     * 
     * Wiring with the local UI
     * 
     * =====================================================================================================================
     */
    public void updateScore         (int score)                 {uiLocal.updateScore        (score);};
    public void updateLevel         (boolean newGameTrigger)    {uiLocal.updateLevel        (newGameTrigger);};
    public void updateTime          (int timeSpent)             {uiLocal.updateTime         (timeSpent);};
    public void updateTimeLimit     (int timeLimit)             {uiLocal.updateTimeLimit    (timeLimit);};
    public void setSizeAdaptation   (boolean enable)            {uiLocal.setSizeAdaptation  (enable);};
    public void setSpriteMesh       (DSprite[][] spriteMesh)    {uiLocal.setSpriteMesh      (spriteMesh);};
    public void displayMesh         ()                          {uiLocal.displayMesh        ();};
    public void endGamePhase        (EndGame endGameType)       {uiLocal.endGamePhase       (endGameType);};




    /**
     * =====================================================================================================================
     * 
     * Wiring with the online UI
     * 
     * =====================================================================================================================
     */
    public void gameCreated(boolean succeed, String failInfo) {uiOnline.gameCreated(succeed, failInfo);}
    public void gameJoinned(boolean succeed, String failInfo) {uiOnline.gameJoinned(succeed, failInfo);}

    public void updatePlayerList(Map<String, String> playerList, String ownerUUID) {
        uiOnline.updatePlayerList(playerList, ownerUUID);
    }

    public void switchIngameUI() {uiOnline.switchSubUIIngame();}

}
