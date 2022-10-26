import java.io.IOException;
/**
 * Class to create connection to server
 *
 * @author Reinder Noordmans
 * @version 1.1
 */
public class Main {
    public static Render render = new Render(); // create render to render the game
    public static void main(String[] args) throws IOException { // main method
        new Connection(); // create connection to server
        Game game = new Game(); // create listener to listen for messages from server in a separate thread
        game.start(); // start listener thread
    }
}

