import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class PanelLogin extends JPanel {

    public JFrame frame = new JFrame();
    private final ActionListener actionListener;
    public PanelLogin(ActionListener actionListener) {

        //Set title of frame
        frame.setTitle("Login");

        //initialize actionListener
        this.actionListener = actionListener;

        //Create the panel
        JPanel panelLogin = new JPanel();

        // Create the username label and field
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField(16);

        // Create the login and cancel buttons
        JButton loginButton = new JButton("Login");
        JButton quitButton = new JButton("Quit");

        usernameField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                loginButton.setActionCommand("Login " + usernameField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                loginButton.setActionCommand("Login " + usernameField.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                loginButton.setActionCommand("Login " + usernameField.getText());
            }
        });

        //Adding the Buttons to the Panel
        panelLogin.add(usernameLabel);
        panelLogin.add(usernameField);
        panelLogin.add(loginButton);
        panelLogin.add(quitButton);

        //Creating actionlisteners for the buttons
        loginButton.addActionListener(this.actionListener);
        quitButton.addActionListener(this.actionListener);

        //Adding the panel to the frame
        add(panelLogin);
    }
}
