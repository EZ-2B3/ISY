import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Render {
    private final JPanel panelLogin = new JPanel();
    private final ActionListener actionListener;
    public JFrame frame = new JFrame();
    public JPanel panelAIChoice = new JPanel();
    public JPanel panelGameChoice = new JPanel();
    public JPanel panelBoard = new JPanel();
    public JPanel panelChallenge = new JPanel();
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

    public void BoardRender(String[][] board, boolean turn, String opponent, String gameType) {
        int rows = board.length;
        int cols = board[0].length;
        panelBoard.removeAll();
        frame.setTitle(gameType + " - " + opponent + " - " + (turn ? "Your turn" : "Opponent's turn"));
        panelBoard.setLayout(new GridLayout(rows, cols));
        int n = 0;
        for (String[] strings : board) {
            for (int j = 0; j < cols; j++) {
                JButton button = new JButton(strings[j]);
                // set fontsize to 25 and accept emoji's
                button.setFont(new Font("", Font.PLAIN, 25));
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
        JButton reversi = new JButton("Reversi");
        JButton challenge = new JButton("Challenge");
        JButton exit = new JButton("Exit");

        ticTacToe.addActionListener(this.actionListener);
        panelGameChoice.add(ticTacToe);

        reversi.addActionListener(this.actionListener);
        panelGameChoice.add(reversi);

        challenge.addActionListener(this.actionListener);
        panelGameChoice.add(challenge);

        panelGameChoice.add(exit);
        exit.addActionListener(this.actionListener);
    }

    public void ChallengeRender(String players) {
        panelChallenge.removeAll();
        frame.setTitle("Challenge");
        String[] playerList = players.split(", ");
        for (String player : playerList) {
            // place the player name in a vertical list with a button to challenge them next to their name (if they are not you) on the right side of the screen
            if (!player.equals(username.getText())) {
                JButton button = new JButton("Challenge " + player);
                button.setActionCommand("ChallengeSend");
                button.addActionListener(this.actionListener);
                panelChallenge.add(button);
            }
        }
        // place a button to go back to the game choice screen on the bottom of the screen and center it
        JButton button = new JButton("Back");
        button.setActionCommand("ChallengeBack");
        button.addActionListener(this.actionListener);
        panelChallenge.add(button);
    }

    public void UpdateFrame(JPanel panel) {
        frame.setContentPane(panel);
        frame.revalidate();
        frame.repaint();
    }

    public void GameOverRender(String result, String opponent) {
        // TODO: GameType meenemen
        JPopupMenu popup = new JPopupMenu();

        JLabel label = new JLabel(result);
        popup.add(label);

        JMenuItem menuItem = new JMenuItem("challenge " + opponent);
        menuItem.setActionCommand("ChallengeSend");
        menuItem.addActionListener(this.actionListener);
        popup.add(menuItem);

        JMenuItem menuItem2 = new JMenuItem("Subscribe to this gametype again");
        menuItem2.setActionCommand("Reversi");
        menuItem2.addActionListener(this.actionListener);
        popup.add(menuItem2);

        JMenuItem menuItem3 = new JMenuItem("Quit");
        menuItem3.addActionListener(this.actionListener);
        popup.add(menuItem3);

        popup.show(frame, frame.getWidth() / 2, frame.getHeight() / 2);
    }
}
