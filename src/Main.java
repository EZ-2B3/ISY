/**
 * Class to create connection to server
 *
 * @author Reinder Noordmans
 * @version 1.1
 */
public class Main {
    final public static Render render = new Render(); // create render to render the game
    public static Game game = new Game(); // create listener to listen for messages from server in a separate thread
    public static void main(String[] args) { // main method
        new Connection(); // create connection to server
        game.start(); // start listener thread
    }
}

