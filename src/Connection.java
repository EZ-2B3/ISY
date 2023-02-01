import java.io.*;
import java.net.Socket;
import java.util.Scanner;


public class Connection {
    final protected Socket echoSocket;
    public static final int portNumber;
    public static final String hostName;

    static {
       int conts_1;
       String const_2;
       try(Scanner s = new Scanner(new File("./resources/config.properties"))) {
           conts_1 = s.nextInt();
           const_2 = s.next();
       } catch (FileNotFoundException e) {
           throw new RuntimeException(e);
       }
        portNumber = conts_1;
        hostName = const_2;
    }

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
