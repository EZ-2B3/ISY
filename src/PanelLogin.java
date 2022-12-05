import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class PanelLogin extends JPanel {

    public PanelLogin() {
        // Create the username label and field
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField(20);

        // Create the login and cancel buttons
        JButton loginButton = new JButton("Login");
        JButton quitButton = new JButton("Quit");

        // Add an action listener to the login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the entered username
                String username = usernameField.getText();

                // Check if the username is correct
                if (username.equals("admin")) {
                    // Login successful, show a message and close the login screen
                    System.out.println("Login successful!");
                    // frame.dispose();
                } else {
                    // Login failed, show an error message
                    System.out.println("Invalid username");
                }
            }
        });

        // Add an action listener to the cancel button
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the login screen
                // frame.dispose();
            }
        });

        // Create a panel to hold the username field
        JPanel fieldPanel = new JPanel();
        fieldPanel.add(usernameLabel);
        fieldPanel.add(usernameField);

        // Create a panel to hold the login and cancel buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loginButton);
        buttonPanel.add(quitButton);

        // Add the field and button panels to the frame
        add(fieldPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        // Set the look and feel to the native look and feel of the operating system
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Do nothing
        }
        // Create the frame
        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null);

        // Create the login panel
        PanelLogin loginPanel = new PanelLogin();

        // Add the login panel to the frame
        frame.add(loginPanel);

        // Display the frame
        frame.pack();
        frame.setVisible(true);
    }
}


