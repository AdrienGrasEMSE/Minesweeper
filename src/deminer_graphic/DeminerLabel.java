// Package declaration
package deminer_graphic;

// Import
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;


/**
 * Class Label, created in order to simplify style application
 * 
 * @author  AdrienG
 * @version 0.0
 */
public class DeminerLabel extends JLabel{

    /**
     * Constructor
     */
    public DeminerLabel(String text, DeminerFont selectedFont, int fontSize, Color color) {

        // Herited constructor
        super(text);
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
        this.setForeground(color);
                

        // Définit la couleur du texte
        setForeground(color);
    }

}
