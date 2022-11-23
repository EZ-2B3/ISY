public class Reversi {
    public Reversi(Board board) {
        board.setBoard(3, 3, "⚪");
        board.setBoard(3, 4, "⚫");
        board.setBoard(4, 3, "⚫");
        board.setBoard(4, 4, "⚪");
    }

    public void CheckValidMoves(Board board, String playerIcon) {
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getCols(); j++) {
                if (board.getBoard()[i][j] == " ") {
                    if (CheckMove(board, i, j, playerIcon)) {
                        board.setBoard(i, j, " ");
                    } else {
                        board.setBoard(i, j, "");
                    }
                }
            }
        }
    }

    private boolean CheckMove(Board board, int row, int col, String playerIcon) {
        boolean validMove = false;
        int rows = board.getRows();
        int cols = board.getCols();
        String opponentIcon = playerIcon == "⚫" ? "⚪" : "⚫";

        // Check if the move is valid
        if (board.getBoard()[row][col] == " ") {
            // Check if the move is valid in the north direction
            if (row - 1 >= 0 && board.getBoard()[row - 1][col] == opponentIcon) {
                for (int i = row - 2; i >= 0; i--) {
                    if (board.getBoard()[i][col] == playerIcon) {
                        validMove = true;
                        break;
                    } else if (board.getBoard()[i][col] == " ") {
                        break;
                    }
                }
            }

            // Check if the move is valid in the south direction
            if (row + 1 < rows && board.getBoard()[row + 1][col] == opponentIcon) {
                for (int i = row + 2; i < rows; i++) {
                    if (board.getBoard()[i][col] == playerIcon) {
                        validMove = true;
                        break;
                    } else if (board.getBoard()[i][col] == " ") {
                        break;
                    }
                }
            }

            // Check if the move is valid in the east direction
            if (col + 1 < cols && board.getBoard()[row][col + 1] == opponentIcon) {
                for (int i = col + 2; i < cols; i++) {
                    if (board.getBoard()[row][i] == playerIcon) {
                        validMove = true;
                        break;
                    } else if (board.getBoard()[row][i] == " ") {
                        break;
                    }
                }
            }

            // Check if the move is valid in the west direction
            if (col - 1 >= 0 && board.getBoard()[row][col - 1] == opponentIcon) {
                for (int i = col - 2; i >= 0; i--) {
                    if (board.getBoard()[row][i] == playerIcon) {
                        validMove = true;
                        break;
                    } else if (board.getBoard()[row][i] == " ") {
                        break;
                    }
                }
            }

            // Check if the move is valid in the north-east direction
            if (row - 1 >= 0 && col + 1 < cols && board.getBoard()[row - 1][col + 1] == opponentIcon) {
                for (int i = row - 2, j = col + 2; i >= 0 && j < cols; i--, j++) {
                    if (board.getBoard()[i][j] == playerIcon) {
                        validMove = true;
                        break;
                    } else if (board.getBoard()[i][j] == " ") {
                        break;
                    }
                }
            }

            // Check if the move is valid in the north-west direction
            if (row - 1 >= 0 && col - 1 >= 0 && board.getBoard()[row - 1][col - 1] == opponentIcon) {
                for (int i = row - 2, j = col - 2; i >= 0 && j >= 0; i--, j--) {
                    if (board.getBoard()[i][j] == playerIcon) {
                        validMove = true;
                        break;
                    } else if (board.getBoard()[i][j] == " ") {
                        break;
                    }
                }
            }

            // Check if the move is valid in the south-east direction
            if (row + 1 < rows && col + 1 < cols && board.getBoard()[row + 1][col + 1] == opponentIcon) {
                for (int i = row + 2, j = col + 2; i < rows && j < cols; i++, j++) {
                    if (board.getBoard()[i][j] == playerIcon) {
                        validMove = true;
                        break;
                    } else if (board.getBoard()[i][j] == " ") {
                        break;
                    }
                }
            }

            // Check if the move is valid in the south-west direction
            if (row + 1 < rows && col - 1 >= 0 && board.getBoard()[row + 1][col - 1] == opponentIcon) {
                for (int i = row + 2, j = col - 2; i < rows && j >= 0; i++, j--) {
                    if (board.getBoard()[i][j] == playerIcon) {
                        validMove = true;
                        break;
                    } else if (board.getBoard()[i][j] == " ") {
                        break;
                    }
                }
            }
        }

        return validMove;
    }
}
