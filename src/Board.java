import java.util.Objects;

public class Board { // class to create the board for the game (CLI)

    private final String[][] board;
    private int rows = 0;
    private int cols = 0;

    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;

        board = new String[rows][cols]; // 2D array waar de board in wordt opgeslagen
        for (int i = 0; i < rows; i++) { // Loop voor elke rij
            for (int j = 0; j < cols; j++) { // Loop voor elke kolom
                board[i][j] = " "; // Zet de waardes van de array op een spatie
            }
        }

    }

    public String[][] getBoard() {
        return board; // Returned het bord
    }

    public int getRows() {
        return rows; // Returned het aantal rijen
    }

    public int getCols() {
        return cols; // Returned het aantal kolommen
    }

    public void setBoard(int rows, int cols, String piece) {
        board[rows][cols] = piece; // Veranderd de waarde van de rows en cols naar de piece
    }

    public void setBoard(int move, String playerIcon) {
        int row = move / rows;
        int col = move % cols;

        board[row][col] = playerIcon;
    }

    public Board Copy() {
        //Makes a copy of the board and returns a new instance
        Board copy = new Board(this.rows, this.cols);

        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                copy.setBoard(i, j, this.board[i][j]);
            }
        }

        return copy;
    }

    public void printBoard() {
        for (int i = 0; i < cols; i++) {
            System.out.print("----");
        }
        System.out.println("-");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print("| " + board[i][j] + " ");
            }
            System.out.println("|");
            for (int k = 0; k < cols; k++) {
                System.out.print("----");
            }
            System.out.println("-");
        }
    }

    public boolean[][] CheckValidMoves(String playerIcon, String gameType) {
        boolean[][] validMoves = new boolean[board.length][board[0].length];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (gameType == "Reversi") {
                    validMoves[i][j] = CheckValidMoveReversi(i, j, playerIcon);
                } else if (gameType == "TicTacToe") {
                    validMoves[i][j] = CheckValidMoveTicTacToe(i, j, playerIcon);
                }
            }
        }
        return validMoves;
    }

    private boolean CheckValidMoveTicTacToe(int row, int col, String playerIcon) {
        return Objects.equals(board[row][col], " ");
    }

    private boolean CheckValidMoveReversi(int row, int col, String playerIcon) {
        boolean validMove = false;
        String opponentIcon = playerIcon == "⚫" ? "⚪" : "⚫";

        // Check if the move is valid
        if (board[row][col] == " " || board[row][col] == "") {
            // Check if the move is valid in the north direction
            if (row - 1 >= 0 && board[row - 1][col] == opponentIcon) {
                for (int i = row - 2; i >= 0; i--) {
                    if (board[i][col] == playerIcon) {
                        validMove = true;
                        break;
                    } else if (board[i][col] == " " || board[i][col] == "") {
                        break;
                    }
                }
            }

            // Check if the move is valid in the south direction
            if (row + 1 < rows && board[row + 1][col] == opponentIcon) {
                for (int i = row + 2; i < rows; i++) {
                    if (board[i][col] == playerIcon) {
                        validMove = true;
                        break;
                    } else if (board[i][col] == " " || board[i][col] == "") {
                        break;
                    }
                }
            }

            // Check if the move is valid in the east direction
            if (col + 1 < cols && board[row][col + 1] == opponentIcon) {
                for (int i = col + 2; i < cols; i++) {
                    if (board[row][i] == playerIcon) {
                        validMove = true;
                        break;
                    } else if (board[row][i] == " " || board[row][i] == "") {
                        break;
                    }
                }
            }

            // Check if the move is valid in the west direction
            if (col - 1 >= 0 && board[row][col - 1] == opponentIcon) {
                for (int i = col - 2; i >= 0; i--) {
                    if (board[row][i] == playerIcon) {
                        validMove = true;
                        break;
                    } else if (board[row][i] == " " || board[row][i] == "") {
                        break;
                    }
                }
            }

            // Check if the move is valid in the north-east direction
            if (row - 1 >= 0 && col + 1 < cols && board[row - 1][col + 1] == opponentIcon) {
                for (int i = row - 2, j = col + 2; i >= 0 && j < cols; i--, j++) {
                    if (board[i][j] == playerIcon) {
                        validMove = true;
                        break;
                    } else if (board[i][j] == " " || board[i][j] == "") {
                        break;
                    }
                }
            }

            // Check if the move is valid in the north-west direction
            if (row - 1 >= 0 && col - 1 >= 0 && board[row - 1][col - 1] == opponentIcon) {
                for (int i = row - 2, j = col - 2; i >= 0 && j >= 0; i--, j--) {
                    if (board[i][j] == playerIcon) {
                        validMove = true;
                        break;
                    } else if (board[i][j] == " " || board[i][j] == "") {
                        break;
                    }
                }
            }

            // Check if the move is valid in the south-east direction
            if (row + 1 < rows && col + 1 < cols && board[row + 1][col + 1] == opponentIcon) {
                for (int i = row + 2, j = col + 2; i < rows && j < cols; i++, j++) {
                    if (board[i][j] == playerIcon) {
                        validMove = true;
                        break;
                    } else if (board[i][j] == " " || board[i][j] == "") {
                        break;
                    }
                }
            }
            // Check if the move is valid in the south-west direction
            if (row + 1 < rows && col - 1 >= 0 && board[row + 1][col - 1] == opponentIcon) {
                for (int i = row + 2, j = col - 2; i < rows && j >= 0; i++, j--) {
                    if (board[i][j] == playerIcon) {
                        validMove = true;
                        break;
                    } else if (board[i][j] == " " || board[i][j] == "") {
                        break;
                    }
                }
            }
        }
        return validMove;
    }

    public boolean IsValidMove(int i, int j, String myPiece) {
        return CheckValidMoveReversi(i, j, myPiece);
    }
}
