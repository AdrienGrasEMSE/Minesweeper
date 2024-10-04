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
     * Empty content = "0"
     */

    /**
     * Hello phase
     * - Server hello   : {<@SERVER@><$SRV_HELLO$><#__client_id__#>}
     * - Client hello   : {<@__ID__@><$CLT_HELLO$><#__player_pseudo__#>}
     */
    HELLO_SRV           ("HELLO_SRV"),
    HELLO_CLT           ("HELLO_CLT"),


    /**
     * Ping requests
     * - Ping request   : {<@__ID__@><$PING$><#0#>}
     * - Ping answer    : {<@__ID__@><$PINGANSWER$><#0#>}
     */
    PING                ("PING"),
    PING_ANSWER         ("PING_ANSWER"),


    /**
     * Disconnect request
     * - Server request : {<@SERVER@><$DISCONNECT$><#__reason__#>}
     *      -> force disconnect a client for a reason
     * - Client request : {<@__ID__@><$DISCONNECT$><#0#>}
     *      -> Inform the server that the client is disconnecting
     */
    DISCONNECT          ("DISCONNECT"),


    /**
     * Server ownership
     * - ASKOWNERSHIP       : {<@__ID__@><$CLT_HELLO$><#__SERVER_ID_#>}
     * - OWNERSHIPREFUSED   : {<@SERVER@><$OWNERSHIPREFUSED$><#0#>}
     * - OWNERSHIPGRANTED   : {<@SERVER@><$OWNERSHIPGRANTED$><#0#>}
     * 
     * Explanation :
     *      The server is owned by an application. But by default, no one is connected,
     * including the app which launch the server. So we need a system of ownership.
     *      The application which created the server now its UUID. So, when asking
     * ownership, it transmit the server UUID which has been never send to anyone.
     *      If the server UUID and the sent one are the same, then the ownership is
     * granted.
     */
    OWNERSHIP_ASK       ("OWNERSHIP_ASK"),
    OWNERSHIP_REFUSED   ("OWNERSHIP_REFUSED"),
    OWNERSHIP_GRANTED   ("OWNERSHIP_GRANTED"),


    // For unrecognized request
    UNRECOGNIZED        ("UNRECOGNIZED");


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
