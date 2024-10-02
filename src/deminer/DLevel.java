// Package declaration
package deminer;


/**
 * Eum Level : to select the difficulty
 * 
 * @author  AdrienG
 * @version 0.0
 * 
 * 
 * Its only purpose is to select the game difficulty.
 */
public enum DLevel {

    /**
     * Main attribute
     */
    EASY    (0),
    MEDIUM  (1),
    HARD    (2),
    CUSTOM  (3);




    /**
     * Attribut to stock the number of the difficulty
     */
    private int nbLevel;




    /**
     * Enum constructor
     * 
     * @param nbLevel
     */
    private DLevel(int nbLevel) {
        this.nbLevel = nbLevel;
    }



    
    /**
     * Getter
     * 
     * @return int linked to the game level
     */
    public int getNbLevel() {
        return this.nbLevel;
    }

}
