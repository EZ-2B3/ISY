import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Render {
    public JFrame frame = new JFrame();
    private ActionListener actionListener;
    private JPanel currentView;

    public Render(ActionListener actionListener) {
        this.actionListener = actionListener;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null); // set location of frame to center of screen
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // set look and feel of frame to look and feel of operating system
        } catch (Exception e) {
            System.out.println("Error setting native LAF: " + e); // print error message to user
        }
        SwingUtilities.updateComponentTreeUI(frame); // update components of frame
        frame.setVisible(true);
        frame.setLayout(new BorderLayout());
    }

    public void UpdateFrame(JPanel panel) {
        currentView = panel;
        frame.getContentPane().removeAll();
        frame.getContentPane().add(panel);
        frame.revalidate();
        frame.repaint();
    }

    public void DrawGameOver(String message) {
        JPanel panelGameOver = new JPanel();
        JLabel gameOverLabel = new JLabel(message);
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(this.actionListener);
        panelGameOver.add(gameOverLabel);
        panelGameOver.add(exitButton);
        UpdateFrame(panelGameOver);
    }

    public void Error(String message) {
        JOptionPane.showMessageDialog(frame, message);
    }
}
