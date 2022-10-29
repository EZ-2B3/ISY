/**
 * Class to create the board for the game (CLI)
 *
 * @author Reinder Noordmans
 * @version 1.0
*/
public class Board { // class to create the board for the game (CLI)

    final public String[][] GameBoard; // create 2D array to store the game board
    public boolean turn = false;
    public int moves = 0;
        public Board(int rows, int cols) { // constructor to create the game board
                GameBoard = CreateBoard(rows, cols); // create the game board
        }

        public static String[][] CreateBoard(int rows, int columns) { // method to create the game board
            String[][] board = new String[rows][columns]; // create 2D array to store the game board
            for (int i = 0; i < rows; i++) { // for each row
                for (int j = 0; j < columns; j++) { // for each column
                    board[i][j] = " "; // set the value of the cell to a space
                }
            }
            return board; // return the game board
        }

}

