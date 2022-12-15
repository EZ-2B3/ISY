import javax.swing.*;
public class PanelBoard extends JPanel {
    Board board = new Board(3,3);

    public PanelBoard() {
        board.getCols();
        board.getRows();
    }


    public void RedrawBoard() {
        //TODO: Redraw het board nadat board gecalled kan worden
    }
}
