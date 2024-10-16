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

            
        } catch (IOException e) {

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
     * 
     * Critical thread : 10ms loop
     */
    @Override
    public void run () {

        // Stop condition
        while (service != null && this.active) {

            // Get the start time of the loop iteration
            long startTime = System.currentTimeMillis();


            // Data reading
            try {

                // New data
                synchronized (readQueue) {
                    if (stream.available() > 0) {
                        readQueue.add(stream.readUTF());
                    }
                }


            } catch (IOException e) {

                // Printing exception
                // System.out.println(e);

            }




            // THREAD LIMITER
            // ====================================================================================
            
            // Calculate how long the operations took
            long elapsedTime = System.currentTimeMillis() - startTime;


            // Calculate the remaining time to sleep
            long sleepTime = 10 - elapsedTime;


            // If there is still time left in the 10ms window, sleep
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
