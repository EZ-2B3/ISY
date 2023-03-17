package Game;
import Game.Board;

public interface Player {
    String getPlayerName();
    char getPlayerSymbol();
    int[] getPlayerMove(Board gameBoard);
}
