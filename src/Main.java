// Import
// import static java.lang.Thread.sleep;

import deminer.DController;


/**
 * Class Main
 * 
 * @author  AdrienG
 * @version 0.0
 * 
 * 
 * Class wich launch the application
 */
public class Main {

    /**
     * Main method
     * 
     * @param args
     */
    public static void main(String[] args) {

        // Starting point
        System.out.println("DEMINER : Starting point");


        // App instanciation
        DController app = new DController();

        // try {
        //     sleep(1000);
        // } catch (InterruptedException e) {

        // }
        app.init();


        // Ending point
        System.out.println("DEMINER : Ending point");
        
    }

}
