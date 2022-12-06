import javax.swing.*;
import java.awt.event.ActionListener;

public class PanelGameChoice extends JPanel {

    private final ActionListener actionListener;
    public PanelGameChoice(ActionListener actionListener) {

        //initialize actionListener
        this.actionListener = actionListener;

        //Create the panel
        JPanel panelgameChoice = new JPanel();

        //Creating the Buttons
        JButton TicTacToe = new JButton("TicTacToe");
        JButton Reversi = new JButton("Reversi");
        JButton Exit = new JButton("Exit");

        //Adding the Buttons to the Panel
        panelgameChoice.add(TicTacToe);
        panelgameChoice.add(Reversi);
        panelgameChoice.add(Exit);

        //Creating actionlisteners for the buttons
        TicTacToe.addActionListener(this.actionListener);
        Reversi.addActionListener(this.actionListener);
        Exit.addActionListener(this.actionListener);

        //Adding the panel to the frame
        add(panelgameChoice);

    }
}
