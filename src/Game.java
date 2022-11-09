import java.io.IOException;


class Game extends Thread { // class to listen for messages from server in a separate thread
    public static String opponent; // create variable to store opponent name
    public static String myPiece = "X"; // create variable to store my piece
    public static String opponentPiece = "O"; // create variable to store opponent piece
    public static boolean ai = false; // create variable to store if opponent is AI
    public static AITicTacToe aiTicTacToe; // create AI

    public void run() { // run method
        //noinspection InfiniteLoopStatement
        while (true) { // while true
            try { // try to read message from server
                if (Connection.in.ready()) { // if message is ready to be read
                    String message = Connection.in.readLine(); // read message from server
                    if (message.contains("SVR GAME MATCH")) {
                        String[] split = message.split(" ");
                        opponent = split[8].replace("\"", "").replace("}", "");
                        Main.render.repaintBoard();
                    }
                    if (message.contains("SVR GAME MOVE")) {
                        int moves = Main.render.board.moves++;
                        String[] split = message.split(" ");
                        String player = split[4].replace(",", "").replace("\"", "");
                        String move = split[6].replace(",", "").replace("\"", "");

                        // if opponent is first to move set opponent piece to X
                        if (moves == 0) {
                            if (player.equals(opponent)) {
                                myPiece = "O";
                                opponentPiece = "X";
                            } else {
                                myPiece = "X";
                                opponentPiece = "O";
                            }
                            aiTicTacToe = new AITicTacToe(myPiece, opponentPiece);
                        }

                        String playerIcon;
                        if (moves % 2 == 0) {
                            playerIcon = "X";
                        } else {
                            playerIcon = "O";
                        }

                        Main.render.board.gameBoard[Integer.parseInt(move) / 3][Integer.parseInt(move) % 3] = playerIcon;
                        Main.render.repaintBoard();
                    } else if (message.contains("SVR GAME YOURTURN")) {
                        if (ai) {
                            AITicTacToe.Move move = aiTicTacToe.getBestMove(Main.render.board.gameBoard);
                            String moveString = String.valueOf(move.x * 3 + move.y);
                            Connection.out.println("move " + moveString);
                        } else {
                            Main.render.board.turn = true;
                        }
                        Main.render.repaintBoard();
                        //Roep AI aan
                    } else if (message.contains("SVR GAME WIN") || message.contains("SVR GAME DRAW") || message.contains("SVR GAME LOSS")) {
                        Main.render.gameOver(message);
                        ai = false;
                        //TODO: game over (win, draw and loss) messages
                    } else if (message == "OK") {
                        // do nothing
                    } else {
                        System.out.println(message); // print message to user
                    }
                }
            } catch (IOException ex) { // if IOException is thrown
                throw new RuntimeException(ex); // throw RuntimeException
            }
        }
    }
}