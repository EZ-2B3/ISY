public class Board { // class to create the board for the game (CLI)

    private String[][] board;

    public Board(int rows, int cols) {
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
}

