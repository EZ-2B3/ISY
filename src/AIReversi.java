import java.util.ArrayList;

public class AIReversi implements AI{

    private String myPiece = "";
    private String opponentPiece = "";
    private String type = "";
    private Reversi reversi;

    public AIReversi(String myPiece, String opponentPiece, String type, Reversi reversi) {
        this.myPiece = myPiece;
        this.opponentPiece = opponentPiece;
        this.type = type;
        this.reversi = reversi;
    }

    public int GetBestMove(Board board) {
        switch (type) {
            case "minimax":
                return GetMinimaxMove(board);
            case "alphabeta":
                return GetAlphaBetaMove(board);
            default:
                return GetRandomMove(board);
        }
    }

    private int GetAlphaBetaMove(Board board) {
        return 0;
    }

    private int GetMinimaxMove(Board board) {
        return 0;
    }

    private int GetRandomMove(Board board) {
        ArrayList<Integer> moves = GetValidMoves(board);
        int randomIndex = (int) (Math.random() * moves.size());
        return moves.get(randomIndex);
    }

    private ArrayList<Integer> GetValidMoves(Board board) {
        ArrayList<Integer> moves = new ArrayList<>();
        for (int i = 0; i < board.getBoard().length; i++) {
            for (int j = 0; j < board.getBoard()[i].length; j++) {
                if (board.getBoard()[i][j].equals(" ")) {
                    moves.add(i * board.getBoard().length + j);
                }
            }
        }
        return moves;
    }
}
