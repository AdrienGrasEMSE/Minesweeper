// Package declaration
package donline.dserver;

import deminer.DLevel;
import deminer.DMinefield;
import donline.DInterpreter;
import donline.DRequestType;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Class server
 * 
 * @author  AdrienG
 * @version 0.0
 * 
 * 
 * 
 */
public class DServer implements Runnable{


    /**
     * Attributes
     */
    private final   Thread                      service;
    private final   String                      uuid;
    private final   Map<String, DClientHandler> clientList              = new HashMap<>();
    private         DClientHandler              owner;
    private         ServerSocket                gestSock                = null;
    private         boolean                     serverOnline;


    /**
     * Requests
     */
    private final   DInterpreter                interpreter             = new DInterpreter();
    private final   Queue<String>               requestQueue            = new LinkedList<>();
    private         DServerClientReception      srvListener             = null;


    /**
     * Game attributes
     */
    private final   DMinefield                  field                   = new DMinefield();
    private         int                         fieldLenght             = 20;
    private         int                         fieldHeight             = 20;
    private         int                         nbMine                  = 75;
    private         int                         nbMaxPlayer             = 5;
    private         boolean[][]                 spriteMeshValidator;
    private         int                         nbSpriteToReveal;
    private         int                         nbSpriteRevealed;


    
    /**
     * Constructor
     */
    public DServer(String uuid) {

        // Getting server id
        this.uuid = uuid;


        // New thread
        service = new Thread(this);


        // Trying to create a new socket manager, and a new socket on the port 10 000
        try {

            // Socket creation
            gestSock = new ServerSocket(10000);


            // Thread initialization
            srvListener = new DServerClientReception(this, this.gestSock, clientList);


            // Server online
            serverOnline = true;


            // Activating listening service
            this.init();

            
        } catch (IOException e) {
            
            // Printing exception
            System.out.println(e);


            // Server not online
            serverOnline = false;
        
        }

    }




    /**
     * Starting service
     */
    private void init() {
        service.start();
    }



    
    /**
     * Getter : to check if the server is online or not
     * 
     * @return serverOnline : flag that show if the server online or not
     */
    public boolean isOnline() {
        return serverOnline;
    }




    /**
     * Getter : to check the server UUID
     * 
     * @return
     */
    public String getUUID() {
        return this.uuid;
    }




    /**
     * Getter : to check the maximum number of player
     * 
     * @return
     */
    public int getNbMaxPlayer() {
        return this.nbMaxPlayer;
    }




    /**
     * Remove a client handler from the client list
     * 
     * @param uuid
     */
    public void removeHandler(String uuid) {
        synchronized (clientList) {
            clientList.remove(uuid);
        }
    }




    /**
     * Add client request to the queue
     * 
     * @param request
     */
    public void addRequest(String request) {
        synchronized (requestQueue) {
            requestQueue.add(request);
        }
    }




    /**
     * Stop server
     */
    public void stop() {

        // Closing all
        srvListener.stop();
        serverOnline = false;

    }




    /**
     * Disconnect a client for a specific reason
     * 
     * @param uuid
     * @param reason
     */
    public void disconnectClient(String uuid, String reason) {
        synchronized (clientList) {

            // Sending the stop request
            clientList.get(uuid).addRequest(interpreter.build("SERVER", DRequestType.DISCONNECT, reason));
            clientList.get(uuid).shutDown();

        }

    }




    /**
     * Send a request to all client
     * 
     * @param request
     */
    public void sendToAll(String request) {

        // Iteration over the client list
        synchronized (clientList) {
            for (DClientHandler client : clientList.values()) {
                client.addRequest(request);
            }

        }

    }




    /**
     * Send the score list to all player
     */
    public void scoreUpdate() {

        // Content
        String content = "";


        // Iteration over the client list
        synchronized (clientList) {
            for (DClientHandler client : clientList.values()) {

                // In case of empty content
                if (content.isEmpty()) {
                    content = client.getUUID() + ":" + client.getScore() + ";";
                } else {
                    content += client.getUUID() + ":" + client.getScore() + ";";
                }
                
            }

        }


        // Sending to all the score list
        this.sendToAll(interpreter.build("SERVER", DRequestType.SCORE, content));

    }




    /**
     * Ask all client to reveal a sprite
     * 
     * @param uuid  player's uuid who clicked on the sprite
     * @param posX
     * @param posY
     */
    public synchronized void spriteReveal(String uuid, int posX, int posY) {

        // Checking if the sprite is already clicked
        if (!this.spriteMeshValidator[posX][posY]) {

            // Revealing the sprite
            if (!field.isMine(posX, posY)) {

                // Sprite reveal
                this.sendToAll(interpreter.build("SERVER", DRequestType.SPRITE_REVEAL, uuid + ":" + posX + "," + posY + "=" + field.mineDetection(posX, posY)));

                // Score update
                DClientHandler client = clientList.get(uuid);
                if (client != null) {
                    client.setScore(clientList.get(uuid).getScore() + 1);
                    this.scoreUpdate();
                }


                // End of game condition
                this.nbSpriteRevealed ++;
                if (nbSpriteRevealed == nbSpriteToReveal) {
                    this.revealAllSprite();
                    this.sendToAll(interpreter.build("SERVER", DRequestType.GAME_WIN, ""));
                }


            } else {

                // Unaliving the client
                DClientHandler client = clientList.get(uuid);
                if (client != null) {
                    client.setAlive(false);
                }


                // Sprite reveal
                this.sendToAll(interpreter.build("SERVER", DRequestType.SPRITE_REVEAL, uuid + ":" + posX + "," + posY + "=" + -1));


                // Lost request
                this.sendToAll(interpreter.build("SERVER", DRequestType.PLAYER_HAS_LOST, uuid));


                // Checking if all player has lost
                boolean allPlayerHasLost = true;
                for (DClientHandler client_ : clientList.values()) {

                    // Checking
                    if (client_ != null && client_.isAlive()) {
                        allPlayerHasLost = false;
                    }
                }


                // If all player has lost
                if (allPlayerHasLost) {

                    // Game lost request
                    this.revealAllSprite();
                    this.sendToAll(interpreter.build("SERVER", DRequestType.GAME_LOST));

                }


            }


            // Sprite validation
            this.spriteMeshValidator[posX][posY] = true;

        }

    }




    /**
     * Asking evry client to reval all sprites
     */
    private void revealAllSprite() {

        // Running trough the whole field
        for (int posX = 0; posX < field.getLenght(); posX++) {
            for (int posY = 0; posY < field.getWidth(); posY++) {

                // Revealing the sprite
                if (field.isMine(posX, posY)) {
                    this.sendToAll(interpreter.build("SERVER", DRequestType.SPRITE_REVEAL, uuid + ":" + posX + "," + posY + "=" + -1));
                } else {
                    this.sendToAll(interpreter.build("SERVER", DRequestType.SPRITE_REVEAL, uuid + ":" + posX + "," + posY + "=" + field.mineDetection(posX, posY)));
                }
            
            }
        }

    }




    /**
     * Online game creation
     */
    public void newOnlineGame() {

        // Reseting all score
        synchronized (clientList) {
            for (DClientHandler client : clientList.values()) {
                client.setScore(0);
                client.setAlive(true);
                
            }

        }


        // Creating the empty field
        this.field.newCustomEmptyField(DLevel.CUSTOM, this.fieldLenght, this.fieldHeight, this.nbMine);


        // Create mesh validator
        this.spriteMeshValidator = new boolean[this.field.getLenght()][this.field.getWidth()];


        // Getting the max number of sprite to reveal
        this.nbSpriteToReveal = this.field.getLenght() * this.field.getWidth() - this.field.getNumberOfMine();


        // Reveal a random sprite
        Random  random  = new Random    ();
        int     startX  = random.nextInt(field.getLenght());
        int     startY  = random.nextInt(field.getWidth());
        this    .field  .fillField      (startX, startY);


        // Sending the field size
        this.sendToAll(interpreter.build("SERVER", DRequestType.FIELD_SIZE,     field.getLenght() + "," + field.getWidth()));
        this.sendToAll(interpreter.build("SERVER", DRequestType.FIELD_READY,    ""));


        // Sending the gamle ready tag
        this.sendToAll(interpreter.build("SERVER", DRequestType.GAME_READY, ""));


        // Revealing the start sprite
        this.spriteReveal("SERVER", startX, startY);

    }




    /**
     * Thread method
     * 
     * Critical thread : 10ms loop
     */
    @Override
    public void run () {

        // Stop condition
        while (service != null && serverOnline) {

            // Get the start time of the loop iteration
            long startTime = System.currentTimeMillis();
            

            synchronized (requestQueue) {

                // Reading request
                if (!requestQueue.isEmpty()) {

                    // TODO : clear this shit
                    System.out.println(requestQueue.peek());


                    // Request interpretation
                    interpreter.interpret(requestQueue.poll());


                    // Request answer
                    switch (interpreter.getRequestType()) {
                        case DRequestType.HELLO_CLT -> {

                            // Get the client handler and apply it the name
                            synchronized (clientList) {

                                // Getting the client handler
                                DClientHandler client = clientList.get(interpreter.getSenderUUID());
                                

                                // If the client exist
                                if (client != null) {

                                    // Set player pseudo and start pinging
                                    client.setPlayerName(interpreter.getContent());
                                    client.startPinging();
                                    
                                    
                                    // Creating the new player list
                                    boolean first       = true;
                                    String  playerList  = "";
                                    for (DClientHandler client_ : clientList.values()) {

                                        // To prevet for getting and null string
                                        if (first) {

                                            // Adding the client uuid and player name
                                            playerList += client_.getUUID();
                                            playerList += ":";
                                            playerList += client_.getPlayerName();
                                            playerList += ";";

                                        } else {

                                            // Adding the client uuid and player name
                                            playerList += client_.getUUID();
                                            playerList += ":";
                                            playerList += client_.getPlayerName();
                                            playerList += ";";

                                        }

                                    }
                                    
                                    
                                    // Sending the player list to everyone
                                    this.sendToAll(interpreter.build("SERVER", DRequestType.PLAYER_LIST,  playerList));
                                    
                                    
                                    // If there is a server owner
                                    DClientHandler owner = this.owner;
                                    if (owner != null) {
                                        this.sendToAll(interpreter.build("SERVER", DRequestType.SERVER_OWNER, owner.getUUID()));
                                    }

                                }

                            }


                        }
                        case DRequestType.PING -> {

                            // Answering the ping
                            synchronized (clientList) {

                                // Getting the client handler
                                DClientHandler client = clientList.get(interpreter.getSenderUUID());
                                

                                // If the client exist
                                if (client != null) {

                                    // Answer the client ping
                                    client.addRequest(interpreter.build("SERVER", DRequestType.PING_ANSWER, ""));

                                }
                                
                            }


                        }
                        case DRequestType.PING_ANSWER -> {

                            // Client answer : taking it into account
                            synchronized (clientList) {

                                // Getting the client handler
                                DClientHandler client = clientList.get(interpreter.getSenderUUID());
                                

                                // If the client exist
                                if (client != null) {

                                    // The client is still there
                                    client.pingReceived();

                                }
                                
                            }


                        }
                        case DRequestType.DISCONNECT -> {

                            // Client answer : taking it into account
                            synchronized (clientList) {

                                // Getting the client handler
                                DClientHandler client = clientList.get(interpreter.getSenderUUID());
                                

                                // If the client exist
                                if (client != null) {

                                    // Shutting down client handler
                                    client.shutDown();

                                }
                                
                            }


                        }
                        case DRequestType.OWNERSHIP_ASK -> {

                            // Answering the client
                            synchronized (clientList) {

                                // Getting the client handler
                                DClientHandler client = clientList.get(interpreter.getSenderUUID());
                                

                                // If the client exist
                                if (client != null) {

                                    // Verifying the UUID sent
                                    if (interpreter.getContent().equals(this.uuid)) {

                                        // Ownership granted
                                        client.addRequest(interpreter.build("SERVER", DRequestType.OWNERSHIP_GRANTED, ""));
                                        client.setOwnership(true);
                                        this.owner = client;
                                        
                                        
                                    } else {

                                        // Ownership refused
                                        client.addRequest(interpreter.build("SERVER", DRequestType.OWNERSHIP_REFUSED, ""));

                                    }

                                }

                            }


                        }
                        case DRequestType.GAME_LAUNCH_ASK -> {

                            // Verifying if there are enough player and if the one who asked is the server owner
                            synchronized (clientList) {
                                if (clientList.size() > 1 && this.owner.getUUID().equals(interpreter.getSenderUUID())) {
                                    this.newOnlineGame();
                                }
                            }


                        }
                        case DRequestType.SPRITE_CLICKED -> {

                            /**
                             * Data shape :
                             * 
                             * CONTENT = 'playerUUID:posX,posY'
                             */


                            // Pattern definition
                            Pattern pattern = Pattern.compile("^([^:]+):([\\d.]+),([\\d.]+)$");
                            Matcher matcher = pattern.matcher(interpreter.getContent());


                            // If the request match the pattern
                            if (matcher.matches()) {

                                // Trying to convert the posX and posY
                                int posX = 0;
                                int posY = 0;
                                try {

                                    // Saving the field length and width
                                    posX = Integer.parseInt(matcher.group(2));
                                    posY = Integer.parseInt(matcher.group(3));


                                } catch (NumberFormatException e) {

                                    // TODO : handle this

                                }

                                // Ask the server to reveal the sprite
                                this.spriteReveal(matcher.group(1), posX, posY);

                               
                            } else {

                                // TODO : Handel this

                            }


                        }
                        default -> {


                        }
                        
                    }
                    
                }

            }



            // THREAD LIMITER
            // ====================================================================================
            
            // Calculate how long the operations took
            long elapsedTime = System.currentTimeMillis() - startTime;


            // Calculate the remaining time to sleep
            long sleepTime = 10 - elapsedTime;


            // If there is still time left in the 10ms window, sleep
            if (sleepTime > 0) {
                try {

                    // Pause
                    Thread.sleep(sleepTime);


                } catch (InterruptedException e) {

                    // Handle the exception
                    System.out.println(e);

                }

            }
                      
        }

    }

}