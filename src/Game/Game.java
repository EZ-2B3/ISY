package Game;
import Core.Move;

import java.util.List;

public interface Game {
    void startGame();
    void endGame();
    boolean isGameOver();
    void updateGameState(Player player, int row, int col);
    void printGameRules();

    // Geeft de naam van het spel terug
    String getGameName();

    // Geeft het huidige speelbord van het spel terug
    Board getGameBoard();

    // Geeft de huidige speler terug die aan de beurt is
    Player getCurrentPlayer();

    // Geeft een lijst met mogelijke zetten terug voor de huidige speler
    List<Move> getPossibleMoves(Player player);

    // Geeft de speler terug die het spel heeft gewonnen, of null als er geen winnaar is
    Player getWinner();
}
