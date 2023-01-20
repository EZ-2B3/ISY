public class Main {
    private static final Game game = new Game();

    public static void main(String[] args) {
        if (args.length == 0) {
            game.Update();
        } else {
            int games = Integer.parseInt(args[0]);
            String player1 = args[1];
            Boolean receiving = Boolean.parseBoolean(args[2]);
            String player2 = args[3];
            System.out.println("Playing " + games + " games with " + player1 + " receiving: " + receiving);
            game.StartGame(player1, receiving, player2, games);
            game.Update();
        }
    }
}
