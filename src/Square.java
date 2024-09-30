// Imports
import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;


/**
 * Square class
 * 
 * @author  AdrienG
 * @version 0.0
 * 
 * 
 */
public class Square extends JPanel implements MouseListener{


    /**
     * Main colors
     * 
     */
    private final static int DEFAULT    = 0x566573;
    private final static int FLYOVER    = 0x808B96;
    private final static int OPENED     = 0xD5D8DC;
    private final static int MINE       = 0xC0392B;
    private final static int COEF_1     = 0x2980b9;
    private final static int COEF_2     = 0x16a085;
    private final static int COEF_3     = 0x1abc9c;
    private final static int COEF_4     = 0x2ecc71;
    private final static int COEF_5     = 0xf1c40f;
    private final static int COEF_6     = 0xf39c12;
    private final static int COEF_7     = 0xe67e22;
    private final static int COEF_8     = 0xd35400;
    private final static int COEF_9     = 0xe74c3c;
    private final static int COEF_MINE  = 0xF9EBEA;




    /**
     * Attributes
     */
    private final   App     app;
    private final   int     posX;
    private final   int     posY;
    private Color   mainColor   = new Color(DEFAULT);
    private boolean isRevealed  = false;
    private int     sqWidth     = 50;                   // 50 px by default
    private int     sqHeight    = 50;                   // 50 px by default
    private int     coefficient;                        // 0 = 0 mine around        -1 = the square is a mine




    /**
     * Graphical attributes
     */
    private final   Font    font        = new Font("Arial", Font.BOLD, 24);




    /**
     * Constructor
     * 
     * TODO : setting size according containers
     */
    public Square(App app, int newPosX, int newPosY, int newSqWidth, int newSqHeight) {

        // Getting attributes
        this.app        = app;
        posX            = newPosX;
        posY            = newPosY;
        sqWidth         = newSqWidth;
        sqHeight        = newSqHeight;

        // Setting the size
        setPreferredSize(new Dimension(sqWidth, sqHeight));
        initListener();
    }




    /**
     * Listener intit
     */
    private void initListener() {
        addMouseListener(this);
    }




    /**
     * Setter
     * @param coefficient
     */
    public void setCoefficient(int coefficient) {
        this.coefficient = coefficient;
    }




    /**
     * Repainting the component
     */
    @Override
    public void paintComponent(Graphics gc) {

        // Clearing the square
        super   .paintComponent (gc);


        // Setting the new color
        gc.setColor(mainColor);
        gc.fillRect(5,5, 40,40);


        // Setting the font color if necessary
        if (isRevealed) {            

            // Setting the string and the color for the display
            gc.setFont(font);
            FontMetrics metrics = gc.getFontMetrics(font);
            switch (coefficient) {
                case -1 -> {
                    gc.setColor(new Color(COEF_MINE));
                    gc.drawString(
                            "¤",
                            (getWidth()     - metrics.stringWidth("¤")) / 2,
                            (getHeight()    - metrics.getHeight())          / 2 + metrics.getAscent()
                    );
                }

                case 1 -> {
                    gc.setColor(new Color(COEF_1));
                    gc.drawString(
                            String.valueOf(coefficient),
                            (getWidth()     - metrics.stringWidth(String.valueOf(coefficient))) / 2,
                            (getHeight()    - metrics.getHeight())                              / 2 + metrics.getAscent()
                    );
                }
                
                case 2 -> {
                    gc.setColor(new Color(COEF_2));
                    gc.drawString(
                            String.valueOf(coefficient),
                            (getWidth()     - metrics.stringWidth(String.valueOf(coefficient))) / 2,
                            (getHeight()    - metrics.getHeight())                              / 2 + metrics.getAscent()
                    );
                }

                case 3 -> {
                    gc.setColor(new Color(COEF_3));
                    gc.drawString(
                            String.valueOf(coefficient),
                            (getWidth()     - metrics.stringWidth(String.valueOf(coefficient))) / 2,
                            (getHeight()    - metrics.getHeight())                              / 2 + metrics.getAscent()
                    );
                }

                case 4 -> {
                    gc.setColor(new Color(COEF_4));
                    gc.drawString(
                            String.valueOf(coefficient),
                            (getWidth()     - metrics.stringWidth(String.valueOf(coefficient))) / 2,
                            (getHeight()    - metrics.getHeight())                              / 2 + metrics.getAscent()
                    );
                }

                case 5 -> {
                    gc.setColor(new Color(COEF_5));
                    gc.drawString(
                            String.valueOf(coefficient),
                            (getWidth()     - metrics.stringWidth(String.valueOf(coefficient))) / 2,
                            (getHeight()    - metrics.getHeight())                              / 2 + metrics.getAscent()
                    );
                }

                case 6 -> {
                    gc.setColor(new Color(COEF_6));
                    gc.drawString(
                            String.valueOf(coefficient),
                            (getWidth()     - metrics.stringWidth(String.valueOf(coefficient))) / 2,
                            (getHeight()    - metrics.getHeight())                              / 2 + metrics.getAscent()
                    );
                }

                case 7 -> {
                    gc.setColor(new Color(COEF_7));
                    gc.drawString(
                            String.valueOf(coefficient),
                            (getWidth()     - metrics.stringWidth(String.valueOf(coefficient))) / 2,
                            (getHeight()    - metrics.getHeight())                              / 2 + metrics.getAscent()
                    );
                }

                case 8 -> {
                    gc.setColor(new Color(COEF_8));
                    gc.drawString(
                            String.valueOf(coefficient),
                            (getWidth()     - metrics.stringWidth(String.valueOf(coefficient))) / 2,
                            (getHeight()    - metrics.getHeight())                              / 2 + metrics.getAscent()
                    );
                }

                case 9 -> {
                    gc.setColor(new Color(COEF_9));
                    gc.drawString(
                            String.valueOf(coefficient),
                            (getWidth()     - metrics.stringWidth(String.valueOf(coefficient))) / 2,
                            (getHeight()    - metrics.getHeight())                              / 2 + metrics.getAscent()
                    );
                }

            }

            
        }


    }




    /**
     * Reveal the square
     */
    public int reveal() {

        // Checking if the square is already revealed
        if (!isRevealed) {

            // Reveal the square
            isRevealed  = true;

            // Applying the color according to its type (mine or not)
            if (coefficient != -1) {
                mainColor   = new Color(OPENED);
            } else {
                mainColor   = new Color(MINE);
            }
            repaint();

            // Success code
            return 0;


        } else {

            // Fail code : the square is already revealed
            return -1;
        }
    }




    /**
     * Getter : to check if the square is revealed or not
     * 
     * @return isRevealed showing if the Square is revealed
     */
    public boolean isSquareRevealed() {
        return isRevealed;
    }




    /**
     * Mouse Pressed on the square
     */  
    @Override 
    public void mousePressed (MouseEvent e) {

        // If the square is not revealed yet
        if (!isRevealed) {
            app.clickEvent(posX, posY);
        }
        
    }
    

    
    
    /**
     * Mouse Clicked on the square
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        // Nothing
    }




    /**
     * Mouse Released on the square
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        // Nothing
    }




    /**
     * Mouse enter the square
     */
    @Override
    public void mouseEntered(MouseEvent e) {

        // Changing color if it's not revealed
        if (!isRevealed) {
            mainColor = new Color(FLYOVER);
            repaint(); 
        }
    }




    /**
     * Mouse leave the square
     */
    @Override
    public void mouseExited(MouseEvent e) {

        // Reseting color according to its state
        if (!isRevealed) {
            mainColor = new Color(DEFAULT);
        }
        repaint();
    }
    

}
