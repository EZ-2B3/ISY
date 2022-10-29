import java.io.IOException;

class Game extends Thread { // class to listen for messages from server in a separate thread
    public static String opponent; // create variable to store opponent name
    public void run() { // run method
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

                        String playerIcon;
                        if (moves % 2 == 0) {
                            playerIcon = "X";
                        } else {
                            playerIcon = "O";
                        }

                        if (player.equals(Main.render.player)) {
                            Main.render.board.GameBoard[Integer.parseInt(move) / 3][Integer.parseInt(move) % 3] = playerIcon;
                            Main.render.repaintBoard();
                        } else {
                            Main.render.board.GameBoard[Integer.parseInt(move) / 3][Integer.parseInt(move) % 3] = playerIcon;
                            Main.render.repaintBoard();
                        }
                    } else if (message.contains("SVR GAME YOURTURN")) {
                        Main.render.board.turn = true;
                        Main.render.repaintBoard();
                    } else if (message.contains("SVR GAME WIN") || message.contains("SVR GAME DRAW") || message.contains("SVR GAME LOSS")) {
                        Main.render.gameOver(message);
                        //TODO: game over (win, draw and loss) messages
                    } else{
                        System.out.println(message); // print message to user
                    }
                }
            } catch (IOException ex) { // if IOException is thrown
                throw new RuntimeException(ex); // throw RuntimeException
            }
        }
    }
}