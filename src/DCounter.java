
import static java.lang.Thread.sleep;


public class DCounter implements Runnable {

    /**
     * Counter attribute
     */
    private final   Thread  counter;
    private final   App     app;
    private         boolean isActive = false;




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

    }




    /**
     * Starting the counter
     */
    public void start() {
        counter.start();
    }




    /**
     * Engage the counter
     */
    public void activate() {
        isActive = true;
    }




    /**
     * Disengage the counter
     */
    public void deactivate() {
        isActive = false;
    }


    

    /**
     * 
     */
    @Override
    public void run () { 

        // Stop condition
        while (counter != null) {

            // Only count when it's active
            if (isActive) {

                // Counting every second
                try {

                    sleep(1000);
                    app.incrTimeSpent();


                } catch(InterruptedException e) {

                    System.err.println(e);

                }

            }

        }

    }

}
