import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Class to create connection to server
 *
 * @author Reinder Noordmans
 * @version 1.0
 */
public class Connection {
    private static String hostName = "localhost"; // host name of server
    private static String portNumber = "7789"; // port number of server
    Socket echoSocket; // create socket to connect to server

    {
        try { // try to create socket to connect to server
            echoSocket = new Socket(hostName, Integer.parseInt(portNumber)); // create socket to connect to server
        } catch (IOException ex) { // if IOException is thrown
            throw new RuntimeException(ex); // throw RuntimeException
        }
    }

    static PrintWriter out;  // create output stream to send data to server

    {
        try {
            out = new PrintWriter(echoSocket.getOutputStream(), true); // create output stream to send data to server
        } catch (IOException ex) { // if IOException is thrown
            throw new RuntimeException(ex); // throw RuntimeException
        }
    }

    static BufferedReader in;  // create input stream to receive data from server

    {
        try { // try to create input stream to receive data from server
            in = new BufferedReader( // create input stream to receive data from server
                    new InputStreamReader(echoSocket.getInputStream())); // create input stream to receive data from server
        } catch (IOException ex) { // if IOException is thrown
            throw new RuntimeException(ex); // throw RuntimeException
        }
    }
    BufferedReader stdIn = // create input stream to receive data from user
                        new BufferedReader( // create input stream to receive data from user
                                new InputStreamReader(System.in)); // create input stream to receive data from user
}