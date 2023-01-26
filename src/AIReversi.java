import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AIReversi implements AI {

    private String myPiece = "";
    private String opponentPiece = "";
    private String type = "";
    private final Reversi reversi;
    private int totalMoves = 0;
    private int myPieces;
    private int opponentPieces;

    public AIReversi(String myPiece, String opponentPiece, String type, Reversi reversi) {
        this.myPiece = myPiece;
        this.opponentPiece = opponentPiece;
        this.type = type;
        this.reversi = reversi;
    }

    public int GetBestMove(Board board) {
        long start = System.currentTimeMillis();
        long end;
        int i;
        System.out.println("--------------------");
        switch (type) {
            case "minimax" -> {
                totalMoves = 0;
                i = GetMinimaxMove(board, start);
                end = System.currentTimeMillis();
                System.out.println("Time taken: " + (end - start) + "ms");
                System.out.println("Type of AI: " + type);
                System.out.println("Total moves: " + totalMoves);
                //totalMoves = 0;
                return i;
            }
            case "alphabeta" -> {
                totalMoves = 0;
                i = GetAlphaBetaMove(board, start);
                end = System.currentTimeMillis();
                System.out.println("Time taken: " + (end - start) + "ms");
                System.out.println("Type of AI: " + type);
                System.out.println("Total moves: " + totalMoves);
                //totalMoves = 0;
                return i;
            }
            default -> {
                totalMoves = 0;
                i = GetAlphaBetaThreadMove(board, start);
                end = System.currentTimeMillis();
                System.out.println("Time taken: " + (end - start) + "ms");
                System.out.println("Type of AI: " + type);
                System.out.println("Total moves: " + totalMoves);
                //totalMoves = 0;
                return i;
            }
//                return GetRandomMove(board);
        }
    }

    private int GetAlphaBetaMove(Board board, long start) {
        int bestMove = -1;
        int bestScore = Integer.MIN_VALUE;
        ArrayList<Integer> moves = GetValidMoves(board);
        System.out.println("Moves: " + moves.size());
        totalMoves += moves.size();
        for (int move : moves) {
            Board newBoard = board.Copy();
            newBoard.setBoard(move, myPiece);
            int score = AlphaBeta(newBoard, -1, false, Integer.MIN_VALUE, Integer.MAX_VALUE, start);
            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }
        }
        return bestMove;
    }

    private int GetAlphaBetaThreadMove(Board board, long start) {
        ArrayList<Integer> moves = GetValidMoves(board);
        System.out.println("Moves: " + moves.size());
        totalMoves += moves.size();
        if (moves.size() == 1) {
            return moves.get(0);
        } else if (moves.size() == 0) {
            return -1;
        }
        final int[] bestMove = {-1};
        final int[] bestScore = {Integer.MIN_VALUE};
        Executor executor = Executors.newFixedThreadPool(moves.size());
        CountDownLatch latch = new CountDownLatch(moves.size());

        for (int move : moves) {
            executor.execute(new Runnable() {
                public void run() {
                    Board newBoard = board.Copy();
                    newBoard.setBoard(move, myPiece);
                    int score = AlphaBeta(newBoard, -1, false, Integer.MIN_VALUE, Integer.MAX_VALUE, start);
                    if (score > bestScore[0]) {
                        bestScore[0] = score;
                        bestMove[0] = move;
                    }
                    latch.countDown();
                }
            });
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (bestMove[0] == -1) {
            return GetRandomMove(board);
        }
        return bestMove[0];
    }


    private int GetMinimaxMove(Board board, long start) {
        ArrayList<Integer> moves = GetValidMoves(board);
        System.out.println("Moves: " + moves.size());
        totalMoves += moves.size();
        int bestMove = -1;
        int bestScore = -1000;
        for (int move : moves) {
            Board newBoard = board.Copy();
            newBoard.setBoard(move, myPiece);
            reversi.CheckCaptures(newBoard, move, myPiece);
            int score = MiniMax(newBoard, 5, false, start);
            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }
        }
        return bestMove;
    }

    private int MiniMax(Board board, int depth, boolean isMaximizing, long start) {
        if (depth == 0 || System.currentTimeMillis() - start > 4500) {
            return EvaluateBoard(board);
        }
        ArrayList<Integer> moves = GetValidMoves(board);
        totalMoves += moves.size();
        if (moves.size() == 0) {
            return EvaluateBoard(board);
        }
        if (isMaximizing) {
            int score = Integer.MIN_VALUE;
            for (int move : moves) {
                Board newBoard = board.Copy();
                newBoard.setBoard(move, myPiece);
                reversi.CheckCaptures(newBoard, move, myPiece);
                score = Math.max(score, MiniMax(newBoard, depth - 1, false, start));
            }
            return score;
        } else {
            int score = Integer.MAX_VALUE;
            for (int move : moves) {
                Board newBoard = board.Copy();
                newBoard.setBoard(move, opponentPiece);
                reversi.CheckCaptures(newBoard, move, opponentPiece);
                score = Math.min(score, MiniMax(newBoard, depth - 1, true, start));
            }
            return score;
        }
    }

    private int AlphaBeta(Board board, int depth, boolean isMaximizing, int alpha, int beta, long start) {
        if (depth == 0 || System.currentTimeMillis() - start > 4500) {
            return EvaluateBoard(board);
        }
        ArrayList<Integer> moves = GetValidMoves(board);
        if (moves.size() == 0) {
            return EvaluateBoard(board);
        }
        if (isMaximizing) {
            int score = Integer.MIN_VALUE;
            for (int move : moves) {
                totalMoves++;
                Board newBoard = board.Copy();
                newBoard.setBoard(move, myPiece);
                reversi.CheckCaptures(newBoard, move, myPiece);
                score = Math.max(score, AlphaBeta(newBoard, depth - 1, false, alpha, beta, start));
                alpha = Math.max(alpha, score);
                if (beta <= alpha) {
                    break;
                }
            }
            return score;
        } else {
            int score = Integer.MAX_VALUE;
            for (int move : moves) {
                totalMoves++;
                Board newBoard = board.Copy();
                newBoard.setBoard(move, opponentPiece);
                reversi.CheckCaptures(newBoard, move, opponentPiece);
                score = Math.min(score, AlphaBeta(newBoard, depth - 1, true, alpha, beta, start));
                beta = Math.min(beta, score);
                if (beta <= alpha) {
                    break;
                }
            }
            return score;
        }
    }
    private int EvaluateBoard(Board board) {
        myPieces = 0;
        opponentPieces = 0;
        int myMoves = 0;
        int opponentMoves = 0;
        int myCornerControl = 0;
        int opponentCornerControl = 0;
        int myStableDisks = 0;
        int opponentStableDisks = 0;
        boolean endGame = false;

        // Count pieces, moves, and corner control
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board.getBoard()[i][j].equals(myPiece)) {
                    myPieces++;
                    if((i==0 && j==0) || (i==7 && j==0) || (i==0 && j==7) || (i==7 && j==7)) {
                        myCornerControl++;
                    }
                    if(IsStable(board, i, j)) {
                        myStableDisks++;
                    }
                } else if (board.getBoard()[i][j].equals(opponentPiece)) {
                    opponentPieces++;
                    if((i==0 && j==0) || (i==7 && j==0) || (i==0 && j==7) || (i==7 && j==7)) {
                        opponentCornerControl++;
                    }
                    if(IsStable(board, i, j)) {
                        opponentStableDisks++;
                    }
                }
                if(board.IsValidMove(i,j, myPiece)) {
                    myMoves++;
                }
                if(board.IsValidMove(i,j, opponentPiece)) {
                    opponentMoves++;
                }
            }
        }
        if(myMoves==0 && opponentMoves==0) {
            endGame=true;
        }
        // Weighting
        int material = 100 * (myPieces - opponentPieces);
        int mobility = 25 * (myMoves - opponentMoves);
        int cornerControl = 75 * (myCornerControl - opponentCornerControl);
        int stableDisk = 50 * (myStableDisks - opponentStableDisks);
        int parity = 0;
        if(endGame) {
            parity = 10 * (myPieces - opponentPieces);
        }
        // Add up the weighted values
        return material + mobility + cornerControl + stableDisk + parity;
    }

    private boolean IsStable(Board board, int i, int j) {
        String piece = board.getBoard()[i][j];
        if (piece.equals(myPiece) || piece.equals(opponentPiece)) {
            int dirI,dirJ;
            for(dirI=-1; dirI<=1; dirI++) {
                for(dirJ=-1; dirJ<=1; dirJ++) {
                    if(dirI==0 && dirJ==0) continue;
                    int r = i+dirI, c = j+dirJ;
                    while(r>=0 && r<=7 && c>=0 && c<=7) {
                        if(board.getBoard()[r][c].equals(piece)) {
                            break;
                        } else if(board.getBoard()[r][c].equals("")) {
                            return false;
                        }
                        r += dirI; c += dirJ;
                    }
                }
            }
            return true;
        }
        return false;
    }



    private int GetRandomMove(Board board) {
        ArrayList<Integer> moves = GetValidMoves(board);
        if (moves.size() == 0) {
        }
        int randomIndex = (int) (Math.random() * moves.size());
        return moves.get(randomIndex);
    }

    private ArrayList<Integer> GetValidMoves(Board board) {
        ArrayList<Integer> moves = new ArrayList<>();
        boolean[][] validMoves = board.CheckValidMoves(myPiece, "Reversi");
        for (int i = 0; i < validMoves.length; i++) {
            for (int j = 0; j < validMoves[i].length; j++) {
                if (validMoves[i][j]) {
                    moves.add(i * validMoves.length + j);
                }
            }
        }
        return moves;
    }

    public int GetTotalMoves() {
        return totalMoves;
    }

    public int GetMyPieces() {
        return myPieces;
    }

    public int GetOpponentPieces() {
        return opponentPieces;
    }


}
