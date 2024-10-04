// Package declaration
package donline.dserver;

// Import
import deminer.DUUID;
import donline.DInterpreter;
import donline.DRequestType;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


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
    private final   Map<String, DClientHandler> clientList      = new HashMap<>();
    private final   String                      uuid;
    private         ServerSocket                gestSock        = null;
    private         boolean                     serverOnline;


    /**
     * Requests
     */
    private final   DInterpreter        interpret       = new DInterpreter();
    private final   Queue<String>       requestQueue    = new LinkedList<>();
    private         DServerListener     srvListener     = null;



    
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
            clientList.get(uuid).addRequest(interpret.build("SERVER", DRequestType.DISCONNECT, reason));
            clientList.get(uuid).shutDown();

        }

    }




    /**
     * Thread method
     */
    @Override
    public void run () {

        // Stop condition
        while (service != null && serverOnline) {

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
                clientHandler.addRequest(interpret.build("SERVER", DRequestType.HELLO_SRV, clientHandler.getUUID()));


                // this.disconnectClient(newId, "Server full");
                
                
                
            } catch (IOException e) {

                // Printing exception
                System.out.println(e);

            }
                      
        }

    }





    public static void main(String [] args) {

        // Starting point
        System.out.println("SERVER : Starting point");


        // Starting server
        DServer srv = new DServer("1234");


        // Checking if the server is online
        if (srv.isOnline()) {
            System.out.println("SERVER : Online, UUID = " + srv.getUUID());
        } else {
            System.out.println("SERVER : Disconnected");
        }


        // Ending point
        // System.out.println("SERVER : Ending point");

    }

}