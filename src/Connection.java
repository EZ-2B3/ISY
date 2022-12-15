import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.Buffer;
import java.util.Dictionary;
import java.util.Hashtable;

public class Connection {
    private Socket socket;
    private String hostname;
    private int port;

    private PrintWriter out;
    private BufferedReader in;

    public Connection(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;

        try {
            socket = new Socket(hostname, port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        try {
            in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private Dictionary formatServerMessage(String message) {
        Dictionary dictionary = new Hashtable();

        String[] splitMessage = message.split(" ");
        dictionary.put("Status", "Invalid");
        boolean start = false;
        String Key = null;

        for (String item : splitMessage) {
            if (!start) {
                if (item.contains("{") || item.contains("OK")) {
                    dictionary.put("Status", "OK");
                    start = true;
                    item = item.replace("\"", "").replace(",", "").replace(":", "").replace("{", "").replace("}", "");
                    if (Key == null) {
                        Key = item;
                    } else {
                        dictionary.put(Key, item);
                        Key = null;
                    }
                }
            } else {
                item = item.replace("\"", "").replace(",", "").replace(":", "").replace("{", "").replace("}", "");
                if (Key == null) {
                    Key = item;
                } else {
                    dictionary.put(Key, item);
                    Key = null;
                }
            }
        }

        return dictionary;
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public Dictionary receiveMessage() {
        try {
            if (in.ready()) {
                return formatServerMessage(in.readLine());
            }
            Dictionary dictionary = new Hashtable();
            dictionary.put("Status", "No message");

            return dictionary;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
