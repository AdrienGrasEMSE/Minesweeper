// Pakage declaration
package donline;

// Import
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.LinkedList;
import java.util.Queue;

import deminer.DController;


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
     */
    public void launchGame() {

        // Asking ownership
        this.addRequest(interpreter.build(uuid, DRequestType.GAME_LAUNCH_ASK, ""));

    }




    /**
     * Asking the ownership of the server
     * 
     * @param serverUUID needed to validate server ownership
     */
    public void askOwnership(String serverUUID) {

        // Asking ownership
        this.addRequest(interpreter.build(uuid, DRequestType.OWNERSHIP_ASK, serverUUID));

    }




    /**
     * Method to send a request to the server
     * 
     * @param request
     */
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
     * Extract mines positions
     * 
     * @param content
     */
    private void extractMinePositions(String content) {

        // Buffer variables
        String  posX_   = "";
        String  posY_   = "";
        int     i       = 0;


        // Traverse the content
        while (i < content.length()) {
            
            //
        }

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

            // Server hello
            case DRequestType.HELLO_SRV:

                // Getting the given ID
                this.uuid       = interpreter.getContent();
                this.registered = true;


                // Creating ping service
                this.pingService = new DPing(uuid, this);
                this.pingService.start();


                // Launching the client hello
                this.addRequest(interpreter.build(this.uuid, DRequestType.HELLO_CLT, name));
                break;


            // Server ping
            case DRequestType.PING:

                // Ansewering the ping
                this.addRequest(interpreter.build(this.uuid, DRequestType.PING_ANSWER, ""));
                break;

            
            // Server ping answer
            case DRequestType.PING_ANSWER:

                // Server answer : taking it into account
                this.pingService.answerPing();
                break;

            
            // Server disconnect client
            case DRequestType.DISCONNECT:

                // Server answer : taking it into account
                this.shutDown();
                System.out.println(interpreter.getContent());
                break;


            // Server answer : server ownership granted
            case DRequestType.OWNERSHIP_GRANTED:

                // Ownership granted
                this.serverOwner    = true;
                this.playerList.put (this.uuid, this.name);
                this.ownerUUID      = this.uuid;
                break;

            
            // Server answer : server ownership refused
            case DRequestType.OWNERSHIP_REFUSED:

                // Ownership granted
                this.serverOwner = false;
                break;

            
            // Player list sent by the server
            case DRequestType.PLAYER_LIST:

                // Extract player list
                this.extractPlayerList(interpreter.getContent());
                controller.updatePlayerList(playerList, ownerUUID);
                break;

            
            // Server owner sent by the server
            case DRequestType.SERVER_OWNER:

                // Saving the server owner
                ownerUUID = interpreter.getContent();
                controller.updatePlayerList(playerList, ownerUUID);
                break;


            // Field size sent by the server
            case DRequestType.FIELD_SIZE:

                // Saving the field size
                this.extractFieldSize(interpreter.getContent());
                break;


            // Number of mines sent by the server
            case DRequestType.MINE_NUMBER:

                // Saving the mine number
                this.nbMines    = Integer.parseInt(interpreter.getContent());
                this.controller .initOnlineGame(fieldLenght, fieldWidth, nbMines);
                break;
        
                
            default:
                break;
        }

    }


     
    
    /**
     * Thread method
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
                            synchronized (writeQueue) {
                                writter.write(writeQueue.poll());
                            }
                            
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
            long sleepTime = 100 - elapsedTime;


            // If there is still time left in the 100ms window, sleep
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