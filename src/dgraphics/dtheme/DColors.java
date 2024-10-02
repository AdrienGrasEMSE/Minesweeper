// Package declaration
package dgraphics.dtheme;

/**
 * This class is onlys used to convert hex color code to rgba.
 * 
 * @author  AdrienG
 * @version 0.0
 * 
 * 
 * I did not found out why, but hex code including alpha canal do not
 * work properly, so I created this converter.
 * 
 * This converter can take two type of hex code :
 *  - 0xRRGGBB
 *  - 0xAARRGGBB
 */
public class DColors {

    /*
     * Attributes
     */
    public final int r;
    public final int g;
    public final int b;
    public final int a;



    /**
     * Constructor
     * 
     * @param packedInt
     */
    public DColors(int packedInt) {

        // Is it a 32bit color ?
        if ((packedInt & 0xFF000000) != 0) {

            // Yes : making the conversion
            a = (packedInt >> 24) & 0xFF;


        } else {

            // No : the color is opaque
            a = 255;
            
        }


        // Data attribution
        r = (packedInt >> 16)   & 0xFF;
        g = (packedInt >> 8)    & 0xFF;
        b = packedInt           & 0xFF;

    }

}
