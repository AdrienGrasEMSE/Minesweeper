// Package declaration
package dgui.dui_online;

// Import
import deminer.DController;
import deminer.DSprite;
import dgui.DGUI;
import donline.DPlayer;
import java.awt.CardLayout;
import java.util.Map;
import javax.swing.JPanel;


/**
 * Class Online UI
 * 
 * @author  AdrienG
 * @version 0.0
 * 
 * 
 * This class represent the UI when the user play in a multiplayer mode. This
 * UI owns other subUI.
 */
public class DUI_Online extends JPanel {

    /**
     * UI main objects
     */
    private final   DController         controller;
    private final   DGUI                gui;
    private final   CardLayout          mainLayout  = new CardLayout();


    /**
     * subUI
     */
    private final   DUI_Online_Default  uiDefault;
    private final   DUI_Online_Wait     uiWait;
    private final   DUI_Online_Ingame   uiIngame;



    /**
     * Constructor
     * 
     * @param controller in order to transmit data or action performed
     */
    public DUI_Online(DGUI gui, DController controller) {

        // Getting the gui and the controller
        this.controller = controller;
        this.gui        = gui;


        // Setting up layout
        this.setLayout(mainLayout);


        // Setting up subUI
        this.uiDefault  = new DUI_Online_Default(this.gui, this,    this.controller);
        this.uiWait     = new DUI_Online_Wait   (this.gui, this,    this.controller);
        this.uiIngame   = new DUI_Online_Ingame (this.gui, this,    this.controller);


        // Plotting subUIs
        this.add(this.uiDefault,"DEFAULT");
        this.add(this.uiWait,   "WAIT");
        this.add(this.uiIngame, "INGAME");


        // Showing the default screen
        this.switchSubUIDefault();

    }




    /**
     * Switch to the default UI
     */
    public void switchSubUIDefault() {
        mainLayout.show(this, "DEFAULT");
    }




    /**
     * Switch to the waiting UI
     */
    public void switchSubUIWait() {
        mainLayout.show(this, "WAIT");
    }




    /**
     * Switch to the ingame UI
     */
    public void switchSubUIIngame() {
        mainLayout.show(this, "INGAME");
    }




    /**
     * =====================================================================================================================
     * 
     * Wiring with the default UI
     * 
     * =====================================================================================================================
     */
    public void gameCreated     (boolean succeed, String failInfo)                  {uiDefault.gameCreated(succeed, failInfo);}
    public void gameJoinned     (boolean succeed, String failInfo)                  {uiDefault.gameJoinned(succeed, failInfo);}




    /**
     * =====================================================================================================================
     * 
     * Wiring with the wait UI
     * 
     * =====================================================================================================================
     */
    public void setServerOwner  (boolean serverOwner)                               {uiWait.setServerOwner(serverOwner);}

    


    /**
     * =====================================================================================================================
     * 
     * Wiring with the ingame UI
     * 
     * =====================================================================================================================
     */
    public void setSpriteMesh       (DSprite[][] spriteMesh)    {uiIngame.setSpriteMesh(spriteMesh);}
    public void setSizeAdaptation   (boolean enable)            {uiIngame.setSizeAdaptation(enable);}
    public void playerLost          ()                          {uiIngame.playerLost();}
    public void gameLost            ()                          {uiIngame.gameLost();}
    public void gameWin             ()                          {uiIngame.gameEnd();}




    /**
     * =====================================================================================================================
     * 
     * Multi-wiring
     * 
     * =====================================================================================================================
     */
    public void updatePlayerList(Map<String, DPlayer> playerList, String ownerUUID)  {
        uiWait.updatePlayerList(playerList, ownerUUID);
        uiIngame.updatePlayerList(playerList);
    }

}
