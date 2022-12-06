import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PanelGameChoice extends JPanel {

    private final ActionListener actionListener;

    public JFrame frame = new JFrame();
    private JToggleButton toggleAI;

    public PanelGameChoice(ActionListener actionListener) {

        //Set title of frame
        frame.setTitle("Game Choice");

        //initialize actionListener
        this.actionListener = actionListener;

        //Create the panel
        JPanel panelgameChoice = new JPanel();

        //Creating the Buttons
        JButton TicTacToe = new JButton("TicTacToe");
        JButton Reversi = new JButton("Reversi");
        JButton Exit = new JButton("Exit");

        //Create a toggle
        this.toggleAI = new JToggleButton("Playing against AI: Yes");

        //Adding the Buttons to the Panel
        panelgameChoice.add(TicTacToe);
        panelgameChoice.add(Reversi);
        panelgameChoice.add(Exit);

        //Adding the toggle to the panel
        panelgameChoice.add(toggleAI);

        //Creating actionlisteners for the buttons
        TicTacToe.addActionListener(this.actionListener);
        Reversi.addActionListener(this.actionListener);
        Exit.addActionListener(this.actionListener);
        toggleAI.addActionListener(this.actionListener);

        //Adding the panel to the frame
        add(panelgameChoice);

    }
}
