// Import
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.Timer;



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
    private         Square[][]  squareMesh ;
    private final   GUI         gui;


    /**
     * Game attribute
     */
    private int     sqRevealed  = 0;
    private int     winScore    = 0;




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
        this.gui = new GUI(this);
        this.setContentPane(gui);


        // Calculing size after its content and setting window visible
        this.setSize            (new Dimension(SCREENWIDTH / 3, SCREENHEIGHT / 2));
        this.setPreferredSize   (new Dimension(SCREENWIDTH / 3, SCREENHEIGHT / 2));
        this.setMinimumSize     (new Dimension(SCREENWIDTH / 3, SCREENHEIGHT / 2));
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


        // Update displayed level and start a new classic game
        gui.updateLevel();
        newClassicGame();
        gui.setSizeAdaptation(true);

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
        field   .newClassicEmptyField(gameLevel);
        this    .meshInit();


        // Reset attributes
        sqRevealed  = 0;
        winScore    = field.getLenght() * field.getWidth() - field.getNumberOfMine();


        // Refresh display
        gui.displayMesh();

    }




    /**
     * Creating a new game with a classic difficulty
     */
    public void newCustomGame(int customLenght, int customWidth, int customNbMines) {

        // Regenerate a new field
        field   .newCustomEmptyField(gameLevel, customLenght, customWidth, customNbMines);
        this    .meshInit();


        // Reset attributes
        sqRevealed  = 0;
        winScore    = field.getLenght() * field.getWidth() - field.getNumberOfMine();


        // Refresh display
        gui.displayMesh();

    }




    /**
     * Square mesh initialization
     */
    private void meshInit() {

        // Generate the square mesh
        squareMesh = new Square[field.getLenght()][field.getWidth()];
        for (int posX = 0; posX < field.getLenght(); posX ++) {
            for (int posY = 0; posY < field.getWidth(); posY ++) {

                // Setting up content
                squareMesh[posX][posY] = new Square(this, posX, posY, 50);

            }
            
        }


        // Transmit the square mesh to the GUI
        gui.setSquareMesh(squareMesh);

    }




    /**
     * Method to take into account a mouse click on a certain square
     * 
     * @param posX
     * @param posy
     */
    public void clickEvent(int posX, int posY) {

        // In case it's the first click
        if (sqRevealed == 0) {

            // Setting up field
            field.initField(posX, posY);


            // Modifying square
            for (int posX_ = 0; posX_ < field.getLenght(); posX_ ++) {
                for (int posY_ = 0; posY_ < field.getWidth(); posY_ ++) {
                    
                    // If it's a mine or a coefficient
                    if (field.isMine(posX_, posY_)) {
                        squareMesh[posX_][posY_].setCoefficient(-1);
                    } else {
                        squareMesh[posX_][posY_].setCoefficient(field.mineDetection(posX_, posY_));
                    }

                }

            }

        }


        // Reveal the square if it's not a mine
        if (!field.isMine(posX, posY)) {

            // Reveal and propagate
            propagation(posX, posY);


        } else {

            // Reveal all and start loose phase
            revealAll();


            // Little pause to see the mine field
            Timer timer = new Timer(1000, (ActionEvent e) -> {

                // End game phase
                gui.endGamePhase(false);
                ((Timer) e.getSource()).stop();

            });
            timer.setRepeats(false);
            timer.start();

        }

    }




    /**
     * Reval the square in posX, posY and propagate
     * 
     * @param posX
     * @param posY
     */
    private void propagation(int posX, int posY) {

        // Reveal the square
        squareMesh[posX][posY].reveal();
        sqRevealed ++;
        gui.updateScore(sqRevealed);


        // Stop condition
        if (field.mineDetection(posX, posY) == 0) {

            // Each X possibilities (X-1 / X / X+1)
            for (int testX = posX - 1; testX <= posX + 1; testX ++) {

                // Each Y possibilities (Y-1 / Y / Y+1)
                for (int testY = posY - 1; testY <= posY + 1; testY ++) {

                    // If the new coordinate are valid
                    if (testX > -1     && testX < field.getLenght() &&
                        testY > -1     && testY < field.getWidth()  &&
                        (testX != posX || testY != posY)) {

                            // If we get here, we need to display it
                            if (squareMesh[testX][testY].isSquareRevealed() == false &&
                                squareMesh[testX][testY].isSquareLocked()   == false) {
                                propagation(testX, testY);
                            }
                        
                    }

                }
            }
        }


        // Win condition
        if (sqRevealed == winScore) {

            // Reveal all and start loose phase
            revealAll();


            // Little pause to see the mine field
            Timer timer = new Timer(1000, (ActionEvent e) -> {

                // End game phase
                gui.endGamePhase(true);
                ((Timer) e.getSource()).stop();

            });
            timer.setRepeats(false);
            timer.start();

        }

    }




    /**
     * Rveal the entire mesh
     */
    private void revealAll() {

        // Rveal the entire mesh
        for (int posX = 0; posX < field.getLenght(); posX ++) {
            for (int posY = 0; posY < field.getWidth(); posY ++) {

                // Setting up content
                squareMesh[posX][posY].reveal();

            }

        }

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


        // Theme application
        // DTheme.highlightCoefficient();


        // App instanciation
        App app = new App();
        app.init();

        // DeminerDialogInfo test = new DeminerDialogInfo(app, "Invalid parameters", new String[]{"<html><span>&#128681;</span></html>"});
        // test.setVisible(true);


        // Enfing point
        System.out.println("Ending point");

    }
}
 