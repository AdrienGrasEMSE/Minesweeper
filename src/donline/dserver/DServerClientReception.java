// Package declaration
package donline.dserver;

// Import
import deminer.DUUID;
import donline.DInterpreter;
import donline.DRequestType;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;


/**
 * Class server listener
 * 
 * @author  AdrienG
 * @version 0.0
 * 
 * 
 * Class that read and interpret request received by the server
 */
public class DServerClientReception implements Runnable{

    /**
     * Attribute
     */
    private final   DServer                     server;
    private final   Thread                      service;
    private final   DInterpreter                interpreter     = new DInterpreter();
    private final   Map<String, DClientHandler> clientList;
    private         ServerSocket                gestSock        = null;
    private         boolean                     serverOnline    = true;




    /**
     * Constructor
     * 
     * @param serverQueue
     */
    public DServerClientReception(DServer server, ServerSocket gestSock, Map<String, DClientHandler> clientList) {

        // Getting attribute
        this.server         = server;
        this.gestSock       = gestSock;
        this.clientList     = clientList;


        // Lauching the service
        this.service = new Thread(this);
        this.init();

    }




    /**
     * Start service
     */
    private void init() {
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
                DClientHandler clientHandler = new DClientHandler(newId, socket, this.server);


                // Init dialog
                clientHandler.addRequest(interpreter.build("SERVER", DRequestType.HELLO_SRV, clientHandler.getUUID()));


                // If there place left
                synchronized (clientList) {
                    if (clientList.size() <= this.server.getNbMaxPlayer()) {
                        clientList.put(newId, clientHandler);
                    } else {
                        this.server.disconnectClient(newId, "Server full");
                    }
                    
                }
                
                
                
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
                    System.out.println(e);

                }
                
            }
                      
        }

    }

}
