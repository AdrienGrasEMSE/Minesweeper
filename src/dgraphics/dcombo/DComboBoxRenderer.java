// Package declaration
package dgraphics.dcombo;

// Import
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import dgraphics.DFont;


/**
 * Class to make a better render for the ComboBox
 * 
 * @author  AdrienG
 * @version 0.0
 * 
 * 
 * Allow the DeminerComboBox to have the popup that has dynamic color change on mouse flyover
 */
public class DComboBoxRenderer extends DefaultListCellRenderer {

    /**
     * Graphical attributes
     */
    private final   Color   fontDefaultColor;
    private final   Color   fontActiveColor;
    private final   Color   boxDefaultColor;
    private final   Color   boxFlyoverColor;
    private final   int     fontSize;




    /**
     * Constructor
     * 
     * @param fontSize         font size
     * @param fontDefaultColor font default color
     * @param fontActiveColor  font color on click
     * @param boxDefaultColor  default color
     * @param boxFlyoverColor  color on flyover
     */
    public DComboBoxRenderer(int fontSize, Color fontDefaultColor, Color fontActiveColor, Color boxDefaultColor, Color boxFlyoverColor) {

        // Herited constructor
        super();


        // Getting attributes
        this.fontSize           = fontSize;
        this.fontDefaultColor   = fontDefaultColor;
        this.fontActiveColor    = fontActiveColor;
        this.boxDefaultColor    = boxDefaultColor;
        this.boxFlyoverColor    = boxFlyoverColor;
    }




    /**
     * Changing displayed labels for the list
     * 
     * @return label : the label of the current item in the list
     */
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

        // Setting up the label
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        label.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        label.setOpaque(true);


        // Changing background color if selected or under the mouse
        if (isSelected) {

            // Setting up color
            label.setBackground(boxFlyoverColor);
            label.setForeground(fontActiveColor);


            // Setting up font
            try {

                // Getting localy installed font
                Font                font            = Font.createFont(Font.TRUETYPE_FONT, new File(DFont.JOST_SEMIBOLD.getFontPath()));
                this.setFont        (font.deriveFont((float) fontSize));


            } catch (FontFormatException | IOException | NullPointerException e) {
                
                // Default font
                System.out.println(e);
                this.setFont(new Font("Serif", Font.PLAIN, fontSize));

            }


        }
        else {

            // Setting up color
            label.setBackground(boxDefaultColor);
            label.setForeground(fontDefaultColor);


            // Setting up font
            try {

                // Getting localy installed font
                Font                font            = Font.createFont(Font.TRUETYPE_FONT, new File(DFont.JOST_REGULAR.getFontPath()));
                this.setFont        (font.deriveFont((float) fontSize));


            } catch (FontFormatException | IOException | NullPointerException e) {
                
                // Default font
                System.out.println(e);
                this.setFont(new Font("Serif", Font.PLAIN, fontSize));

            }
        }


        // Returning the label
        return label;

    }

}
