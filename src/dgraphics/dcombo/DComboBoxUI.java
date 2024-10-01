// Package declaration
package deminer_graphic.combo_box;

// Import
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicComboBoxUI;


/**
 * Class to make a better ComboBoxUI
 * 
 * @author  AdrienG
 * @version 0.0
 * 
 * 
 * Allow the DeminerComboBox to have :
 * - the arrow button with dynamic color change on mouse action
 */
public class DComboBoxUI extends BasicComboBoxUI{

    /**
     * Graphical attributes
     */
    private final Color       fontDefaultColor;
    private final Color       fontActiveColor;
    private final Color       boxDefaultColor;
    private final Color       boxFlyoverColor;
    private final Color       boxActiveColor;
    private final int         fontSize;




    /**
     * Constructor
     * 
     * @param fontDefaultColor
     * @param fontSize         font size
     * @param fontDefaultColor font default color
     * @param fontActiveColor  font color on click
     * @param boxDefaultColor  default color
     * @param boxFlyoverColor  color on flyover
     * @param boxActiveColor   color on click
     */
    public DComboBoxUI(int fontSize, Color fontDefaultColor, Color fontActiveColor, Color boxDefaultColor, Color boxFlyoverColor, Color boxActiveColor) {

        // Herited constructor
        super();


        // Getting attributes
        this.fontSize           = fontSize;
        this.fontDefaultColor   = fontDefaultColor;
        this.fontActiveColor    = fontActiveColor;
        this.boxDefaultColor    = boxDefaultColor;
        this.boxFlyoverColor    = boxFlyoverColor;
        this.boxActiveColor     = boxActiveColor;
    }





    /**
     * Arrow Button Creation
     */
    @Override
    protected JButton createArrowButton() {

        // Create a custom component for the arrow button
        JButton button = new JButton() {

            /**
             * Repainting the component
             */
            @Override
            protected void paintComponent(Graphics g) {

                // Use Graphics2D for anti-aliasing
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


                // Set background color based on the current state
                Color backgroundColor;
                Color arrowColor;


                // Set background color based on the current state
                if (getModel().isPressed()) {

                    // On click
                    backgroundColor = boxActiveColor;
                    arrowColor      = fontActiveColor;


                } else if (getModel().isRollover()) {

                    // On flyover
                    backgroundColor = boxFlyoverColor;
                    arrowColor      = fontDefaultColor;


                } else {

                    // Default
                    backgroundColor = boxDefaultColor;
                    arrowColor      = fontDefaultColor;
                }


                // Draw the button background
                g2d.setColor(backgroundColor);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), getHeight() / 4, getHeight() / 4);


                // Applying the font color
                this.setForeground(arrowColor);


                // Repainting the component
                super.paintComponent(g2d);
            }
        };


        // Set button properties
        button.setText              ("▼");
        button.setFont              (new Font("Arial", Font.BOLD, fontSize));
        button.setBorder            (BorderFactory.createEmptyBorder());
        button.setContentAreaFilled (false);
        button.setFocusable         (false);


        // Returning the button
        return button;
    }




    /**
     * Painting the component
     */
    @Override
    public void paint(Graphics g, JComponent c) {

        // Herited method
        super.paint(g, c);


        // Use Graphics2D to get anti-aliasing
        Graphics2D g2d = (Graphics2D) g.create();


        // Acivate aliasing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


        // Painting the button with cornered angle
        g2d.setColor      (boxDefaultColor);
        g2d.fillRoundRect (0, 0, c.getWidth(), c.getHeight(), c.getHeight() / 4, c.getHeight() / 4);
        g2d.dispose();
    }

}
