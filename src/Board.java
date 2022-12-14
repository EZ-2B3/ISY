public class Board {

    private final Piece[] pieces;
    private final int rows;
    private final int cols;

    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;

        pieces = new Piece[rows * cols];
    }

    public void SetBoard(Piece piece) {
        int index = piece.GetIndex();

        if (pieces[index] == null) {
            pieces[index] = piece;
        }
    }

    public void ChangeBoard(int index, char icon) {
        if (pieces[index] != null) {
            pieces[index].SetIcon(icon);
        }
    }

    public Board Copy() {
        Board board = new Board(rows, cols);
        // voor elk piece in pieces zet het op dezelfde index op het nieuwe bord
        for (Piece piece : pieces) {
            if (piece != null) {
                board.SetBoard(piece);
            }
        }
        return board;
    }

    public Piece[] GetBoard() {
        return pieces;
    }

    public int GetRows() {
        return rows;
    }

    public int GetCols() {
        return cols;
    }
}
