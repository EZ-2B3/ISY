import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game implements ActionListener {
    protected Render render = null;
    protected AI ai = null;
    protected boolean IsMyTurn = false;
    protected String player = "";
    protected String opponent = "";
    protected Board board = null;
    protected Connection connection = null;
    protected boolean useAI = false;


    public Game(){
        this.render = new Render(this);
        this.connection = new Connection("game.bier.dev",7789);
        this.update();
    }

    public void update(){
        while(true){

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
            String[] event = e.getActionCommand().split(" ");

            switch (event[0]) {
                case "Login" ->{
                    OnLogin(event[1]);
                }
                case "Exit" ->{
                    OnExit();
                }

                case "AIChoice" ->{
                    OnAIChoice(event[1]);
                }

                case "Challenge" ->{
                    OnChallenge();
                }

                case "ChallengeSend" ->{
                    OnChallengeSend(event[1]);
                }

                case "ChallengeReceived"->{
                    OnChallengeReceive(event[1],event[2],event[3]);
                }

                case "move" ->{
                    OnMove(event[1]);
                }
                case "Tic-Tac-Toe" ->{

                    OnSubscribe("TicTacToe");
                }

                case "Reversi" ->{
                    OnSubscribe("Reversi");
                }

                case "Gameover"->{
                    OnGameOver(event[1]);
                }

                case "Quit" ->{
                    OnQuit();
                }

                case "ChallengeBack" ->{
                    //TODO Met Raoul kijken naar praktische functie waardoor game niet met UI bemoeit
                }
            }
    }

    protected void OnLogin(String username){
        
    }

    protected void OnSubscribe(String gameType){

    }

    protected void OnChallengeSend(String buttonText){

    }

    protected void OnChallengeReceive(String challenger,String challengeNumber,String gameType){

    }

    protected void OnChallenge(){

    }
    protected void OnMove (String move){

    }

    protected void OnGameOver(String result){

    }

    protected void OnExit(){

    }

    protected void OnQuit(){

    }

    protected void OnAIChoice(String choice){

    }





}
