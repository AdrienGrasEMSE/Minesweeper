// Package declaration
package dgui.dui_online;

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
public class DUI_Online_Updater implements Runnable {

    /**
     * Counter attribute
     */
    private         Thread          counter;
    private final   DUI_Updatable   ui;




    /**
     * Constructor
     * 
     * @param ui
     */
    public DUI_Online_Updater(DUI_Updatable ui) {

        // New thread
        counter = new Thread(this);


        // Getting attributes
        this.ui = ui;


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

                // 0.5 sec pause
                sleep(500);
                ui.updatableAction();


            } catch(InterruptedException e) {

                // Printing exception
                System.err.println(e);

            }

        }

    }

}
