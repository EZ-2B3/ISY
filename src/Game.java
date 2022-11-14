import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


class Game implements ActionListener { // class to listen for messages from server in a separate thread
    private Render render;
    private String opponent = "No opponent";
    private int moves;
    private Board board;
    private boolean useAI;
    private Connection connection = new Connection();
    private String player;
    private boolean isMyTurn = false;
    private String players;

    public Game() {
        this.render = new Render(this);
    }

    public void Update() {
        try {
            while (true) { // while true
                if (Connection.in.ready()) { // if message is ready to be read
                    String message = Connection.in.readLine(); // read message from server
                    if (message.contains("SVR GAME")) {
                        if (message.contains("MATCH")) {
                            String[] split = message.split(" ");
                            opponent = split[8].replace("}", "").replace("\"", "");
                            render.BoardRender(board.getBoard(), isMyTurn, opponent);
                            render.UpdateFrame(render.panelBoard);
                        } else if (message.contains("YOURTURN")) {
                            // TODO: Logica voor als het jouw beurt is
                            isMyTurn = true;
                            render.BoardRender(board.getBoard(), isMyTurn, opponent);
                            render.UpdateFrame(render.panelBoard);
                        } else if (message.contains("CHALLENGE")) {
                            // SVR GAME CHALLENGE {CHALLENGER: "reeeed", CHALLENGENUMBER: "6", GAMETYPE: "tic-tac-toe"}
                            String[] split = message.split(",");
                            String challenger = split[0].replace("SVR GAME CHALLENGE {CHALLENGER: \"", "").replace("\"", "");
                            String challengeNumber = split[1].replace(" CHALLENGENUMBER: \"", "").replace("\"", "");
                            String gameType = split[2].replace(" GAMETYPE: \"", "").replace("\"}", "");
                            OnChallengeReceive(challenger, challengeNumber, gameType);
                        } else if (message.contains("MOVE")) {
                            String[] split = message.split(" ");
//                        String player = split[4].replace(",", "").replace("\"", ""); kan later nog wel handig zijn
                            int move = Integer.parseInt(split[6].replace(",", "").replace("\"", ""));

                            String playerIcon;
                            if (moves % 2 == 0) {
                                playerIcon = "X";
                            } else {
                                playerIcon = "O";
                            }

                            board.setBoard(move, playerIcon);
                            moves++;
                            render.BoardRender(board.getBoard(), isMyTurn, opponent);
                            render.UpdateFrame(render.panelBoard);
                        } else {
                            System.out.println(message);
                        }
                    } else if (message.contains("SVR PLAYERLIST")) {
                        String[] split = message.split("SVR PLAYERLIST ");
                        players = split[1].replace("[", "").replace("]", "").replace("\"", "");
                    } else {
                        System.out.println(message);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void OnMove(String buttonText) {
        Connection.out.println("MOVE " + buttonText);
        isMyTurn = false;
    }

    private void OnAIChoice(String command) {
        useAI = command.contains("Yes");
        System.out.println("AI: " + useAI);
        render.UpdateFrame(render.panelGameChoice);
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

    private void OnChallenge() {
        Connection.out.println("get playerlist");
        while (players == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        render.ChallengeRender(players, player);
        render.UpdateFrame(render.panelChallenge);
    }

    private void OnChallengeSend(String opponent) {
        players = null;
        Connection.out.println(opponent + " tic-tac-toe");
        board = new Board(3, 3);
        render.BoardRender(board.getBoard(), isMyTurn, opponent);
        render.UpdateFrame(render.panelBoard);
    }

    private void OnChallengeReceive(String challenger, String challengeNumber, String gameType) {
        //TODO: Een popup via de frame maken met de challenge informatie en een accept en decline knop
        int option = JOptionPane.showConfirmDialog(render.frame, challenger + " has challenged you to a game of " + gameType + "\nDo you want to play against them?", "Challenge", JOptionPane.YES_NO_OPTION);
        // option = 0 -> yes
        // option = 1 -> no

        if (option == 0) {
            Connection.out.println("challenge accept " + challengeNumber);
            board = new Board(3, 3);
            render.BoardRender(board.getBoard(), isMyTurn, challenger);
            render.UpdateFrame(render.panelBoard);
        } else {
            Connection.out.println("challenge decline " + challengeNumber);
        }
    }

    private void OnSubscribe(String gameType) {
        if (gameType.contains("TicTacToe")) {
            Connection.out.println("subscribe Tic-Tac-Toe");
            board = new Board(3, 3);
            render.BoardRender(board.getBoard(), isMyTurn, opponent);
            render.UpdateFrame(render.panelBoard);
        }
    }

    public void OnLogin(String username) {
        this.player = username;
        Connection.out.println("login " + username);
        try {
            String message = Connection.in.readLine();
            if (message.equals("OK")) {
                System.out.println("Login succesfull");
                render.UpdateFrame(render.panelAIChoice);
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
        String buttonText;
        switch (e.getActionCommand()) {
            case "Login":
                OnLogin(render.username.getText());
                break;

            case "Exit":
                OnQuit();

            case "AIChoice":
                buttonText = ((JButton) e.getSource()).getText();
                OnAIChoice(buttonText);
                break;

            case "Challenge":
                OnChallenge();
                break;

            case "ChallengeSend":
                buttonText = ((JButton) e.getSource()).getText();
                OnChallengeSend(buttonText);
                break;

            case "move":
                String buttonName = ((JButton) e.getSource()).getName();
                OnMove(buttonName);
                break;

            case "Tic-Tac-Toe":
                OnSubscribe("TicTacToe");
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