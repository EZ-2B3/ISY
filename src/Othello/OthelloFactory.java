package Othello;

import Game.Game;
import  Game.Player;
import Game.GameFactory;
import Game.Board;

import java.awt.*;

public class OthelloFactory implements GameFactory {
    public Game createGame() {
        return new OthelloGame();
    }

    public Player[] createPlayers() {
        Player[] players = new Player[2];
        players[0] = new OthelloPlayer(Color.BLACK);
        players[1] = new OthelloPlayer(Color.WHITE);
        return players;
    }

    public Board createGameBoard() {
        return new OthelloBoard();
    }
}
