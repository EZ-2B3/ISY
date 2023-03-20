import javax.swing.*;
import java.awt.*;

public class StatsFrame extends JFrame {
    private JLabel player1Label;
    private JLabel player2Label;
    private JLabel player1ScoreLabel;
    private JLabel player2ScoreLabel;
    private JLabel turnLabel;
    private JLabel currentPlayerLabel;

    public StatsFrame(String player1Name, String player2Name) {
        super("Game Stats"); // set the title of the frame
        setSize(300, 200); // set the size of the frame
        setLocationRelativeTo(null); // center the frame
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // close the frame when the user clicks the close button
        setLayout(new GridLayout(6, 2));

        player1Label = new JLabel(player1Name + ": ");
        player2Label = new JLabel(player2Name + ": ");
        player1ScoreLabel = new JLabel("0");
        player2ScoreLabel = new JLabel("0");
        turnLabel = new JLabel("Turn: ");
        currentPlayerLabel = new JLabel(player1Name);

        add(player1Label);
        add(player1ScoreLabel);
        add(player2Label);
        add(player2ScoreLabel);
        add(turnLabel);
        add(currentPlayerLabel);

        setVisible(true);
    }

    public void updateStats(int player1Score, int player2Score, String currentPlayer) {
        player1ScoreLabel.setText(String.valueOf(player1Score));
        player2ScoreLabel.setText(String.valueOf(player2Score));
        currentPlayerLabel.setText(currentPlayer);
    }
}
