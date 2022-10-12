import java.io.IOException;

/**
 * Class to start the program and read the input from the user
 *
 * @author Reinder Noordmans
 * @version 1.0
 */
public class Main {
    static Board board = new Board(); // create board
    static String[][] bord = board.createBoard(); // create board
    static String username; // create username
    public static void main(String[] args){

        Connection connection = new Connection(); // create connection to server

        try {
            System.out.println(Connection.in.readLine()); // print version of server
            System.out.println(Connection.in.readLine()); // print copy right message from server
        } catch (IOException ex) { // if IOException is thrown
            throw new RuntimeException(ex); // throw RuntimeException
        }

        boolean loggedIn = false; // flag to check if user is logged in
        while (!loggedIn) { // loop until user is logged in
            System.out.println("Please enter your username:"); // prompt user for username
            String response; // create variable to store response from server
            try {
                username = connection.stdIn.readLine(); // read username from user
                connection.out.println("login " + username); // send username to server
                response = Connection.in.readLine(); // read response from server
            } catch (IOException ex) { // if IOException is thrown
                throw new RuntimeException(ex); // throw RuntimeException
            }
            System.out.println(response); // print response from server
            if (response.equals("OK")) { // if response is OK
                loggedIn = true; // set flag to true
            }
        }


        String userInput; // variable to store user input
        Listener listener = new Listener(); // create listener to listen for messages from server in a separate thread
        listener.start(); // start listener thread
        while (true) { // loop until user enters "quit"
            try {
                if ((userInput = connection.stdIn.readLine()) != null && !userInput.equals("")) {
                    connection.out.println(userInput); // send user input to server
                    if (userInput.equals("quit") || userInput.equals("exit") || userInput.equals("disconnect")) { // if user enters "quit" or "exit" or "disconnect"
                        listener.interrupt(); // interrupt listener thread
                        System.exit(1); // exit program
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

/**
 * Class to listen for messages from server in a separate thread
 *
 * @author Reinder Noordmans
 * @version 1.0
 */
class Listener extends Thread { // class to listen for messages from server in a separate thread
    public void run() { // method to run when thread is started
        //noinspection InfiniteLoopStatement
        while (true) { // loop until thread is interrupted
            try { // try to read message from server
                if (Connection.in.ready()) { // if message is ready to be read
                    String message = Connection.in.readLine(); // read message from server
                    if (message.contains("SVR GAME")) {
                        if (message.contains("SVR GAME MATCH")) {
                            Main.board.printBoard(Main.bord);
                        }
                        if (message.contains("SVR GAME MOVE")) {
                            String[] move = message.split(" ");
                            String alteredmove = move[6].replace("\"", "").replace(",", "");
                            String altereduser = move[4].replace("\"", "").replace(",", "");
                            int position = Integer.parseInt(alteredmove);
                            if (altereduser.equals(Main.username)) {
                                Main.board.changeBoard(Main.bord, position, "🤡");
                            } else {
                                Main.board.changeBoard(Main.bord, position, "🦧");
                            }
                            Main.board.printBoard(Main.bord);
                        } else {
                            System.out.println(message); // print message from server
                        }
                    } else {
                        System.out.println(message); // print message from server
                    }
                }
            } catch (IOException e) { // if IOException is thrown
                e.printStackTrace(); // print stack trace
            }
        }
    }
}

