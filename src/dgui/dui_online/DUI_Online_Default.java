// Pakcage declaration
package dgui.dui_online;

// Import
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dgraphics.DButton;
import dgraphics.DFont;
import dgraphics.DLabel;
import dgraphics.dtheme.DTheme;


/**
 * Class Default Online UI
 * 
 * @author  AdrienG
 * @version 0.0
 * 
 * 
 * This class represent the default screen when the user play in multiplayer mode.
 */
public class DUI_Online_Default extends JPanel implements ActionListener {

    /**
     * UI main objects
     */
    private final   DUI_Online  mainUI;


    /**
     * UI main panels
     */
    private final   JPanel      northPanel          = new JPanel();
    private final   JPanel      southPanel          = new JPanel();
    private final   JPanel      centerPanel         = new JPanel();


    /**
     * South panels elements
     */
    private final   DButton     backButton          = new DButton("Back",           DFont.JOST_SEMIBOLD, 24, DTheme.BTN_RED);
    private final   DButton     createGameButton    = new DButton("Create a game",  DFont.JOST_SEMIBOLD, 24, DTheme.BTN_GRN);
    private final   DButton     joinGameButton      = new DButton("Join a game",    DFont.JOST_SEMIBOLD, 24, DTheme.BTN_GRN);




    /**
     * Constructor
     * 
     * @param controller in order to transmit data or action performed
     */
    public DUI_Online_Default(DUI_Online mainUI) {

        // Getting the gui and the controller
        this.mainUI = mainUI;


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
        this                .add(northPanel,BorderLayout.NORTH);
        northPanel          .setLayout      (new FlowLayout());
        northPanel          .setBackground  (DTheme.GUI_VAR.BCK_N);

        
        // Creaing title label
        DLabel titleLabel   = new DLabel("Multiplayer", DFont.JOST_SEMIBOLD, 40, DTheme.LAB_NTL);
        titleLabel          .setAlignmentX(Component.CENTER_ALIGNMENT);
        northPanel          .add(titleLabel);

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
        southPanel          .add(backButton);


        // Setting up listeners
        backButton          .addActionListener(this);

    }




    /**
     * Setting up center panel
     */
    private void centerPanelSetup() {

        // Plotting panel and set VFX
        this                    .add            (centerPanel,   BorderLayout.CENTER);
        centerPanel             .setLayout      (new GridBagLayout());
        centerPanel             .setBackground  (DTheme.GUI_NTL.BCK_N);


        // Constraint creation
        GridBagConstraints gbc  = new GridBagConstraints();
        gbc.gridx               = 0;
        gbc.gridy               = 0;
        gbc.weightx             = 1.0;
        gbc.weighty             = 1.0;
        gbc.anchor              = GridBagConstraints.CENTER;


        // Centered panel
        JPanel centeredPanel    = new JPanel();
        centeredPanel           .setLayout      (new BoxLayout(centeredPanel, BoxLayout.Y_AXIS));
        centeredPanel           .setBorder      (new EmptyBorder(30, 30, 30, 30));
        centeredPanel           .setBackground  (DTheme.GUI_NTL.BCK_D);


        // Game creation panel
        JPanel createPanel      = new JPanel    (new FlowLayout());
        createPanel             .setBackground  (DTheme.GUI_NTL.BCK_D);
        createPanel             .add            (createGameButton);


        // Game join panel
        JPanel joinPanel        = new JPanel    (new FlowLayout());
        joinPanel               .setBackground  (DTheme.GUI_NTL.BCK_D);
        joinPanel               .add            (joinGameButton);


        // Plotting panels
        centeredPanel           .add(createPanel);
        centeredPanel           .add(Box.createRigidArea(new Dimension(10, 30)));
        centeredPanel           .add(joinPanel);


        // Plotting the centered panel
        centerPanel             .add(centeredPanel, gbc);

    }




    /**
     * Action after a trigger from any event listener
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        // Actions according to the source
        if (e.getSource() == backButton) {

            // Getting back to the previous screen
            mainUI.switchUIPrevious();
            return;


        }

        // Default action
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }

}
