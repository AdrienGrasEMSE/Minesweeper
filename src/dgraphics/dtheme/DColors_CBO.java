// Package declaration
package dgraphics.dtheme;


/**
 * Class that hold a set of colors for combo box
 * 
 * @author  AdrienG
 * @version 0.0
 * 
 * 
 * Its utilities is to specify a combo box colors using one object
 * 
 * Basically the same as DColors_BTN
 */
public class DColors_CBO extends DColors_BTN {

    /**
     * Constructor
     * 
     * @param BCK_D Hex int for the dark background
     * @param BCK_N Hex int for the neutral background
     * @param BCK_L Hex int for the light background
     * @param FNT_D Hex int for the dark font
     * @param FNT_L Hex int for the light font
     */
    public DColors_CBO(int BCK_D, int BCK_N, int BCK_L, int FNT_D, int FNT_L) {

        // Herited constructor
        super(BCK_D, BCK_N, BCK_L, FNT_D, FNT_L);

    }

}
