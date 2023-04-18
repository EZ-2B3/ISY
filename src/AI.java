public interface AI {
    int GetBestMove(Board board);

    int GetTotalMoves();

    int GetMyPieces();

    int GetOpponentPieces();
}
