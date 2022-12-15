import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Dictionary;

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
        render.UpdateFrame(new PanelLogin(this));
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
        this.player = username;

        connection.sendMessage("login ".concat(username));
        Dictionary response = connection.receiveMessage();
        if (response.get("Status").equals("OK")){
            render.UpdateFrame(new PanelGameChoice(this));
        }
    }

    protected void OnSubscribe(String gameType){
        connection.sendMessage("subscribe ".concat(gameType));

        if(gameType.equals("Tic-Tac-Toe")){
            this.board = new Board(3,3);
        }else{
            this.board = new Board(8,8);
        }
    }

    protected void OnChallengeSend(String message){
        connection.sendMessage("Challenge ".concat(message));

    }

    protected void OnChallengeReceive(String challenger,String challengeNumber,String gameType){

    }

    protected void OnChallenge(){
        connection.sendMessage("get playerlist");
        //TODO get playerlist and send give it to lobbypane no need to save it in game

    }
    protected void OnMove (String move){
        connection.sendMessage("Move ".concat(move));
    }

    protected void OnGameOver(String result){
        render.DrawGameOver(result);
    }

    protected void OnExit(){
        connection.sendMessage("exit");
        System.exit(0);
    }

    protected void OnQuit(){
        //TODO Wat is on quit?
    }

    protected void OnAIChoice(String choice){
        useAI = choice.equals("yes");
    }





}
