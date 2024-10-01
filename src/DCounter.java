// Import
import static java.lang.Thread.sleep;


/**
 * Deminer Counter : class that allow the deminer to be equiped with a counter
 * 
 * @author  AdrienG
 * @version 0.0
 */
public class DCounter implements Runnable {

    /**
     * Counter attribute
     */
    private         Thread  counter;
    private final   App     app;




    /**
     * Constructor
     * 
     * @param app
     */
    public DCounter(App app) {

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
