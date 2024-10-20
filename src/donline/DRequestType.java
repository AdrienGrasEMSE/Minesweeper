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
     * Empty UUID                   -> Bad request
     * Request type = UNRECOGNIZED  -> Bad request
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
     * - Ping request   : {<@__ID__@><$PING$><##>}
     * - Ping answer    : {<@__ID__@><$PINGANSWER$><##>}
     */
    PING                ("PING"),
    PING_ANSWER         ("PING_ANSWER"),


    /**
     * Disconnect request
     * - Server request : {<@SERVER@><$DISCONNECT$><#__reason__#>}
     *      -> force disconnect a client for a reason
     * 
     * - Client request : {<@__ID__@><$DISCONNECT$><##>}
     *      -> Inform the server that the client is disconnecting
     */
    DISCONNECT          ("DISCONNECT"),


    /**
     * Server ownership
     * - ASKOWNERSHIP       : {<@__ID__@><$CLT_HELLO$><#__SERVER_ID_#>}
     * - OWNERSHIPREFUSED   : {<@SERVER@><$OWNERSHIPREFUSED$><##>}
     * - OWNERSHIPGRANTED   : {<@SERVER@><$OWNERSHIPGRANTED$><##>}
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


    /**
     * Waiting screen infos
     */
    PLAYER_LIST         ("PLAYER_LIST"), 
    SERVER_OWNER        ("SERVER_OWNER"),


    /**
     * Game infos
     */
    GAME_LAUNCH_ASK     ("GAME_LAUNCH_ASK"),
    GAME_LAUNCH         ("GAME_LAUNCH"),
    GAME_READY          ("GAME_READY"),
    GAME_WIN            ("GAME_WIN"),
    GAME_LOST           ("GAME_LOST"),
    GAME_ABORTED        ("GAME_ABORTED"),


    /**
     * Field info
     */
    FIELD_SIZE          ("FIELD_SIZE"),
    FIELD_READY         ("FIELD_READY"),


    /**
     * Ingame action
     */
    SPRITE_REVEAL       ("SPRITE_REVEAL"),
    SPRITE_CLICKED      ("SPRITE_CLICKED"),
    SCORE               ("SCORE"),
    PLAYER_HAS_LOST     ("PLAYER_HAS_LOST"),


    /**
     * For unrecognized request
     */
    UNRECOGNIZED        ("UNRECOGNIZED");




    /**
     * Attribut to stock the string equivalent of the request type
     */
    private final String string;




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
