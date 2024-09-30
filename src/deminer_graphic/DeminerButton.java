// Package declaration
package deminer_graphic;

// Import
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;


/**
 * Class Button, created in order to simplify style application
 * 
 * @author  AdrienG
 * @version 0.0
 * 
 * 
 * This button act like a normal one but appears different. It has :
 * - Rounded angle
 * - Custom font (from DeminerFont)
 * - Custom font size
 * - Custom font color
 * - Background color that react to the mouse (flyover and click)
 */
public class DeminerButton extends JButton implements MouseListener{

    /**
     * Graphical attributes
     */
    private final Color colorDefault;
    private final Color colorFlyover;
    private final Color colorActive;




    /**
     * Constructor
     * 
     * @param text          button's label
     * @param selectedFont  selected font from DeminerFont
     * @param fontSize      font size
     * @param colorFont     font color
     * @param colorDefault  default color
     * @param colorFlyover  fylover color
     * @param colorActive   active color (on click)
     */
    public DeminerButton(String text, DeminerFont selectedFont, int fontSize, Color colorFont, Color colorDefault, Color colorFlyover, Color colorActive) {

        // Setting text
        this.setText(text);


        // Inner border
        this.setBorder(new EmptyBorder(0, 10, 0, 10));


        // Setting font
        try {

            // Getting localy installed font
            Font font = Font.createFont(Font.TRUETYPE_FONT, new File(selectedFont.getFontPath()));
            this.setFont(font.deriveFont((float) fontSize));


        } catch (FontFormatException | IOException | NullPointerException  e) {
            
            // Default font
            System.out.println(e);
            this.setFont(new Font("Arial", Font.BOLD, fontSize));

        }


        // Setting font color
        this.setForeground(colorFont);


        // Getting attributes
        this.colorDefault   = colorDefault;
        this.colorFlyover   = colorFlyover;
        this.colorActive    = colorActive;


        // Setting default backgroud color
        this.setBackground      (colorDefault);
        this.setOpaque          (false);
        this.setBorderPainted   (false);
        this.setFocusPainted    (false);
        this.setContentAreaFilled(false);


        // Adding the listener
        initListener();
    }




    /**
     * Init listener method
     */
    private void initListener() {
        this.addMouseListener(this);
    }




    /**
     * Repainting the button
     */
    @Override
    protected void paintComponent(Graphics g) {
        
        // Use Graphics2D to get anti-aliasing
        Graphics2D g2d =    (Graphics2D) g;
        
        
        // Acivate aliasing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        

        // Painting the button with cornered angle
        g2d.setColor        (this.getBackground());
        g2d.fillRoundRect   (0, 0, getWidth(), getHeight(), getHeight() / 4, getHeight() / 4);
        

        // Repainting the component
        super   .paintComponent(g2d);
        g2d     .dispose();
        
    }



    
    /**
     * Mouse Pressed on the button
     */  
    @Override 
    public void mousePressed (MouseEvent e) {
        this.setBackground(colorActive);
        this.repaint();
    }
    

    
    
    /**
     * Mouse Clicked on the button
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        this.setBackground(colorActive);
        this.repaint();
    }




    /**
     * Mouse Released on the button
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        this.setBackground(colorDefault);
        this.repaint();
    }




    /**
     * Mouse enter the button
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        this.setBackground(colorFlyover);
        this.repaint();
    }




    /**
     * Mouse leave the button
     */
    @Override
    public void mouseExited(MouseEvent e) {
        this.setBackground(colorDefault);
        this.repaint();
    }

}
