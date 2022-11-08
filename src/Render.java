import javax.swing.*;
import java.awt.event.ActionListener;

public class Render {
    public JFrame frame = new JFrame();
    public JPanel panelLogin = new JPanel();
    public JPanel panelGameChoice = new JPanel();
    public JPanel panelBoard = new JPanel();

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
        CreateGameChoice();
        frame.setTitle("Login"); // set title of frame
        UpdateFrame(panelLogin);
    }

    private void BoardRender(String[][] board, boolean turn) {
        //TODO
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

    public void UpdateFrame(JPanel panel) {
        frame.setContentPane(panel);
        frame.revalidate();
        frame.repaint();
    }

//    public String player;
//    public Board board;
//    final private JFrame frame = new JFrame("Game"); // create frame to render the game
//    final private JPanel loginPanel = new JPanel(); // create panel to render the login screen
//    final private JLabel askName = new JLabel("Enter your name:"); // create label to ask user for name
//    final private JTextField username = new JTextField(20); // create text field to enter name
//    final private JButton sendUsername = new JButton("Send"); // create button to send name to server
//    final private JPanel gameOptions = new JPanel(); // create panel to render the game options
//    final private JButton ticTacToe = new JButton("Tic-Tac-Toe"); // create button to start Tic-Tac-Toe game
//    final private JButton challenge = new JButton("Challenge"); // create button to challenge another player
//    final private JButton exit = new JButton("Exit"); // create button to exit the game
//    final private JPanel bord = new JPanel(); // create panel to render the game board
//    public Render() { // constructor to render the game
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // set default close operation to exit the game
//        frame.setSize(500, 500); // set size of frame
//        frame.setLocationRelativeTo(null); // set location of frame to center of screen
//        try {
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // set look and feel of frame to look and feel of operating system
//        } catch (Exception e) {
//            System.out.println("Error setting native LAF: " + e); // print error message to user
//        }
//        SwingUtilities.updateComponentTreeUI(frame); // update components of frame
//
//        login(); // render login screen
//
//        frame.setVisible(true); // set frame visible
//    }
//
//    private void gameOptions() { // method to render game options
//        frame.setTitle("Game options"); // set title of frame to game options
//        ticTacToe.addActionListener(this); // add action listener to ticTacToe button
//        gameOptions.add(ticTacToe); // add ticTacToe button to gameOptions panel
//
//        challenge.addActionListener(this); // add action listener to challenge button
//        gameOptions.add(challenge); // add challenge button to gameOptions panel
//
//        gameOptions.add(exit);
//    }
//
//    private void login() { // method to render login screen
//        frame.setTitle("Login");
//        frame.add(loginPanel); // add loginPanel to frame
//
//        loginPanel.add(askName); // add askName label to loginPanel panel
//
//        username.setActionCommand("Send"); // set action command of username text field to Send
//        username.addActionListener(this); // add action listener to username text field
//        loginPanel.add(username); // add username text field to loginPanel panel
//
//        sendUsername.addActionListener(this);   // add action listener to sendUsername button
//        loginPanel.add(sendUsername);   // add sendUsername button to loginPanel panel
//
//        exit.addActionListener(this);   // add action listener to exit button
//        loginPanel.add(exit);   // add exit button to loginPanel panel
//
//    }
//
//    private void boardRender(String[][] board, boolean turn) { // method to render the game board
//        int rows = board.length; // get number of rows
//        int cols = board[0].length;  // get number of columns
//        bord.removeAll(); // remove all components from bord panel
//        // add opponent name to frame title
//        frame.setTitle("Tic-Tac-Toe - " + Game.opponent);
////        bord.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30)); // set border of bord panel
//        bord.setLayout(new GridLayout(rows, cols)); // set layout of bord panel
//        int n = 0;
//        for (String[] strings : board) {    // for each row
//            for (int j = 0; j < cols; j++) {    // for each column
//                JButton button = new JButton(strings[j]);  // create button to render the cell
//                button.setActionCommand("MOVE " + n);// set action command of button to row,column
//                n++;
//                if (strings[j].equals(" ")) {   // if the cell is empty
//                    if (turn) { // if it is the player's turn
//                        button.setEnabled(true);    // enable the button
//                        button.addActionListener(this);  // add action listener to button
//                    } else {
//                        button.setEnabled(false);   // disable button
//                    }
//                } else {  // if the cell is not empty
//                    button.setEnabled(false);   // disable button
//                }
//                bord.add(button);   // add button to bord panel
//            }
//        }
//    }
//
//    /**
//     * Invoked when an action occurs.
//     *
//     * @param e the event to be processed
//     */
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        String command = e.getActionCommand();
//        if (command.equals("Send")) {
//            loginHandler();
//        } else if (command.equals("Tic-Tac-Toe")) {
//            TicTacToe();
//        } else if (command.equals("Challenge")) {
//            Challenge();
//        } else if (command.equals("Exit")) {
//            Exit();
//        } else if (command.contains("MOVE")) {
//            Connection.out.println(command);
//            board.turn = false;
//        }
//    }
//    public void gameOver(String message) {
//        JOptionPane.showMessageDialog(frame, message);
//        frame.remove(bord);
//        gameOptions();
//        frame.add(gameOptions);
//        frame.revalidate();
//        frame.repaint();
//    }
//
//    public void repaintBoard() {
//        boardRender(board.GameBoard, board.turn);
//        frame.add(bord);
//        frame.revalidate();
//        frame.repaint();
//    }
//
//    private void Exit() {
//        try {
//            Connection.out.println("exit");
//            frame.setVisible(false);
//            frame.dispose();
//
//            System.exit(0);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void Challenge() {
//        System.out.println("To be implemented");
//    }
//
//    private void TicTacToe() {
//        Connection.out.println("subscribe tic-tac-toe");
//        board = new Board(3,3);
//        boardRender(board.GameBoard, board.turn);
//        frame.remove(gameOptions);
//        frame.add(bord);
//        frame.revalidate();
//        frame.repaint();
//    }
//
//    private void loginHandler() {
//        Connection.out.println("login " + username.getText());
//        try {
//            String message = Connection.in.readLine();
//            if (message.equals("OK")) {
//                player = username.getText();
//                frame.remove(loginPanel);
//                gameOptions();
//                frame.add(gameOptions);
//                frame.repaint();
//                frame.revalidate();
//            } else {
//                JOptionPane.showMessageDialog(frame, message);
//            }
//        } catch (IOException ex) {
//            throw new RuntimeException(ex);
//        }
//    }
}
