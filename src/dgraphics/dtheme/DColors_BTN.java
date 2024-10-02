// Package declaration
package dgraphics.dtheme;

// Import
import java.awt.Color;


/**
 * Class that hold a set of colors for buttons
 * 
 * @author  AdrienG
 * @version 0.0
 * 
 * 
 * Its utilities is to specify a button colors using one object
 */
public class DColors_BTN {

    /**
     * Attributes
     * 
     * They can be read by all other classes (public), but cannot be rewritten (final).
     * 
     * 
     * Name explanation :
     *  - BCK for background, used for background colors
     *  - FNT for font, used for font colors
     *  - D for dark
     *  - N for neutral
     *  - L for light
     */
    public final Color BCK_D;
    public final Color BCK_N;
    public final Color BCK_L;
    public final Color FNT_D;
    public final Color FNT_L;




    /**
     * Constructor
     * 
     * @param BCK_D Hex int for the dark background
     * @param BCK_N Hex int for the neutral background
     * @param BCK_L Hex int for the light background
     * @param FNT_D Hex int for the dark font
     * @param FNT_L Hex int for the light font
     */
    public DColors_BTN(int BCK_D, int BCK_N, int BCK_L, int FNT_D, int FNT_L) {

        // Data attribution
        this.BCK_D = new Color(BCK_D);
        this.BCK_N = new Color(BCK_N);
        this.BCK_L = new Color(BCK_L);
        this.FNT_D = new Color(FNT_D);
        this.FNT_L = new Color(FNT_L);

    }

}
