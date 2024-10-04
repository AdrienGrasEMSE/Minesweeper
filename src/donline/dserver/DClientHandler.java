// Package declaration
package donline.dserver;

// Import
import java.util.LinkedList;
import java.util.Queue;

import donline.DConnexionHandler;
import donline.DPing;
import donline.DReader;
import donline.DWritter;
import java.io.IOException;
import java.net.Socket;


/**
 * Class Client Handler
 * 
 * @author AdrienG
 * @version 0.0
 * 
 * 
 * Class wich can hold a client by sending and receiving message
 */
public class DClientHandler implements DConnexionHandler{

    /**
     * Attributes
     */
    private         Thread          service;
    private final   Socket          socket;
    private final   String          uuid;
    private final   DServer         server;
    private         DPing           pingService;
    private         boolean         connected   = true;
    private         String          playerName  = "";


    /**
     * Write stream
     */
    private final   DWritter        writter;
    private final   Queue<String>   writeQueue  = new LinkedList<>();


    /**
     * Read stream
     */
    private final   DReader         reader;
    private final   Queue<String>   readQueue   = new LinkedList<>();




    /**
     * Constructor
     * 
     * @param uuid
     * @param socket
     * @param server
     */
    public DClientHandler(String uuid, Socket socket, DServer server) {

        // Getting attributes
        this.uuid   = uuid;
        this.socket = socket;
        this.server = server;


        // Setting up writter and reader
        reader  = new DReader   (socket, readQueue);
        writter = new DWritter  (socket);


        // Check writter and reader status
        if (!writter.isReady() || !reader.isReady()) {

            // Disconnect client
            this.connected = false;

        }


        // Starting services
        this.pingService    = new DPing(uuid, this);
        this.service        = new Thread(this);
        this.service.start();

    }




    /**
     * Getter : to get the id of the client
     * 
     * @return uuid
     */
    public String getUUID() {
        return uuid;
    }




    /**
     * Method that add a server request that will be sent to the client
     * 
     * @param request
     */
    public void addRequest(String request) {

        // Adding the request to the queue
        synchronized (writeQueue) {
            writeQueue.add(request);
        }

    }




    /**
     * Setter : to set the player name after the client hello
     * 
     * @param name
     */
    public void setPlayerName(String name) {
        playerName = name;
    }




    /**
     * Getter : to get the player pseudo
     * 
     * @return playerName
     */
    public String getPlayerName() {
        return playerName;
    }




    /**
     * Start ping service
     */
    public void startPinging() {
        pingService.start();
    }




    /**
     * Valid ping response
     */
    public void pingReceived() {
        pingService.answerPing();
    }




    /**
     * Stop the client handler
     */
    public void shutDown() {

        // Sending all data before closing all
        synchronized (writeQueue) {
            while (!writeQueue.isEmpty()) {}
        }


        // Disconnecting
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


        // Removing the handler from the server
        server.removeHandler(uuid);

    }

    


    /**
     * Thread method
     */
    @Override
    public void run () {

        // Stop condition
        while (service != null) {

            // While connected
            if (connected) {

                // Cheking client request
                synchronized (readQueue) {
                    if (!readQueue.isEmpty()) {

                        // Reading data
                        server.addRequest(readQueue.poll());

                    }
                }


                // Cheking server request
                synchronized (writeQueue) {
                    if (!writeQueue.isEmpty()) {

                        // Sending data
                        writter.write(writeQueue.poll());
                        
                    }

                }


            } else {

                // Disconnect
                this.disconnect();

            }
                      
        }

    }

}
