// Package declaration
package donline;

// Import
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


/**
 * Class Writter
 * 
 * @author  AdrienG
 * @version 0.0
 * 
 * 
 * Class that write data on an OutputDataStream.
 */
public class DWritter {

    /**
     * Attribute
     */
    private DataOutputStream    stream  = null;
    private boolean             ready   = false;




    /**
     * Constructor
     * 
     * @param socket
     */
    public DWritter(Socket socket) {

        // Creating the stream
        try {

            // Stream creation
            this.stream  = new DataOutputStream (socket.getOutputStream());
            this.ready = true;

            
        } catch (IOException e) {

            // Printing exception
            System.out.println(e);

        }

    }




    /**
     * To check if the writter is ok
     * 
     * @return writter status (ok / not ok)
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
            return true;


        } catch (IOException e) {

            // Printing exception
            System.out.println(e);

        }

        return false;
        
    }




    /**
     * Send data
     * 
     * @param data
     * @return status (true = data sent, false = excepetion)
     */
    public boolean write(String data) {

        // Trying to send data
        try {

            // Sending data
            stream.writeUTF(data);
            return true;
            
        } catch (IOException e) {
            
            // Printing exception
            System.err.println(e);
            return false;

        }
        
    }

}
