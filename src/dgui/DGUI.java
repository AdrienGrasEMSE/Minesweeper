// Package declaration
package dgui;

// Import
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import deminer.DController;
import deminer.DLevel;
import deminer.DSprite;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import ddialog.EndGame;


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
    private         DUI_Local   uiLocal;
    private         boolean     ready           = false;


    /**
     * GUI menu
     */
    private final   JMenuBar    menuBar         = new JMenuBar();
    private final   JMenu       menuGame        = new JMenu("Game");
    private final   JMenu       menuMod         = new JMenu("Game mod");


    /**
     * Getting the screen size
     */
    private final   Toolkit     toolkit         = Toolkit.getDefaultToolkit();
    private final   Dimension   screenSize      = toolkit.getScreenSize();
    private final   int         SCREENWIDTH     = screenSize.width;
    private final   int         SCREENHEIGHT    = screenSize.height;



    /**
     * Constructor
     * 
     * @param controller the controller controller
     */
    public DGUI(DController controller) {

        // Setting up global style
        ImageIcon   logoIcon        = new ImageIcon("./img/mine.png");
        Image       logo            = logoIcon.getImage();
        this        .setIconImage   (logo);
        this        .setName        ("AdrienG's deminer");
        this        .setTitle       ("AdrienG's deminer");


        // Getting the controller
        this.controller = controller;


        // Setting up menus
        this.menuGameSetup  ();
        this.menuModSetup   ();
        this.setJMenuBar    (menuBar);


        // Calculing size after its content and setting window visible
        this.setSize            (new Dimension(SCREENWIDTH / 2, 2 * SCREENHEIGHT / 3));
        this.setPreferredSize   (new Dimension(SCREENWIDTH / 2, 2 * SCREENHEIGHT / 3));
        this.setMinimumSize     (new Dimension(SCREENWIDTH / 2, 2 * SCREENHEIGHT / 3));
        this.setVisible         (true);


        // Setting up action on click on the close button
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);


        // Lauching the local UI
        this.setUILocal();


        // GUI ready
        this.ready = true;

    }




    /**
     * Menu game setup
     */
    private void menuGameSetup() {
        
        // Creating item for the menuGame
        JMenu       mNewGame            = new JMenu("New Game");
        JMenu       mNewClassicGame     = new JMenu("Classic Game");
        JMenuItem   mNewEasyGame        = new JMenuItem("Easy");
        JMenuItem   mNewMediumGame      = new JMenuItem("Medium");
        JMenuItem   mNewHardGame        = new JMenuItem("Hard");
        JMenuItem   mNewCustomGame      = new JMenuItem("Custom Game");
        JMenuItem   mLevelChange        = new JMenuItem("Change difficulty");
        JMenuItem   mOption             = new JMenuItem("Options");
        JMenuItem   mQuit               = new JMenuItem("Quit");


        // mNewEasyGame action
        mNewEasyGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.setLevel    (DLevel.EASY);
                // newClassicGame  (false);
                // updateLevel     ();

            }

        });


        // mNewMediumGame action
        mNewMediumGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.setLevel    (DLevel.MEDIUM);
                // newClassicGame  (false);
                // updateLevel     ();

            }

        });


        // mNewHardGame action
        mNewHardGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.setLevel    (DLevel.HARD);
                // newClassicGame  (false);
                // updateLevel     ();

            }

        });


        // mNewHardGame action
        mNewCustomGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.setLevel    (DLevel.CUSTOM);
                // newCustomGame   (true);
                // updateLevel     ();

            }

        });


        // mLevelChange action
        mLevelChange.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO : display level change dialog

            }

        });


        // mOption action
        mOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                // // Switching UI if needed
                // if (guiType != GUIType.GUI_OPTION) {

                //     // Swicthing UI
                //     guiPreviousType     = guiType;
                //     guiType             = GUIType.GUI_OPTION;
                //     panelSetup          (guiType);

                // }

            }

        });


        // mQuit action
        mQuit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // quitConfirm(false);

            }
            
        });


        // Adding sub menu
        mNewClassicGame     .add(mNewEasyGame);
        mNewClassicGame     .add(mNewMediumGame);
        mNewClassicGame     .add(mNewHardGame);
        mNewGame            .add(mNewClassicGame);
        mNewGame            .add(mNewCustomGame);


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
        
        // Creating item for the menuMod
        JMenuItem   mSolo               = new JMenuItem("Solo game");
        JMenuItem   mMultiPlayer        = new JMenuItem("Multiplayer");


        // mSolo action
        mSolo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Launching a local interface
                setUILocal();

            }

        });


        // mMultiPlayer action
        mMultiPlayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                // TODO : Multiplayer interface

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
     * Getter : to check if the GUI is ready
     * 
     * @return ready, a flag that specify if the GUI is ready or not
     */
    public boolean isReady() {
        return this.ready;
    }




    /**
     * Setting up local interface
     */
    private void setUILocal() {

        // New local interface
        this.uiLocal = new DUI_Local(this, this.controller);
        this.setContentPane(uiLocal);
        this.repaint();

    }





    /**
     * =====================================================================================================================
     * 
     * Wiring with the local UI
     * 
     * =====================================================================================================================
     */
    public void updateScore         (int score)                 {uiLocal.updateScore        (score);};
    public void updateLevel         ()                          {uiLocal.updateLevel        ();};
    public void updateTime          (int timeSpent)             {uiLocal.updateTime         (timeSpent);};
    public void updateTimeLimit     (int timeLimit)             {uiLocal.updateTimeLimit    (timeLimit);};
    public void setSizeAdaptation   (boolean enable)            {uiLocal.setSizeAdaptation  (enable);};
    public void setSpriteMesh       (DSprite[][] spriteMesh)    {uiLocal.setSpriteMesh      (spriteMesh);};
    public void displayMesh         ()                          {uiLocal.displayMesh        ();};
    public void endGamePhase        (EndGame endGameType)       {uiLocal.endGamePhase       (endGameType);};

}
