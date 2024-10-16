// Pakage declaration
package donline;

// Import
import deminer.DController;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;


/**
 * Class Client
 * 
 * @author  AdrienG
 * @version 0.0
 * 
 * 
 * Class wich communicate with a server
 */
public class DClient implements DConnexionHandler {

    /**
     * Client attribute
     */
    private final   String          name;
    private final   DController     controller;


    /**
     * Attributes
     */
    private         Thread          service;
    private         DPing           pingService;
    private         Socket          socket;
    private         String          uuid;
    private         boolean         connected   = false;
    private         boolean         registered  = false;
    private         boolean         serverOwner = false;
    private final   DInterpreter    interpreter = new DInterpreter();


    /**
     * Write stream
     */
    private         DWritter        writter;
    private final   Queue<String>   writeQueue  = new LinkedList<>();


    /**
     * Read stream
     */
    private         DReader         reader;
    private final   Queue<String>   readQueue   = new LinkedList<>();


    /**
     * Players
     */
    private final   Map<String, String> playerList = new HashMap<>();
    private         String          ownerUUID;


    /**
     * Field info
     */
    int fieldLenght;
    int fieldWidth;
    int nbMines;


    

    /**
     * Constructor
     */
    public DClient (DController controller, String name) {

        // Getting attributes
        this.controller = controller;
        this.name       = name;


        // Creating thread
        this.service = new Thread(this);

    }




    /**
     * Getter : to see if the client is registered
     * 
     * @return registered
     */
    public boolean isRegistered() {
        return registered;
    }




    /**
     * Getter : to check if the client is the server owner
     * 
     * @return
     */
    public boolean isServerOwner() {
        return serverOwner;
    }
    



    /**
     * Try to connect on a server
     * 
     * @param host
     * @param port
     */
    public boolean connect(String host, int port) {

        // Trying to open a socket
        try {

            // Getting socket
            socket = new Socket(host, port);


            // Setting up writter and reader
            reader  = new DReader   (socket, readQueue);
            writter = new DWritter  (socket);


            // Check writter and reader status
            if (!writter.isReady() || !reader.isReady()) {

                // Disconnect client
                this.connected = false;

            }


            // Connected
            connected = true;
            this.service.start();
            return true;
            

        } catch (UnknownHostException e) {
            
            // Printing exception
            System.out.println(host);


        } catch (IOException e) {

            // Printing exception
            System.out.println(e);
        
        }


        // Not connected
        this.connected  = false;
        this.service    = null;
        return false;

    }




    /**
     * Getter : to get the client UUID
     * 
     * @return
     */
    public String getUUID() {
        return uuid;
    }




    /**
     * Stop the client
     */
    @Override
    public void shutDown() {
        connected = false;
    }




    /**
     * Closing all
     */
    public void disconnect() {

        // TODO : info
        System.out.println("Disconnecting " + this.uuid);


        // Trying to stop all properly
        if (reader.stop() && writter.stop()) {

            // Trying to close the socket
            try {

                // Closing socket
                socket.close();

            } catch (IOException e) {

                // TODO : handle this one (omg)
                System.out.println(e);

            }

        }


        // Shutting down service
        this.service = null;
        this.pingService.stop();

    }

    


    /**
     * Ask server to launch the game
     * 
     * TODO : delete this (makes the controller with interpreter)
     */
    public void launchGame() {
        this.addRequest(interpreter.build(uuid, DRequestType.GAME_LAUNCH_ASK, ""));
    }




    /**
     * Asking the ownership of the server
     * 
     * @param serverUUID needed to validate server ownership
     * 
     * TODO : delete this (makes the controller with interpreter)
     */
    public void askOwnership(String serverUUID) {
        this.addRequest(interpreter.build(uuid, DRequestType.OWNERSHIP_ASK, serverUUID));
    }




    /**
     * Sending the click event to the server
     * 
     * @param posX
     * @param posY
     * 
     * TODO : delete this (makes the controller with interpreter)
     */
    public void clickEvent(int posX, int posY) {
        this.addRequest(interpreter.build(this.uuid, DRequestType.SPRITE_CLICKED, posX + ":" + posY));
    }




    /**
     * Method to send a request to the server
     * 
     * @param request
     */
    @Override
    public void addRequest(String request) {
        synchronized (writeQueue) {
            writeQueue.add(request);
        }
    }




    /**
     * Extract the player list from the request
     * 
     * @param content
     */
    private void extractPlayerList(String content) {

        // Emptying the player list
        playerList.clear();
        int contentLenght = content.length();


        // If the content is not empty
        if (contentLenght > 8) {

            // Traverse the content
            boolean isUUID      = true;
            int     i           = 0;
            String  keyBuffer   = "";
            String  valBuffer   = "";
            while (i < contentLenght) {

                // End of UUID
                if (i < contentLenght && content.charAt(i) == ':') {

                    // Switching state
                    isUUID = false;


                    // Jumping the ':'
                    i++;

                }


                // End of player name
                if (i < contentLenght && content.charAt(i) == ';') {

                    // Update list
                    playerList.put(keyBuffer, valBuffer);
                    keyBuffer = "";
                    valBuffer = "";

                    // Switching state
                    isUUID = true;


                    // Jumping the ';'
                    i++;

                }


                // Completing the key
                if (i < contentLenght && isUUID) {

                    // If the buffer is empty
                    if (keyBuffer.isEmpty()) {
                        keyBuffer = String.valueOf(content.charAt(i));
                    } else {
                        keyBuffer += content.charAt(i);
                    }
                    
                }


                // Completing the value
                if (i < contentLenght && !isUUID) {

                    // If the buffer is empty
                    if (valBuffer.isEmpty()) {
                        valBuffer = String.valueOf(content.charAt(i));
                    } else {
                        valBuffer += content.charAt(i);
                    }
                    
                }


                // Incrementing
                i++;
                
            }

        }

    }




    /**
     * Extract field size
     * 
     * @param content
     */
    private void extractFieldSize(String content) {

        // Getting all chars from the request content
        int     i                   = 0;
        String  fieldLengthString   = "";
        String  fieldWidthString    = "";
        boolean isLenght            = true;
        while (i < content.length()) {

            // If the length is fully captured
            if (i < content.length() && content.charAt(i) == ':') {
                i ++;
                isLenght = false;
            }

            
            // Getting the lenght
            if (i < content.length() && isLenght) {


                // In case of empty string buffer
                if (fieldLengthString.isEmpty()) {
                    fieldLengthString =     String.valueOf(content.charAt(i));
                } else {
                    fieldLengthString +=    String.valueOf(content.charAt(i));
                }

            }


            // Getting the width
            if (i < content.length() && !isLenght) {


                // In case of empty string buffer
                if (fieldWidthString.isEmpty()) {
                    fieldWidthString =     String.valueOf(content.charAt(i));
                } else {
                    fieldWidthString +=    String.valueOf(content.charAt(i));
                }

            }


            // Incrementing i
            i ++;

        }


        // Updating the field length and width
        this.fieldLenght    = Integer.parseInt(fieldLengthString);
        this.fieldWidth     = Integer.parseInt(fieldWidthString);

    }




    /**
     * Extract mine positions
     * 
     * @param content
     */
    private void extractMinePosition(String content) {

        // Buffer variable
        String  posX_   = "";
        String  posY_   = "";
        int     i       = 0;
        boolean isPosX  = true;


        // Getting all mines
        while (i < content.length()) {

            // Switching data type
            if (i < content.length() && content.charAt(i) == ':') {

                // Switching to posX data
                isPosX = false;
                i++;


            } else if (i < content.length() && content.charAt(i) == ';') {

                // Switching to posY data
                isPosX = true;
                i++;


                // Saving data
                controller.fillField(Integer.parseInt(posX_), Integer.parseInt(posY_));
                posX_ = "";
                posY_ = "";

            }


            // Saving posX
            if (i < content.length() && isPosX) {

                // In case of empty buffer
                if (posX_.isEmpty()) {
                    posX_ = String.valueOf(content.charAt(i));
                } else {
                    posX_ += String.valueOf(content.charAt(i));
                }

            }


            // Saving posX
            if (i < content.length() && !isPosX) {

                // In case of empty buffer
                if (posY_.isEmpty()) {
                    posY_ = String.valueOf(content.charAt(i));
                } else {
                    posY_ += String.valueOf(content.charAt(i));
                }

            }


            // Incrementing
            i++;

        }

    }




    /**
     * Extract the sprite position and the sprite values
     * 
     * @param content
     */
    private void extractSpritePosition(String content) {

        // Variable buffer
        int     i               = 0;
        String  posX_           = "";
        String  posY_           = "";
        String  spriteValue_    = "";


        // Data localisation variables
        boolean isPosX          = true;
        boolean isCoordinate    = true;


        // Getting the sprite position
        while (i < content.length()) {

            // Switching data type
            if (i < content.length() && content.charAt(i) == ':') {

                // Switching to posY
                isPosX = false;
                i++;


            } else if (i < content.length() && content.charAt(i) == '=') {

                // Switching to spriteValue
                isCoordinate = false;
                i++;

            }


            // Getting posX
            if (i < content.length() && isCoordinate && isPosX) {

                // In case of empty buffer
                if (posX_.isEmpty()) {
                    posX_ = String.valueOf(content.charAt(i));
                } else {
                    posX_ += String.valueOf(content.charAt(i));
                }


            }
            
            
            // Getting posY
            if (i < content.length() && isCoordinate && !isPosX) {

                // In case of empty buffer
                if (posY_.isEmpty()) {
                    posY_ = String.valueOf(content.charAt(i));
                } else {
                    posY_ += String.valueOf(content.charAt(i));
                }


            }
            
            
            // Getting spriteValue
            if (i < content.length() && !isCoordinate) {

                // In case of empty buffer
                if (spriteValue_.isEmpty()) {
                    spriteValue_ = String.valueOf(content.charAt(i));
                } else {
                    spriteValue_ += String.valueOf(content.charAt(i));
                }

            }


            // Incrementing
            i ++;

        }


        // Display the sprite position and spriteValue
        this.controller.spriteReveal(Integer.parseInt(posX_), Integer.parseInt(posY_), Integer.parseInt(spriteValue_));

    }




    /**
     * Method wich answer a request
     * 
     * @param request
     */
    private void requestAction(String request) {

        // Request interpretation
        interpreter.interpret(request);


        // Action according to the request type
        switch (interpreter.getRequestType()) {
            case DRequestType.HELLO_SRV -> {

                // Getting the given ID
                this.uuid       = interpreter.getContent();
                this.registered = true;
                
                
                // Creating ping service
                this.pingService = new DPing(uuid, this);
                this.pingService.start();
                
                
                // Launching the client hello
                this.addRequest(interpreter.build(this.uuid, DRequestType.HELLO_CLT, name));


            }
            case DRequestType.PING -> {

                // Ansewering the ping
                this.addRequest(interpreter.build(this.uuid, DRequestType.PING_ANSWER, ""));


            }
            case DRequestType.PING_ANSWER -> {

                // Server answer : taking it into account
                this.pingService.answerPing();


            }
            case DRequestType.DISCONNECT -> {

                // Server answer : taking it into account
                this.shutDown();
                System.out.println(interpreter.getContent());


            }
            case DRequestType.OWNERSHIP_GRANTED -> {

                // Ownership granted
                this.serverOwner    = true;
                this.playerList.put (this.uuid, this.name);
                this.ownerUUID      = this.uuid;


            }
            case DRequestType.OWNERSHIP_REFUSED -> {

                // Ownership granted
                this.serverOwner = false;


            }
            case DRequestType.PLAYER_LIST -> {

                // Extract player list
                this.extractPlayerList(interpreter.getContent());
                controller.updatePlayerList(playerList, ownerUUID);


            }
            case DRequestType.SERVER_OWNER -> {

                // Saving the server owner
                ownerUUID = interpreter.getContent();
                controller.updatePlayerList(playerList, ownerUUID);


            }
            case DRequestType.FIELD_SIZE -> {

                // Saving the field size
                this.extractFieldSize(interpreter.getContent());


            }
            case DRequestType.MINE_NUMBER -> {

                // Saving the mine number
                this.nbMines = Integer.parseInt(interpreter.getContent());


            }
            case DRequestType.MINE_POSITION -> {

                // Saving the mine number
                this.extractMinePosition(interpreter.getContent());


            }
            case DRequestType.FIELD_READY -> {

                // Saving the mine number
                this.controller.initOnlineField(fieldLenght, fieldWidth, nbMines);


            }
            case DRequestType.GAME_READY -> {

                // Saving the mine number
                this.controller.gameStart();


            }
            case DRequestType.SPRITE_REVEAL -> {

                // Revealing the sprite
                this.extractSpritePosition(interpreter.getContent());


            }
        
                
            default -> {
            }
        }
        
    }


     
    
    /**
     * Thread method
     * 
     * Critical thread : 10ms loop
     */
    @Override
    public void run () {

        // Stop condition
        while (service != null) {

            // Get the start time of the loop iteration
            long startTime = System.currentTimeMillis();


            // While connected
            if (connected) {

                // Cheking client request
                synchronized (readQueue) {
                    if (!readQueue.isEmpty()) {

                        // Reading data
                        System.out.println(readQueue.peek());
                        requestAction(readQueue.poll());
    
                    }
                }


                // Cheking server request
                synchronized (writeQueue) {
                    if (!writeQueue.isEmpty() && registered) {

                        // Sending data
                        synchronized (writter) {
                            writter.write(writeQueue.poll());
                            
                        }
                        
                    }

                }


            } else {

                // Disconnect
                this.disconnect();

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
                    e.printStackTrace();

                }
            }
                      
        }

    }

}