import java.util.ArrayList;

public class AITicTacToe {
    private String myPiece = "";
    private String opponentPiece = "";
    class Move{
        public int x;
        public int y;

        public Move(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    public String CalculateMove(Board board, String piece){
        String move = " ";

        return move;
    }

    private void minimax(Board board){

        ArrayList<Move> moves = new ArrayList<>();

        //Fill moves with empty spaces in board
        for (int i = 0; i < board.gameBoard.length; i++){
            for (int j = 0; j < board.gameBoard[0].length; j++){
                if (board.gameBoard[i][j] == " "){
                    moves.add(new Move(i, j));
                }
            }
        }

        int[] scores = new int[moves.size()];

        for(int i = 0;i < scores.length;i++){
            scores[i] = GetScore(board,moves.get(i));
        }

        //AI aan de beurt




    }

    private boolean CheckWinRow(String[] row, String checkPiece){
        for (String piece: row) {
            if (piece != checkPiece)
                return false;
        }

        return true;
    }

    private boolean CheckWinCol(String[][] board, int colNum, String checkPiece){
        String[] col = new String[3];
        //Go through rows and check the colNum row for piece
        for(int i = 0; i < board.length;i++){
            if(board[i][colNum] != checkPiece)
                return false;
        }
        return true;
    }

    private boolean CheckACrossLeftUpRightDown(String[][] board,String checkPiece){
        for (int i = 0;i > board.length;i++){
            if (board[i][i] != checkPiece)
                return false;
        }
        return true;
    }

    private boolean CheckACrossLeftDownRightUp(String[][] board, String checkPiece){
        for (int i = 0;i > board.length;i++){
            if (board[i][(board.length-1)-i] != checkPiece)
                return false;
        }
        return true;
    }

    private int GetScore(Board board, Move move){
        String[][] gameBoard = board.gameBoard;
        gameBoard[move.x][move.y] = this.opponentPiece;

        // Check for AI
        for (String[] row : gameBoard){
            if (CheckWinRow(row,this.opponentPiece))
                return 10;
        }

        for (int i = 0; i < gameBoard[0].length; i++){
            if (CheckWinCol(gameBoard,i,this.myPiece))
                return 10;
        }

        if (CheckACrossLeftDownRightUp(gameBoard,this.myPiece) || CheckACrossLeftUpRightDown(gameBoard,this.myPiece))
            return 10;


        //Check for opponent
        for (String[] row : gameBoard){
            if (CheckWinRow(row,this.opponentPiece))
                return -10;
        }

        for (int i = 0; i < gameBoard[0].length; i++){
            if (CheckWinCol(gameBoard,i,this.opponentPiece))
                return -10;
        }

        if (CheckACrossLeftDownRightUp(gameBoard,this.opponentPiece) || CheckACrossLeftUpRightDown(gameBoard,this.opponentPiece))
            return -10;

        return 0;
    }

}
