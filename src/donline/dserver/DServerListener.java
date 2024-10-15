// Package declaration
package donline.dserver;

// Import
import java.util.Map;
import java.util.Queue;
import donline.DInterpreter;
import donline.DRequestType;


/**
 * Class server listener
 * 
 * @author  AdrienG
 * @version 0.0
 * 
 * 
 * Class that read and interpret request received by the server
 */
public class DServerListener implements Runnable{

    /**
     * Attribute
     */
    private final   DServer                     server;
    private final   Thread                      service;
    private final   Queue<String>               requestQueue;
    private final   Map<String, DClientHandler> clientList;
    private final   DInterpreter                interpreter     = new DInterpreter();
    private         boolean                     serverOnline    = true;




    /**
     * Constructor
     * 
     * @param serverQueue
     */
    public DServerListener(DServer server, Queue<String> serverQueue, Map<String, DClientHandler> clientList) {

        // Getting attribute
        this.server         = server;
        this.requestQueue   = serverQueue;
        this.clientList     = clientList;


        // Lauching the service
        this.service = new Thread(this);
        this.service.start();

    }




    /**
     * Stop service
     */
    public void stop() {
        serverOnline = false;
    }




    /**
     * Thread method
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
                    String str = requestQueue.peek();
                    System.out.println(str);
                    // interpret.interpret(str);
                    // System.err.println(interpret.getSenderUUID());
                    // System.err.println(interpret.getRequestType().getString());
                    // System.err.println(interpret.getContent());



                    // Request interpretation
                    interpreter.interpret(requestQueue.poll());


                    // Request answer
                    switch (interpreter.getRequestType()) {

                        // Client hello
                        case DRequestType.HELLO_CLT:

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
                                    server.sendToAll(interpreter.build("SERVER", DRequestType.PLAYER_LIST,  playerList));


                                    // If there is a server owner
                                    DClientHandler owner = server.getOwner();
                                    if (owner != null) {
                                        server.sendToAll(interpreter.build("SERVER", DRequestType.SERVER_OWNER, owner.getUUID()));
                                    }

                                }

                            }
                            break;


                        // Client ping
                        case DRequestType.PING:

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
                            break;

                        
                        // Client ping received
                        case DRequestType.PING_ANSWER:

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
                            break;

                        
                        // Client disconnect request received
                        case DRequestType.DISCONNECT:

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
                            break;


                        // Client ask owner ship
                        case DRequestType.OWNERSHIP_ASK:

                            // Answering the client
                            synchronized (clientList) {

                                // Getting the client handler
                                DClientHandler client = clientList.get(interpreter.getSenderUUID());
                                

                                // If the client exist
                                if (client != null) {

                                    // Verifying the UUID sent
                                    if (interpreter.getContent().equals(server.getUUID())) {

                                        // Ownership granted
                                        client.addRequest(interpreter.build("SERVER", DRequestType.OWNERSHIP_GRANTED, ""));
                                        client.setOwnership(true);
                                        server.setOwner(client);

                                        
                                    } else {

                                        // Ownership refused
                                        client.addRequest(interpreter.build("SERVER", DRequestType.OWNERSHIP_REFUSED, ""));

                                    }

                                }

                            }
                            break;

                        
                        // Client ask to launch the game
                        case DRequestType.GAME_LAUNCH_ASK:

                            // Verifying if there are enough player
                            synchronized (clientList) {
                                if (clientList.size() > 1) {
                                    server.newOnlineGame();
                                }
                            }
                            break;
                    

                        // Default
                        default:
                            break;
                    }

                }

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
