// Package declaration
package deminer;

// Import
import ddialog.EndGame;
import dgui.DGUI;
import donline.DClient;
import donline.DInterpreter;
import donline.DPlayer;
import donline.DRequestType;
import donline.dserver.DServer;
import java.awt.event.ActionEvent;
import java.util.Map;
import java.util.Random;
import javax.swing.SwingUtilities;
import javax.swing.Timer;


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
    private         int         sqRevealed  = 0;
    private         int         score       = 0;
    private         int         winScore    = 0;
    private         int         timeSpent   = 0;
    private         int         timeLimit   = 0;


    /**
     * 
     */
    private         boolean     onlineGame  = false;
    

    /**
     * Multiplayer attributes
     */
    private         DClient     client;
    private final   DInterpreter interpreter = new DInterpreter();




    /**
     * Constructor
     */
    public DController() {

        // Setting up the GUI
        this.gui    = new DGUI(this);

    }




    /**
     * =====================================================================================================================
     * 
     * Common method
     * 
     * =====================================================================================================================
     */




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
        if (onlineGame) {
            gui.setSpriteMeshOnline(spriteMesh);
        } else {
            gui.setSpriteMeshLocal(spriteMesh);
        }

    }




    /**
     * Method to take into account a mouse click on a certain sprite
     * 
     * @param posX
     * @param posy
     */
    public void clickEvent(int posX, int posY) {

        // Action according to the game type
        if (onlineGame) {

            // Sending the event to the server only if the player can play = if it's alive
            if (client.isAlive()) {
                this.client.addRequest(interpreter.build(client.getUUID(), DRequestType.SPRITE_CLICKED, client.getUUID() + ":" + posX + "," + posY));
            }


        } else {

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

    }



    /**
     * =====================================================================================================================
     * 
     * Solo Mod
     * 
     * =====================================================================================================================
     */




    /**
     * Init method
     */
    public void init() {
        
        // Creating a new classic (random difficulty) game
        Random  random  = new Random();
        int     nb      = random.nextInt(3);
        switch (nb) {
            case 0 -> gameLevel = DLevel.EASY;
            case 1 -> gameLevel = DLevel.MEDIUM;
            case 2 -> gameLevel = DLevel.HARD;
        }


        // Update displayed level and start a new classic game
        gui.updateLevel(false);
        newClassicGame();
        gui.setSizeAdaptationLocal(true);

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
    public void newGame() {

        // Regenerate the field
        this    .meshInit();


        // If it's an online game
        if (!this.onlineGame) {

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



    /**
     * =====================================================================================================================
     * 
     * Online Mode
     * 
     * =====================================================================================================================
     */

    

    
    /**
     * Server creation
     * 
     * @param pseudo pseudo of the first client
     */
    public void newServer(String pseudo) {

        // Starting point
        System.out.println("SERVER : Starting point");


        // Starting server
        DServer server = new DServer(DUUID.generate());


        // Checking if the server is online
        if (server.isOnline()) {

            // Creation succeed
            System.out.println("SERVER : Online, UUID = " + server.getUUID());


            // Client creation
            client = new DClient(this, pseudo);
            if (client.tryConnection("localhost", 10000)) {

                // Waiting for the client to be registered (5sec max)
                int loopCounter = 0;
                while (!client.isRegistered() && loopCounter < 50) {

                    // Loop limiter
                    try {

                        // 100ms wait
                        Thread.sleep(100);

                    } catch (InterruptedException e) {

                        // Handle the exception
                        System.out.println(e);

                    }


                    // Increase loop counter
                    loopCounter ++;

                }


                // Verifying client registration
                if (loopCounter <= 50 && client.isRegistered()) {

                    // Asking server ownership to control game settings
                    client.addRequest(interpreter.build(client.getUUID(), DRequestType.OWNERSHIP_ASK, server.getUUID()));


                    // Waiting for the client to be the server owner (5sec max)
                    loopCounter = 0;
                    while (!client.isServerOwner() && loopCounter < 50) {

                        // Loop limiter
                        try {

                            // 100ms wait
                            Thread.sleep(100);

                        } catch (InterruptedException e) {

                            // Handle the exception
                            System.out.println(e);

                        }


                        // Increase loop counter
                        loopCounter ++;

                    }


                    // Verifying server ownership
                    if (loopCounter <= 50 && client.isServerOwner()){

                        // Asking the gui to display the nextstep
                        gui.gameCreated(true, "");
                        gui.setServerOwner(true);
                        onlineGame = true;


                    } else {

                        // Shutting down the server
                        server.stop();
                        gui.gameCreated(false, "Unable to take control of the server...");

                    }


                } else {

                    // Shutting down the server
                    server.stop();
                    gui.gameCreated(false, "Unable to connect to the server...");

                }


            } else {

                // Shutting down the server
                server.stop();
                gui.gameCreated(false, "Unable to connect to the server...");

            }


        } else {

            // Creation failed
            System.out.println("SERVER : Disconnected");


            // Asking the gui to inform and come back
            gui.gameCreated(false, "Unable to create server...");

        }

    }




    /**
     * Join a server on its IP
     * 
     * @param serverIP
     */
    public void joinGame(String pseudo) {

        // Client creation
        client = new DClient(this, pseudo);
        if (client.autoConnect()) {

            // Waiting for the client to be registered (5sec max)
            int loopCounter = 0;
            while (!client.isRegistered() && loopCounter < 50) {

                // Loop limiter
                try {

                    // 100ms wait
                    Thread.sleep(100);

                } catch (InterruptedException e) {

                    // Handle the exception
                    System.out.println(e);

                }


                // Increase loop counter
                loopCounter ++;

            }


            // Verifying client registration
            if (loopCounter <= 50 && client.isRegistered()) {

                // Game joinned
                gui.gameJoinned(true, "");
                gui.setServerOwner(false);
                onlineGame = true;


            } else {

                // Diconnect client
                gui.gameJoinned(false, "Unable to connect to the server...");

            }


        } else {

            // Shutting down the server
            gui.gameJoinned(false, "Unable to connect to the server...");

        }


    }




    /**
     * Updating the player list
     * 
     * @param playerList
     * @param ownerUUID
     */
    public void updatePlayerList(Map<String, DPlayer> playerList, String ownerUUID) {
        gui.updatePlayerList(playerList, ownerUUID);
    }




    /**
     * Get the client to ask to launch the game
     */
    public void launchGame() {
        this.client.addRequest(interpreter.build(client.getUUID(), DRequestType.GAME_LAUNCH_ASK));
    }




    /**
     * Creating the client mine field
     * 
     * @param fieldLenght
     * @param fieldWidth
     */
    public void initOnlineField(int fieldLenght, int fieldWidth) {
        this.field.newCustomEmptyField(DLevel.CUSTOM, fieldLenght, fieldWidth, 0);
    }


    

    /**
     * Start the new game
     */
    public void gameStart() {

        // Init the mesh
        this.meshInit();
        this.gui.setSizeAdaptationOnline(true);
        this.gui.switchIngameUI();

    }




    /**
     * Reveal a square att posX, posY
     * 
     * @param posX
     * @param posY
     */
    public void spriteReveal(int posX, int posY, int spriteValue) {
        spriteMesh[posX][posY].setCoefficient(spriteValue);
        spriteMesh[posX][posY].reveal();
    }




    /**
     * Client disconnexion
     */
    public void disconnect() {
        this.client.addRequest(interpreter.build(this.client.getUUID(), DRequestType.DISCONNECT));
        this.client.shutDown();
    }




    /**
     * Getting back to the default screen
     * 
     * @param reason
     */
    public void backDefaultOnlineUi(String reason) {

        // Switching back to the default online screen
        SwingUtilities.invokeLater(() -> {
            this.gui.switchUIOnline();
        });


        // In case of bad reason (any type of kick)
        if (!reason.isEmpty()) {
            this.gui.gameDisconnexion(reason);
        }

    }




    /**
     * Apply the parameter for the online game
     * 
     * @param length
     */
    public void applyLength(int length) {
        this.client.addRequest(interpreter.build(this.client.getUUID(), DRequestType.PARAM_FIELD_LENGTH, String.valueOf(length)));
    }




    /**
     * Apply the parameter for the online game
     * 
     * @param height
     */
    public void applyHeigth(int height) {
        this.client.addRequest(interpreter.build(this.client.getUUID(), DRequestType.PARAM_FIELD_HEIGTH, String.valueOf(height)));
    }




    /**
     * Apply the parameter for the online game
     * 
     * @param nbMine
     */
    public void applyNbMine(int nbMine) {
        this.client.addRequest(interpreter.build(this.client.getUUID(), DRequestType.PARAM_FIELD_NBMINES, String.valueOf(nbMine)));
    }




    /**
     * Apply the parameter for the online game
     * 
     * @param nbMaxPlayer
     */
    public void applyNMaxPlayer(int nbMaxPlayer) {
        this.client.addRequest(interpreter.build(this.client.getUUID(), DRequestType.PARAM_NMAX_PLAYER, String.valueOf(nbMaxPlayer)));
    }




    /**
     * Wiring
     */
    public void playerLost()        {this.gui.playerLost();}
    public void gameLost()          {this.gui.gameLost();}
    public void gameWin()           {this.gui.gameWin();}
    public void gameAborted()       {this.gui.gameAborted();}
    public void setLength           (int length)                                        {this.gui.setLength(length);}
    public void setHeigth           (int heigth)                                        {this.gui.setHeigth(heigth);}
    public void setNbMine           (int setNbMine)                                     {this.gui.setNbMine(setNbMine);}
    public void setNMaxPlayer       (int nbMaxPlayer)                                   {this.gui.setNMaxPlayer(nbMaxPlayer);}

}
 