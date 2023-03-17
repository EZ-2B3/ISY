package TicTacToe;
import Game.*;
public class TicTacToeGame implements Game {
    private Player[] players;
    private Board board;
    private int currentPlayerIndex;

    public TicTacToeGame(Player[] players, Board board) {
        this.players = players;
        this.board = board;
        currentPlayerIndex = 0;
    }

    public boolean isGameOver() {
        return board.isGameOver();
    }

    public boolean isValidMove(int row, int col) {
        return board.isValidMove(row, col);
    }

    public void makeMove(int row, int col) {
        Player currentPlayer = getCurrentPlayer();
        char playerSymbol = currentPlayer.getSymbol();
        board.makeMove(row, col, playerSymbol);
        switchPlayer();
    }

    public Player getCurrentPlayer() {
        return players[currentPlayerIndex];
    }

    public void switchPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
    }

    public void reset() {
        board.reset();
        currentPlayerIndex = 0;
    }

    public Board getBoard() {
        return board;
    }
}

