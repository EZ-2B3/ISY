import javax.swing.*;
import java.io.IOException;

public class PanelLobby extends JPanel {

    public JFrame frame = new JFrame();
    public PanelLobby() throws IOException {

        //Set title of frame
        frame.setTitle("Lobby");

        //Create the panel
        JPanel panelLobby = new JPanel();

        //Show players connected to the server
        JLabel playerLabel = new JLabel("Players connected to the server: ");
        JTextArea playerArea = new JTextArea(20, 20);
        playerArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(playerArea);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        panelLobby.add(playerLabel);
        panelLobby.add(scroll);

        //Show players in the lobby
        JLabel lobbyLabel = new JLabel("Players in the lobby: ");
        JTextArea lobbyArea = new JTextArea(20, 20);
        lobbyArea.setEditable(false);
        JScrollPane scroll2 = new JScrollPane(lobbyArea);
        scroll2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        panelLobby.add(lobbyLabel);
        panelLobby.add(scroll2);

        //Create the refresh button
        JButton refreshButton = new JButton("Refresh");
        panelLobby.add(refreshButton);

        //Create the start button
        JButton startButton = new JButton("Start");
        panelLobby.add(startButton);

        //Create the back button
        JButton backButton = new JButton("Back");
        panelLobby.add(backButton);

        //Adding the panel to the frame
        add(panelLobby);
    }
}
