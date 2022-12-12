import java.net.Socket;
import java.util.Dictionary;
import java.util.Hashtable;

public class Connection {
    protected Socket socket;
    protected String hostname;
    protected int port;

    public Connection(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    private Dictionary formatServerMessage(String message) {
//         TODO: Maak het server bericht zo dat het voorste stuk er af gaat en de rest in een array gezet wordt

//         SVR GAME CHALLENGE {CHALLENGER: "reeeed", CHALLENGENUMBER: "6", GAMETYPE: "tic-tac-toe"}

         Dictionary dictionary = new Hashtable();

         dictionary.put("Challenger", "reeeed");
         dictionary.put("ChallengeNumber", "6");
         dictionary.put("GameType", "tic-tac-toe");
         return dictionary;
    }


}
