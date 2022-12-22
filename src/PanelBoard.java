import javax.swing.*;
import java.awt.*;

public class PanelBoard extends JPanel {
    private JButton[][] board;
    private int rows;
    private int cols;

    public PanelBoard(Board board) {
        this.rows = board.getRows();
        this.cols = board.getCols();
        this.board = new JButton[rows][cols];
        setLayout(new GridLayout(rows, cols));
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.board[i][j] = new JButton();
                this.board[i][j].setText("");
                this.board[i][j].setFont(new Font("Arial", Font.BOLD, 50));
                this.board[i][j].setEnabled(false);
                add(this.board[i][j]);
            }
        }
    }


    public void RedrawBoard(Board board) {
        Piece[] pieces = board.getBoard();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (pieces[i * cols + j].getIcon() == ' ') {
                    this.board[i][j].setEnabled(true);
                } else {
                    this.board[i][j].setText(String.valueOf(pieces[i * cols + j].getIcon()));
                    this.board[i][j].setEnabled(false);
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(750, 750);
        Board board = new Board(8, 8);
        PanelBoard panel = new PanelBoard(board);
        frame.add(panel);
        frame.setVisible(true);
        panel.RedrawBoard(board);
        Thread.sleep(1000);
        board.changeBoard(0, 'X');
        panel.RedrawBoard(board);
    }
}
