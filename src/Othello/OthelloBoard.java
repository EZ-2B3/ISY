package Othello;

import Game.*;
import Core.Move;

import java.util.ArrayList;
import java.util.List;

public class OthelloBoard implements Board {
    private static final int BOARD_SIZE = 8;
    private Piece[][] board;
    private List<Move> validMoves;
    private Piece winner;

    public OthelloBoard() {
        board = new Piece[BOARD_SIZE][BOARD_SIZE];
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                board[row][col] = Piece.EMPTY;
            }
        }
        board[3][3] = board[4][4] = Piece.WHITE;
        board[3][4] = board[4][3] = Piece.BLACK;
        updateValidMoves();
    }

    public void makeMove(Move move, Piece piece) {
        board[move.getRow()][move.getCol()] = piece;
        List<Move> captured = getCapturedPieces(move, piece);
        for (Move capturedPiece : captured) {
            board[capturedPiece.getRow()][capturedPiece.getCol()] = piece;
        }
        updateValidMoves();
    }

    public boolean isValidMove(Move move) {
        for (Move validMove : validMoves) {
            if (validMove.equals(move)) {
                return true;
            }
        }
        return false;
    }

    public boolean isGameOver() {
        return validMoves.isEmpty();
    }

    public Piece getWinner() {
        return winner;
    }

    public void printBoard() {
        System.out.print("  ");
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int row = 0; row < BOARD_SIZE; row++) {
            System.out.print(row + " ");
            for (int col = 0; col < BOARD_SIZE; col++) {
                System.out.print(board[row][col].getSymbol() + " ");
            }
            System.out.println(row);
        }
        System.out.print("  ");
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    public String getScoreString() {
        int blackCount = 0;
        int whiteCount = 0;
        for (int row =        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (board[row][col] == Piece.BLACK) {
                    blackCount++;
                } else if (board[row][col] == Piece.WHITE) {
                    whiteCount++;
                }
            }
        }
        return "Black: " + blackCount + " White: " + whiteCount;
    }

    private List<Move> getCapturedPieces(Move move, Piece piece) {
        List<Move> captured = new ArrayList<>();
        for (int rowDelta = -1; rowDelta <= 1; rowDelta++) {
            for (int colDelta = -1; colDelta <= 1; colDelta++) {
                if (rowDelta == 0 && colDelta == 0) {
                    continue;
                }
                int row = move.getRow() + rowDelta;
                int col = move.getCol() + colDelta;
                while (row >= 0 && row < BOARD_SIZE && col >= 0 && col < BOARD_SIZE && board[row][col] == piece.getOpposite()) {
                    row += rowDelta;
                    col += colDelta;
                }
                if (row >= 0 && row < BOARD_SIZE && col >= 0 && col < BOARD_SIZE && board[row][col] == piece) {
                    while (true) {
                        row -= rowDelta;
                        col -= colDelta;
                        if (row == move.getRow() && col == move.getCol()) {
                            break;
                        }
                        captured.add(new Move(row, col));
                    }
                }
            }
        }
        return captured;
    }

    private void updateValidMoves() {
        validMoves = new ArrayList<>();
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Move move = new Move(row, col);
                if (isValidMove(move)) {
                    validMoves.add(move);
                }
            }
        }
        if (validMoves.isEmpty()) {
            int blackCount = 0;
            int whiteCount = 0;
            for (int row = 0; row < BOARD_SIZE; row++) {
                for (int col = 0; col < BOARD_SIZE; col++) {
                    if (board[row][col] == Piece.BLACK) {
                        blackCount++;
                    } else if (board[row][col] == Piece.WHITE) {
                        whiteCount++;
                    }
                }
            }
            if (blackCount > whiteCount) {
                winner = Piece.BLACK;
            } else if (whiteCount > blackCount) {
                winner = Piece.WHITE;
            } else {
                winner = Piece.EMPTY;
            }
        }
    }

    private enum Piece {
        BLACK('B', 'W'),
        WHITE('W', 'B'),
        EMPTY('-', '-');

        private final char symbol;
        private final char opposite;

        Piece(char symbol, char opposite) {
            this.symbol = symbol;
            this.opposite = opposite;
        }

        public char getSymbol() {
            return symbol;
        }

        public Piece getOpposite() {
            if (this == BLACK) {
                return WHITE;
            } else if (this == WHITE) {
                return BLACK;
            } else {
                return EMPTY;
            }
        }
    }
}


