// Package declaration
package donline.dserver;

// Import
import java.util.UUID;
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
 * Class server listener
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
    private         boolean                     serverOnline;
    private final   Thread                      service;
    private         ServerSocket                gestSock        = null;
    private final   Map<String, DClientHandler> clientList      = new HashMap<>();


    /**
     * Requests
     */
    private final   DInterpreter        interpret       = new DInterpreter();
    private final   Queue<String>       requestQueue    = new LinkedList<>();
    private         DServerListener     srvListener     = null;



    
    /**
     * Constructor
     */
    public DServer() {

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
            srvListener = new DServerListener(requestQueue, clientList);

            
        } catch (IOException e) {
            
            // Printing exception
            System.err.println(e);


            // Server not online
            serverOnline = false;
        
        }

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
     * ID generation using a UUID method (thanks ChatGPT)
     * 
     * @return
     */
    private String idGeneration() {

        // UUID (Universally Unique Identifier)
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
        
    }




    /**
     * Add client request to the queue
     * 
     * @param request
     */
    public synchronized void addRequest(String request) {
        requestQueue.add(request);
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
                String newId = idGeneration();
                DClientHandler clientHandler = new DClientHandler(newId, socket, this);
                clientList.put(newId, clientHandler);


                // Init dialog
                clientHandler.addServerRequest(interpret.build("SERVER", DRequestType.SRV_HELLO, clientHandler.getUUID()));
                
                
                
            } catch (IOException e) {

                // Printing exception
                System.out.println(e);

            }
                      
        }

    }




    public void getClientHandler(String uuid) {}


    public static void main(String [] args) {

        // Starting point
        System.out.println("SERVER : Starting point");


        // Starting server
        DServer srv = new DServer();


        // Checking if the server is online
        if (srv.isOnline()) {
            System.out.println("SERVER : Online");
        } else {
            System.out.println("SERVER : Disconnected");
        }


        // Ending point
        System.out.println("SERVER : Ending point");

    }

}