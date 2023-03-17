package Game;

public class GameRunner {
    private Game game;
    private Player[] players;
    private Board gameBoard;

    public void selectGame(GameFactory gameFactory) {
        game = gameFactory.createGame();
        players = gameFactory.createPlayers();
        gameBoard = gameFactory.createGameBoard();
    }

    public void initializeGame() {
        gameBoard.initializeBoard();
    }

    public void playGame() {
        int currentPlayerIndex = 0;

        while (!game.isGameOver()) {
            Player currentPlayer = players[currentPlayerIndex];

            gameBoard.printBoard();
            System.out.println("It's " + currentPlayer.getPlayerName() + "'s turn.");

            int[] move = currentPlayer.getPlayerMove(gameBoard);

            game.updateGameState(currentPlayer, move[0], move[1]);

            currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
        }
    }

    public void endGame() {
        System.out.println("Game over!");
        gameBoard.printBoard();
        System.out.println("Final scores:");
        for (Player player : players) {
            System.out.println(player.getPlayerName() + ": " + game.getScore(player));
        }
    }
}

