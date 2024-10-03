// Package delaration
package donline;

// Import
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Queue;


/**
 * Class reader
 * 
 * @author  AdrienG
 * @version 0.0
 * 
 * 
 * Class that wait data on an InputDataStream, before pushing it in a queue.
 */
public class DReader implements Runnable{

    /**
     * Attribute
     */
    private Thread              service     = null;
    private DataInputStream     stream      = null;
    private Queue<String>       readQueue   = null;
    private boolean             ready       = false;
    private boolean             active      = true;




    /**
     * Constructor
     * 
     * @param socket
     */
    public DReader(Socket socket, Queue<String> readQueue) {

        // Creating the stream
        try {

            // Stream creation
            this.stream     = new DataInputStream (socket.getInputStream());
            this.readQueue  = readQueue;
            this.service    = new Thread(this);
            this.service    .start();
            this.ready      = true;

            
        } catch (Exception e) {

            // Printing exception
            System.out.println(e);

        }

    }




    /**
     * To check if the reader is ok
     * 
     * @return reader status (ok / not ok)
     */
    public boolean isReady() {
        return ready;
    }




    /**
     * Method that close all
     */
    public boolean stop() {

        // Closing all
        try {

            // Closing stream
            stream.close();
            active = false;
            return true;


        } catch (IOException e) {

            // Printing exception
            System.out.println(e);

        }

        return false;
        
    }


    
    
    /**
     * Thread method
     */
    @Override
    public void run () {

        // Stop condition
        while (service != null && this.active) {

            // Data reading
            try {

                // New data
                if (stream.available() > 0) {
                    readQueue.add(stream.readUTF());
                }


            } catch (IOException e) {

                // Printing exception
                System.out.println(e);

            }            
                      
        }

    }

}
