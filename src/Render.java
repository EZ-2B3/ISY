import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.sound.sampled.*;
import java.io.*;


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
        // create menu bar
        JMenuBar menuBar = new JMenuBar();

        // create file menu
        JMenu Menu = new JMenu("Menu");
        menuBar.add(Menu);

        // create edit frame size option
        JMenuItem editSizeItem = new JMenuItem("Edit Frame Size");

        // create settings menu
        JMenu Settings = new JMenu("Settings");
        menuBar.add(Settings);

        // create screen size submenu
        JMenu ScreenSize = new JMenu("Screen Size");
        Settings.add(ScreenSize);

        // create 500x500 option
        JMenuItem size500Item = new JMenuItem("500x500");
        size500Item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setSize(500, 500);
            }
        });
        ScreenSize.add(size500Item);

        // create 800x600 option
        JMenuItem size800Item = new JMenuItem("800x600");
        size800Item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setSize(800, 600);
            }
        });
        ScreenSize.add(size800Item);

        // create 1024x768 option
        JMenuItem size1024Item = new JMenuItem("1024x768");
        size1024Item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setSize(1024, 768);
            }
        });
        ScreenSize.add(size1024Item);

        // create fullscreen option
        JMenuItem fullscreenItem = new JMenuItem("Fullscreen");
        fullscreenItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            }
        });
        ScreenSize.add(fullscreenItem);

        // create exit option
        JMenuItem exitItem = new JMenuItem("Exit Game");
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }

        });
        Menu.add(exitItem);

        JMenu songMenu = new JMenu("Select music");
        songMenu.addActionListener(new ActionListener() {

        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }

    });
        Menu.add(songMenu);

        JMenuItem song1 = new JMenuItem("Song 1");
        song1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Song 1 selected");
                try {
                    // Load audio file
                    AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("song1.wav"));
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioIn);

                    // Start playing audio
                    clip.start();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        songMenu.add(song1);

        JMenuItem song2 = new JMenuItem("Song 2");
        song2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Song 2 selected");
                try {
                    // Load audio file
                    AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("src/Sounds/brute_force.wav"));
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioIn);

                    // Start playing audio
                    clip.start();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        });
        songMenu.add(song2);

        JMenuItem song3 = new JMenuItem("Song 3");
        song3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Song 3 selected");
                try {


                    // Load audio file
                    AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("src/Sounds/nightdelivery5.wav"));
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioIn);

                    // Start playing audio
                    clip.start();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        });
        songMenu.add(song3);

        JMenuItem stopSong = new JMenuItem("Stop the music");
        stopSong.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Music stopped!");
                // Stop current playing clips


            }
        });
        songMenu.add(stopSong);



        // add menu bar to frame
        frame.setJMenuBar(menuBar);


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
        JLabel askAI = new JLabel("Do you want to play as an AI?");
        JButton yes = new JButton("Yes");
        JButton no = new JButton("No");
        JButton tournament = new JButton("Tournament");

        yes.setActionCommand("AIChoice"); //Set action command of button
        yes.addActionListener(this.actionListener); //Add action listener to button
        panelAIChoice.add(yes); //Add button to panel

        no.setActionCommand("AIChoice"); //Set action command of button
        no.addActionListener(this.actionListener); //Add action listener to button
        panelAIChoice.add(no); //Add button to panel

        panelAIChoice.add(askAI);

        tournament.setActionCommand("Tournament");
        tournament.addActionListener(this.actionListener);
        panelAIChoice.add(tournament);

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

    private void playMenuMusic() {
        try {
            // Load the music file
            File musicFile = new File("menu_music.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicFile);

            // Create a clip to play the music
            Clip musicClip = AudioSystem.getClip();
            musicClip.open(audioStream);

            // Start playing the music in a loop
            musicClip.loop(Clip.LOOP_CONTINUOUSLY);

            // Create a dialog box to allow the user to select the song to play
            String[] songNames = {"Song 1", "Song 2", "Song 3"};
            int songChoice = JOptionPane.showOptionDialog(frame, "Select a song to play", "Menu Music",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                    songNames, songNames[0]);

            // Stop the music clip
            musicClip.stop();

            // Play the selected song
            String songFilename = "song" + (songChoice + 1) + ".wav";
            File songFile = new File(songFilename);
            AudioInputStream songStream = AudioSystem.getAudioInputStream(songFile);
            Clip songClip = AudioSystem.getClip();
            songClip.open(songStream);
            songClip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
