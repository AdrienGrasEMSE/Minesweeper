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
                    interpret.interpret(requestQueue.poll());


                    // Request answer
                    switch (interpret.getRequestType()) {

                        // Client hello
                        case DRequestType.CLT_HELLO:

                            // Get the client handler and apply it the name
                            synchronized (clientList) {
                                clientList.get(interpret.getSenderUUID()).setPlayerName(interpret.getContent());
                                clientList.get(interpret.getSenderUUID()).startPinging();
                            }
                            break;


                        // Client ping
                        case DRequestType.PING:

                            // Answering the ping
                            synchronized (clientList) {
                                clientList.get(interpret.getSenderUUID()).addRequest(interpret.build("SERVER", DRequestType.PINGANSWER, ""));                            }
                            break;

                        
                        // Client ping received
                        case DRequestType.PINGANSWER:

                            // Client answer : taking it into account
                            synchronized (clientList) {
                                clientList.get(interpret.getSenderUUID()).pingReceived();
                            }
                            break;

                        
                        // Client disconnect request received
                        case DRequestType.DISCONNECT:

                            // Client answer : taking it into account
                            synchronized (clientList) {
                                clientList.get(interpret.getSenderUUID()).shutDown();
                            }
                            break;
                    

                        // Default
                        default:
                            break;
                    }

                }

            }
                      
        }

    }

}
