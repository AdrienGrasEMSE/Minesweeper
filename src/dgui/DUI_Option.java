// Package declaration
package dgui;

// Import
import dgraphics.DButton;
import dgraphics.DFont;
import dgraphics.DLabel;
import dgraphics.dtheme.DTheme;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


/**
 * Class Option UI
 * 
 * @author  AdrienG
 * @version 0.0
 * 
 * 
 * This class represent the UI when the user goes in the option screen.
 */
public class DUI_Option extends JPanel implements ActionListener {

    /**
     * UI main objects
     */ 
    private final   DGUI        gui;


    /**
     * UI main panels
     */
    private final   JPanel      northPanel          = new JPanel();
    private final   JPanel      southPanel          = new JPanel();
    private final   JPanel      centerPanel         = new JPanel();


    /**
     * South panel elements
     */
    private final   DButton     backButton          = new DButton("Back",           DFont.JOST_SEMIBOLD, 24, DTheme.BTN_RED);
    private final   DButton     saveButton          = new DButton("Save settings",  DFont.JOST_SEMIBOLD, 24, DTheme.BTN_GRN);


    /**
     * Center panel elements
     */
    private final   DButton     darkMode            = new DButton("☾", DFont.NONE, 24, DTheme.BTN_DRK);
    private final   DButton     lightMode           = new DButton("☀", DFont.NONE, 24, DTheme.BTN_VAR);




    /**
     * Constructor
     * 
     * @param gui
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
        this                .add(northPanel,BorderLayout.NORTH);
        northPanel          .setLayout      (new FlowLayout());
        northPanel          .setBackground  (DTheme.GUI_VAR.BCK_N);


        // Creaing title label
        DLabel titleLabel   = new DLabel("Options", DFont.JOST_SEMIBOLD, 40, DTheme.LAB_NTL);
        titleLabel          .setAlignmentX(Component.CENTER_ALIGNMENT);
        northPanel          .add(titleLabel);

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
        this                    .add                (centerPanel,   BorderLayout.CENTER);
        centerPanel             .setLayout          (new FlowLayout());
        centerPanel             .setBackground      (DTheme.GUI_NTL.BCK_N);


        // Plotting panel and set VFX
        this                    .add                (centerPanel,   BorderLayout.CENTER);
        centerPanel             .setLayout          (new GridBagLayout());
        centerPanel             .setBackground      (DTheme.GUI_NTL.BCK_N);


        // Constraint creation
        GridBagConstraints gbc  = new GridBagConstraints();
        gbc.gridx               = 0;
        gbc.gridy               = 0;
        gbc.weightx             = 1.0;
        gbc.weighty             = 1.0;
        gbc.anchor              = GridBagConstraints.CENTER;


        // Centered panel
        JPanel centeredPanel    = new JPanel();
        centeredPanel           .setLayout          (new BoxLayout(centeredPanel, BoxLayout.Y_AXIS));
        centeredPanel           .setBorder          (new EmptyBorder(30, 30, 30, 30));
        centeredPanel           .setBackground      (DTheme.GUI_NTL.BCK_D);


        // Game creation panel
        JPanel darkModePanel    = new JPanel        (new GridLayout(1, 2));
        darkModePanel           .setBackground      (DTheme.GUI_NTL.BCK_D);
        darkModePanel           .add                (new DLabel("Dark mode", DFont.JOST_SEMIBOLD, 20, DTheme.LAB_TRS));
        darkModePanel           .add                (darkMode);


        // Game join panel
        JPanel lightModePanel   = new JPanel        (new GridLayout(1, 2));
        lightModePanel          .setBackground      (DTheme.GUI_NTL.BCK_D);
        lightModePanel          .add                (new DLabel("Light mode", DFont.JOST_SEMIBOLD, 20, DTheme.LAB_TRS));
        lightModePanel          .add                (lightMode);


        // Plotting panels
        centeredPanel           .add                (darkModePanel);
        centeredPanel           .add                (Box.createRigidArea(new Dimension(10, 30)));
        centeredPanel           .add                (lightModePanel);


        // Plotting the centered panel
        centerPanel             .add                (centeredPanel, gbc);


        // Poltting button
        darkMode                .addActionListener  (this);
        lightMode               .addActionListener  (this);

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


        } else if (e.getSource() == darkMode) {

            // Applying dark mode
            DTheme.default_();
            gui.updateTheme();
            return;


        } else if (e.getSource() == lightMode) {

            // Applying dark mode
            DTheme.lightMode();
            gui.updateTheme();
            return;


        }

        // Default action
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }

}
