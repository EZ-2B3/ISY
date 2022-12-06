import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main implements ActionListener {

    public void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // set look and feel of frame to look and feel of operating system
        } catch (Exception e) {
            System.out.println("Error setting native LAF: " + e); // print error message to user
        }
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // set default close operation to exit the game
        frame.setSize(500, 500); // set size of frame
        frame.setLocationRelativeTo(null); // set location of frame to center of screen
        frame.add(new PanelLogin(null));
        frame.setVisible(true); // set frame visible
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
