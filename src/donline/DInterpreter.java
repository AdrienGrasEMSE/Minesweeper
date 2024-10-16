// Package declaration
package donline;

// Import
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
     * Data pattern :
     * 
     * {<@___ID___@><$___REQUEST_TYPE___$><#___CONTENT___#>}
     */
    private static  final   Pattern DATA_PATTERN = Pattern.compile("\\{<@(.*?)@><\\$(.*?)\\$><#(.*?)#>\\}");




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
     * Reset its field
     */
    public void clear() {
        this.senderUUID     = "";
        this.requestType    = DRequestType.UNRECOGNIZED;
        this.content        = "";
    }




    /**
     * Interpret a request to separate sender ID, request type, request content
     * 
     * @param request
     */
    public void interpret(String request) {

        // Data decompiler
        Matcher matcher = DATA_PATTERN.matcher(request);


        // If the data matches the pattern
        if (matcher.matches()) {

            // UUID and content extractions
            this.senderUUID = matcher.group(1);
            this.content    = matcher.group(3);


            // Request type interpretation
            String requestType_ = matcher.group(2);
            for (DRequestType type : DRequestType.values()) {
                    
                // Request type matching
                if (requestType_.equals(type.getString())) {
                    requestType = type;
                }

            }

            
        } else {

            // If not, clear the interpreter
            this.clear();

        }

    }




    /**
     * Build a request
     * 
     * @param senderUUID
     * @param requestType
     * @param content
     * 
     * @return request
     */
    public String build(String senderUUID, DRequestType requestType, String content) {

        // Avoiding empty senderUUID
        if (senderUUID.isEmpty()) {
            senderUUID = "";
            requestType = DRequestType.UNRECOGNIZED;
        }

        return "{<@" + senderUUID + "@><$" + requestType.getString() + "$><#" + content + "#>}";
    }




    /**
     * Build a request without a content
     * 
     * @param senderUUID
     * @param requestType
     * 
     * @return request
     */
    public String build(String senderUUID, DRequestType requestType) {

        // Avoiding empty senderUUID
        if (senderUUID.isEmpty()) {
            senderUUID = "";
            requestType = DRequestType.UNRECOGNIZED;
        }

        return "{<@" + senderUUID + "@><$" + requestType.getString() + "$><##>}";
    }

}
