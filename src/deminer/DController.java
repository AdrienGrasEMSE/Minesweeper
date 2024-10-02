// Package declaration
package deminer;

// Import
import java.awt.event.ActionEvent;
import java.util.Random;
import javax.swing.Timer;
import ddialog.EndGame;
import dgui.DGUI;


/**
 * Deminer controller
 * 
 * @author  AdrienG
 * @version 0.0
 */
public class DController {

    /**
     * Controller main attributes
     */
    private final   DGUI         gui;


    /**
     * Application attribute
     */
    private final   DMinefield  field       = new DMinefield();
    private         DLevel      gameLevel   = DLevel.EASY;
    private         DSprite[][] spriteMesh ;
    private         DCounter    counter;


    /**
     * Game attribute
     */
    private int     sqRevealed  = 0;
    private int     score       = 0;
    private int     winScore    = 0;
    private int     timeSpent   = 0;
    private int     timeLimit   = 0;




    /**
     * Constructor
     */
    public DController() {

        // Setting up the GUI
        this.gui    = new DGUI(this);

    }




    /**
     * Init method
     */
    public void init() {
        
        // Creating a new classic (random difficulty) game
        Random  random  = new Random();
        int     nb      = random.nextInt(2);
        switch (nb) {
            case 0 -> gameLevel = DLevel.EASY;
            case 1 -> gameLevel = DLevel.MEDIUM;
            case 2 -> gameLevel = DLevel.HARD;
        }


        // Update displayed level and start a new classic game
        gui.updateLevel(false);
        newClassicGame();
        gui.setSizeAdaptation(true);

    }




    /**
     * getLevel : getter for the current game difficulty
     * 
     * @return the selected difficulty
     */
    public DLevel getLevel() {
        return gameLevel;
    }




    /**
     * Setter for gameLevel
     */
    public void setLevel(DLevel newLevel) {
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
        

        // Lauching the new game
        this    .newGame();

    }




    /**
     * Creating a new game with a classic difficulty
     */
    public void newCustomGame(int customLenght, int customWidth, int customNbMines) {

        // Regenerate a new field
        field   .newCustomEmptyField(gameLevel, customLenght, customWidth, customNbMines);


        // Lauching the new game
        this    .newGame();

    }




    /**
     * Lauching the new game
     */
    private void newGame() {

        // Regenerate the field
        this    .meshInit();


        // Reset attributes
        this.sqRevealed = 0;
        this.score      = 0;
        this.winScore   = field.getLenght() * field.getWidth() - field.getNumberOfMine();
        this.timeSpent  = 0;
        this.timeLimit  = field.getTimeLimit();


        // Refresh display
        gui.displayMesh     ();
        gui.updateTimeLimit (this.timeLimit);
        gui.updateTime      (this.timeSpent);

    }




    /**
     * Sprite mesh initialization
     */
    private void meshInit() {

        // Generate the sprite mesh
        spriteMesh = new DSprite[field.getLenght()][field.getWidth()];
        for (int posX = 0; posX < field.getLenght(); posX ++) {
            for (int posY = 0; posY < field.getWidth(); posY ++) {

                // Setting up content
                spriteMesh[posX][posY] = new DSprite(this, posX, posY, 50);

            }
            
        }


        // Transmit the sprite mesh to the GUI
        gui.setSpriteMesh(spriteMesh);

    }




    /**
     * Method to take into account a mouse click on a certain sprite
     * 
     * @param posX
     * @param posy
     */
    public void clickEvent(int posX, int posY) {

        // In case it's the first click
        if (sqRevealed == 0) {

            // Setting up field
            field.fillField(posX, posY);


            // Modifying sprite
            for (int posX_ = 0; posX_ < field.getLenght(); posX_ ++) {
                for (int posY_ = 0; posY_ < field.getWidth(); posY_ ++) {
                    
                    // If it's a mine or a coefficient
                    if (field.isMine(posX_, posY_)) {
                        spriteMesh[posX_][posY_].setCoefficient(-1);
                    } else {
                        spriteMesh[posX_][posY_].setCoefficient(field.mineDetection(posX_, posY_));
                    }

                }

            }

            // Start counter
            counter = new DCounter(this);

        }


        // Reveal the sprite if it's not a mine
        if (!field.isMine(posX, posY)) {

            // Reveal and propagate
            propagation(posX, posY);


        } else {

            // Stop counter
            counter.stop();


            // Reveal all and start loose phase
            revealAll();


            // Little pause to see the mine field
            Timer timer = new Timer(1000, (ActionEvent e) -> {

                // End game phase
                gui.endGamePhase(EndGame.MINES_CLIKED);
                ((Timer) e.getSource()).stop();

            });
            timer.setRepeats(false);
            timer.start();

        }

    }




    /**
     * Reval the sprite in posX, posY and propagate
     * 
     * @param posX
     * @param posY
     */
    private void propagation(int posX, int posY) {

        // Reveal the sprite
        spriteMesh[posX][posY]  .reveal();
        sqRevealed              ++;
        score                   += timeLimit - timeSpent;
        gui                     .updateScore(score);


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
                            if (spriteMesh[testX][testY].isSpriteRevealed() == false &&
                                spriteMesh[testX][testY].isSpriteLocked()   == false) {
                                propagation(testX, testY);
                            }
                        
                    }

                }
            }
        }


        // Win condition
        if (sqRevealed == winScore) {

            // Stop counter
            counter.stop();


            // Reveal all and start loose phase
            revealAll();


            // Little pause to see the mine field
            Timer timer = new Timer(1000, (ActionEvent e) -> {

                // End game phase
                gui.endGamePhase(EndGame.WIN);
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
                spriteMesh[posX][posY].reveal();

            }

        }

    }




    /**
     * Increment the time spent in a game
     */
    public void incrTimeSpent() {

        // Updating time
        this.timeSpent ++;
        gui.updateTime(timeSpent);


        // Checking the time limit
        if (this.timeSpent == this.timeLimit) {
            
            // Stop counter
            counter.stop();


            // Reveal all and start loose phase
            revealAll();


            // Little pause to see the mine field
            Timer timer = new Timer(1000, (ActionEvent e) -> {

                // End game phase
                gui.endGamePhase(EndGame.MAX_TIME_REACHED);
                ((Timer) e.getSource()).stop();

            });
            timer.setRepeats(false);
            timer.start();

        }

    }
    
}
 