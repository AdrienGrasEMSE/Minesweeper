// Package declaration
package dgui.dui_online;

// Import
import java.awt.CardLayout;
import javax.swing.JPanel;
import dgui.DGUI;


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
    private final   DGUI                gui;
    private final   CardLayout          mainLayout  = new CardLayout();


    /**
     * subUI
     */
    private final   DUI_Online_Default  uiDefault;



    /**
     * Constructor
     * 
     * @param controller in order to transmit data or action performed
     */
    public DUI_Online(DGUI gui) {

        // Getting the gui and the controller
        this.gui        = gui;


        // Setting up layout
        this.setLayout(mainLayout);


        // Setting up subUI
        this.uiDefault = new DUI_Online_Default(this);


        // Plotting subUIs
        this.add(this.uiDefault,  "DEFAULT");


        // Showing the default screen
        this.switchSubUIDefault();

    }




    /**
     * Switch to the local UI
     */
    public void switchSubUIDefault() {
        mainLayout.show(this, "DEFAULT");
    }





    /**
     * =====================================================================================================================
     * 
     * Wiring between the GUI and SubUI
     * 
     * =====================================================================================================================
     */
    public void switchUIPrevious() {gui.switchUIPrevious();}

}
