
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable{


    /**
     * Attributes
     */
    private         boolean         serverOnline;
    private final   Thread          thread;
    private         ServerSocket    gestSock    = null;
    private final   Socket[]        clientList  = new Socket[10];
    private static  int             cpt         = 0;




    /**
     * Thread method
     */
    @Override
    public void run () {

        // Stop condition
        while (thread != null) {

            // Waiting connection
            try {

                // New socket
                Socket socket = gestSock.accept();
                

                // Refer socket (= client)
                clientList[cpt] = socket;
                cpt ++;


                // Open stream
                DataInputStream     in  = new DataInputStream   (socket.getInputStream());
                DataOutputStream    out = new DataOutputStream  (socket.getOutputStream());


                // Data reading
                String playerName = in.readUTF();
                System.out.println(playerName + "connected");


                // Data sending
                out.writeInt(0);


                // New thread
                Thread newThread = new Thread(this);
                newThread.start();
                
                
            } catch (IOException e) {

                // Printing exception
                System.err.println(e);

            }
                      
        }

    }



    
    /**
     * Constructor
     */
    public Server() {

        // New thread
        thread = new Thread(this);


        // Trying to create a new socket manager, and a new socket on the port 10 000
        try {

            // Socket creation
            gestSock = new ServerSocket(10000);


            // Thread initialization
            this.init();


            // Server online
            serverOnline = true;

            
        } catch (IOException e) {
            
            // Printing exception
            System.err.println(e);


            // Server not online
            serverOnline = false;
        
        }

    }




    /**
     * Start thread services
     */
    public final void init() {
        thread.start();
    }




    /**
     * Getter : to check if the server is online or not
     * 
     * @return serverOnline : flag that show if the server online or not
     */
    public boolean isOnline() {
        return serverOnline;
    }





    public static void main(String [] args) {

        // Starting point
        System.out.println("SERVER : Starting point");


        // Starting server
        Server srv = new Server();


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