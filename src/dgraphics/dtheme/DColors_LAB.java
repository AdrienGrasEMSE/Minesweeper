// Package declaration
package dgraphics.dtheme;

// Import
import java.awt.Color;


/**
 * Class that hold a set of colors for labels
 * 
 * @author  AdrienG
 * @version 0.0
 * 
 * 
 * Its utilities is to specify a label colors using one object
 */
public class DColors_LAB {

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
    public final Color BCK_N;
    public final Color FNT_N;




    /**
     * Constructor
     * 
     * @param BCK_D Hex int for the neutral background
     * @param FNT_N Hex int for the neutal font
     */
    public DColors_LAB(int BCK_N, int FNT_N) {

        // Data attribution
        this.FNT_N      = new Color(FNT_N);


        // In case of transparent backgrounf
        if (BCK_N == 0) {
            this.BCK_N      = DTheme.TRSPCOL;
        } else {
            this.BCK_N      = new Color(BCK_N);
        }

    }

}
