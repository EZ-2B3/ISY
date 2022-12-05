import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Render {
    public JFrame frame = new JFrame();
    private ActionListener actionListener;
    private JPanel currentView;

    public Render(ActionListener actionListener) {
        this.actionListener = actionListener;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setVisible(true);
        frame.setLayout(new BorderLayout());
        frame.add(new JButton("test"), BorderLayout.NORTH);
    }

    public void UpdateFrame(JPanel panel) {
        currentView = panel;
        frame.revalidate();
        frame.repaint();
    }

    public void DrawGameOver(String message) {
//        TODO:
    }
}
