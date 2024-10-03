// Package declaration
package donline;


/**
 * Class interpreter
 * 
 * @author  AdrienG
 * @version 0.0
 * 
 * 
 * Class that read and interpret request received by the server
 */
public class DInterpreter {

    /**
     * Attribute
     */
    private String          senderUUID;
    private DRequestType    requestType = DRequestType.UNRECOGNIZED;
    private String          content;



    /**
     * Constructor
     */
    public DInterpreter() {}



    /**
     * Getter : to get the sender UUID
     * 
     * @return senderUUID
     */
    public String getSenderUUID() {
        return senderUUID;
    }




    /**
     * Getter : to get te request type
     * 
     * @return requestType
     */
    public DRequestType getRequestType() {
        return requestType;
    }




    /**
     * Getter : to get the request content
     * 
     * @return content
     */
    public String getContent() {
        return content;
    }




    /**
     * In case of bad request
     */
    private void badRequest(boolean idUsable) {

        // If the id is recognized
        if (!idUsable) {
            this.senderUUID     = "";
        }
        this.requestType    = DRequestType.UNRECOGNIZED;
        this.content        = "";

    }




    /**
     * Method that exctract a senderUUI from a request
     * 
     * @param request
     * @param rOpening  request opening
     * @param rEnding   request ending
     */
    private void getSenderUUID(String request, int rOpening, int rEnding) {

        // Seraching for '<@' to get the sender id
        int i = rOpening;
        while (i < rEnding && (request.charAt(i) != '<' || request.charAt(i + 1) != '@')) {
            i++;
        }


        // If we get it
        if (request.charAt(i) == '<' && request.charAt(i + 1) == '@') {

            // Jumping the <@ and getting the first character of the id
            senderUUID  =   String.valueOf(request.charAt(i + 2));
            i           +=  3;


            // Seraching for '@>' to get the end of the sender id
            while (i < rEnding && (request.charAt(i) != '@' || request.charAt(i + 1) != '>')) {
                senderUUID += request.charAt(i);
                i++;
            }


            // If we can't get it
            if (request.charAt(i) != '@' || request.charAt(i + 1) != '>') {

                // Bad request
                badRequest(false);

            }


        } else {
            
            // Bad request
            badRequest(false);

        }

    }




    /**
     * Method that exctract a request type from a request
     * 
     * @param request
     * @param rOpening  request opening
     * @param rEnding   request ending
     */
    private void getRequestType(String request, int rOpening, int rEnding) {

        // Seraching for '<$' to get the request type
        int     i               = rOpening;
        String  requestType_    = "";
        while (i < rEnding && (request.charAt(i) != '<' || request.charAt(i + 1) != '$')) {
            System.out.println(i + " : " + request.charAt(i));
            i++;
        }


        // If we get it
        if (request.charAt(i) == '<' && request.charAt(i + 1) == '$') {

            // Jumping the <$ and getting the first character of the request type
            requestType_    =   String.valueOf(request.charAt(i + 2));
            i               +=  3;


            // Seraching for '$>' to get the end of the request type
            while (i < rEnding && (request.charAt(i) != '$' || request.charAt(i + 1) != '>')) {
                requestType_ += request.charAt(i);
                i++;
            }


            // If we can't get it
            if (request.charAt(i) != '$' || request.charAt(i + 1) != '>') {

                // Bad request
                badRequest(true);

            } else {

                // Getting the request type
                for (DRequestType type : DRequestType.values()) {
                    
                    // Request type matching
                    if (requestType_.equals(type.getString())) {
                        requestType = type;
                    }

                }

            }


        } else {
            
            // Bad request
            badRequest(true);

        }

    }



    
    /**
     * Method that exctract a content from a request
     * 
     * @param request
     * @param rOpening  request opening
     * @param rEnding   request ending
     */
    private void getContent(String request, int rOpening, int rEnding) {

        // Seraching for '<#' to get the content
        int i = rOpening;
        while (i < rEnding && (request.charAt(i) != '<' || request.charAt(i + 1) != '#')) {
            i++;
        }


        // If we get it
        if (request.charAt(i) == '<' && request.charAt(i + 1) == '#') {

            // Jumping the <# and getting the first character of the content
            content =   String.valueOf(request.charAt(i + 2));
            i       +=  3;


            // Seraching for '#>' to get the end of the content
            while (i < rEnding && (request.charAt(i) != '#' || request.charAt(i + 1) != '>')) {
                content += request.charAt(i);
                i++;
            }


            // If we can't get it
            if (request.charAt(i) != '#' || request.charAt(i + 1) != '>') {

                // Bad request
                badRequest(true);

            }


        } else {
            
            // Bad request
            badRequest(true);

        }

    }




    /**
     * Interpret a request to separate sender ID, request type, request content
     * 
     * @param request
     */
    public void interpret(String request) {

        // Declaration
        int rOpening    = -1;
        int rEnding     = -1;


        // Seraching for '{' to get the request openning
        int i           = 0;
        while (i < request.length() - 1 && request.charAt(i) != '{') {
            i++;
        }


        // If we get it
        if (request.charAt(i) == '{') {
            rOpening = i;
        }

        
        // Seraching for '{' to get the request ending
        i               = request.length() - 1;
        while (i > 0 && request.charAt(i) != '}') {
            i--;
        }


        // If we get it
        if (request.charAt(i) == '}') {
            rEnding = i;
        }


        // Validation
        if (rOpening    <   rEnding &&
            rOpening    !=  -1      &&
            rEnding     !=  -1) {

            
            // Getting senderUUI
            this.getSenderUUID(request, rOpening, rEnding);


            // Getting request type
            this.getRequestType(request, rOpening, rEnding);


            // Getting senderUUI
            this.getContent(request, rOpening, rEnding);


        } else {

            // Bad request
            badRequest(false);

        }

    }




    /**
     * Build a request
     * 
     * @param senderUUID
     * @param requestType
     * @param content
     * @return
     */
    public String build(String senderUUID, DRequestType requestType, String content) {
        return "{<@" + senderUUID + "@><$" + requestType.getString() + "$><#" + content;
    }

}
