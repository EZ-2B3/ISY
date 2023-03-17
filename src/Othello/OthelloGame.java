package Othello;
import Game.*;
import Core.Move;

public class OthelloGame {
    private OthelloBoard board;
    private Player[] players;
    private int currentPlayerIndex;

    public OthelloGame(Player player1, Player player2) {
        this.board = new OthelloBoard();
        this.players = new Player[]{player1, player2};
        this.currentPlayerIndex = 0;
    }

    public void play() {
        while (!board.isGameOver()) {
            Player currentPlayer = players[currentPlayerIndex];
            board.printBoard();
            System.out.println("It is " + currentPlayer.getName() + "'s turn.");

            Move move = currentPlayer.getMove(board);
            if (board.isValidMove(move)) {
                board.makeMove(move, currentPlayer.getPiece());
                currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
            } else {
                System.out.println("Invalid move. Try again.");
            }
        }
        board.printBoard();
        System.out.println("Game over! Final score: " + board.getScoreString());
    }
}

