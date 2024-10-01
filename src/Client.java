import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    public static void main(String [] args ) {

        // Starting point
        System.out.println("CLIENT : Starting point");


        // Trying to open a socket on the port 10 000
        try (Socket sock = new Socket("localhost",10000)) {
            
            // ouverture de la socket et des streams
            DataInputStream     in  = new DataInputStream   (sock.getInputStream());
            DataOutputStream    out = new DataOutputStream  (sock.getOutputStream());


            // Data sending
            out.writeUTF("Oskur77");
            

            // Data reading
            int numJoueur = in.readInt(); // reception d’un nombre
            System.out.println("Player no. :" + numJoueur);

            // Closing all
            // in  .close();
            // out .close();
            // sock.close();
            

        } catch (UnknownHostException e) {
            
            System.out.println("R2D2 est inconnue");


        } catch (IOException e) {

            // Printing exception
            System.err.println(e);
        
        }


        // Ending point
        System.out.println("CLIENT : Ending point");

    }

} 