import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


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
    private String aiType;


    public Game() {
        this.render = new Render(this);
    }

    public void Update() {
        try {
            while (true) { // while true
                if (Connection.in.ready()) { // if message is ready to be read
                    String message = Connection.in.readLine(); // read message from server\
                    if (message.contains("SVR GAME")) {
                        if (message.contains("MATCH")) {
                            String[] split = message.split(" ");
                            opponent = split[8].replace("}", "").replace("\"", "");
                            render.BoardRender(board.getBoard(), isMyTurn, opponent, gameType, board.CheckValidMoves(myPiece, gameType));
                            render.UpdateFrame(render.panelBoard);
                        } else if (message.contains("YOURTURN")) {
                            isMyTurn = true;
                            if (gameType.equals("TicTacToe")) {
                                if (myPiece == null) {
                                    myPiece = "O";
                                    opponentPiece = "X";
                                    ai = new AITicTacToe(myPiece, opponentPiece);
                                }
                            } else if (gameType.equals("Reversi")) {
                                if (myPiece == null) {
                                    myPiece = "⚫";
                                    opponentPiece = "⚪";
                                    ai = new AIReversi(myPiece, opponentPiece, aiType, reversi);
                                }
                            }

                            board.CheckValidMoves(myPiece, gameType);

                            if (useAI) {
                                int move = ai.GetBestMove(board);
                                movesEvaluated = movesEvaluated + ai.GetTotalMoves();
                                movesPlayed++;
                                if (move == -1) {
                                    System.out.println("No valid moves");

                                } else {
                                    String moveString = String.valueOf(move);
                                    Connection.out.println("move " + moveString);
                                }
                            } else {
                                isMyTurn = true;
                            }



                            render.BoardRender(board.getBoard(), isMyTurn, opponent, gameType, board.CheckValidMoves(myPiece, gameType));
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
                                    ai = new AIReversi(myPiece, opponentPiece, aiType, reversi);
                                }
                                if (message.contains(player)) {
                                    playerIcon = myPiece;
                                } else {
                                    playerIcon = opponentPiece;
                                }


                            }

                            board.setBoard(move, playerIcon);
                            if (gameType.equals("Reversi")) {
                                reversi.CheckCaptures(board, move, playerIcon);
                            }
//                            board.printBoard();
                            moves++;
                            render.BoardRender(board.getBoard(), isMyTurn, opponent, gameType, board.CheckValidMoves(myPiece, gameType));
                            render.UpdateFrame(render.panelBoard);
                            // WIN DRAW LOSS
                        } else if (message.contains("WIN")) {
                            String result = "Gefeliciteerd, je hebt gewonnen!";
                            winner = "WIN";
                            OnGameOver(result);
                        } else if (message.contains("DRAW")) {
                            String result = "Jammer, je hebt gelijk gespeeld!";
                            winner = "DRAW";
                            OnGameOver(result);
                        } else if (message.contains("LOSS")) {
                            String result = "Helaas, je hebt verloren!";
                            winner = "LOSS";
                            OnGameOver(result);
                        } else {
                            System.out.println(message);
                        }
                    } else if (message.contains("SVR PLAYERLIST")) {
                        String[] split = message.split("SVR PLAYERLIST ");
                        players = split[1].replace("[", "").replace("]", "").replace("\"", "");
                    } else if (message.contains("OK")) {

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

    private void OnAIChoice(String buttonText) {
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
        String playerPieces = Integer.toString(ai.GetMyPieces(board));
        String opponentPieces = Integer.toString(ai.GetOpponentPieces(board));
        String myPieceString;
        if (myPiece == "⚪") {
            myPieceString = "White";
        } else {
            myPieceString = "Black";
        }

        dataSet.add(new String[]{winner, myPieceString, depth, playerPieces, opponentPieces});

        myPiece = null;
        opponentPiece = null;
        moves = 0;
        isMyTurn = false;
        if (batch) {
            games--;
            if (games == 0) {
                System.out.println("Done");
                OnQuit();
                System.exit(0);
            }
            if (!receiving) {
                OnChallengeSend("challenge " + opponent);
            }
        } else {
            render.GameOverRender(result, opponent);
        }
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
        render.ChallengeRender(players);
        render.UpdateFrame(render.panelChallenge);
    }

    private void OnChallengeSend(String buttonText) {
        players = null;
        Connection.out.println(buttonText + " Reversi");
        board = new Board(8, 8);
        reversi = new Reversi(board);
        render.BoardRender(board.getBoard(), isMyTurn, opponent, gameType, board.CheckValidMoves(myPiece, gameType));
        render.UpdateFrame(render.panelBoard);
    }

    private void OnChallengeReceive(String challenger, String challengeNumber, String gameType) {
        int option = 0;
        if (option == 0) {
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
            Connection.out.println("subscribe Tic-Tac-Toe");
            board = new Board(3, 3);
            render.BoardRender(board.getBoard(), isMyTurn, opponent, gameType, board.CheckValidMoves(myPiece, gameType));
            render.UpdateFrame(render.panelBoard);
        } else if (gameType.equals("Reversi")) {
            Connection.out.println("subscribe Reversi");
            board = new Board(8, 8);
            reversi = new Reversi(board);
            render.BoardRender(board.getBoard(), isMyTurn, opponent, gameType, board.CheckValidMoves(myPiece, gameType));
            render.UpdateFrame(render.panelBoard);
        }
    }

    public void OnLogin(String username) {
        this.player = username;
        this.aiType = username;
        Connection.out.println("login " + username);
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
        Results.WriteToCSV(dataSet);

        render.UpdateFrame(render.panelGameChoice);
    }

    public void actionPerformed(ActionEvent e) {
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
            //TODO add all Event calls.
            case "Tournament":
                OnTournament();
                break;
        }

    }

    private void OnTournament() {
        board = new Board(8, 8);
        useAI = true;
        reversi = new Reversi(board);
        gameType = "Reversi";
        render.BoardRender(board.getBoard(), isMyTurn, opponent, gameType, board.CheckValidMoves(myPiece, gameType));
        render.UpdateFrame(render.panelBoard);

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

    private String randomName(String AITYPE) {
        Random rand = new Random();
        char rand_char1 = (char) (rand.nextInt(26) + 'a');
        char rand_char2 = (char) (rand.nextInt(26) + 'a');
        char rand_char3 = (char) (rand.nextInt(26) + 'a');
        String name = AITYPE + rand_char1 + rand_char2 + rand_char3;
        System.out.println(name);
        return name;
    }
    public void StartGame(String player1, Boolean receiving, String Opponent, int games) {
        String name = randomName(player1);
        System.out.println(name);
        OnLogin(name);
        OnAIChoice("Yes");
        gameType = "Reversi";
        batch = true;
        aiType = player1;
        this.receiving = receiving;
        this.games = games;
        if (!receiving) {
            OnChallengeSend("challenge " + Opponent);
        }
    }
}
