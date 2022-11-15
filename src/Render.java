import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Render {
    public JFrame frame = new JFrame();
    private JPanel panelLogin = new JPanel();
    public JPanel panelAIChoice = new JPanel();
    public JPanel panelGameChoice = new JPanel();
    public JPanel panelBoard = new JPanel();
    public JPanel panelChallenge = new JPanel();

    private ActionListener actionListener;

    public JTextField username = new JTextField(16);

    public Render(ActionListener actionListener) {
        this.actionListener = actionListener;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // set default close operation to exit the game
        frame.setSize(500, 500); // set size of frame
        frame.setLocationRelativeTo(null); // set location of frame to center of screen
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // set look and feel of frame to look and feel of operating system
        } catch (Exception e) {
            System.out.println("Error setting native LAF: " + e); // print error message to user
        }
        SwingUtilities.updateComponentTreeUI(frame); // update components of frame

        frame.setVisible(true); // set frame visible
        CreateLogin();
        CreateAIChoice();
        CreateGameChoice();
        frame.setTitle("Login"); // set title of frame
        UpdateFrame(panelLogin);
    }

    public void BoardRender(String[][] board, boolean turn, String opponent) {
        int rows = board.length;
        int cols = board[0].length;
        panelBoard.removeAll();
        frame.setTitle("Tic-Tac-Toe - " + opponent + " - " + (turn ? "Your turn" : "Opponent's turn"));
        panelBoard.setLayout(new GridLayout(rows, cols));
        int n = 0;
        for (String[] strings : board) {
            for (int j = 0; j < cols; j++) {
                JButton button = new JButton(strings[j]);
                if (!turn || !strings[j].equals(" ")) {
                    button.setEnabled(false);
                } else {
                    button.setEnabled(true);
                    button.setName(String.valueOf(n));
                    button.setActionCommand("move");
                    button.addActionListener(actionListener);
                }
                panelBoard.add(button);
                n++;
            }
        }
    }

    private void CreateLogin() {
        JLabel askName = new JLabel("Enter your name:");
        JButton exit = new JButton("Exit");

        panelLogin.add(askName);

        username.setActionCommand("Login");
        username.addActionListener(this.actionListener);
        panelLogin.add(username);

        exit.addActionListener(this.actionListener);
        panelLogin.add(exit);
    }

    private void CreateAIChoice() {
        JLabel askAI = new JLabel("Do you want to play as an AI?");
        JButton yes = new JButton("Yes");
        JButton no = new JButton("No");

        yes.setActionCommand("AIChoice");
        yes.addActionListener(this.actionListener);
        panelAIChoice.add(yes);

        no.setActionCommand("AIChoice");
        no.addActionListener(this.actionListener);
        panelAIChoice.add(no);

        panelAIChoice.add(askAI);
    }

    private void CreateGameChoice() {
        JButton ticTacToe = new JButton("Tic-Tac-Toe");
        JButton challenge = new JButton("Challenge");
        JButton exit = new JButton("Exit");

        ticTacToe.addActionListener(this.actionListener);
        panelGameChoice.add(ticTacToe);

        challenge.addActionListener(this.actionListener);
        panelGameChoice.add(challenge);

        panelGameChoice.add(exit);
        exit.addActionListener(this.actionListener);
    }

    public void ChallengeRender(String players, String playerName) {
        panelChallenge.removeAll();
        frame.setTitle("Challenge");
        String[] playerList = players.split(", ");
        for (String player : playerList) {
            // place the player name in a vertical list with a button to challenge them next to their name (if they are not you) on the right side of the screen
            if (!player.equals(playerName)) {
                JButton button = new JButton("Challenge " + player);
                button.setActionCommand("ChallengeSend");
                button.addActionListener(this.actionListener);
                panelChallenge.add(button);
            }
        }
    }

    public void UpdateFrame(JPanel panel) {
        frame.setContentPane(panel);
        frame.revalidate();
        frame.repaint();
    }
}

