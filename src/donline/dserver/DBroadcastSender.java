// Package declaration
package donline.dserver;

import java.io.IOException;
// Import
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import donline.DRequestType;


/**
 * Class Broadcast Sender
 * 
 * @author AdrienG
 * @version 0.0
 * 
 * 
 * Class wich allow the server to send its adress in broadcast
 */
public class DBroadcastSender implements Runnable {

    /**
     * Attributes
     */
    private DatagramSocket  socket = null;
    private InetAddress     broadcastAddress;
    private Thread          service;


    /**
     * Message related attribute
     */
    private String          serverIP;
    private byte[]          buffer;
    private DatagramPacket  packet;




    /**
     * Constructor
     */
    public DBroadcastSender() {

        // Trying to get the device IP
        try {

            // Getting the device IP
            InetAddress localHost   = InetAddress.getLocalHost();
            this.serverIP           = localHost.getHostAddress();


            // Creating the socket
            socket                  = new DatagramSocket();
            socket                  .setBroadcast(true);


            // Casting the server IP into bytes
            this.buffer             = this.serverIP.getBytes();


            // Setting up the broadcast adress
            this.broadcastAddress   = InetAddress.getByName("255.255.255.255");


            // Setting up the packet
            this.packet             = new DatagramPacket(buffer, buffer.length, broadcastAddress, 8888);


            // Starting the service
            this.service            = new Thread(this);
            this.init();

            
        } catch (UnknownHostException | SocketException e) {

            // Printing exception
            System.out.println(e);

        }

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
        this.service = null;
    }




    /**
     * Thread method
     * 
     * Non-critical thread : 2s loop
     */
    @Override
    public void run () {

        // TODO : clean this
        System.out.println("Device IP " + this.serverIP);

        // Stop condition
        while (service != null) {

            // Get the start time of the loop iteration
            long startTime = System.currentTimeMillis();


            // Trying to send the packet
            try {

                // Sending packet
                this.socket.send(packet);


            } catch (IOException e) {

                // Printing exception
                System.out.println(e);

            }
            

            // THREAD LIMITER
            // ====================================================================================
            
            // Calculate how long the operations took
            long elapsedTime = System.currentTimeMillis() - startTime;


            // Calculate the remaining time to sleep
            long sleepTime = 2000 - elapsedTime;


            // If there is still time left in the 2s window, sleep
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
