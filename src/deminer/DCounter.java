// Package declaration
package deminer;

// Import
import static java.lang.Thread.sleep;


/**
 * Deminer Counter
 * 
 * @author  AdrienG
 * @version 0.0
 * 
 * 
 * Class that allow the deminer to be equiped with a counter
 */
public class DCounter implements Runnable {

    /**
     * Counter attribute
     */
    private         Thread  counter;
    private final   DController     app;




    /**
     * Constructor
     * 
     * @param app
     */
    public DCounter(DController app) {

        // New thread
        counter = new Thread(this);


        // Getting attributes
        this.app = app;


        // Starting the thread
        this.init();

    }




    /**
     * Initialise the thread
     */
    private void init() {
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

                // 1 sec pause
                sleep(1000);
                app.incrTimeSpent();


            } catch(InterruptedException e) {

                // Printing exception
                System.err.println(e);

            }

        }

    }

}
