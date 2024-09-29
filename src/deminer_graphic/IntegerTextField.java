// Package déclaration
package deminer_graphic;


// Import
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;


/**
 * Class Text Field, created in order take only integer
 * 
 * @author  AdrienG
 * @version 0.0
 */
public class IntegerTextField extends JTextField {

    // Constructeur de la classe
    public IntegerTextField(int columns) {
        super(columns);  // Appelle le constructeur parent de JTextField avec un nombre de colonnes
        init();
    }

    // Méthode d'initialisation pour restreindre la saisie aux entiers
    private void init() {
        // Ajoute un KeyAdapter pour gérer les événements de frappe au clavier
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();

                // Si le caractère tapé n'est pas un chiffre ou un caractère de contrôle (comme backspace)
                if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE) {
                    // Empêche la saisie du caractère
                    e.consume();
                }
            }
        });
    }

    // Méthode pour récupérer la valeur sous forme d'entier
    public int getIntegerValue() {
        String text = getText();
        // Si le champ est vide, retourner 0 (ou une autre valeur par défaut)
        if (text.isEmpty()) {
            return 0;
        }
        // Retourner la valeur entière
        return Integer.parseInt(text);
    }

    // Méthode pour définir une valeur entière dans le champ de texte
    public void setIntegerValue(int value) {
        setText(String.valueOf(value));
    }

    public static void main(String[] args) {
        // Exemple d'utilisation de IntegerTextField dans un JFrame
        JFrame frame = new JFrame("Test IntegerTextField");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 100);

        // Créer un IntegerTextField qui n'accepte que des entiers
        IntegerTextField integerTextField = new IntegerTextField(10);

        // Ajouter le IntegerTextField à la fenêtre
        frame.add(integerTextField);
        
        frame.setVisible(true);
    }
}

