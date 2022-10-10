import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;



public class Main {
    public static void main(String[] args) {
        String hostName = "localhost";
        String portNumber = "7789";

        try (
                Socket echoSocket = new Socket(hostName, Integer.parseInt(portNumber)); // create socket to connect to server
                PrintWriter out = // create output stream to send data to server
                        new PrintWriter(echoSocket.getOutputStream(), true);
                BufferedReader in = // create input stream to receive data from server
                        new BufferedReader(
                                new InputStreamReader(echoSocket.getInputStream()));
                BufferedReader stdIn = // create input stream to receive data from user
                        new BufferedReader(
                                new InputStreamReader(System.in))
        ) {
            int emptyLines = 0; // set emptyLines to 0
            while (emptyLines < 2) { // while emptyLines is less than 2 print out the data from the server (the first 2 copyright lines)
                System.out.println(in.readLine()); // print out the data from the server
                emptyLines++; // increment emptyLines
            }
            String userInput; // create string to store user input
            while ((userInput = stdIn.readLine()) != null) { // while the user input is not null
                out.println(userInput); // send the user input to the server
                if (userInput.equals("exit")) { // if the user input is exit
                    System.exit(1); // exit the program
                }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(in.readLine()); // print out the data from the server

                while (in.ready()) { // while the input stream is ready
                    System.out.println(in.readLine()); // print out the data from the server
                }
            }
        } catch (UnknownHostException e) { // catch unknown host exception
            System.err.println("Don't know about host " + hostName); // print out error message
            System.exit(1); // exit the program
        } catch (IOException e) { // catch io exception
            System.err.println("Couldn't get I/O for the connection to " + // print out error message
                    hostName);
            System.exit(1); // exit the program

        }
    }
}