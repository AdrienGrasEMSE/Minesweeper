// Pakcage declaration
package dgui.dui_online;

// Import
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
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.HashMap;
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
public class DUI_Online_Ingame extends JPanel implements ActionListener {

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


        // Pannel setup
        this.northPanelSetup    ();
        this.southPanelSetup    ();
        this.centerPanelSetup   ();
        this.eastPanelSetup     ();


        // Listener init
        this.listenerInit       ();

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
    private void displayMesh() {
       
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




    /**
     * Update the player list
     * 
     * @param playerList
     */
    public void updatePlayerList(Map<String, DPlayer> playerList) {
        this.playerList = playerList;
    }




    /**
     * 
     */
    public void updateScore() {

        // Usually, the list is not empty BUT
        if (playerList != null && !playerList.isEmpty()) {

            // Clearing the side panel
            this.eastPanel.removeAll();


            // Creating the title pabel
            JPanel titlePanel   = new JPanel    (new GridLayout(1, 2));
            titlePanel          .setBorder      (new EmptyBorder(0, 0, 10, 0));
            titlePanel          .setBackground  (DTheme.GUI_NTL.BCK_N);
            titlePanel          .setMaximumSize (new Dimension(Integer.MAX_VALUE, 50));


            // Creating the title label
            DLabel titleLabel   = new DLabel    ("Player list", DFont.JOST_SEMIBOLD, 24, DTheme.LAB_TRS);
            titlePanel          .add            (titleLabel);
            titlePanel          .add            (new JLabel(""));
            eastPanel           .add            (titlePanel);


            // Running through the player list
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


                // Plotting the player panel
                eastPanel.add(playerPanel);

            }


            // Updating the center panel
            eastPanel.revalidate();

        }

    }




    /**
     * Action after a trigger from any event listener
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        // Actions according to the source
        if (e.getSource() == leaveButton) {

            // Getting back to the previous screen
            gui.switchUIPrevious();
            return;


        }

        // Default action
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }

}
