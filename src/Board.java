/**
 * Class to create the board for the game (CLI)
 *
 * @author Reinder Noordmans
 * @version 1.0
 */
public class Board { //
    public String[][] createBoard() { // method to create board
        String[][] board = new String[3][3]; // initialize board
        for (int i = 0; i < 3; i++) { // loop through rows
            for (int j = 0; j < 3; j++) { // loop through columns
                board[i][j] = Integer.toString(i * 3 + j); // set value of board at position [i][j] to i * 3 + j
            }
        }
        return board; // return board
    }

    public void printBoard(String[][] board) { // method to print board
        for (String[] strings : board) { // loop through rows
            System.out.println("-------------"); // print line
            for (String string : strings) { // loop through columns
                System.out.print("| " + string + " "); // print value of board at position [i][j]
            }
            System.out.println("|"); // print line
        }
        System.out.println("-------------"); // print line
    }

    public void changeBoard(String[][] board, int position, String player) { // method to change board
        int row = position / 3; // calculate row
        int column = position % 3; // calculate column
        board[row][column] = player; // set value of board at position [row][column] to player
    }
}
