// Package declaration
package dgraphics;

// Import
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import dgraphics.dtheme.DColors_LAB;


/**
 * Class Label, created in order to simplify style application
 * 
 * @author  AdrienG
 * @version 0.0
 * 
 * 
 * This label act like a normal JLabel but has :
 * - Custom font from DeminerFont
 * - Custom font size
 * - Custom font color
 */
public class DLabel extends JLabel{

    /**
     * Attributes
     */
    private final Color     backgroudColor;


    /**
     * Constructor with a background color
     * 
     * @param text          Label's text
     * @param selectedFont  Selected font from DeminerFont
     * @param fontSize      Font size
     * @param fontColor     Font color
     */
    public DLabel(String text, DFont selectedFont, int fontSize, DColors_LAB colorSet) {

        // Herited constructor
        super(text);


        // Inner border
        this.setBorder(new EmptyBorder(0, 10, 0, 10));


        // Setting font
        try {

            // Getting localy installed font
            Font font = Font.createFont(Font.TRUETYPE_FONT, new File(selectedFont.getFontPath()));
            this.setFont(font.deriveFont((float) fontSize));


        } catch (FontFormatException | IOException | NullPointerException e) {
            
            // Default font
            System.out.println(e);
            this.setFont(new Font("Arial", Font.BOLD, fontSize));
        }


        // Setting font color
        this.setForeground(colorSet.FNT_N);


        // Background
        this.backgroudColor     = colorSet.BCK_N;

    }




    /**
     * Repainting the label
     */
    @Override
    protected void paintComponent(Graphics g) {

        // Use Graphics2D to get anti-aliasing
        Graphics2D g2d =    (Graphics2D) g;
        
        
        // Acivate aliasing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        

        // Painting the button with cornered angle
        g2d.setColor        (this.backgroudColor);
        g2d.fillRoundRect   (0, 0, getWidth(), getHeight(), getHeight() / 4, getHeight() / 4);
        

        // Repainting the component
        super   .paintComponent(g2d);
        g2d     .dispose();
        
    }

}
