import javax.swing.*;
public class PanelBoard extends JPanel {
    board board = new board(3,3);

    public PanelBoard() {
        board.getCols();
        board.getRows();
    }


    public void RedrawBoard() {
        //TODO: Redraw het board nadat board gecalled kan worden
    }
}
