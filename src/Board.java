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

    public void changeBoard(int rows, int cols, String piece) {
        board[rows][cols] = piece; // Veranderd de waarde van de rows en cols naar de piece
    }

    public Board Copy() {
        //Makes a copy of the board and returns a new instance
        Board copy = new Board(this.rows, this.cols);

        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                copy.changeBoard(i, j, this.board[i][j]);
            }
        }

        return copy;
    }

    public void setBoard(int move, String playerIcon) {
        int row = move / 3;
        int col = move % 3;

        board[row][col] = playerIcon;
    }
}
