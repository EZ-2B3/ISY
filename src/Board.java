public class Board {

    private Piece[] pieces;
    private int rows;
    private int cols;

    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
    }

    public void SetBoard(Piece piece) {
//        TODO: Zet het stuk op het bord
    }

    public Piece[] GetBoard() {
        //TODO: Geef het bord terug
        return pieces;
    }

    public Board Copy() {
        //TODO: Maak een kopie van het bord en geef deze terug
//        Let op return hier onder doet niks nuttigs
        return new Board(rows, cols);
    }

    public int GetRows() {
        return rows;
    }

    public int GetCols() {
        return cols;
    }
}


