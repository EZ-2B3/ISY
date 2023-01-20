import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Connection {
    final protected Socket echoSocket;
    final protected int portNumber = 7789;
    final protected String hostName = "localhost";

    {
        try { // try to create socket to connect to server
            // port number of server
            echoSocket = new Socket(hostName, portNumber);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    static PrintWriter out;

    {
        try {
            out = new PrintWriter(echoSocket.getOutputStream(), true);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    static BufferedReader in;

    {
        try {
            in = new BufferedReader(
                    new InputStreamReader(echoSocket.getInputStream()));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
