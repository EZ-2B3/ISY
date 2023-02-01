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

    public void BoardRender(String[][] board, boolean turn, String opponent, String gameType, boolean[][] validMoves) {
        int rows = board.length; //Get number of rows
        int cols = board[0].length; //Get number of columns

        panelBoard.removeAll(); //Remove all components from panel
        frame.setTitle(gameType + " - " + opponent + " - " + (turn ? "Your turn" : "Opponent's turn")); //Set title of frame
        panelBoard.setLayout(new GridLayout(rows, cols)); //Set layout of panel

        int n = 0;
        for (String[] strings : board) { //Loop through rows
            for (int j = 0; j < cols; j++) { //Loop through columns
                JButton button = new JButton(strings[j]); //Create button
                button.setFont(new Font("", Font.PLAIN, 25)); //Set font of button
                button.setEnabled(validMoves[n / cols][n % cols] && turn); //Set button enabled if it is a valid move and it is the player's turn
                button.setName(String.valueOf(n)); //Set name of button to the index of the button
                button.addActionListener(actionListener); //Add action listener to button
                button.setActionCommand("move"); //Set action command of button
                panelBoard.add(button); //Add button to panel
                n++; //Increment n
            }
        }
    }

    private void CreateLogin() {
        JLabel askName = new JLabel("Enter your name:"); //Create label
        JButton exit = new JButton("Exit"); //Create button

        panelLogin.add(askName); //Add label to panel

        username.setActionCommand("Login"); //Set action command of text field
        username.addActionListener(this.actionListener); //Add action listener to text field
        panelLogin.add(username); //Add text field to panel

        exit.addActionListener(this.actionListener); //Add action listener to button
        panelLogin.add(exit); //Add button to panel
    }

    private void CreateAIChoice() {
        JLabel askAI = new JLabel("Do you want to play as an AI?"); //Create label
        JButton yes = new JButton("Yes"); //Create button
        JButton no = new JButton("No"); //Create button

        yes.setActionCommand("AIChoice"); //Set action command of button
        yes.addActionListener(this.actionListener); //Add action listener to button
        panelAIChoice.add(yes); //Add button to panel

        no.setActionCommand("AIChoice"); //Set action command of button
        no.addActionListener(this.actionListener); //Add action listener to button
        panelAIChoice.add(no); //Add button to panel

        panelAIChoice.add(askAI); //Add label to panel
    }

    private void CreateGameChoice() {
        JButton ticTacToe = new JButton("Tic-Tac-Toe"); //Create button
        JButton reversi = new JButton("Reversi"); //Create button
        JButton challenge = new JButton("Challenge"); //Create button
        JButton exit = new JButton("Exit"); //Create button

        ticTacToe.addActionListener(this.actionListener); //Add action listener to button
        panelGameChoice.add(ticTacToe); //Add button to panel

        reversi.addActionListener(this.actionListener); //Add action listener to button
        panelGameChoice.add(reversi); //Add button to panel

        challenge.addActionListener(this.actionListener); //Add action listener to button
        panelGameChoice.add(challenge); //Add button to panel

        panelGameChoice.add(exit); //Add button to panel
        exit.addActionListener(this.actionListener); //Add action listener to button
    }

    public void ChallengeRender(String players) {
        panelChallenge.removeAll(); //Remove all components from panel
        frame.setTitle("Challenge"); //Set title of frame
        String[] playerList = players.split(", "); //Split players string into array of players
        for (String player : playerList) { //Loop through players
            if (!player.equals(username.getText())) { //If player is not the current player
                JButton button = new JButton("Challenge " + player); //Create button
                button.setActionCommand("ChallengeSend"); //Set action command of button
                button.addActionListener(this.actionListener); //Add action listener to button
                panelChallenge.add(button); //Add button to panel
            }
        }

        JButton button = new JButton("Back"); //Create button
        button.setActionCommand("ChallengeBack"); //Set action command of button
        button.addActionListener(this.actionListener); //Add action listener to button
        panelChallenge.add(button); //Add button to panel
    }

    public void UpdateFrame(JPanel panel) {
        frame.setContentPane(panel); //Set content pane of frame to panel
        frame.revalidate(); //Revalidate frame
        frame.repaint(); //Repaint frame
    }

    public void GameOverRender(String result, String opponent) {
        // TODO: GameType meenemen
        JPopupMenu popup = new JPopupMenu(); //Create popup menu

        JLabel label = new JLabel(result); //Create label
        popup.add(label); //Add label to popup menu

        JMenuItem menuItem = new JMenuItem("challenge " + opponent); //Create menu item
        menuItem.setActionCommand("ChallengeSend"); //Set action command of menu item
        menuItem.addActionListener(this.actionListener); //Add action listener to menu item
        popup.add(menuItem); //Add menu item to popup menu

        JMenuItem menuItem2 = new JMenuItem("Subscribe to this gametype again"); //Create menu item
        menuItem2.setActionCommand("Reversi"); //Set action command of menu item
        menuItem2.addActionListener(this.actionListener); //Add action listener to menu item
        popup.add(menuItem2); //Add menu item to popup menu

        JMenuItem menuItem3 = new JMenuItem("Quit"); //Create menu item
        menuItem3.addActionListener(this.actionListener); //Add action listener to menu item
        popup.add(menuItem3); //Add menu item to popup menu

        popup.show(frame, frame.getWidth() / 2, frame.getHeight() / 2); //Show popup menu
    }
}
