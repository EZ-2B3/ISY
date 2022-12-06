import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

public class PanelLogin extends JPanel {

    private final ActionListener actionListener;
    public PanelLogin(ActionListener actionListener) {

        //initialize actionListener
        this.actionListener = actionListener;

        //Create the panel
        JPanel panelLogin = new JPanel();

        // Create the username label and field
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField(20);

        // Create the login and cancel buttons
        JButton loginButton = new JButton("Login");
        JButton quitButton = new JButton("Quit");

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


