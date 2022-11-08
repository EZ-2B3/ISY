import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


class Game implements ActionListener { // class to listen for messages from server in a separate thread
    private Render render;
    private String Opponent;
    private int Moves;
    private Board board;
    private static Connection connection = new Connection();
    private String player;

    public Game(){
        this.render = new Render(this);
    }

    public void Update() {
        try {
            while (true) { // while true
                if (connection.in.ready()) { // if message is ready to be read
                    String message = connection.in.readLine(); // read message from server
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void OnMove() {
        //TODO
    }

    private void OnQuit() {
        try {
            Connection.out.println("exit");
            render.frame.setVisible(false);
            render.frame.dispose();

            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void OnGameOver() {
        //TODO
    }

    private void OnChallengeSend() {
        //TODO
    }

    private void OnChallengeReceive() {
        //TODO
    }

    private void OnSubscribe(String gameType) {
        //TODO
        }

    public void OnLogin(String username) {
        this.player = username;
        Connection.out.println("login " + username);
        try {
            String message = Connection.in.readLine();
            if (message.equals("OK")) {
                System.out.println("Login succesfull");
                render.UpdateFrame(render.panelGameChoice);
            } else {
                JOptionPane.showMessageDialog(render.frame, message);
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private boolean ValidateMove() {
        //TODO
        return false;
    }

    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "Login":
                OnLogin(render.username.getText());
                break;

            case "Exit":
                OnQuit();

            case "SVR GAME MOVE":
                OnMove();
                break;
            case "Tic-Tac-Toe":
                //call tictactoe game
                break;
            //TODO add all Event calls.

        }

    }


//    public static String opponent; // create variable to store opponent name
//    public void run() { // run method
//        //noinspection InfiniteLoopStatement
//        while (true) { // while true
//            try { // try to read message from server
//                if (Connection.in.ready()) { // if message is ready to be read
//                    String message = Connection.in.readLine(); // read message from server
//                    if (message.contains("SVR GAME MATCH")) {
//                        String[] split = message.split(" ");
//                        opponent = split[8].replace("\"", "").replace("}", "");
//                        Main.render.repaintBoard();
//                    }
//                    if (message.contains("SVR GAME MOVE")) {
//                        int moves = Main.render.board.moves++;
//                        String[] split = message.split(" ");
////                        String player = split[4].replace(",", "").replace("\"", ""); kan later nog wel handig zijn
//                        String move = split[6].replace(",", "").replace("\"", "");
//
//                        String playerIcon;
//                        if (moves % 2 == 0) {
//                            playerIcon = "X";
//                        } else {
//                            playerIcon = "O";
//                        }
//
//                        Main.render.board.GameBoard[Integer.parseInt(move) / 3][Integer.parseInt(move) % 3] = playerIcon;
//                        Main.render.repaintBoard();
//                    } else if (message.contains("SVR GAME YOURTURN")) {
//                        Main.render.board.turn = true;
//                        Main.render.repaintBoard();
//                    } else if (message.contains("SVR GAME WIN") || message.contains("SVR GAME DRAW") || message.contains("SVR GAME LOSS")) {
//                        Main.render.gameOver(message);
//                    } else if (message == "OK") {
//                        // do nothing
//                    }else{
//                        System.out.println(message); // print message to user
//                    }
//                }
//            } catch (IOException ex) { // if IOException is thrown
//                throw new RuntimeException(ex); // throw RuntimeException
//            }
//        }
//    }
}