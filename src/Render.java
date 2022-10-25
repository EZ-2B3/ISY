import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Render implements ActionListener { // class to render the game
    JFrame frame = new JFrame("Game"); // create frame to render the game
    JPanel loginPanel = new JPanel(); // create panel to render the login screen
    JLabel askName = new JLabel("Enter your name:"); // create label to ask user for name
    JTextField username = new JTextField(20); // create text field to enter name
    JButton sendUsername = new JButton("Send"); // create button to send name to server
    JPanel gameOptions = new JPanel(); // create panel to render the game options
    JButton ticTacToe = new JButton("Tic-Tac-Toe"); // create button to start Tic-Tac-Toe game
    JButton challenge = new JButton("Challenge"); // create button to challenge another player
    JButton exit = new JButton("Exit"); // create button to exit the game
    JPanel bord = new JPanel(); // create panel to render the game board
    public Render() { // constructor to render the game
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // set default close operation to exit the game
        frame.setSize(300, 300); // set size of frame
        frame.setLocationRelativeTo(null); // set location of frame to center of screen

        login(); // render login screen

        gameOptions(); // render game options

        frame.setVisible(true); // set frame visible
    }

    private void gameOptions() { // method to render game options
        ticTacToe.addActionListener(this); // add action listener to ticTacToe button
        gameOptions.add(ticTacToe); // add ticTacToe button to gameOptions panel

        challenge.addActionListener(this); // add action listener to challenge button
        gameOptions.add(challenge); // add challenge button to gameOptions panel
    }

    private void login() { // method to render login screen
        frame.add(loginPanel); // add loginPanel to frame

        loginPanel.add(askName); // add askName label to loginPanel panel

        username.setActionCommand("Send"); // set action command of username text field to Send
        username.addActionListener(this); // add action listener to username text field
        loginPanel.add(username); // add username text field to loginPanel panel

        sendUsername.addActionListener(this);   // add action listener to sendUsername button
        loginPanel.add(sendUsername);   // add sendUsername button to loginPanel panel

        exit.addActionListener(this);   // add action listener to exit button
        loginPanel.add(exit);   // add exit button to loginPanel panel

    }

    private void boardRender(String[][] board) {
        int rows = board.length; // get number of rows
        int cols = board[0].length;  // get number of columns
        bord.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30)); // set border of bord panel
        bord.setLayout(new GridLayout(rows, cols)); // set layout of bord panel
        int n = 0;
        for (int i = 0; i < rows; i++) {    // for each row
            for (int j = 0; j < cols; j++) {    // for each column
                JButton button = new JButton(board[i][j]);  // create button to render the cell
                button.setActionCommand(String.valueOf(n));// set action command of button to row,column
                n++;
                button.addActionListener(this); // add action listener to button
                bord.add(button);   // add button to bord panel
            }
        }
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("Send")) {
            loginHandler();
        } else if (command.equals("Tic-Tac-Toe")) {
            TicTacToe();
        } else if (command.equals("Challenge")) {
            Challenge();
        } else if (command.equals("Exit")) {
            Exit();
        } else
            System.out.println(command);
    }

    private void Exit() {
        try {
            Connection.out.println("exit");
            frame.setVisible(false);
            frame.dispose();

            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void Challenge() {
        System.out.println("To be implemented");
        // TODO: Challenge GUI and logic
    }

    private void TicTacToe() {
        Connection.out.println("subscribe tic-tac-toe");
        Board board = new Board(3, 3);
        boardRender(board.GameBoard);
        frame.remove(gameOptions);
        frame.add(bord);
        frame.setVisible(true);
    }

    private void loginHandler() {
        Connection.out.println("login " + username.getText());
        try {
            String message = Connection.in.readLine();
            if (message.equals("OK")) {
                frame.remove(loginPanel);
                loginPanel.remove(exit);
                gameOptions.add(exit);
                frame.add(gameOptions);
                frame.repaint();
                frame.revalidate();
            } else {
                JOptionPane.showMessageDialog(frame, message);
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
