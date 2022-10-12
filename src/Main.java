import java.io.IOException;

/**
 * Class to start the program and read the input from the user
 *
 * @author Reinder Noordmans
 * @version 1.0
 */
public class Main {
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
                String username = connection.stdIn.readLine(); // read username from user
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
                    try { // try to sleep for 100 milliseconds
                        //noinspection BusyWait
                        Thread.sleep(100); // sleep for 100 milliseconds
                    } catch (InterruptedException e) { // if thread is interrupted
                        e.printStackTrace(); // print stack trace
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
                    System.out.println(Connection.in.readLine()); // print message from server
                }
            } catch (IOException e) { // if IOException is thrown
                e.printStackTrace(); // print stack trace
            }
        }
    }
}

