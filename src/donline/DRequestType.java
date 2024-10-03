// Package declaration
package donline;


/**
 * Enum request type
 * 
 * @author  AdrienG
 * @version 0.0
 * 
 * 
 * Enum that list all request type possible, including UNRECOGNIZED
 */
public enum DRequestType {

    /**
     * Elements
     */
    HELLO       ("HELLO"),
    UNRECOGNIZED("UNRECOGNIZED");


    /**
     * Attribut to stock the string equivalent of the request type
     */
    private String string;




    /**
     * Enum constructor
     * 
     * @param string
     */
    private DRequestType(String string) {
        this.string = string;
    }



    
    /**
     * Getter
     * 
     * @return string equivalent of the request type
     */
    public String getString() {
        return this.string;
    }

}
