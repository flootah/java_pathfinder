import java.util.Arrays;

public class Board {
	public int[][] board;		// board to analyze
	public int[][] augBoard;	// augmented board accumulating values
	public int[] route;		// array of items in board with desired route
	public int maximum;		// max value
		

	public Board(int[][] matrix) {
		board = matrix;
		augBoard = makeAugBoard();
		maximum = getMax(augBoard);
		route = getRoute();
	}
	
	private int getMax(int[][] arr) {
		return arr[arr.length - 1][arr[arr.length - 1].length - 1];
	}

	private int[] getRoute() {
		
		int i = augBoard.length - 1;
		int j = augBoard[0].length - 1;
		int[][] routeCords = new int[augBoard.length + augBoard[0].length][]; 
		int cordIndex = 0;

		
		while(!(i == 0 && j == 0)) {
			routeCords[cordIndex] = new int[] {i,j};
			if(i == 0) {
				j--;
			} else
			if(j == 0) {
				i--;
			} else {	
				int max = max3(augBoard[i-1][j],augBoard[i][j-1],augBoard[i-1][j-1]);
				if (max == augBoard[i-1][j]) {
					i--;
				} else 
				if (max == augBoard[i][j-1]) {
					j--;
				} else
				if (max == augBoard[i-1][j-1]) {
					i--;
					j--;
				}	
			}
			cordIndex++;
		}
		
		cordIndex++;
		routeCords[cordIndex] = new int[] {0,0};
		
		int[] temproute = new int[cordIndex];
		
		int l = 0;
		for(int k = routeCords.length - 1; k >=0; k--) {
			if(routeCords[k] == null) continue;
			
			temproute[l] = board[routeCords[k][0]][routeCords[k][1]];
			l++;
		}
		return temproute;
	}

	private int[][] makeAugBoard() {
		augBoard = arrayDeepCopy(board);
		// solve first row
		for(int j = 1; j < augBoard[0].length; j++) {
			augBoard[0][j] = augBoard[0][j] + augBoard[0][j-1];
		}
		// solve first column
		for(int i = 1; i < augBoard.length; i++) {
			augBoard[i][0] = augBoard[i][0] + augBoard[i-1][0];
		}
		
		for(int i = 1; i < augBoard.length; i++) {
			for(int j = 1; j < augBoard[i].length; j++) {
				augBoard[i][j] = augBoard[i][j] + max3(augBoard[i-1][j],augBoard[i][j-1],augBoard[i-1][j-1]);
			}
		}
		return augBoard;
	}
	
	private int[][] arrayDeepCopy(int[][] arr) {
		int[][] result = new int[arr.length][];
		for(int i = 0; i < result.length; i++) {
			result[i] = Arrays.copyOf(arr[i], arr[i].length);
		}
		return result;
	}
	
	private int max3(int a, int b, int c) {
		return Math.max(Math.max(a, b), c);
	}
}
