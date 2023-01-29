import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


class Game implements ActionListener { // class to listen for messages from server in a separate thread
    private final Render render;
    private final Connection connection = new Connection();
    private Board board;
    private String player;
    private String opponent = "No opponent";
    private int moves;
    private boolean useAI;
    private boolean isMyTurn = false;
    private String players;
    private AI ai;
    private String myPiece;
    private String opponentPiece;
    private String gameType = "No game";
    private Reversi reversi;
    private boolean batch = false;
    private boolean receiving;
    private int games; // number of games to play
    private List dataSet = new ArrayList<String[]>();
    private String winner;
    private int movesEvaluated;
    private int movesPlayed;



    public Game() {
        this.render = new Render(this);
    }

    public void Update() {
        try {
            while (true) { // while true
                if (Connection.in.ready()) { // if message is ready to be read
                    String message = Connection.in.readLine(); // read message from server\
                    System.out.println("Received: " + message); // print message to console

                    switch (message){
                        case "SVR GAME MATCH" ->{
                            // Split message and remove brackets to get the opponent
                            String[] split = message.split(" ");
                            opponent = split[8].replace("}", "").replace("\"", "");

                            //Update the board with the opponent.
                            render.BoardRender(board.getBoard(), isMyTurn, opponent, gameType, board.CheckValidMoves(myPiece, gameType));
                            render.UpdateFrame(render.panelBoard);
                        }
                        case "SVR GAME YOURTURN" ->{
                            isMyTurn = true;

                            if (gameType.equals("TicTacToe")) { // If playing tictactoe
                                if (myPiece == null) { // assign piece if null
                                    myPiece = "O";
                                    opponentPiece = "X";
                                    ai = new AITicTacToe(myPiece, opponentPiece); // set TicTacToe AI
                                }
                            } else if (gameType.equals("Reversi")) { // If playing reversi
                                if (myPiece == null) { // assign piece if null
                                    myPiece = "⚫";
                                    opponentPiece = "⚪";
                                    ai = new AIReversi(myPiece, opponentPiece, player, reversi); // set Reversi AI
                                }
                            }

                            if (useAI) { // If using AI get the best move
                                int move = ai.GetBestMove(board);
                                movesEvaluated = movesEvaluated + ai.GetTotalMoves();
                                movesPlayed++;
                                if (move == -1) { // What to do when no valid moves left
                                    System.out.println("No valid moves");

                                } else { // Send Valid move to the server
                                    String moveString = String.valueOf(move);
                                    Connection.out.println("move " + moveString);
                                }
                            }
                            else {
                                isMyTurn = true;
                            }

                            // Update renderer
                            render.BoardRender(board.getBoard(), isMyTurn, opponent, gameType, board.CheckValidMoves(myPiece, gameType));
                            render.UpdateFrame(render.panelBoard);
                            // Missing isMyTurn = false;?
                            // End of SVR GAME YOURTURN
                        }
                        case "SVR GAME CHALLENGE" ->{
                            // SVR GAME CHALLENGE {CHALLENGER: "reeeed", CHALLENGENUMBER: "6", GAMETYPE: "tic-tac-toe"}
                            // Split message
                            String[] split = message.split(",");

                            // Get challenger from split
                            String challenger = split[0].replace("SVR GAME CHALLENGE {CHALLENGER: \"", "").replace("\"", ""); // get challenger

                            // Get challenge number from split
                            String challengeNumber = split[1].replace(" CHALLENGENUMBER: \"", "").replace("\"", ""); // get challenge number

                            // Get gametype from split
                            String gameType = split[2].replace(" GAMETYPE: \"", "").replace("\"}", ""); // Get gametype

                            // Call for challenge received
                            OnChallengeReceive(challenger, challengeNumber, gameType);
                        }
                        case "SVR GAME MOVE" ->{
                            String[] split = message.split(" ");
//                          String player = split[4].replace(",", "").replace("\"", ""); kan later nog wel handig zijn
                            int move = Integer.parseInt(split[6].replace(",", "").replace("\"", ""));// Get the move made

                            // Set the icons
                            String playerIcon = null;
                            if (gameType.equals("TicTacToe")) {
                                if (myPiece == null) {
                                    myPiece = "O";
                                    opponentPiece = "X";
                                    ai = new AITicTacToe(myPiece, opponentPiece);
                                }


                                if (moves % 2 == 0) {
                                    playerIcon = "X";
                                } else {
                                    playerIcon = "O";
                                }
                            } else if (gameType.equals("Reversi")) {

                                if (myPiece == null) {
                                    myPiece = "⚪";
                                    opponentPiece = "⚫";
                                    ai = new AIReversi(myPiece, opponentPiece, player, reversi);
                                }
                                if (message.contains(player)) {
                                    playerIcon = myPiece;
                                } else {
                                    playerIcon = opponentPiece;
                                }
                            }

                            board.setBoard(move, playerIcon); // Put move on board with icon
                            moves++;

                            if (gameType.equals("Reversi")) {
                                reversi.CheckCaptures(board, move, playerIcon); // Check for captures in reversi
                            }

                            // Update the board UI
                            render.BoardRender(board.getBoard(), isMyTurn, opponent, gameType, board.CheckValidMoves(myPiece, gameType));
                            render.UpdateFrame(render.panelBoard);
                        }
                        case "SVR GAME WIN" ->{ // Player has won
                            String result = "Gefeliciteerd, je hebt gewonnen!";
                            winner = "WIN";
                            OnGameOver(result);
                        }
                        case "SVR GAME DRAW" ->{ // Game ends in draw
                            String result = "Jammer, je hebt gelijk gespeeld!";
                            winner = "DRAW";
                            OnGameOver(result);
                        }
                        case "SVR GAME LOSS" ->{ // Player lost the game
                            String result = "Helaas, je hebt verloren!";
                            winner = "LOSS";
                            OnGameOver(result);
                        }
                        case "SVR PLAYERLIST" -> { // retrieve player list
                            String[] split = message.split("SVR PLAYERLIST ");
                            players = split[1].replace("[", "").replace("]", "").replace("\"", "");
                        }
                        default -> {
                            // On OK or non-command message from server just print message
                            System.out.println(message);
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void OnMove(String buttonText) {
        //Send move to server
        Connection.out.println("MOVE " + buttonText);
        isMyTurn = false;
    }

    private void OnAIChoice(String buttonText) {
        // User made choice on using AI
        useAI = buttonText.contains("Yes");
        render.UpdateFrame(render.panelGameChoice);
    }

    private void OnExit() {
        try {
            Connection.out.println("exit");
            render.frame.setVisible(false);
            render.frame.dispose();

            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void OnGameOver(String result) {
        String depth = CalculateAverageDepth();
        String playerPieces = Integer.toString(ai.GetMyPieces());
        String opponentPieces = Integer.toString(ai.GetOpponentPieces());

        dataSet.add(new String[] {winner, depth, playerPieces, opponentPieces});

        myPiece = null;
        opponentPiece = null;
        moves = 0;
        isMyTurn = false;
        if (batch) {
            if (!receiving) {
                if (games != 0) {
                    games--;
                    OnChallengeSend("challenge " + opponent);
                } else {
                    System.out.println("Done");
                    System.exit(0);
                }
            }
        } else {
            render.GameOverRender(result, opponent);
        }
    }

    private void OnChallenge() {
        Connection.out.println("get playerlist");
        while (players == null) { // Wait until the player list has updated
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        render.ChallengeRender(players); // render the player list
        render.UpdateFrame(render.panelChallenge);
    }

    private void OnChallengeSend(String buttonText) {
        players = null;
        Connection.out.println(buttonText + " Reversi");

        //Create new reversi board and update UI
        board = new Board(8, 8);
        reversi = new Reversi(board);
        render.BoardRender(board.getBoard(), isMyTurn, opponent, gameType, board.CheckValidMoves(myPiece, gameType));
        render.UpdateFrame(render.panelBoard);
    }

    private void OnChallengeReceive(String challenger, String challengeNumber, String gameType) {
        int option = 0;
//        int option = JOptionPane.showConfirmDialog(render.frame, challenger + " has challenged you to a game of " + gameType + "\nDo you want to play against them?", "Challenge", JOptionPane.YES_NO_OPTION);
        // option = 0 -> yes
        // option = 1 -> no

        if (option == 0) { // Always yes
            Connection.out.println("challenge accept " + challengeNumber);
            board = new Board(8, 8);
            reversi = new Reversi(board);
            render.BoardRender(board.getBoard(), isMyTurn, opponent, gameType, board.CheckValidMoves(myPiece, gameType));
            render.UpdateFrame(render.panelBoard);
        } else {
            Connection.out.println("challenge decline " + challengeNumber);
        }
    }

    private void OnSubscribe(String gameType) {
        this.gameType = gameType;
        if (gameType.equals("TicTacToe")) {
            Connection.out.println("subscribe Tic-Tac-Toe"); // Subscribe to tictactoe

            // Set pieces and AI
            myPiece = "O";
            opponentPiece = "X";
            ai = new AITicTacToe(myPiece, opponentPiece); // set TicTacToe AI

            // Create board and update
            board = new Board(3, 3);
            render.BoardRender(board.getBoard(), isMyTurn, opponent, gameType, board.CheckValidMoves(myPiece, gameType));
            render.UpdateFrame(render.panelBoard);

        } else if (gameType.equals("Reversi")) {
            Connection.out.println("subscribe Reversi"); // Subscribe to reversi

            // Set Reversi pieces
            myPiece = "⚫";
            opponentPiece = "⚪";
            ai = new AIReversi(myPiece, opponentPiece, player, reversi); // set Reversi AI

            // Create board and update
            board = new Board(8, 8);
            reversi = new Reversi(board);
            render.BoardRender(board.getBoard(), isMyTurn, opponent, gameType, board.CheckValidMoves(myPiece, gameType));
            render.UpdateFrame(render.panelBoard);
        }
    }

    public void OnLogin(String username) {
        // Set username and login to server
        this.player = username;
        Connection.out.println("login " + username);

        // If login was OK, show AI Choice panel
        try {
            String message = Connection.in.readLine();
            if (message.equals("OK")) {
                render.UpdateFrame(render.panelAIChoice);
            } else {
//                JOptionPane.showMessageDialog(render.frame, message);
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void OnQuit() {
        // Write data results to a CSV file
        Results.WriteToCSV(dataSet);

        render.UpdateFrame(render.panelGameChoice);
    }

    public void actionPerformed(ActionEvent e) {
        //Checks for UI Events using a switch
        String buttonText;
        switch (e.getActionCommand()) {
            case "Login":
                OnLogin(render.username.getText());
                break;

            case "Exit":
                OnExit();
                break;

            case "AIChoice":
                buttonText = ((JButton) e.getSource()).getText();
                OnAIChoice(buttonText);
                break;

            case "Challenge":
                OnChallenge();
                break;

            case "ChallengeSend":
                if (e.getSource() instanceof JMenuItem menuItem) {
                    OnChallengeSend(menuItem.getText());
                } else {
                    buttonText = ((JButton) e.getSource()).getText();
                    OnChallengeSend(buttonText);
                }
                break;

            case "move":
                String buttonName = ((JButton) e.getSource()).getName();
                OnMove(buttonName);
                break;

            case "Tic-Tac-Toe":
                OnSubscribe("TicTacToe");
                break;

            case "Reversi":
                OnSubscribe("Reversi");
                break;

            case "Quit":
                OnQuit();
                break;

            case "ChallengeBack":
                render.UpdateFrame(render.panelGameChoice);
                break;
        }

    }

    private String CalculateAverageDepth() {
        float evaluated = movesEvaluated;
        float played = movesPlayed;
        float avg = evaluated / played;
        String avgString = Float.toString(avg);
        movesPlayed = 0;
        movesEvaluated = 0;

        return avgString;
    }

    public void setMyTurn(boolean myTurn) {
        isMyTurn = myTurn;
    }

    public void StartGame(String player1, Boolean receiving, String Opponent, int games) {
        // Start game uses data form console arguments to start a game
        OnLogin(player1);
        OnAIChoice("Yes");
        gameType = "Reversi";
        batch = true;
        this.receiving = receiving;
        this.games = games;
        if (!receiving) {
            OnChallengeSend("challenge " + Opponent);
        }
    }
}
