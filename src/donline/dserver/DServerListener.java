// Package declaration
package donline.dserver;

import java.util.HashMap;
import java.util.Map;
// Import
import java.util.Queue;
import java.util.Set;

import donline.DInterpreter;


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
    private final   Thread                      service;
    private final   Queue<String>               requestQueue;
    private final   Map<String, DClientHandler> clientList;
    private final   DInterpreter                interpret       = new DInterpreter();
    private         boolean                     serverOnline    = true;




    /**
     * Constructor
     * 
     * @param serverQueue
     */
    public DServerListener(Queue<String> serverQueue, Map<String, DClientHandler> clientList) {

        // Getting attribute
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


            // 
            synchronized (requestQueue) {

                // Reading request
                if (!requestQueue.isEmpty()) {

                    System.out.println(requestQueue.peek());

                    // Request interpretation
                    interpret.interpret(requestQueue.poll());


                    // Request answer
                    switch (interpret.getRequestType()) {

                        // Client hello
                        case CLT_HELLO:

                            // Get the client handler and apply it the name
                            clientList.get(interpret.getSenderUUID()).setPlayerName(interpret.getContent());
                            break;
                    
                        default:
                            break;
                    }

                }

            }
                      
        }

    }

}
