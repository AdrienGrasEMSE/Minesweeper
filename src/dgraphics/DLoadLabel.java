// Package declaration
package dgraphics;

// Import
import static java.lang.Thread.sleep;
import dgraphics.dtheme.DColors_LAB;


/**
 * Class Load Label
 * 
 * @author  AdrienG
 * @version 0.0
 * 
 * 
 * This label act like a normal DLabel but has a custom animation
 * at its end.
 * 
 * The animation has 4 steps :
 * - label 
 * - label. 
 * - label..
 * - label...
 */
public class DLoadLabel extends DLabel implements Runnable {

    /**
     * Attributes
     */
    private         Thread    animationService;
    private final   String    label;


    /**
     * Constructor with a background color
     * 
     * @param text          Label's text
     * @param selectedFont  Selected font from DeminerFont
     * @param fontSize      Font size
     * @param fontColor     Font color
     */
    public DLoadLabel(String label, DFont selectedFont, int fontSize, DColors_LAB colorSet) {

        // Herited constructor
        super(label + "...", selectedFont, fontSize, colorSet);


        // Getting attributes
        this.label = label;


        // Starting animation service
        this.animationService   = new Thread(this);
        this.animationService.start();

    }




    /**
     * Stop animation
     */
    public void stop() {
        animationService = null;
    }



    
    /**
     * Thread method
     */
    @Override
    public void run() {
        
        // Stop condition
        while (animationService != null) {

            // Counting
            try {

                // 0.5 sec pause
                sleep(500);
                this.setText(label + ".");

            } catch(InterruptedException e) {

                // Printing exception
                System.err.println(e);

            }


            // Couting
            try {

                // 0.5 sec pause
                sleep(500);
                this.setText(label + "..");

            } catch(InterruptedException e) {

                // Printing exception
                System.err.println(e);

            }


            // Counting
            try {

                // 0.5 sec pause
                sleep(500);
                this.setText(label + "...");

            } catch(InterruptedException e) {

                // Printing exception
                System.err.println(e);

            }


            // Counting
            try {

                // 0.5 sec pause
                sleep(500);
                this.setText(label);

            } catch(InterruptedException e) {

                // Printing exception
                System.err.println(e);

            }

        }

    }

}
