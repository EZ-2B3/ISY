public interface AI {
    int GetBestMove(Board board);

    int GetTotalMoves();

    int GetMyPieces(Board board);

    int GetOpponentPieces(Board board);
}
