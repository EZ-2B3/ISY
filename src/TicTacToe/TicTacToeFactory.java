package TicTacToe;
import Game.*;

public class TicTacToeFactory implements GameFactory {
    public Game createGame() {
        return new TicTacToeGame();
    }

    public Player[] createPlayers() {
        Player[] players = new Player[2];
        players[0] = new TicTacToePlayer("Player 1", 'X');
        players[1] = new TicTacToePlayer("Player 2", 'O');
        return players;
    }

    public Board createGameBoard() {
        return new TicTacToeBoard();
    }
}

