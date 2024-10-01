// Package déclaration
package deminer_graphic;

// Import
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


/**
 * Class Text Field, created in order take only integer
 * 
 * @author  AdrienG
 * @version 0.0
 * 
 * 
 * Field that act like a normal DeminerStringField but can only accpect integer
 */
public class DIntegerField extends DStringField {

    /**
     * Constructor
     * 
     * @param selectedFont  selected font from DeminerFont
     * @param fontSize      font size
     * @param fontColor     Font color
     * @param defaultColor  Default background color
     * @param flyoverColor  Fylover background color
     */
    public DIntegerField(DFont selectedFont, int fontSize, Color fontColor, Color defaultColor, Color flyoverColor) {

        // Herited constructor
        super(selectedFont, fontSize, fontColor, defaultColor, flyoverColor);
        init();
    }



    
    /**
     * Init method that limit this field to integer
     */
    private void init() {

        // KeyAdapter to read keyboard input
        addKeyListener(new KeyAdapter() {

            // On key input
            @Override
            public void keyTyped(KeyEvent e) {

                // If the char is not an integer or a delete input
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE) {

                    // Prevents the character from being entered
                    e.consume();
                }
            }
        });
    }




    /**
     * Methode that replace the classic JTextField.getText()
     * 
     * @return parsed text to the type int
     */
    public int getIntegerValue() {

        // Getting the input text
        String text = getText();
        

        // Security check
        if (text.isEmpty()) {
            return 0;
        }

        
        // Parsing the text into int
        return Integer.parseInt(text);
    }




    /**
     * Methode that replace the classic JTextField.setText(String)
     * 
     * Allow the user tho set the content to a int type
     */
    public void setIntegerValue(int value) {
        setText(String.valueOf(value));
    }

}

