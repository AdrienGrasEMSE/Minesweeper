/**
 * ENUM : to select the difficulty
 */
public enum Level {

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
    Level(int nbLevel) {
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
