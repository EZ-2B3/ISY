import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Dictionary;
import java.util.Hashtable;

public class Connection {
    private Dictionary lastMessage = new Hashtable();
    private final PrintWriter out;
    private final BufferedReader in;

    public Connection(String hostname, int port) {

        Socket socket;
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

    private Dictionary<String, String> formatServerMessage(String message) {
        Dictionary<String, String> dictionary = new Hashtable<String, String>();

        String[] splitMessage = message.split(" ");
        dictionary.put("Status", "Invalid");
        boolean start = false;
        String Key = null;

        for (String item : splitMessage) {
            if (!start) {
                if (item.contains("{") || item.contains("OK")) {
                    dictionary.put("Status", "OK");
                    start = true;
                    Key = getString(dictionary, Key, item);
                } else if (item.contains("ERR")) {
                    dictionary.put("Status", "Error");

                }
            } else {
                Key = getString(dictionary, Key, item);
            }
        }
        System.out.println(dictionary);
        return dictionary;
    }

    private String getString(Dictionary<String, String> dictionary, String key, String item) {
        item = item.replace("\"", "").replace(",", "").replace(":", "").replace("{", "").replace("}", "");
        if (key == null) {
            key = item;
        } else {
            dictionary.put(key, item);
            key = null;
        }
        return key;
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public Dictionary receiveMessage() {
        for (int i = 0; i < 10; i++) {
            try {
                if (in.ready()) {
                    lastMessage = formatServerMessage(in.readLine());
                    return lastMessage;
                } else {
                    Thread.sleep(10);
                }
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return lastMessage;
    }
}
