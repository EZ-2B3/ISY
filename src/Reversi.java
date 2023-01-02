import javax.swing.*;
import java.awt.*;
import java.util.Dictionary;

public class Reversi {

    private Game game;
    private char playerIcon = ' ';


    public Reversi(Game game) {
        this.game = game;

        game.board.changeBoard(28, '⚪');
        game.board.changeBoard(27, '⚫');
        game.board.changeBoard(35, '⚫');
        game.board.changeBoard(36, '⚪');
    }

    public boolean[] CheckValidMoves(Board board) {
        boolean[] validIndex = new boolean[board.getRows() * board.getCols()];
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getCols(); j++) {
                int index = i * board.getCols() + j;
                if (board.getBoard()[index].getIcon() == ' ' || board.getBoard()[index].getIcon() == 'V') {
                    validIndex[index] = CheckMove(board, index, playerIcon);
                }
            }
        }
        return validIndex;
    }

    private boolean CheckMove(Board board, int index, char playerIcon) {
        int rows = board.getRows();
        int cols = board.getCols();
        char opponentIcon = playerIcon == '⚫' ? '⚪' : '⚫';

        // Check if the move is valid
        if (board.getBoard()[index].getIcon() == ' ' || board.getBoard()[index].getIcon() == 'V') {
            // Check if the move is valid in the north direction
            if (index - cols >= 0 && board.getBoard()[index - cols].getIcon() == opponentIcon) {
                for (int i = index - cols; i >= 0; i -= cols) {
                    if (board.getBoard()[i].getIcon() == playerIcon) {
                        return true;
                    } else if (board.getBoard()[i].getIcon() == ' ') {
                        break;
                    }
                }
            }

            // Check if the move is valid in the south direction
            if (index + cols < rows * cols && board.getBoard()[index + cols].getIcon() == opponentIcon) {
                for (int i = index + cols; i < rows * cols; i += cols) {
                    if (board.getBoard()[i].getIcon() == playerIcon) {
                        return true;
                    } else if (board.getBoard()[i].getIcon() == ' ') {
                        break;
                    }
                }
            }

            // Check if the move is valid in the east direction
            if (index % cols != cols - 1 && board.getBoard()[index + 1].getIcon() == opponentIcon) {
                for (int i = index + 1; i % cols != cols - 1; i++) {
                    if (board.getBoard()[i].getIcon() == playerIcon) {
                        return true;
                    } else if (board.getBoard()[i].getIcon() == ' ') {
                        break;
                    }
                }
            }

            // Check if the move is valid in the west direction
            if (index % cols != 0 && board.getBoard()[index - 1].getIcon() == opponentIcon) {
                for (int i = index - 1; i % cols != 0; i--) {
                    if (board.getBoard()[i].getIcon() == playerIcon) {
                        return true;
                    } else if (board.getBoard()[i].getIcon() == ' ') {
                        break;
                    }
                }
            }

            // Check if the move is valid in the north-east direction
            if (index - cols >= 0 && index % cols != cols - 1 && board.getBoard()[index - cols + 1].getIcon() == opponentIcon) {
                for (int i = index - cols + 1; i - cols >= 0 && i % cols != cols - 1; i += cols + 1) {
                    if (board.getBoard()[i].getIcon() == playerIcon) {
                        return true;
                    } else if (board.getBoard()[i].getIcon() == ' ') {
                        break;
                    }
                }
            }

            // Check if the move is valid in the north-west direction
            if (index - cols >= 0 && index % cols != 0 && board.getBoard()[index - cols - 1].getIcon() == opponentIcon) {
                for (int i = index - cols - 1; i - cols >= 0 && i % cols != 0; i += cols - 1) {
                    if (board.getBoard()[i].getIcon() == playerIcon) {
                        return true;
                    } else if (board.getBoard()[i].getIcon() == ' ') {
                        break;
                    }
                }
            }

            // Check if the move is valid in the south-east direction
            if (index + cols < rows * cols && index % cols != cols - 1 && board.getBoard()[index + cols + 1].getIcon() == opponentIcon) {
                for (int i = index + cols + 1; i + cols < rows * cols && i % cols != cols - 1; i += cols + 1) {
                    if (board.getBoard()[i].getIcon() == playerIcon) {
                        return true;
                    } else if (board.getBoard()[i].getIcon() == ' ') {
                        break;
                    }
                }
            }

            // Check if the move is valid in the south-west direction
            if (index + cols < rows * cols && index % cols != 0 && board.getBoard()[index + cols - 1].getIcon() == opponentIcon) {
                for (int i = index + cols - 1; i + cols < rows * cols && i % cols != 0; i += cols - 1) {
                    if (board.getBoard()[i].getIcon() == playerIcon) {
                        return true;
                    } else if (board.getBoard()[i].getIcon() == ' ') {
                        break;
                    }
                }
            }
        }
        return false;
    }
}
