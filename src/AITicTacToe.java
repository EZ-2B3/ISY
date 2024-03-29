public class AITicTacToe implements AI {
    private String myPiece = "";
    private String opponentPiece = "";

    public AITicTacToe(String myPiece, String opponentPiece) {
        this.myPiece = myPiece;
        this.opponentPiece = opponentPiece;
    }

    public int GetBestMove(Board board) {
        int bestScore = Integer.MIN_VALUE;
        int x = 0;
        int y = 0;
        for (int i = 0; i < board.getBoard().length; i++) {
            for (int j = 0; j < board.getBoard()[i].length; j++) {
                if (board.getBoard()[i][j].equals(" ")) {
                    board.getBoard()[i][j] = myPiece;
                    int score = minimax(board.getBoard(), false);
                    board.getBoard()[i][j] = " ";
                    if (score > bestScore) {
                        bestScore = score;
                        x = i;
                        y = j;
                    }
                }
            }
        }
        return x * board.getBoard().length + y;
    }

    private int minimax(String[][] board, boolean isMaximizing) {
        String result = checkWinner(board);
        if (!result.equals(" ")) {
            if (result.equals(myPiece)) {
                return 1;
            } else if (result.equals(opponentPiece)) {
                return -1;
            } else {
                return 0;
            }
        }
        int bestScore;
        if (isMaximizing) {
            bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    if (board[i][j].equals(" ")) {
                        board[i][j] = myPiece;
                        int score = minimax(board, false);
                        board[i][j] = " ";
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
        } else {
            bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    if (board[i][j].equals(" ")) {
                        board[i][j] = opponentPiece;
                        int score = minimax(board, true);
                        board[i][j] = " ";
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
        }
        return bestScore;
    }

    private String checkWinner(String[][] board) {
        for (int i = 0; i < board.length; i++) {
            if (board[i][0].equals(board[i][1]) && board[i][0].equals(board[i][2]) && !board[i][0].equals(" ")) {
                return board[i][0];
            }
        }
        for (int i = 0; i < board.length; i++) {
            if (board[0][i].equals(board[1][i]) && board[0][i].equals(board[2][i]) && !board[0][i].equals(" ")) {
                return board[0][i];
            }
        }
        if (board[0][0].equals(board[1][1]) && board[0][0].equals(board[2][2]) && !board[0][0].equals(" ")) {
            return board[0][0];
        }
        if (board[0][2].equals(board[1][1]) && board[0][2].equals(board[2][0]) && !board[0][2].equals(" ")) {
            return board[0][2];
        }
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].equals(" ")) {
                    return " ";
                }
            }
        }
        return "tie";
    }

    public int GetTotalMoves(){
        return 0;
    }

    public int GetMyPieces(Board board){
        return 0;
    }

    public int GetOpponentPieces(Board board){
        return 0;
    }
}
