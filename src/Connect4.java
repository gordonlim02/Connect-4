import java.util.Arrays;
import java.util.Scanner;

public class Connect4 {
	public static void main(String[] args) {

		Scanner s = new Scanner(System.in);
		char[][] board = new char[6][7];		
		fillBoard(board);
		char currentPlayer = 'O';
		
		while (true) {
			System.out.println();
			printBoard(board);
			System.out.println();
			int colToDrop;
			boolean fullBoard = false;
			
			while (true) {
				System.out.print("Player " + currentPlayer + ", please enter the column where you want to drop your piece (1-7): ");
				if (s.hasNextInt()) { // check whether next token is int
					colToDrop = s.nextInt();
                    s.nextLine();
                    if (colToDrop >= 1 && colToDrop <= 7) { // check whether token is between 1 and 7
                        if (!dropPiece(board, colToDrop-1, currentPlayer)) {
							System.out.println("That column is full. Please drop your piece in other columns.");
							continue;
						}
						break;
					} else { // token is int but not between 1 and 7
                        System.out.println("That was not a valid number! Please try again.");
                    }
				} else { // token is not int
					s.nextLine();
					System.out.println("That was not a valid number! Please try again.");
                }
			}
			
			// Current player wins
			if (win(board)) {
				System.out.println();
				printBoard(board);
				System.out.println();
				System.out.println("Player " + currentPlayer + ", you win!");
				break;
			}
			
			// Check whether it's a draw
			int pieces = 0;
            for (char[] chars : board) {
                for (char aChar : chars) {
                    if (aChar != ' ') pieces++;
                    if (pieces == 42) fullBoard = true;
                }
            }
			if (fullBoard) {
				System.out.println();
				printBoard(board);
				System.out.println();
				System.out.println("It's a draw!");
				break;
			}
			
			// Switch players
			if (currentPlayer == 'O') currentPlayer = 'X';
			else currentPlayer = 'O';
		}
		s.close();
	}
	
	public static void printBoard(char[][] board) {
		System.out.println("-----------------------------");
        for (char[] chars : board) {
            System.out.println(toString(chars));
            System.out.println("-----------------------------");
        }
		System.out.println("  1   2   3   4   5   6   7");
	}
	
	public static void fillBoard(char[][] board) {
        for (char[] chars : board) {
            Arrays.fill(chars, ' ');
        }
	}
    
    public static boolean dropPiece (char[][] array, int col, char player) { 
        
        // If the top spot is already taken, then we know that the column is already full
        if (array[0][col] != ' ' ) {
            return false;
        }
       
        // Find the lowest empty spot in the column and drop the piece
        for (int i = 5; i >= 0 ; i--) {
            if (array[i][col] == ' ' ) {
                array[i][col] = player;
                return true;
            }
        }     
        return true;
    }
	
    public static boolean win(char[][] board) {
    	
    	// Check horizontal
        for (char[] chars : board) {
            int sameColor = 1;
            for (int col = 1; col < chars.length; col++) {
                if (chars[col] == chars[col - 1] && chars[col] != ' ') {
                    sameColor++;
                } else {
                    sameColor = 1;
                }
                if (sameColor >= 4) return true;
            }
        }
    	
    	// Check vertical
    	for (int col = 0; col < board.length; col++) {
    		int sameColor = 1;
    		for (int row = 1; row < board.length; row++) {
    			if (board[row][col] == board[row-1][col] && board[row][col] != ' ') {
    				sameColor++;
    			} else {
    				sameColor = 1;
    			}
    			if (sameColor >= 4) return true;
    		}   		
    	}
    	
    	// Check diagonal ( / ) 
    	for (int row = 3; row <= 5; row++) {
    		int currentRow = row;
    		int col = 0;
    		int sameColor = 1;
    		while ((currentRow-1 >= 0) && (col+1 <= 6)) {
    			if (board[currentRow][col] == board[currentRow-1][col+1] && board[currentRow][col] != ' ') {
    				sameColor++;
    			} else {
    				sameColor = 1;
    			}
    			currentRow--;
    			col++;
    			if (sameColor >= 4) return true;
    		}
    	}
    	for (int col = 1; col <= 3; col++) {
    		int currentCol = col;
    		int row = 5;
    		int sameColor = 1;
    		while ((row-1 >= 0) && (currentCol+1 <= 6)) {
    			if (board[row][currentCol] == board[row-1][currentCol+1] && board[row][currentCol] != ' ') {
    				sameColor++;
    			} else {
    				sameColor = 1;
    			}
    			row--;
    			currentCol++;
    			if (sameColor >= 4) return true;
    		}
    	}
    	
    	// Check diagonal ( \ )
    	for (int row = 0; row <= 2; row++) {
    		int currentRow = row;
    		int col = 0;
    		int sameColor = 1;
    		while ((currentRow+1 <= 5) && (col+1 <= 6)) {
    			if (board[currentRow][col] == board[currentRow+1][col+1] && board[currentRow][col] != ' ') {
    				sameColor++;
    			} else {
    				sameColor = 1;
    			}
    			currentRow++;
    			col++;
    			if (sameColor >= 4) return true;
    		}
    	}
    	for (int col = 1; col <= 3; col++) {
    		int currentCol = col;
    		int row = 0;
    		int sameColor = 1;
    		while ((row+1 <= 5) && (currentCol+1 <= 6)) {
    			if (board[row][currentCol] == board[row+1][currentCol+1] && board[row][currentCol] != ' ') {
    				sameColor++;
    			} else {
    				sameColor = 1;
    			}
    			row++;
    			currentCol++;
    			if (sameColor >= 4) return true;
    		}
    	}
    	
    	// None of the above win conditions are met, current player does not win
    	return false;
    }
    
    public static String toString(char[] a) {
        if (a == null)
            return "null";
        int iMax = a.length - 1;
        if (iMax == -1)
            return "[]";

        StringBuilder b = new StringBuilder();
        b.append("| ");
        for (int i = 0; ; i++) {
            b.append(a[i]);
            if (i == iMax)
                return b.append(" |").toString();
            b.append(" | ");
        }
    }
}
