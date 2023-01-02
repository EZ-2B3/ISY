import javax.swing.*;
import java.awt.*;

public class PanelBoard extends JPanel {
    private JButton[][] buttons;
    private int rows;
    private int cols;

    public PanelBoard(Board board) {
        this.rows = board.getRows();
        this.cols = board.getCols();
        this.buttons = new JButton[rows][cols];
        setLayout(new GridLayout(rows, cols));
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.buttons[i][j] = new JButton();
                this.buttons[i][j].setText("");
                this.buttons[i][j].setFont(new Font("Arial", Font.BOLD, 50));
                this.buttons[i][j].setEnabled(false);
                this.buttons[i][j].setActionCommand("Move " + i * cols + j);
                add(this.buttons[i][j]);
            }
        }
    }


    public void RedrawBoard(Board board, boolean[] validIndex) {
        Piece[] pieces = board.getBoard();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int index = i * cols + j;
                this.buttons[i][j].setText(String.valueOf(pieces[index].getIcon()));
                this.buttons[i][j].setEnabled(validIndex[index]);
            }
        }
    }
}
