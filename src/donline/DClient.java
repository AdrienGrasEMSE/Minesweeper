// Pakage declaration
package donline;

// Import
import deminer.DController;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
    private         boolean         alive;


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
    private final   Map<String, DPlayer> playerList = new HashMap<>();
    private         String          ownerUUID;


    /**
     * Field info
     */
    int fieldLenght;
    int fieldWidth;


    

    /**
     * Constructor
     */
    public DClient (DController controller, String name) {

        // Getting attributes
        this.controller = controller;
        this.name       = name;

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
     * Getter : to see if the client is alive
     * 
     * @return registered
     */
    public boolean isAlive() {
        return this.alive;
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
     * Try to reach atomatically the server
     * 
     * @return true / false according to the connexion state (established or not)
     */
    public boolean autoConnect() {

        // Testing on the local device
        if (this.tryConnection("127.0.0.1", 10000)) {

            // Connexion established
            return true;


        } else {

            // Trying to listen in broadcast
            try {

                // Socket creation
                DatagramSocket socket_  = new DatagramSocket(8888, InetAddress.getByName("0.0.0.0"));
                socket_                 .setBroadcast(true);
                socket_                 .setSoTimeout(5000);


                // Waiting for message (5sec max)
                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                try {

                    // Essayer de recevoir un paquet
                    socket_.receive(packet);


                    // Closing socket
                    socket_.close();


                    // Getting the server IP
                    interpreter.interpret(new String(packet.getData(), 0, packet.getLength()));


                    // Trying to connect to the server
                    // return this.tryConnection(interpreter.getContent(), 10000);
                    return this.tryConnection("192.168.61.237", 10000);


                } catch (SocketTimeoutException e) {

                    // Closing socket
                    socket_.close();
                    return false;

                }
                

            } catch (Exception e) {

                // Printing exception
                System.out.println(e);
                return false;

            }

        }

    }



    /**
     * Try to connect on a server
     * 
     * @param host
     * @param port
     */
    public boolean tryConnection(String host, int port) {

        // Creating thread
        this.service = new Thread(this);
        

        // Trying to open a socket
        try {

            // Getting socket
            socket = new Socket();
            socket.connect(new InetSocketAddress(host, port), 2000);


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

        // Stop service
        connected = false;

        
        // Switching back to the default UI
        this.controller.backDefaultOnlineUi("");

    }




    /**
     * Stop the client
     */
    public void shutDown(String reason) {

        // Stop service
        connected = false;

        
        // If there is any reason
        if (!reason.isEmpty()) {
            this.controller.backDefaultOnlineUi(reason);
        }

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
     * Method wich answer a request
     * 
     * @param request
     */
    private void requestAction(String request) {

        // Request interpretation
        interpreter.interpret(request);


        // Action according to the request type
        switch (interpreter.getRequestType()) {
            case DRequestType.HELLO_SRV             -> {

                // Getting the given ID
                this.uuid       = interpreter.getContent();
                this.registered = true;
                
                
                // Creating ping service
                this.pingService = new DPing(uuid, this);
                this.pingService.start();
                
                
                // Launching the client hello
                this.addRequest(interpreter.build(this.uuid, DRequestType.HELLO_CLT, name));


            }
            case DRequestType.PING                  -> {

                // Ansewering the ping
                this.addRequest(interpreter.build(this.uuid, DRequestType.PING_ANSWER, ""));


            }
            case DRequestType.PING_ANSWER           -> {

                // Server answer : taking it into account
                this.pingService.answerPing();


            }
            case DRequestType.DISCONNECT            -> {

                // Server answer : taking it into account
                this.shutDown(interpreter.getContent());


            }
            case DRequestType.OWNERSHIP_GRANTED     -> {

                // Ownership granted
                this.serverOwner    = true;


                // Updating the player list with the client itslef
                DPlayer player      = new DPlayer();
                player.setUUID      (this.uuid);
                player.setPlayerName(this.name);
                this.playerList.put (this.uuid, player);


                // Saving the owner UUID
                this.ownerUUID      = this.uuid;


            }
            case DRequestType.OWNERSHIP_REFUSED     -> {

                // Ownership granted
                this.serverOwner = false;


            }
            case DRequestType.PLAYER_LIST           -> {

                /**
                 * Data shape :
                 * 
                 * CONTENT = 'UUID1:playerName_1;UUID2:playerName_2;...UUIDn:playerName_n;'
                 */


                // Clearing the list
                this.playerList.clear();


                // Separate pairs (UUID, playerName)
                String[] pairs_ = interpreter.getContent().split(";");


                // Ruuning trough pairs
                for (String pair_ : pairs_) {

                    // Ignore empty pairs
                    if (!pair_.isEmpty()) {

                        // Separate UUID and playerName
                        String[] pair = pair_.split(":");
                        if (pair.length == 2) {

                            // Adding data to the player list
                            DPlayer player      = new DPlayer();
                            player.setUUID      (pair[0]);
                            player.setPlayerName(pair[1]);
                            this.playerList.put (pair[0], player);

                        }
                        
                    }

                }


                // Updating the player list
                controller.updatePlayerList(playerList, ownerUUID);


            }
            case DRequestType.SERVER_OWNER          -> {

                // Saving the server owner
                ownerUUID = interpreter.getContent();
                controller.updatePlayerList(playerList, ownerUUID);


            }
            case DRequestType.FIELD_SIZE            -> {

                /**
                 * Data shape :
                 * 
                 * CONTENT = 'fieldLenght,fieldWidth'
                 */


                // Length and width separation
                String[] parts = interpreter.getContent().split(",");
                if (parts.length != 2) {

                    // TODO : handle this
                    
                } else {

                    // Trying to convert length and width
                    try {

                        // Saving the field length and width
                        this.fieldLenght    = Integer.parseInt(parts[0]);
                        this.fieldWidth     = Integer.parseInt(parts[1]);


                    } catch (NumberFormatException e) {

                        // TODO : handle this

                    }

                }


            }
            case DRequestType.FIELD_READY           -> {

                // Saving the mine number
                this.controller.initOnlineField(fieldLenght, fieldWidth);


            }
            case DRequestType.GAME_READY            -> {

                // Displaying the field and making the client alive
                this.alive = true;
                this.controller.gameStart();


            }
            case DRequestType.SPRITE_REVEAL         -> {

                // Pattern definition
                Pattern pattern = Pattern.compile("^([^:]+):([\\d.]+),([\\d.]+)=([^=]+)$");
                Matcher matcher = pattern.matcher(interpreter.getContent());


                // If the data match the pattern
                if (matcher.matches()) {

                    // Trying to convert the posX and posY
                    int posX;
                    int posY;
                    int spriteValue;
                    try {

                        // Saving the field length and width
                        posX        = Integer.parseInt(matcher.group(2));
                        posY        = Integer.parseInt(matcher.group(3));
                        spriteValue = Integer.parseInt(matcher.group(4));


                        // Display the sprite position and spriteValue
                        this.controller.spriteReveal(posX, posY, spriteValue);


                    } catch (NumberFormatException e) {

                        // TODO : handle this

                    }


                } else {

                    // TODO : Handle this shit

                }


            }
            case DRequestType.SCORE                 -> {

                /**
                 * Data shape :
                 * 
                 * CONTENT = 'UUID1:score_1;UUID2:score_2;...UUIDn:score_n;'
                 */


                // Separate pairs (UUID, playerName)
                String[] pairs_ = interpreter.getContent().split(";");


                // Ruuning trough pairs
                for (String pair_ : pairs_) {

                    // Ignore empty pairs
                    if (!pair_.isEmpty()) {

                        // Separate UUID and playerName
                        String[] pair = pair_.split(":");
                        if (pair.length == 2) {

                            // Modifying player score
                            DPlayer player = playerList.get(pair[0]);
                            if (player != null) {

                                // Trying to convert top int
                                try {

                                    // Applying player score
                                    player.setScore(Integer.parseInt(pair[1]));

                                    
                                } catch (NumberFormatException e) {

                                    // TODO : Handle this

                                }

                            }

                        }
                        
                    }

                }

            
            }
            case DRequestType.PLAYER_HAS_LOST       -> {

                // Getting the player UUID
                DPlayer player = playerList.get(interpreter.getContent());
                if (player != null) {
                    player.setAlive(false);
                }


                // If the client has lost
                if (this.uuid.equals(interpreter.getContent())) {
                    this.alive = false;
                    this.controller.playerLost();
                }


            }
            case DRequestType.GAME_LOST             -> {

                // Displaying the info
                this.controller.gameLost();


            }
            case DRequestType.GAME_WIN              -> {

                // Displaying the info
                this.controller.gameWin();


            }
            case DRequestType.GAME_ABORTED          -> {

                // Displaying the info
                this.controller.gameAborted();


            }
            case DRequestType.PARAM_FIELD_LENGTH    -> {

                // Trying to get the field length
                int length = 0;
                try {

                    // Saving the parameter
                    length = Integer.parseInt(interpreter.getContent());


                    // Display the sprite position and spriteValue
                    this.controller.setLength(length);


                } catch (NumberFormatException e) {

                    // TODO : handle this

                }


            }
            case DRequestType.PARAM_FIELD_HEIGTH    -> {

                // Trying to get the field heigth
                int heigth = 0;
                try {

                    // Saving the parameter
                    heigth = Integer.parseInt(interpreter.getContent());


                    // Display the sprite position and spriteValue
                    this.controller.setHeigth(heigth);


                } catch (NumberFormatException e) {

                    // TODO : handle this

                }


            }
            case DRequestType.PARAM_FIELD_NBMINES   -> {

                // Trying to get the number of mine
                int nbMine = 0;
                try {

                    // Saving the parameter
                    nbMine = Integer.parseInt(interpreter.getContent());


                    // Display the sprite position and spriteValue
                    this.controller.setNbMine(nbMine);


                } catch (NumberFormatException e) {

                    // TODO : handle this

                }


            }
            case DRequestType.PARAM_NMAX_PLAYER     -> {

                // Trying to get the player limit
                int nMaxPlayer = 0;
                try {

                    // Saving the parameter
                    nMaxPlayer = Integer.parseInt(interpreter.getContent());


                    // Display the sprite position and spriteValue
                    this.controller.setNMaxPlayer(nMaxPlayer);


                } catch (NumberFormatException e) {

                    // TODO : handle this

                }


            }
            case DRequestType.GAME_LAUNCH_REFUSED   -> {

                // Display info
                this.controller.gameLauchRefused();


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
                synchronized (writeQueue) {

                    // Disconnect only if the write queue is empty
                    if (!writeQueue.isEmpty() && registered) {

                        // Sending data
                        synchronized (writter) {
                            writter.write(writeQueue.poll());
                            
                        }
                        

                    } else {

                        // Disconnect
                        this.disconnect();

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
                    e.printStackTrace();

                }
            }
                      
        }

    }

}