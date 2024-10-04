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
     * Data shape :
     * 
     * {<@__ID__@><$__REQUEST_TYPE__$><#__CONTENT__#>}
     * 
     * 
     * 
     * EMpty content = "0"
     */

    // Hello phase
    SRV_HELLO   ("SRV_HELLO"),      // Server hello : {<@SERVER@><$SRV_HELLO$><#__client_id__#>}
    CLT_HELLO   ("CLT_HELLO"),      // Client hello : {<@__client_id__@><$CLT_HELLO$><#__player_pseudo__#>}


    // Ping request
    PING        ("PING"),           // Ping request : {<@__ID__@><$PING$><#0#>}
    PINGANSWER  ("PINGANSWER"),     // Ping request : {<@__ID__@><$PINGANSWER$><#0#>}


    // Disconnect request :
    //  - For client, its purpose is to inform the server that tha client is disconnecting
    //  - For server, its purpose is to force disconnect the client
    DISCONNECT  ("DISCONNECT"),


    // For unrecognized request
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
