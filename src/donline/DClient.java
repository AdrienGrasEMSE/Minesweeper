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
public class DClient implements DConnexionHandler {

    /**
     * Client attribute
     */
    private final   String  name;


    /**
     * Attributes
     */
    private         Thread          service;
    private         DPing           pingService;
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
            System.out.println(e);
        
        }


        // Not connected
        this.connected  = false;
        this.service    = null;
        return false;

    }




    /**
     * Stop the client
     */
    public void shutDown() {
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

    }

    


    /**
     * Method to send a request to the server
     * 
     * @param request
     */
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
        interpret.interpret(request);


        // TODO Clean this shit
        // System.out.println(interpret.getRequestType().getString());


        // Action according to the request type
        switch (interpret.getRequestType()) {

            // Server hello
            case DRequestType.HELLO_SRV:

                // Getting the given ID
                this.uuid = interpret.getContent();


                // Creating ping service
                this.pingService = new DPing(uuid, this);
                this.pingService.start();


                // Launching the client hello
                this.addRequest(interpret.build(this.uuid, DRequestType.HELLO_CLT, name));
                break;


            // Server ping
            case DRequestType.PING:

                // Ansewering the ping
                this.addRequest(interpret.build(this.uuid, DRequestType.PING_ANSWER, ""));
                break;

            
            // Server ping answer
            case DRequestType.PING_ANSWER:

                // Server answer : taking it into account
                this.pingService.answerPing();
                break;

            
            // Server disconnect client
            case DRequestType.DISCONNECT:

                // Server answer : taking it into account
                this.shutDown();
                System.out.println(interpret.getContent());
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
                        synchronized (writter) {
                            synchronized (writeQueue) {
                                writter.write(writeQueue.poll());
                            }
                            
                        }
                        
                    }

                }


            } else {

                // Disconnect
                this.disconnect();

            }
                      
        }

    }






    public static void main(String [] args ) {

        // Starting point
        System.out.println("CLIENT : Starting point");


        DClient client = new DClient("AdrienG");
        client.connect("localhost", 10000);


        // Test
        try {

            // 1sec
            Thread.sleep(1000);

        } catch (InterruptedException e) {

            //
            e.printStackTrace();

        }
        client.addRequest(client.interpret.build(client.uuid, DRequestType.OWNERSHIP_ASK, "1234"));


        // Ending point
        System.out.println("CLIENT : Ending point");

    }

} 