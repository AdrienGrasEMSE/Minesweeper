// Package declaration
package deminer_graphic;


// Import
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JTextField;



/**
 * Class Text Field, created in order to simplify style
 * 
 * @author  AdrienG
 * @version 0.0
 */
public class DeminerStringField extends JTextField {
    
    /**
     * Attributes
     */
    private Color defaultColor;
    private Color flyoverColor;

    


    /**
     * Constructor
     * 
     * @param fontColor     Font color
     * @param defaultColor  Default background color
     * @param flyoverColor  Fylover background color
     */
    public DeminerStringField(int fontSize, Color fontColor, Color defaultColor, Color flyoverColor) {

        // Herited constructor
        super();


        // Appliquer la police personnalisée
        this.setFont(new Font("Serif", Font.BOLD, fontSize));


        // Getting attributes
        this.defaultColor = defaultColor;
        this.flyoverColor = flyoverColor;


        // Appliquer la couleur du texte
        this.setForeground(fontColor);
        this.setBackground(defaultColor);


        // Ajouter un MouseListener pour détecter le survol
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Changer la couleur de fond en cas de survol
                setBackground(flyoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Réinitialiser la couleur de fond lorsque la souris quitte
                setBackground(defaultColor);
            }
        });
    }


    

    // Méthode pour changer dynamiquement la police
    public void setCustomFont(String fontName, int fontStyle, int fontSize) {
        setFont(new Font(fontName, fontStyle, fontSize));
    }

    // Méthode pour changer dynamiquement la couleur du texte
    public void setTextColor(Color color) {
        setForeground(color);
    }

    // Méthode pour changer dynamiquement la couleur de fond
    public void setDefaultColor(Color color) {
        this.defaultColor = color;
        setBackground(color);
    }

    // Méthode pour changer la couleur de fond au survol
    public void setFlyoverColor(Color color) {
        this.flyoverColor = color;
    }

    public static void main(String[] args) {
        // Créer une fenêtre JFrame pour tester CustomTextField
        JFrame frame = new JFrame("Test CustomTextField");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new FlowLayout());

        // Créer un CustomTextField avec police et couleurs personnalisées
        DeminerStringField customTextField = new DeminerStringField(
                18,    // Police : Serif, style : gras, taille : 18
                Color.WHITE,               // Couleur du texte : blanc
                Color.DARK_GRAY,           // Couleur de fond par défaut : gris foncé
                Color.LIGHT_GRAY           // Couleur de fond au survol : gris clair
        );
        customTextField.setColumns(20);  // Largeur du champ de texte

        // Ajouter le CustomTextField à la fenêtre
        frame.add(customTextField);

        // Afficher la fenêtre
        frame.setVisible(true);
    }
}
