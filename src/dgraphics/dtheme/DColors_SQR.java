// Package declaration
package dgraphics.dtheme;

// Import
import java.awt.Color;


/**
 * Class that hold a set of colors for mine squares
 * 
 * @author  AdrienG
 * @version 0.0
 * 
 * 
 * Its utilities is to specify a mine square colors using one object
 */
public class DColors_SQR {

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
     *  - M for mined square
     *  - P for pinned square
     *  - x = 1 to 9 for the number of mines around
     */


    /**
     * Square colors
     */
    public final Color BCK_D;
    public final Color BCK_N;
    public final Color BCK_L;
    public final Color BCK_M;



    /**
     * Square coefficient
     */
    public final Color FNT_1;
    public final Color FNT_2;
    public final Color FNT_3;
    public final Color FNT_4;
    public final Color FNT_5;
    public final Color FNT_6;
    public final Color FNT_7;
    public final Color FNT_8;
    public final Color FNT_M;
    public final Color FNT_P;




    /**
     * Constructor
     * 
     * @param BCK_D Hex int for the dark background
     * @param BCK_N Hex int for the neutral background
     * @param BCK_L Hex int for the light background
     * @param FNT_D Hex int for the dark font
     * @param FNT_L Hex int for the light font
     */
    public DColors_SQR( int BCK_D,
                        int BCK_N,
                        int BCK_L,
                        int BCK_M,
                        int FNT_1,
                        int FNT_2,
                        int FNT_3,
                        int FNT_4,
                        int FNT_5,
                        int FNT_6,
                        int FNT_7,
                        int FNT_8,
                        int FNT_M,
                        int FNT_P
                    ) {

        // Data attribution
        this.BCK_D = new Color(BCK_D);
        this.BCK_N = new Color(BCK_N);
        this.BCK_L = new Color(BCK_L);
        this.BCK_M = new Color(BCK_M);
        this.FNT_1 = new Color(FNT_1);
        this.FNT_2 = new Color(FNT_2);
        this.FNT_3 = new Color(FNT_3);
        this.FNT_4 = new Color(FNT_4);
        this.FNT_5 = new Color(FNT_5);
        this.FNT_6 = new Color(FNT_6);
        this.FNT_7 = new Color(FNT_7);
        this.FNT_8 = new Color(FNT_8);
        this.FNT_M = new Color(FNT_M);
        this.FNT_P = new Color(FNT_P);

    }

}
