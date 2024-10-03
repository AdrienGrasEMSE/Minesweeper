// Pakage declaration
package donline;

// Import
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Queue;


/**
 * Class Client
 * 
 * @author  AdrienG
 * @version 0.0
 * 
 * 
 * Class wich communicate with a server
 */
public class DClient implements Runnable {

    /**
     * Client attribute
     */
    private final   String  name;


    /**
     * Attributes
     */
    private final   Thread          service;
    private         Socket          socket;
    private         String          uuid;
    private         boolean         connected   = false;
    private final   DInterpreter    interpret   = new DInterpreter();


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
     * Constructor
     */
    public DClient (String name) {

        // Getting attributes
        this.name = name;


        // Creating thread
        this.service = new Thread(this);

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
            System.err.println(e);
        
        }


        // Not connected
        connected =  false;
        return false;

    }




    /**
     * Method to send a request to the server
     * 
     * @param request
     */
    public void sendRequest(String request) {
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
        interpret.interpret(request);


        // Action according to the request type
        switch (interpret.getRequestType()) {

            // Server hello
            case DRequestType.SRV_HELLO:

                // Getting the given ID
                this.uuid = interpret.getContent();


                // Launching the client hello
                this.sendRequest(interpret.build(this.uuid, DRequestType.CLT_HELLO, name));
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
                    if (!writeQueue.isEmpty()) {

                        // Sending data
                        writter.write(writeQueue.poll());
                        
                    }
                }


            } else {

                // TODO : disconnect
                System.out.println("Disconnecting");


                // Closing all
                if (reader.stop() && writter.stop()) {

                    // Closing the socket
                    try {
                        socket.close();
                    } catch (IOException e) {
                        // TODO : handle this one (omg)
                        System.out.println(e);;
                    }

                }

            }
                      
        }

    }






    public static void main(String [] args ) {

        // Starting point
        System.out.println("CLIENT : Starting point");


        DClient client = new DClient("AdrienG");
        client.connect("localhost", 10000);


        // Ending point
        System.out.println("CLIENT : Ending point");

    }

} 