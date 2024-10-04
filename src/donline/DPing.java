// Pakcage delcaration
package donline;

// Import
import static java.lang.Thread.sleep;


/**
 * Deminer Counter
 * 
 * @author  AdrienG
 * @version 0.0
 * 
 * 
 * Class that allow a connexion handler to ping the other part.
 * It is the start point of the connexion check system.
 */
public class DPing implements Runnable {

    /**
     * Counter attribute
     */
    private final   String              uuid;
    private final   DInterpreter        interpret   = new DInterpreter();
    private final   DConnexionHandler   handler;
    private         Thread              counter;
    private         boolean             pingAnswered;




    /**
     * Constructor
     * 
     * @param app
     */
    public DPing(String uuid, DConnexionHandler handler) {

        // New thread
        counter = new Thread(this);


        // Getting attributes
        this.uuid       = uuid;
        this.handler    = handler;

    }




    /**
     * Peer has answered
     */
    public void answerPing() {
        pingAnswered = true;
    }




    /**
     * Start pinging
     */
    public void start() {
        counter.start();
    }




    /**
     * Stop counter
     */
    public void stop() {
        counter = null;
    }
    


    
    /**
     * Thread method
     */
    @Override
    public void run () {

        // Stop condition
        while (counter != null) {

            // Counting every second
            try {

                // Send ping
                pingAnswered    = false;
                handler.addRequest(interpret.build(uuid, DRequestType.PING, ""));


                // 5 sec pause
                sleep(5000);


                // Getting answer
                if (!pingAnswered) {

                    // Shuting down the connexion handler
                    handler.shutDown();

                }


                // 5 sec pause
                sleep(5000);


            } catch(InterruptedException e) {

                // Printing exception
                System.out.println(e);

            }

        }

    }

}
