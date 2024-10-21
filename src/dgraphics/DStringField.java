// Package declaration
package dgraphics;

// Import
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import dgraphics.dtheme.DColors_FLD;


/**
 * Class Text Field, created in order to simplify style
 * 
 * @author  AdrienG
 * @version 0.0
 * 
 * 
 * This Text field act like a normal one except it has :
 * - Custom font from DeminerFont
 * - Custom font size
 * - Custom font color
 * - Dynamic background color change (mouse flyover)
 */
public class DStringField extends JTextField implements MouseListener {
    
    /**
     * Attributes
     */
    private final Color defaultColor;
    private final Color flyoverColor;

    


    /**
     * Constructor
     * 
     * @param selectedFont  selected font from DeminerFont
     * @param fontSize      font size
     * @param fontColor     Font color
     * @param defaultColor  Default background color
     * @param flyoverColor  Fylover background color
     */
    public DStringField(DFont selectedFont, int fontSize, DColors_FLD colorSet) {

        // Herited constructor
        super();


        // Inner border
        this.setBorder(new EmptyBorder(0, 10, 0, 10));


        // Setting font
        try {

            // Getting localy installed font
            Font font = Font.createFont(Font.TRUETYPE_FONT, new File(selectedFont.getFontPath()));
            this.setFont(font.deriveFont((float) fontSize));


        } catch (FontFormatException | IOException | NullPointerException  e) {
            
            // Default font
            this.setFont(new Font("Serif", Font.PLAIN, fontSize));

        }


        // Getting attributes
        this.defaultColor = colorSet.BCK_D;
        this.flyoverColor = colorSet.BCK_L;


        // Applying colors
        this.setForeground(colorSet.FNT_N);
        this.setBackground(defaultColor);


        // Mouslistener
        this.initListener();

    }




    /**
     * Init listener method
     */
    private void initListener() {
        this.addMouseListener(this);
    }




    /**
     * Mouse Pressed on the field
     */  
    @Override 
    public void mousePressed (MouseEvent e) {
        // Nothing
    }
    

    
    
    /**
     * Mouse Clicked on the field
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        // Nothing
    }




    /**
     * Mouse Released on the field
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        // Nothing
    }




    /**
     * Mouse enter the button
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        this.setBackground(flyoverColor);
    }




    /**
     * Mouse leave the button
     */
    @Override
    public void mouseExited(MouseEvent e) {
        this.setBackground(defaultColor);
    }

}
