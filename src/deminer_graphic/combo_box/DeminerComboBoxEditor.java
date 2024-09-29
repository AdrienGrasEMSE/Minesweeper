// Package declaration
package deminer_graphic.combo_box;


// Import
import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.plaf.basic.BasicComboBoxEditor;


/**
 * Class to make a better ComboBoxEditor
 * 
 * @author  AdrienG
 * @version 0.0
 */
public class DeminerComboBoxEditor extends BasicComboBoxEditor {

    /**
     * Constructor
     * 
     * @param fontActiveColor color on click
     */
    public DeminerComboBoxEditor(Color fontActiveColor) {

        // Initialize the text field
        editor.setBorder        (BorderFactory.createEmptyBorder(0, 10, 0, 10));
        editor.setOpaque        (false);
        editor.setForeground    (fontActiveColor);


        // Locking it
        editor.setEditable(false);

    }




    /**
     * Getter : to get the JTextField that display the selected item
     * 
     * @return editor : the JTextField that display the selected item
     */
    @Override
    public Component getEditorComponent() {
        return editor;
    }




    /**
     * Setter : to change the selected item
     */
    @Override
    public void setItem(Object anObject) {

        // Security check : in cas of null object
        if (anObject != null) {
            editor.setText(anObject.toString());
        } else {
            editor.setText("");
        }
    }
}
