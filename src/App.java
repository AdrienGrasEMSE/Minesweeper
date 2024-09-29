// Import
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Random;
import javax.swing.*;



/**
 * Deminer controller
 * 
 * @author  AdrienG
 * @version 0.0
 */
public class App extends JFrame {

    /**
     * Application attribute
     */
    private         Level       gameLevel   = Level.EASY;
    private final   Random      random      = new Random();
    private final   Minefield   field       = new Minefield();
    private final   GUI         gui;


    /**
     * Game attribute
     */
    // private int     sqRevealed  = 0;
    // private int     winScore    = 0;




    // Récupérer la taille de l'écran
    private final   Toolkit     toolkit         = Toolkit.getDefaultToolkit();
    private final   Dimension   screenSize      = toolkit.getScreenSize();
    private final   int         SCREENWIDTH     = screenSize.width;
    private final   int         SCREENHEIGHT    = screenSize.height;




    /**
     * Constructor
     */
    public App() {

        // Setting up global style
        ImageIcon   logoIcon        = new ImageIcon("./img/mine.png");
        Image       logo            = logoIcon.getImage();
        this        .setIconImage   (logo);
        this        .setName        ("AdrienG's deminer");
        this        .setTitle       ("AdrienG's deminer");


        // Setting up the GUI and adding it up to the Frame
        this.gui = new GUI(this, field);
        this.setContentPane(gui);


        // Calculing size after its content and setting window visible
        this.setSize            (new Dimension(SCREENWIDTH / 3, SCREENHEIGHT / 2));
        this.setPreferredSize   (new Dimension(SCREENWIDTH / 3, SCREENHEIGHT / 2));
        this.setVisible         (true);


        // Setting up action on click on the close button
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

    }





    /**
     * Init method
     */
    private void init() {

        // Creating a new classic (random difficulty) game
        int nb = random.nextInt(2);
        switch (nb) {
            case 0 -> gameLevel = Level.EASY;
            case 1 -> gameLevel = Level.MEDIUM;
            case 2 -> gameLevel = Level.HARD;
        }
        newClassicGame();
    }




    /**
     * getLevel : getter for the current game difficulty
     * 
     * @return the selected difficulty
     */
    public Level getLevel() {
        return gameLevel;
    }




    /**
     * Setter for gameLevel
     */
    public void setLevel(Level newLevel) {
        gameLevel = newLevel;
    }




    /**
     * Quit method : exit the application
     */
    public void quit() {
        System.exit(0);
    }




     /**
     * Creating a new game with a classic difficulty
     */
    public void newClassicGame() {

        // Regenerate the field
        field.newClassicEmptyField(gameLevel);

        // Reset attributes
        // sqRevealed  = 0;
        // winScore    = field.getLenght() * field.getWidth() - field.getNumberOfMine();

        // Refresh display
        gui.displayField();

    }




    /**
     * Creating a new game with a classic difficulty
     */
    public void newCustomGame(int customLenght, int customWidth, int customNbMines) {

        // Regenerate a new field
        field.newCustomEmptyField(gameLevel, customLenght, customWidth, customNbMines);

        // Reset attributes
        // sqRevealed  = 0;
        // winScore    = field.getLenght() * field.getWidth() - field.getNumberOfMine();

        // Refresh display
        gui.displayField();

    }




    /**
     * Fonction main
     * 
     * @param   args        not used
     * @throws  Exception   it's not my problem
     */
    public static void main(String[] args) throws Exception {

        // Starting point
        System.out.println("Starting point");


        // ??
        App app = new App();
        app.init();


        // DeminerDialogCustomNewGame test = new DeminerDialogCustomNewGame(app);
        // test.setVisible(true);


        // Enfing point
        System.out.println("Ending point");

    }
}
 