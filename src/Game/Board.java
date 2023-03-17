package Game;

public interface Board {
    void initializeBoard();
    void printBoard();
    boolean isCellEmpty(int row, int col);
    boolean placePiece(int row, int col, char symbol);
    char getPieceAt(int row, int col);
}
