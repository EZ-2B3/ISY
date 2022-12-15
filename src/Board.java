public class Board {
    private final Piece[] pieces;
    private final int rows;
    private final int cols;

    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;

        pieces = new Piece[rows * cols];
        for (int i = 0; i < pieces.length; i++) {
            pieces[i] = new Piece(i, ' ');
        }
    }

    public void changeBoard(int index, char icon) {
        pieces[index].setIcon(icon);
    }

    public Board copy() {
        Board board = new Board(rows, cols);
        // voor elk piece in pieces zet het op dezelfde index op het nieuwe bord
        for (Piece piece : pieces) {
            board.changeBoard(piece.getIndex(), piece.getIcon());
        }
        return board;
    }

    public Piece[] getBoard() {
        return pieces;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }
}
