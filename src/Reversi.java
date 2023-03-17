public class Reversi {
    public Reversi(Board board) {
        board.setBoard(3, 3, "⚪");
        board.setBoard(3, 4, "⚫");
        board.setBoard(4, 3, "⚫");
        board.setBoard(4, 4, "⚪");
    }

    public void CheckCaptures(Board board, int move, String playerIcon) {
        // check if there are any captures from the location of the move
        int row = move / board.getCols();
        int col = move % board.getCols();
        String opponentIcon = playerIcon == "⚫" ? "⚪" : "⚫";

        // check for captures in the north direction from the move location
        if (row - 1 >= 0 && board.getBoard()[row - 1][col] == opponentIcon) {
            for (int i = row - 2; i >= 0; i--) {
                if (board.getBoard()[i][col] == playerIcon) {
                    for (int j = row - 1; j > i; j--) {
                        board.setBoard(j, col, playerIcon);
                    }
                    break;
                } else if (board.getBoard()[i][col] == " " || board.getBoard()[i][col] == "") {
                    break;
                }
            }
        }
        // check for captures in the south direction from the move location
        if (row + 1 < board.getRows() && board.getBoard()[row + 1][col] == opponentIcon) {
            for (int i = row + 2; i < board.getRows(); i++) {
                if (board.getBoard()[i][col] == playerIcon) {
                    for (int j = row + 1; j < i; j++) {
                        board.setBoard(j, col, playerIcon);
                    }
                    break;
                } else if (board.getBoard()[i][col] == " " || board.getBoard()[i][col] == "") {
                    break;
                }
            }
        }
        // check for captures in the east direction from the move location
        if (col + 1 < board.getCols() && board.getBoard()[row][col + 1] == opponentIcon) {
            for (int i = col + 2; i < board.getCols(); i++) {
                if (board.getBoard()[row][i] == playerIcon) {
                    for (int j = col + 1; j < i; j++) {
                        board.setBoard(row, j, playerIcon);
                    }
                    break;
                } else if (board.getBoard()[row][i] == " " || board.getBoard()[row][i] == "") {
                    break;
                }
            }
        }
        // check for captures in the west direction from the move location
        if (col - 1 >= 0 && board.getBoard()[row][col - 1] == opponentIcon) {
            for (int i = col - 2; i >= 0; i--) {
                if (board.getBoard()[row][i] == playerIcon) {
                    for (int j = col - 1; j > i; j--) {
                        board.setBoard(row, j, playerIcon);
                    }
                    break;
                } else if (board.getBoard()[row][i] == " " || board.getBoard()[row][i] == "") {
                    break;
                }
            }
        }
        // check for captures in the north-east direction from the move location
        if (row - 1 >= 0 && col + 1 < board.getCols() && board.getBoard()[row - 1][col + 1] == opponentIcon) {
            for (int i = row - 2, j = col + 2; i >= 0 && j < board.getCols(); i--, j++) {
                if (board.getBoard()[i][j] == playerIcon) {
                    for (int k = row - 1, l = col + 1; k > i && l < j; k--, l++) {
                        board.setBoard(k, l, playerIcon);
                    }
                    break;
                } else if (board.getBoard()[i][j] == " " || board.getBoard()[i][j] == "") {
                    break;
                }
            }
        }
        // check for captures in the north-west direction from the move location
        if (row - 1 >= 0 && col - 1 >= 0 && board.getBoard()[row - 1][col - 1] == opponentIcon) {
            for (int i = row - 2, j = col - 2; i >= 0 && j >= 0; i--, j--) {
                if (board.getBoard()[i][j] == playerIcon) {
                    for (int k = row - 1, l = col - 1; k > i && l > j; k--, l--) {
                        board.setBoard(k, l, playerIcon);
                    }
                    break;
                } else if (board.getBoard()[i][j] == " " || board.getBoard()[i][j] == "") {
                    break;
                }
            }
        }
        // check for captures in the south-east direction from the move location
        if (row + 1 < board.getRows() && col + 1 < board.getCols() && board.getBoard()[row + 1][col + 1] == opponentIcon) {
            for (int i = row + 2, j = col + 2; i < board.getRows() && j < board.getCols(); i++, j++) {
                if (board.getBoard()[i][j] == playerIcon) {
                    for (int k = row + 1, l = col + 1; k < i && l < j; k++, l++) {
                        board.setBoard(k, l, playerIcon);
                    }
                    break;
                } else if (board.getBoard()[i][j] == " " || board.getBoard()[i][j] == "") {
                    break;
                }
            }
        }
        // check for captures in the south-west direction from the move location
        if (row + 1 < board.getRows() && col - 1 >= 0 && board.getBoard()[row + 1][col - 1] == opponentIcon) {
            for (int i = row + 2, j = col - 2; i < board.getRows() && j >= 0; i++, j--) {
                if (board.getBoard()[i][j] == playerIcon) {
                    for (int k = row + 1, l = col - 1; k < i && l > j; k++, l--) {
                        board.setBoard(k, l, playerIcon);
                    }
                    break;
                } else if (board.getBoard()[i][j] == " " || board.getBoard()[i][j] == "") {
                    break;
                }
            }
        }
    }
}
