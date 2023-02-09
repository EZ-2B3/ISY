import java.io.*; // importeren van de input en output
import java.net.Socket; // importeren van de socket
import java.util.Scanner; // importeren van de scanner


public class Connection {
    final protected Socket echoSocket; // socket om met de server te connecten
    public static final int portNumber; // poort nummer van de server
    public static final String hostName;  // hostnaam van de server

    static {
       int conts_1;
       String const_2;  
       try(Scanner s = new Scanner(new File("./resources/config.properties"))) { // proberen om een scanner te creeren om de config file te lezen
           conts_1 = s.nextInt(); // poort nummer van de server
           const_2 = s.next(); // hostnaam van de server
       } catch (FileNotFoundException e) { // als er een fout optreedt wordt een runtime exception gegooid
           throw new RuntimeException(e); 
       }
        portNumber = conts_1; 
        hostName = const_2;
    }

    {
        try { // proberen om een socket te creeren om met de server te connecten
            
            echoSocket = new Socket(hostName, portNumber); // socket om met de server te connecten
        } catch (IOException ex) {
            throw new RuntimeException(ex); // als er een fout optreedt wordt een runtime exception gegooid
        }
    }

    static PrintWriter out; // printwriter om data naar de server te sturen

    {
        try {
            out = new PrintWriter(echoSocket.getOutputStream(), true); // true zorgt ervoor dat de data direct wordt verstuurd
        } catch (IOException ex) {
            throw new RuntimeException(ex); // als er een fout optreedt wordt een runtime exception gegooid
        }
    }

    static BufferedReader in; // bufferedreader om data van de server te lezen

    {
        try { // proberen om een bufferedreader te creeren om data van de server te lezen
            in = new BufferedReader(
                    new InputStreamReader(echoSocket.getInputStream())); // bufferedreader om data van de server te lezen
        } catch (IOException ex) { // als er een fout optreedt wordt een runtime exception gegooid
            throw new RuntimeException(ex);
        }
    }
}
