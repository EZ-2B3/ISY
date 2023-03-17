package TicTacToe;
import Game.*;

public interface TicTacToePlayer extends Player {
    char getSymbol();
    int[] getNextMove(TicTacToeBoard board);
}

