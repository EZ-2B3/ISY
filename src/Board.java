import java.util.Objects;

public class Board { // class to create the board for the game (CLI)

    // private final String[][] board; // 2D array waar de board in wordt opgeslagen
    private final String[][] board;
    // private final int rows; // aantal rijen
    private int rows = 0;
    // private final int cols; // aantal kolommen
    private int cols = 0;

    // public Board(int rows, int cols) {
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

    // zet de board opnieuw
    public void setBoard(int move, String playerIcon) {
        int row = move / rows; // berekent de row
        int col = move % cols; // berekent de col

        board[row][col] = playerIcon; // zet de waarde van de row en col naar de playerIcon
    }

    public Board Copy() { // Maakt een kopie van het bord en returned een nieuwe instantie
        Board copy = new Board(this.rows, this.cols); // Maakt een nieuwe instantie van het bord

        for (int i = 0; i < this.rows; i++) { // Loop voor elke rij
            for (int j = 0; j < this.cols; j++) { // Loop voor elke kolom
                copy.setBoard(i, j, this.board[i][j]); // Zet de waarde van de rij en kolom op de waarde van de rij en kolom van het originele bord
            }
        }
        return copy;
    }

//    public void printBoard() {
//        for (int i = 0; i < cols; i++) {
//            System.out.print("----");
//        }
//        System.out.println("-");
//        for (int i = 0; i < rows; i++) {
//            for (int j = 0; j < cols; j++) {
//                System.out.print("| " + board[i][j] + " ");
//            }
//            System.out.println("|");
//            for (int k = 0; k < cols; k++) {
//                System.out.print("----");
//            }
//            System.out.println("-");
//        }
//    }

    public boolean[][] CheckValidMoves(String playerIcon, String gameType) { // Check of de move valid is
        boolean[][] validMoves = new boolean[board.length][board[0].length]; // Maakt een 2D array aan met de lengte van het bord
        for (int i = 0; i < rows; i++) { // Loop voor elke rij
            for (int j = 0; j < cols; j++) { // Loop voor elke kolom
                if (gameType == "Reversi") { // Checkt of het spel Reversi is
                    validMoves[i][j] = CheckValidMoveReversi(i, j, playerIcon); // Checkt of de move valid is
                } else if (gameType == "TicTacToe") { // Checkt of het spel TicTacToe is
                    validMoves[i][j] = CheckValidMoveTicTacToe(i, j, playerIcon); // Checkt of de move valid is
                }
            }
        }
        return validMoves; // Returned de 2D array met de valid moves
    }

    private boolean CheckValidMoveTicTacToe(int row, int col, String playerIcon) { // Checkt of de move valid is voor TicTacToe
        return Objects.equals(board[row][col], " "); // Returned of de waarde van de row en col gelijk is aan een spatie
    }

    private boolean CheckValidMoveReversi(int row, int col, String playerIcon) { // Checkt of de move valid is voor Reversi
        boolean validMove = false; // Zet de validMove op false
        String opponentIcon = playerIcon == "⚫" ? "⚪" : "⚫"; // Checkt of de playerIcon gelijk is aan een zwarte of witte piece

        // Check if the move is valid
        if (board[row][col] == " " || board[row][col] == "") { // Checkt of de waarde van de row en col gelijk is aan een spatie

            if (row - 1 >= 0 && board[row - 1][col] == opponentIcon) { // Checkt of de row - 1 groter of gelijk is aan 0 en of de waarde van de row - 1 en col gelijk is aan de opponentIcon
                for (int i = row - 2; i >= 0; i--) { // Loop voor elke rij vanaf de row - 2
                    if (board[i][col] == playerIcon) { // Checkt of de waarde van de rij en col gelijk is aan de playerIcon
                        validMove = true; // Zet de validMove op true
                        break;
                    } else if (board[i][col] == " " || board[i][col] == "") { // Checkt of de waarde van de rij en col gelijk is aan een spatie
                        break;
                    }
                }
            }

            // Check if the move is valid in the south direction
            if (row + 1 < rows && board[row + 1][col] == opponentIcon) { // Checkt of de row + 1 kleiner is dan het aantal rijen en of de waarde van de row + 1 en col gelijk is aan de opponentIcon
                for (int i = row + 2; i < rows; i++) { // Loop voor elke rij vanaf de row + 2
                    if (board[i][col] == playerIcon) { // Checkt of de waarde van de rij en col gelijk is aan de playerIcon
                        validMove = true; // Zet de validMove op true
                        break; // Breakt de loop
                    } else if (board[i][col] == " " || board[i][col] == "") { // Checkt of de waarde van de rij en col gelijk is aan een spatie
                        break; // Breakt de loop
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

    public boolean IsValidMove(int i, int j, String myPiece) { // Checkt of de move valid is
        return CheckValidMoveReversi(i, j, myPiece); // Returned de CheckValidMoveReversi
    }
}
