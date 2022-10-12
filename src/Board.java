public class Board {
    public String[][] createBoard() {
        String[][] board = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = Integer.toString(i * 3 + j);
            }
        }
        return board;
    }

    public void printBoard(String[][] board) {
        for (String[] strings : board) {
            System.out.println("-------------");
            for (String string : strings) {
                System.out.print("| " + string + " ");
            }
            System.out.println("|");
        }
        System.out.println("-------------");
    }

    public void changeBoard(String[][] board, int position, String player) {
        int row = position / 3;
        int column = position % 3;
        board[row][column] = player;
    }

}
