// Package declaration
package dgraphics.dtheme;

// Import
import java.awt.Color;


/**
 * Class that hold a set of colors for input fields
 * 
 * @author  AdrienG
 * @version 0.0
 * 
 * 
 * Its utilities is to specify a input field colors using one object
 */
public class DColors_FLD {

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
    public final Color BCK_L;
    public final Color FNT_N;




    /**
     * Constructor
     * 
     * @param BCK_D Hex int for the dark background
     * @param BCK_L Hex int for the light background
     * @param FNT_N Hex int for the neutal font
     */
    public DColors_FLD(int BCK_D, int BCK_L, int FNT_N) {

        // Data attribution
        this.BCK_D = new Color(BCK_D);
        this.BCK_L = new Color(BCK_L);
        this.FNT_N = new Color(FNT_N);

    }

}
