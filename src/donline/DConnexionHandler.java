package donline;

public interface DConnexionHandler extends Runnable{

    public void shutDown();
    public void addRequest(String request);

}
