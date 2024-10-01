// Import
import java.util.Random;



/**
 * Minefield class
 * 
 * @author  AdrienG
 * @version 0.0
 * 
 * 
 */
public class Minefield {

    /**
     * Minefield attributes
     */
    private                 int         numberOfMine;
    private                 boolean[][] field;
    private                 int         timeLimit;
    private final           Random      random          = new Random();
    private final static    int[]       tabSize         = { 5,      10,     15};
    private final static    int[]       tabNbMines      = { 3,      9,      27};
    private final static    int[]       tabTimeLimit    = { 60,     300,    600};




    /**
     * Constructor
     */
    public Minefield() {
    
    }




    /**
     * Reseting for a classic difficulty game, without plotting mines
     */
    public int newClassicEmptyField(Level selectedLevel) {

        // Creation according to the selected difficulty
        int nb = selectedLevel.getNbLevel();
        if (nb != 3) {

            // Creating for a common difficulty game
            timeLimit       = tabTimeLimit[nb];
            numberOfMine    = tabNbMines[nb];
            field           = new boolean [tabSize[nb]][tabSize[nb]];
            return 0;

        } else {

            // Different args required
            return -1;

        }
    
    }




    /**
     * Reseting for a custom difficulty game, without plotting mines
     * 
     * @param fieldLenght   lenght of the field
     * @param fieldWidth    width of the field
     */
    public int newCustomEmptyField(Level selectedLevel, int fieldLenght, int fieldWidth, int numberOfMineRequired) {

        // Creation according to the selected difficulty
        int nb = selectedLevel.getNbLevel();
        if (nb == 3) {

            // Creating for a custom difficulty game
            timeLimit       = 0;
            numberOfMine    = numberOfMineRequired;
            field           = new boolean [fieldLenght][fieldWidth];
            return 0;

        } else {

            // Not what this function is all about
            return -1;

        }

    }




    /**
     * getLenght : getter for the lenght of the field
     * 
     * @return Lenght of the field
     */
    public int getLenght() {
        
        // Value
        return field.length;
    }




    /**
     * getWidth : getter for the width of the field
     * 
     * @return Width of the field
     */
    public int getWidth() {

        // Value
        return field[0].length;
    }




    /**
     * getNumberOfMine : getter for the number of mine
     * 
     * @return Number of mine in the field
     */
    public int getNumberOfMine() {
        return numberOfMine;
    }




    /**
     * Getter : to check the time limit
     * 
     * @return timeLimit
     */
    public int getTimeLimit() {
        return timeLimit;
    }




    /**
     * fillingField : to fill the minefield with mines, avoiding the start point
     * 
     * @param startX
     * @param startY
     */
    public void fillField(int startX, int startY) {

        // Looping until there is no mines left
        int minesLeft = numberOfMine;
        while (minesLeft != 0) {

            // Filling the field
            int x = random.nextInt(field.length);
            int y = random.nextInt(field[0].length);
            if (!field[x][y] && (x != startX || y != startY)) {

                // Plotting the mine
                field[x][y] = true;

                // Decrementing
                minesLeft --;
            }

        }
    }




    /**
     * isMine : check if there is a mine at coordinate (posX, posY)
     * 
     * @param   posX    X coordinate
     * @param   posY    Y coordinate
     * 
     * @return Is there a mine in (posX, posY) ?
     */
    public boolean isMine(int posX, int posY) {

        // Value
        return field[posX][posY];
    }




    /**
     * mineDetection : count the number of mines around a coordinate (posX, posY)
     * 
     * @param   posX    X coordinate
     * @param   posY    Y coordinate
     * 
     * @return How many mines are around (posX, posY) ?
     */
    public int mineDetection(int posX, int posY) {

        // Number of mines detected
        int nbMines = 0;

        // Each X possibilities (X-1 / X / X+1)
        for (int testX = posX - 1; testX <= posX + 1; testX ++) {

            // Each Y possibilities (Y-1 / Y / Y+1)
            for (int testY = posY - 1; testY <= posY + 1; testY ++) {

                // If there is a Mine : count + 1
                if (testX != -1 && testX != field.length    &&
                    testY != -1 && testY != field[0].length &&
                    field[testX][testY]) {
                    nbMines ++;
                }

            }
        }


        return nbMines;
    }




    /**
     * displayMines : Display the mines and the coefficients
     * 
     * DEBUG METHOD
     */
    public void displayMines() {

        // Crossing the field in its width
        for (int posX = 0; posX < field.length; posX ++) {

            // Crossing the field in its lenght
            for (int posY = 0; posY < field[0].length; posY ++) {

                // Display the mine or the coefficient
                if (isMine(posX, posY)) {

                    // Display the mine
                    System.out.print("# ");

                } else {

                    // Display the coefficient
                    System.out.print(mineDetection(posX, posY) + " ");
    
                }
                
            }

            // Line break
            System.out.println();

        }

    }




    /**
     * displayPrettyMines : Display the mines and the coefficients with prettiness
     * 
     * DEBUG METHOD
     */
    public void displayPrettyMines() {

        // Security
        if (this != null) {
    
            // Printing parameters
            System.out.println("Size : " + field.length + " x " + field[0].length);
            System.out.println("Number of mines : " + numberOfMine);

            // Crossing the field in its width
            for (int posX = 0; posX < field.length + 2; posX ++) {

                // Crossing the field in its lenght
                for (int posY = 0; posY < field[0].length + 2; posY ++) {

                    // Printing things
                    if ((posX == 0                  && posY == field[0].length + 1) ||
                        (posX == 0                  && posY == 0)                   ||
                        (posX == field.length + 1   && posY == field[0].length + 1) ||
                        (posX == field.length + 1   && posY == 0)) {

                        // Display the Corner
                        System.out.print("# ");

                    } else if (posX == 0 || posX == field.length + 1) {

                        // Display the Honrizontal Border
                        System.out.print("= ");

                    } else if (posY == 0 || posY == field[0].length + 1) {

                        // Display the Vertical Border
                        System.out.print("| ");
                        
                    } else if (isMine(posX - 1, posY - 1)) {

                        // Display the mine
                        System.out.print("# ");

                    } else {

                        // Display the coefficient
                        int nbMines = mineDetection(posX - 1, posY - 1);
                        if (nbMines > 0) {
                            
                            // Display the digit
                            System.out.print(nbMines + " ");

                        } else {

                            // Display nothing to clear up interface
                            System.out.print("  ");
                        }
        
                    }
                    
                }

                // Line break
                System.out.println();

            }

        }

    }

}
