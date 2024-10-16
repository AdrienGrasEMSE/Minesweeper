// Package declaration
package donline.dserver;

import deminer.DLevel;
import deminer.DMinefield;
import deminer.DUUID;
import donline.DInterpreter;
import donline.DRequestType;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Random;


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
    private final   Map<String, DClientHandler> clientList      = new HashMap<>();
    private         DClientHandler              owner;
    private         ServerSocket                gestSock        = null;
    private         boolean                     serverOnline;


    /**
     * Requests
     */
    private final   DInterpreter        interpreter             = new DInterpreter();
    private final   Queue<String>       requestQueue            = new LinkedList<>();
    private         DServerListener     srvListener             = null;


    /**
     * Game attributes
     */
    private final   DMinefield          field                   = new DMinefield();


    
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
            service.start();


            // Server online
            serverOnline = true;


            // Activating listening service
            srvListener = new DServerListener(this, requestQueue, clientList);

            
        } catch (IOException e) {
            
            // Printing exception
            System.err.println(e);


            // Server not online
            serverOnline = false;
        
        }

    }




    /**
     * Getter : to get the server UUID
     * 
     * @return
     */
    public String getUUID() {
        return uuid;
    }




    /**
     * Getter : to check who is the server owner
     * 
     * @return
     */
    public DClientHandler getOwner() {
        return owner;
    }




    /**
     * Setter : to change server owner
     * 
     * @param owner
     */
    public void setOwner(DClientHandler owner) {
        this.owner = owner;
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
     * Ask all client to reveal a sprite
     * 
     * @param posX
     * @param posY
     * 
     * 
     * TODO : include sprite revealer
     */
    public void spriteReveal(int posX, int posY) {

        // Revealing the sprite
        if (!field.isMine(posX, posY)) {
            this.sendToAll(interpreter.build("SERVER", DRequestType.SPRITE_REVEAL, posX + ":" + posY + "=" + field.mineDetection(posX, posY)));
        } else {
            this.sendToAll(interpreter.build("SERVER", DRequestType.SPRITE_REVEAL, posX + ":" + posY + "=" + -1));
        }

    }




    /**
     * Online game creation
     */
    public void newOnlineGame() {

        // Creating the empty field
        field.newCustomEmptyField(DLevel.CUSTOM, 20, 20, 50);


        // Reveal a random sprite
        Random  random  = new Random    ();
        int     startX  = random.nextInt(field.getLenght());
        int     startY  = random.nextInt(field.getWidth());
        field           .fillField      (startX, startY);


        // Sending the field size
        this.sendToAll(interpreter.build("SERVER", DRequestType.FIELD_SIZE,     field.getLenght() + ":" + field.getWidth()));
        this.sendToAll(interpreter.build("SERVER", DRequestType.MINE_NUMBER,    String.valueOf(field.getNumberOfMine())));
        this.sendToAll(interpreter.build("SERVER", DRequestType.FIELD_READY,    ""));


        // Generating the content for the mines position
        String content = "";


        // Getting all the mine position
        for (int posX = 0; posX < field.getLenght(); posX++) {
            for (int posY = 0; posY < field.getWidth(); posY++) {

                // If it's a mine
                if (field.isMine(posX, posY)) {
                    
                    // In case of empty content
                    if (content.isEmpty()) {
                        content = posX + ":" + posY + ";";
                    } else {
                        content += posX + ":" + posY + ";";
                    }

                }

            }

        }


        // Sending mine positions
        this.sendToAll(interpreter.build("SERVER", DRequestType.MINE_POSITION, content));


        // Sending the gamle ready tag
        this.sendToAll(interpreter.build("SERVER", DRequestType.GAME_READY, ""));


        // Revealing the start sprite
        this.spriteReveal(startX, startY);

    }




    /**
     * Thread method
     * 
     * Non critical thread : 500ms loop
     */
    @Override
    public void run () {

        // Stop condition
        while (service != null && serverOnline) {

            // Get the start time of the loop iteration
            long startTime = System.currentTimeMillis();


            // Waiting connection
            try {

                // New socket
                Socket socket = gestSock.accept();


                // New thread
                Thread newThread = new Thread(this);
                newThread.start();


                // Client holder creation
                String newId = DUUID.generate();
                DClientHandler clientHandler = new DClientHandler(newId, socket, this);
                synchronized (clientList) {
                    clientList.put(newId, clientHandler);
                }


                // Init dialog
                clientHandler.addRequest(interpreter.build("SERVER", DRequestType.HELLO_SRV, clientHandler.getUUID()));


                // this.disconnectClient(newId, "Server full");
                
                
                
            } catch (IOException e) {

                // Printing exception
                System.out.println(e);

            }



            // THREAD LIMITER
            // ====================================================================================
            
            // Calculate how long the operations took
            long elapsedTime = System.currentTimeMillis() - startTime;


            // Calculate the remaining time to sleep
            long sleepTime = 500 - elapsedTime;


            // If there is still time left in the 500ms window, sleep
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