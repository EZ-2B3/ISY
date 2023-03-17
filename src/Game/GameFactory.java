package Game;

public interface GameFactory {
    Game createGame();
    Player[] createPlayers();
    Board createGameBoard();
}

