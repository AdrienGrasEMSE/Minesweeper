// Package declaration
package dgraphics.dtheme;

// Import
import java.awt.Color;


/**
 * Class that hold a set of colors for UI
 * 
 * @author  AdrienG
 * @version 0.0
 * 
 * 
 * Its utilities is to specify a UI colors using one object
 */
public class DColors_UI {

    /**
     * Attributes
     * 
     * They can be read by all other classes (public), but cannot be rewritten (final).
     * 
     * 
     * Name explanation :
     *  - BCK for background, used for background colors
     *  - D for dark
     *  - N for neutral
     *  - L for light
     */
    public final Color BCK_D;
    public final Color BCK_N;
    public final Color BCK_L;




    /**
     * Constructor
     * 
     * @param BCK_D Hex int for the dark background
     * @param BCK_N Hex int for the neutral background
     * @param BCK_L Hex int for the light background
     */
    public DColors_UI(int BCK_D, int BCK_N, int BCK_L) {

        // Data attribution
        DColors BCK_D_  = new DColors(BCK_D);
        DColors BCK_N_  = new DColors(BCK_N);
        DColors BCK_L_  = new DColors(BCK_L);
        this.BCK_D      = new Color(BCK_D_.r, BCK_D_.g, BCK_D_.b, BCK_D_.a);
        this.BCK_N      = new Color(BCK_N_.r, BCK_N_.g, BCK_N_.b, BCK_N_.a);
        this.BCK_L      = new Color(BCK_L_.r, BCK_L_.g, BCK_L_.b, BCK_L_.a);

    }

}
