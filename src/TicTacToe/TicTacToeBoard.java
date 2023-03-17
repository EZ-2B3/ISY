package TicTacToe;
import Game.*;
public class TicTacToeBoard implements Board {
    private char[][] board;

    public TicTacToeBoard() {
        board = new char[3][3];
        reset();
    }

    public void reset() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                board[row][col] = '-';
            }
        }
    }

    public boolean isValidMove(int row, int col) {
        return board[row][col] == '-';
    }

    public boolean isGameOver() {
        return getWinner() != '-';
    }

    public char getWinner() {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != '-' && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                return board[i][0];
            }
            if (board[0][i] != '-' && board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                return board[0][i];
            }
        }
        if (board[0][0] != '-' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            return board[0][0];
        }
        if (board[0][2] != '-' && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            return board[0][2];
        }
        return '-';
    }

    public void makeMove(int row, int col, char playerSymbol) {
        board[row][col] = playerSymbol;
    }

    public char[][] getBoard() {
        return board;
    }

    public void setBoard(char[][] board) {
        this.board = board;
    }
}

