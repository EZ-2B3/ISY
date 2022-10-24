import java.io.IOException;
/**
 * Class to create connection to server
 *
 * @author Reinder Noordmans
 * @version 1.1
 */
public class Main {
    public static void main(String[] args) throws IOException { // main method
        new Connection(); // create connection to server
        Listener listener = new Listener(); // create listener to listen for messages from server in a separate thread
        listener.start(); // start listener thread
        new Render(); // create render to render the game
    }
}

/**
 * Class to listen for messages from server in a separate thread
 *
 * @author Reinder Noordmans
 * @version 1.1
 */
class Listener extends Thread { // class to listen for messages from server in a separate thread
    public void run() { // run method
        while (true) { // while true
            try { // try to read message from server
                if (Connection.in.ready()) { // if message is ready to be read
                    String message = Connection.in.readLine(); // read message from server
                    System.out.println(message); // print message to user
                }
            } catch (IOException ex) { // if IOException is thrown
                throw new RuntimeException(ex); // throw RuntimeException
            }
        }
        }
}
