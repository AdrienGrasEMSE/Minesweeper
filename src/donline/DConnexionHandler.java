// Package declaration
package donline;


/**
 * Interface Connexion Handler
 * 
 * @author AdrienG
 * @version 0.0
 * 
 * 
 * Interface oriented to hold a socket connexion by sending and receiving message
 */
public interface DConnexionHandler extends Runnable{

    public void shutDown();
    public void addRequest(String request);

}
