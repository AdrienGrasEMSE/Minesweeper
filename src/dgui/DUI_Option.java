// Package declaration
package dgui;

// Import
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import dgraphics.DButton;
import dgraphics.DFont;
import dgraphics.dtheme.DTheme;


/**
 * Class Local UI
 * 
 * @author  AdrienG
 * @version 0.0
 * 
 * 
 * This class represent the UI when the user play in a solo mode.
 */
public class DUI_Option extends JPanel implements ActionListener {

    /**
     * UI main objects
     */ 
    private final   DGUI        gui;


    /**
     * GUI main panels
     */
    private final   JPanel      northPanel          = new JPanel();
    private final   JPanel      southPanel          = new JPanel();
    private final   JPanel      centerPanel         = new JPanel();


    /**
     * South panels elements
     */
    private final   DButton     backButton          = new DButton("Back",           DFont.JOST_SEMIBOLD, 24, DTheme.BTN_RED);
    private final   DButton     saveButton          = new DButton("Save settings",  DFont.JOST_SEMIBOLD, 24, DTheme.BTN_GRN);




    /**
     * Constructor
     * 
     * @param controller in order to transmit data or action performed
     */
    public DUI_Option(DGUI gui) {

        // Getting the gui and the controller
        this.gui        = gui;


        // Setting up layout and VFX
        this.setLayout(new BorderLayout());


        // Pannel setup
        this.northPanelSetup    ();
        this.southPanelSetup    ();
        this.centerPanelSetup   ();

    }


    /**
     * Setting up north panel
    */
    private void northPanelSetup() {

        // Plotting panel and set VFX
        this.add(northPanel,BorderLayout.NORTH);
        northPanel          .setLayout      (new BoxLayout(northPanel, BoxLayout.Y_AXIS));
        northPanel          .setBackground  (DTheme.GUI_VAR.BCK_N);

    }




    /**
     * Setting up south panel
    */
    private void southPanelSetup() {

        // Clearing the southpanel
        southPanel.removeAll();


        // Plotting panel and set VFX
        this                .add            (southPanel,BorderLayout.SOUTH);
        southPanel          .setLayout      (new FlowLayout());
        southPanel          .setBackground  (DTheme.GUI_DRK.BCK_N);


        // Plotting elements inside
        southPanel          .add(backButton);
        southPanel          .add(saveButton);


        // Setting up listeners
        backButton          .addActionListener(this);
        saveButton          .addActionListener(this);

    }




    /**
     * Setting up center panel
     */
    private void centerPanelSetup() {

        // Plotting panel and set VFX
        this                    .add            (centerPanel,   BorderLayout.CENTER);
        centerPanel             .setLayout      (new FlowLayout());
        centerPanel             .setBackground  (DTheme.GUI_NTL.BCK_N);

    }




    /**
     * Action after a trigger from any event listener
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        // Actions according to the source
        if (e.getSource() == backButton) {

            // Getting back to the previous screen
            gui.switchUIPrevious();
            return;


        } else if (e.getSource() == backButton) {

            return;


        }

        // Default action
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }

}
