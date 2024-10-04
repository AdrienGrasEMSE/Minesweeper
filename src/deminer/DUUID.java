// Pakcage declaration
package deminer;

// Import
import java.util.UUID;


/**
 * Class Deminer UUID (Universally Unique Identifier)
 * 
 * @author  AdrienG
 * @version 0.0
 * 
 * 
 * This class only generate a UUID
 */
public class DUUID {

    /**
     * ID generation using a UUID method (thanks ChatGPT)
     * 
     * @return String formated UUID
     */
    public synchronized static String generate() {

        // UUID (Universally Unique Identifier)
        UUID    uuid = UUID.randomUUID();
        return  uuid.toString();
        
    }

}
