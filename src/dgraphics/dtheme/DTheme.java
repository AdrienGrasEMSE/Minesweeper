// Package declaration
package dgraphics.dtheme;

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
     *  - GUI   : Graphical User Interfaces
     *  - DLG   : Dialog Interfaces
     *  - BTN   : Buttons
     *  - FLD   : Fields (text field / int)
     *  - LAB   : Labels
     *  - CBO   : Combo box
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
     *  - TRS   : transparent
     */



    /**
     * Transparent color
     */
    public static Color         TRSPCOL = new Color(0, 0, 0, 0);
    

    /**
     * GUI colors
     */
    public static DColors_UI    GUI_VAR = new DColors_UI(0xE6A397, 0xD76C58, 0x7F5056);
    public static DColors_UI    GUI_NTL = new DColors_UI(0x222D41, 0x374158, 0x828897);
    public static DColors_UI    GUI_DRK = new DColors_UI(0x0F1219, 0x11151D, 0x1D1F24);
    

    /**
     * Dialog interface colors
     */
    public static DColors_UI    DLG_DRK = new DColors_UI(0xE00F1219, 0xE011151D, 0xE01D1F24);


    /**
     * Button colors
     */
    public static DColors_BTN   BTN_RED = new DColors_BTN(0xCB4335, 0xE74C3C, 0xEA5E50, 0xD5D8DC, 0xEAECEE);
    public static DColors_BTN   BTN_GRN = new DColors_BTN(0x239B56, 0x28B463, 0x2ECC71, 0xD5D8DC, 0xEAECEE);
    public static DColors_BTN   BTN_VAR = new DColors_BTN(0x8E5556, 0xAB5E57, 0xC86758, 0xD5D8DC, 0xEAECEE);
    public static DColors_BTN   BTN_NTL = new DColors_BTN(0x222D41, 0x374158, 0x828897, 0xD5D8DC, 0xEAECEE);
    public static DColors_BTN   BTN_DRK = new DColors_BTN(0x0F1219, 0x11151D, 0x2F3239, 0xD5D8DC, 0xEAECEE);


    /**
     * Field colors
     */
    public static DColors_FLD   FLD_NTL = new DColors_FLD(0x222D41, 0x374158, 0xEAECEE);


    /**
     * Label colors
     */
    public static DColors_LAB   LAB_TRS = new DColors_LAB(0x00000000, 0xD5D8DC);
    public static DColors_LAB   LAB_NTL = new DColors_LAB(0xAB5E57, 0xD5D8DC);


    /**
     * Combo colors
     */
    public static DColors_CBO   CBO_VAR = new DColors_CBO(0x8E5556, 0xAB5E57, 0xC86758, 0xD5D8DC, 0xEAECEE);


    /**
     * Square colors
     */
    public static DColors_SQR   SQR_NTL = new DColors_SQR(  0x444D63,
                                                            0x697082,
                                                            0x828897,
                                                            0xCB4335,
                                                            0x2980b9,
                                                            0x16a085,
                                                            0x1abc9c,
                                                            0x2ecc71,
                                                            0xf1c40f,
                                                            0xf39c12,
                                                            0xe67e22,
                                                            0xd35400,
                                                            0xF9EBEA,
                                                            0xFFFFFF);




    /**
     * Constructor
     */
    public DTheme() {}




    /**
     * Apply default theme
     */
    public static void default_() {

        /**
         * Transparent color
         */
        TRSPCOL = new Color(0, 0, 0, 0);
        

        /**
         * GUI colors
         */
        GUI_VAR = new DColors_UI(0xE6A397, 0xD76C58, 0x7F5056);
        GUI_NTL = new DColors_UI(0x222D41, 0x374158, 0x828897);
        GUI_DRK = new DColors_UI(0x0F1219, 0x11151D, 0x1D1F24);
        

        /**
         * Dialog interface colors
         */
        DLG_DRK = new DColors_UI(0xE00F1219, 0xE011151D, 0xE01D1F24);


        /**
         * Button colors
         */
        BTN_RED = new DColors_BTN(0xCB4335, 0xE74C3C, 0xEA5E50, 0xD5D8DC, 0xEAECEE);
        BTN_GRN = new DColors_BTN(0x239B56, 0x28B463, 0x2ECC71, 0xD5D8DC, 0xEAECEE);
        BTN_VAR = new DColors_BTN(0x8E5556, 0xAB5E57, 0xC86758, 0xD5D8DC, 0xEAECEE);
        BTN_NTL = new DColors_BTN(0x222D41, 0x374158, 0x828897, 0xD5D8DC, 0xEAECEE);
        BTN_DRK = new DColors_BTN(0x0F1219, 0x11151D, 0x2F3239, 0xD5D8DC, 0xEAECEE);


        /**
         * Field colors
         */
        FLD_NTL = new DColors_FLD(0x222D41, 0x374158, 0xEAECEE);


        /**
         * Label colors
         */
        LAB_TRS = new DColors_LAB(0x00000000, 0xD5D8DC);
        LAB_NTL = new DColors_LAB(0xAB5E57, 0xD5D8DC);


        /**
         * Combo colors
         */
        CBO_VAR = new DColors_CBO(0x8E5556, 0xAB5E57, 0xC86758, 0xD5D8DC, 0xEAECEE);


        /**
         * Square colors
         */
        SQR_NTL = new DColors_SQR(  0x444D63,
                                    0x697082,
                                    0x828897,
                                    0xCB4335,
                                    0x2980b9,
                                    0x16a085,
                                    0x1abc9c,
                                    0x2ecc71,
                                    0xf1c40f,
                                    0xf39c12,
                                    0xe67e22,
                                    0xd35400,
                                    0xF9EBEA,
                                    0xFFFFFF);

    }



    
    /**
     * Apply default theme
     */
    public static void lightMode() {

        /**
         * Transparent color
         */
        TRSPCOL = new Color(0, 0, 0, 0);
        

        /**
         * GUI colors
         */
        GUI_VAR = new DColors_UI(0xE6A397, 0xD76C58, 0x7F5056);
        GUI_NTL = new DColors_UI(0x6f7474, 0xb1bab9, 0xcfd9d7);
        GUI_DRK = new DColors_UI(0x0f3431, 0x18534F, 0x226D68);
        

        /**
         * Dialog interface colors
         */
        DLG_DRK = new DColors_UI(0xE00f3431, 0xE018534F, 0xE0226D68);


        /**
         * Button colors
         */
        BTN_RED = new DColors_BTN(0xCB4335, 0xE74C3C, 0xEA5E50, 0xD5D8DC, 0xEAECEE);
        BTN_GRN = new DColors_BTN(0x239B56, 0x28B463, 0x2ECC71, 0xD5D8DC, 0xEAECEE);
        BTN_VAR = new DColors_BTN(0x8E5556, 0xAB5E57, 0xC86758, 0xD5D8DC, 0xEAECEE);
        BTN_NTL = new DColors_BTN(0x2e6f64, 0x397268, 0x517670, 0xD5D8DC, 0xEAECEE);
        BTN_DRK = new DColors_BTN(0x0F1219, 0x11151D, 0x2F3239, 0xD5D8DC, 0xEAECEE);


        /**
         * Field colors
         */
        FLD_NTL = new DColors_FLD(0x2e6f64, 0x397268, 0xEAECEE);


        /**
         * Label colors
         */
        LAB_TRS = new DColors_LAB(0x00000000, 0xD5D8DC);
        LAB_NTL = new DColors_LAB(0xAB5E57, 0xD5D8DC);


        /**
         * Combo colors
         */
        CBO_VAR = new DColors_CBO(0x8E5556, 0xAB5E57, 0xC86758, 0xD5D8DC, 0xEAECEE);


        /**
         * Square colors
         */
        SQR_NTL = new DColors_SQR(  0x9ba3a2,
                                    0x6f7474,
                                    0x585d5c,
                                    0xCB4335,
                                    0x2470a2,
                                    0x107864,
                                    0x148d75,
                                    0x239955,
                                    0xf1c40f,
                                    0xf39c12,
                                    0xe67e22,
                                    0xd35400,
                                    0xF9EBEA,
                                    0x373737);

    }




    /**
     * Apply the highlightCoefficient theme
     * 
     * Makes all mines coefficient white
     */
    public static void highlightCoefficient() {
        
        /**
         * Square colors
         */
        SQR_NTL = new DColors_SQR(  0x444D63,
                                    0x697082,
                                    0x828897,
                                    0xCB4335,
                                    0xFFFFFF,
                                    0xFFFFFF,
                                    0xFFFFFF,
                                    0xFFFFFF,
                                    0xFFFFFF,
                                    0xFFFFFF,
                                    0xFFFFFF,
                                    0xFFFFFF,
                                    0xFFFFFF,
                                    0xFFFFFF);

    }

}
