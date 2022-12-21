import javax.swing.*;
public class PanelBoard extends JPanel {

    public PanelBoard() {
        //Create the panel
        JPanel panelBoard = new JPanel();

        //Create the buttons
        JButton button1 = new JButton("");
        JButton button2 = new JButton("");
        JButton button3 = new JButton("");
        JButton button4 = new JButton("");
        JButton button5 = new JButton("");
        JButton button6 = new JButton("");
        JButton button7 = new JButton("");
        JButton button8 = new JButton("");
        JButton button9 = new JButton("");

        //Add the buttons to the panel
        panelBoard.add(button1);
        panelBoard.add(button2);
        panelBoard.add(button3);
        panelBoard.add(button4);
        panelBoard.add(button5);
        panelBoard.add(button6);
        panelBoard.add(button7);
        panelBoard.add(button8);
        panelBoard.add(button9);

        //Adding the panel to the frame
        add(panelBoard);
    }


    public void RedrawBoard() {
        //TODO: Redraw the board
    }
}
