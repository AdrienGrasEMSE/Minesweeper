// Package declaration
package donline;


/**
 * Class Player
 * 
 * @author  AdrienG
 * @version 0.0
 * 
 * 
 * This class represent a player, only to stock data
 */
public class DPlayer {

    /**
     * Attributes
     */
    private String  uuid;
    private String  playerName;
    private int     score;
    private boolean alive;




    /**
     * =====================================================================================================================
     * 
     * Method
     * 
     * =====================================================================================================================
     */


     

    /**
     * Constructor
     */
    public DPlayer() {
        this.uuid       = "";
        this.playerName = "";
        this.score      = 0;
        this.alive      = true;
    }




    /**
     * =====================================================================================================================
     * 
     * Method : Getter
     * 
     * =====================================================================================================================
     */



    
    /**
     * Getter : to get the player UUID
     * 
     * @return
     */
    public String getUUID() {
        return this.uuid;
    }




    /**
     * Getter : to get the player name
     * 
     * @return playerName
     */
    public String getPlayerName() {
        return this.playerName;
    }
    
    
    
    
    /**
     * Getter : to get the player score
     * 
     * @return score
     */
    public int getScore() {
        return this.score;
    }




    /**
     * Getter : to check if the player is alive
     * 
     * @return alive
     */
    public boolean isAlive() {
        return this.alive;
    }



    /**
     * =====================================================================================================================
     * 
     * Method : Setter
     * 
     * =====================================================================================================================
     */




    /**
     * Setter : to change player uuid
     * 
     * @param uuid
     */
    public void setUUID(String uuid) {
        this.uuid = uuid;
    }




    /**
     * Setter : to change player name
     * 
     * @param playerName
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }




    /**
     * Setter : to change the player score
     * 
     * @param score
     */
    public void setScore(int score) {
        this.score = score;
    }




    /**
     * Setter : to change the player state
     * 
     * @param alive
     */
    public void setAlive(boolean alive) {
        this.alive = alive;
    }
    
}
