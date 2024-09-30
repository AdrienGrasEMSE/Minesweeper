// Package declaration
package deminer_graphic;

// Import
import java.awt.Color;


/**
 * Class that hold all the project colors
 * 
 * @author  AdrienG
 * @version 0.0
 */
public class DTheme {

    /**
     * Color name code : <Domain>_<Color>_<Variation>
     * 
     * <Domain> : in 3 letter, the application domain of the color
     * 
     * <Domain> list :
     *  - GUI   : Graphical User Interface
     *  - BTN   : Button
     *  - FNT   : Font
     *  - SQR   : Squares
     * 
     * 
     * 
     * 
     * <Color> : in 3 letter
     * 
     * <Color> list :
     *  - BLK   : black
     *  - GRA   : gray
     *  - WHT   : white
     *  - RED   : red
     *  - ORA   : orange
     *  - GLD   : gold
     *  - YEL   : yellow
     *  - GRN   : green
     *  - CYN   : cyan
     *  - BLU   : blue
     *  - PRP   : purple
     *  - PNK   : pink
     * 
     *  - VAR   : variation color
     *  - NTL   : neutral color
     *  - DRK   : dark color
     * 
     *  - COF   : squares coefficient
     * 
     * 
     * 
     * 
     * <Variation> : specify if it's a light, normal or dark color
     * 
     * <Variation> list :
     *  - L     : light
     *  - N     : neutral
     *  - D     : dark
     * 
     *  - x     : squares coefficient (x in 1 - 9)
     *  - M     : mines
     */


    

    /**
     * GUI colors
     */

    // Variation color
    public static Color GUI_VAR_L = new Color(0xE6A397);
    public static Color GUI_VAR_N = new Color(0xD76C58);
    public static Color GUI_VAR_D = new Color(0x7F5056);

    // Neutral color
    public static Color GUI_NTL_L = new Color(0x828897);
    public static Color GUI_NTL_N = new Color(0x374158);
    public static Color GUI_NTL_D = new Color(0x222D41);

    // Dark color
    public static Color GUI_DRK_L = new Color(0x1D1F24);
    public static Color GUI_DRK_N = new Color(0x11151D);
    public static Color GUI_DRK_D = new Color(0x0F1219);




    /**
     * Button colors
     */

    // Red button
    public static Color BTN_RED_L = new Color(0xEA5E50);
    public static Color BTN_RED_N = new Color(0xE74C3C);
    public static Color BTN_RED_D = new Color(0xCB4335);

    // Green button
    public static Color BTN_GRN_L = new Color(0x2ECC71);
    public static Color BTN_GRN_N = new Color(0x28B463);
    public static Color BTN_GRN_D = new Color(0x239B56);

    // Variation color
    public static Color BTN_VAR_L = new Color(0xC86758);
    public static Color BTN_VAR_N = new Color(0xAB5E57);
    public static Color BTN_VAR_D = new Color(0x8E5556);

    // Neutral color
    public static Color BTN_NTL_L = new Color(0x828897);
    public static Color BTN_NTL_N = new Color(0x374158);
    public static Color BTN_NTL_D = new Color(0x222D41);

    // Dark color
    public static Color BTN_DRK_L = new Color(0x2F3239);
    public static Color BTN_DRK_N = new Color(0x11151D);
    public static Color BTN_DRK_D = new Color(0x0F1219);




    /**
     * Square colors
     */
    public static Color SQR_NTL_D = new Color(0x444D63);
    public static Color SQR_NTL_N = new Color(0x697082);
    public static Color SQR_NTL_L = new Color(0x828897);
    public static Color SQR_NTL_M = new Color(0xCB4335);





    /**
     * Font color
     */
    
    // Variation color
    public static Color FNT_VAR_L = new Color(0xE6A397);
    public static Color FNT_VAR_N = new Color(0xD76C58);
    public static Color FNT_VAR_D = new Color(0x7F5056);

    // Neutral color
    public static Color FNT_NTL_L = new Color(0xFFFFFF);
    public static Color FNT_NTL_N = new Color(0xEAECEE);
    public static Color FNT_NTL_D = new Color(0xD5D8DC);

    // Dark color
    public static Color FNT_DRK_L = new Color(0x2F3239);
    public static Color FNT_DRK_N = new Color(0x11151D);
    public static Color FNT_DRK_D = new Color(0x0F1219);

    // Square coefficient
    public static Color FNT_COF_1 = new Color(0x2980b9);
    public static Color FNT_COF_2 = new Color(0x16a085);
    public static Color FNT_COF_3 = new Color(0x1abc9c);
    public static Color FNT_COF_4 = new Color(0x2ecc71);
    public static Color FNT_COF_5 = new Color(0xf1c40f);
    public static Color FNT_COF_6 = new Color(0xf39c12);
    public static Color FNT_COF_7 = new Color(0xe67e22);
    public static Color FNT_COF_8 = new Color(0xd35400);
    public static Color FNT_COF_9 = new Color(0xe74c3c);
    public static Color FNT_COF_M = new Color(0xF9EBEA);




    /**
     * Constructor
     */
    public DTheme() {}




    /**
     * Apply default theme
     */
    public static void default_() {

        /**
         * GUI colors
         */

        // Variation color
        GUI_VAR_L = new Color(0xE6A397);
        GUI_VAR_N = new Color(0xD76C58);
        GUI_VAR_D = new Color(0x7F5056);

        // Neutral color
        GUI_NTL_L = new Color(0x828897);
        GUI_NTL_N = new Color(0x374158);
        GUI_NTL_D = new Color(0x222D41);

        // Dark color
        GUI_DRK_L = new Color(0x1D1F24);
        GUI_DRK_N = new Color(0x11151D);
        GUI_DRK_D = new Color(0x0F1219);




        /**
         * Button colors
         */

        // Red button
        BTN_RED_L = new Color(0xEA5E50);
        BTN_RED_N = new Color(0xE74C3C);
        BTN_RED_D = new Color(0xCB4335);

        // Green button
        BTN_GRN_L = new Color(0x2ECC71);
        BTN_GRN_N = new Color(0x28B463);
        BTN_GRN_D = new Color(0x239B56);

        // Variation color
        BTN_VAR_L = new Color(0xC86758);
        BTN_VAR_N = new Color(0xAB5E57);
        BTN_VAR_D = new Color(0x8E5556);

        // Neutral color
        BTN_NTL_L = new Color(0x828897);
        BTN_NTL_N = new Color(0x374158);
        BTN_NTL_D = new Color(0x222D41);

        // Dark color
        BTN_DRK_L = new Color(0x2F3239);
        BTN_DRK_N = new Color(0x11151D);
        BTN_DRK_D = new Color(0x0F1219);




        /**
         * Square colors
         */
        SQR_NTL_D = new Color(0x444D63);
        SQR_NTL_N = new Color(0x697082);
        SQR_NTL_L = new Color(0x828897);
        SQR_NTL_M = new Color(0xCB4335);





        /**
         * Font color
         */
        
        // Variation color
        FNT_VAR_L = new Color(0xE6A397);
        FNT_VAR_N = new Color(0xD76C58);
        FNT_VAR_D = new Color(0x7F5056);

        // Neutral color
        FNT_NTL_L = new Color(0xFFFFFF);
        FNT_NTL_N = new Color(0xEAECEE);
        FNT_NTL_D = new Color(0xD5D8DC);

        // Dark color
        FNT_DRK_L = new Color(0x2F3239);
        FNT_DRK_N = new Color(0x11151D);
        FNT_DRK_D = new Color(0x0F1219);

        // Square coefficient
        FNT_COF_1 = new Color(0x2980b9);
        FNT_COF_2 = new Color(0x16a085);
        FNT_COF_3 = new Color(0x1abc9c);
        FNT_COF_4 = new Color(0x2ecc71);
        FNT_COF_5 = new Color(0xf1c40f);
        FNT_COF_6 = new Color(0xf39c12);
        FNT_COF_7 = new Color(0xe67e22);
        FNT_COF_8 = new Color(0xd35400);
        FNT_COF_9 = new Color(0xe74c3c);
        FNT_COF_M = new Color(0xF9EBEA);

    }




    /**
     * Apply the highlightCoefficient theme
     * 
     * Makes all mines coefficient white
     */
    public static void highlightCoefficient() {

        /**
         * Font color
         */
        
        // Square coefficient
        FNT_COF_1 = new Color(0xFFFFFF);
        FNT_COF_2 = new Color(0xFFFFFF);
        FNT_COF_3 = new Color(0xFFFFFF);
        FNT_COF_4 = new Color(0xFFFFFF);
        FNT_COF_5 = new Color(0xFFFFFF);
        FNT_COF_6 = new Color(0xFFFFFF);
        FNT_COF_7 = new Color(0xFFFFFF);
        FNT_COF_8 = new Color(0xFFFFFF);
        FNT_COF_9 = new Color(0xFFFFFF);
        FNT_COF_M = new Color(0xFFFFFF);

    }

}
