package Game;

import Othello.OthelloFactory;
import TicTacToe.TicTacToeFactory;

import java.util.Scanner;

public class GameLauncher { // This class is the entry point for the program
    public static void main(String[] args) {
        GameFactory[] gameFactories = {new OthelloFactory(), new TicTacToeFactory()};

        Scanner scanner = new Scanner(System.in);

        int choice = -1;

        while (choice < 0 || choice >= gameFactories.length) {
            System.out.println("Please choose a game:");
            for (int i = 0; i < gameFactories.length; i++) {
                System.out.println((i + 1) + ") " + gameFactories[i].createGame().getGameName());
            }
            choice = scanner.nextInt() - 1;
        }

        GameRunner gameRunner = new GameRunner();
        gameRunner.selectGame(gameFactories[choice]);
        gameRunner.initializeGame();
        gameRunner.playGame();
        gameRunner.endGame();
    }
}
