public interface AI {
    Move GetBestMove(String[][] board);

    class Move {
        public int x;
        public int y;

        public Move(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
