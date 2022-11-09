import java.util.ArrayList;

public class AITicTacToe {
    private String myPiece = "";
    private String opponentPiece = "";

    class Move {
        public int x;
        public int y;

        public Move(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public AITicTacToe(String myPiece, String opponentPiece) {
        this.myPiece = myPiece;
        this.opponentPiece = opponentPiece;
    }

    public Move getBestMove(String[][] board) {
        int bestScore = Integer.MIN_VALUE;
        Move bestMove = new Move(-1, -1);
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].equals(" ")) {
                    board[i][j] = myPiece;
                    int score = minimax(board, false);
                    board[i][j] = " ";
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove.x = i;
                        bestMove.y = j;
                    }
                }
            }
        }
        return bestMove;
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
                        int score = minimax(board,  false);
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


}
