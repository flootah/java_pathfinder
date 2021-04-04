import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import java.io.*;
import java.text.DecimalFormat;

public class UserInterface {
	private int state;			// current state of state machine
	UIDictionary dictionary;	// dictionary of UI prompts
	Scanner sc;					// i/o scanner
	DecimalFormat df;			// default decimal format
	Board board;
	boolean input_manual;		// 0: File, 1: Manual
	boolean input_random;		// 0: File, 1: Random
	int rows;					// number of rows
	int columns;				// number of columns
	int[][] matrix;				// the board to process
	
	/*
	 * constructor for the UserInterface object.
	 * sets default values and initializes class fields.
	 */
	public UserInterface() {
		state = 1;								// init state always 1
		dictionary = new UIDictionary();		// default dictionary
		sc = new Scanner(System.in);			// init input scanner
		df = new DecimalFormat("###");			// default DF is to 4 decimal places
		input_manual = false;					// default input method is by file entry
	}
	
	/*
	 * Begins execution of UI state machine.
	 */
	public void start() {
		String response = "";
		while(true) {
			switch(state) {
			/*
			 * State 0: Exit state
			 * exits the program after printing an exit dialogue.
			 * exit code returns as exiting normally.
			 */
			case 0:
				System.out.println(dictionary.exit);
				System.exit(0);
				break;
			/*
			 * State 1: Main Menu
			 * User will choose between two options:
			 * a: File Entry
			 * b: Manual Entry
			 * c: Random Entry
			 * Upon completion, goes to state 2.
			 * 
			 * !! this state may set/change the input_manual or random_manual global.
			 */
			case 1:
				// prompt user and store their response string
				response = prompt(dictionary.mainMenu);
				switch(response) {
				case "a":
					input_manual = false;
					state = 2; // goto row/col entry
					break;
				case "b":
					input_manual = true;
					state = 2; // goto row/col entry
					break;
				case "c":
					input_random = true;
					state = 2;
					break;
				}
				break;
				/*
				 * State 2: rows columns entry
				 * user enters the number of cols and rows in the matrix.
				 * goes to state 3, 4, or 5 based on answer from state 1
				 */
				
			case 2:
				int rInt;
				try {
					response = prompt("enter number of rows in the board\n"
										+ "maximum supported is 99");
					rInt = Integer.parseInt(response);
					if(rInt < 100 ) {
						rows = rInt;
					} else {
						break;
					}
					
					response = prompt("enter number of columns in the board\n"
							+ "maximum supported is 99");
					rInt = Integer.parseInt(response);
					if(rInt <= 100 ) {
						columns = rInt;
					} else {
						break;
					}
					matrix = new int[rows][columns];
				} catch (Exception e) {
					prompt("error...");
				}
				
				state = 3;
				if(input_manual) state = 4;
				if(input_random) state = 5;

				
				break;
				/*
				 * State 3: File Entry
				 * prompts user for the file with the board, and then parses the file into the board global.
				 */
			case 3:
				response = prompt(dictionary.fileEntry);
				if(response.equals("back")) {
					state = 1;
					break;
				}
			    try {
			        // read file, putting numbers into a 2d array
			        File file = new File(response);			// return file based on response
			        Scanner s = new Scanner(file);			// scanner for the file
			        
			        int lineIndex = 0;						// set line index
			        while(s.hasNextLine()) {
			        	Scanner ls = new Scanner(s.nextLine());
			        	if(lineIndex == 0) {
			        		
			        	}
			        	int i = 0;
			        	
			        	while(ls.hasNextInt()) {
			        		matrix[lineIndex][i] = ls.nextInt();
			        		i++;
			        	}

			        	lineIndex++;
			        	ls.close();
			        }
			        
			        s.close();
			    } catch (FileNotFoundException e) {
			        System.out.println("File not found! Please try again.");
					break;

			    } catch (ArrayIndexOutOfBoundsException e) {
					System.out.println(dictionary.numberEntryError);
					System.out.println("invalid number of items. check your matrix file");
					break;
			    } catch (NumberFormatException e) {
					// if parsed value can't be parsed as an Int, return error value
					System.out.println(dictionary.numberEntryError);
					System.out.println("NFE Exception. Ensure all numbers are integers");
					break;
				} catch (InputMismatchException e) {
					// if parsed value isn't of expected type, return error value
					System.out.println(dictionary.numberEntryError);
					System.out.println("IME Exception. Ensure all numbers are integers");
					break;
				}
				state = 6;

				break;
			/*
			 * State 4: Manual Entry
			 * User will input the matrix manually, row by row. parsed into the board global
			 */
			case 4: 
				for(int i = 0; i < rows; i++) {
					boolean error = false;
					response = prompt("enter row " + (i+1) + ":");
					Scanner sc = new Scanner(response);
					int j = 0;
					while(sc.hasNextInt()) {
						if(j > columns - 1) {
							System.out.println("too many items! you only have " + columns + " columns!");
							error = true;
							break;
						}
						matrix[i][j] = sc.nextInt();
						j++;
					}
					if(error) i--;
					sc.close();
				}
				state = 6;
				break;
			/*
			 * State 5: Random Entry
			 * generate random board based on number of rows and columns decided in state 2
			 */
			case 5:
				Random rand = new Random();
				for(int i = 0; i < matrix.length; i++) {
					for (int j = 0; j < matrix[i].length; j++) {
						matrix[i][j] = rand.nextInt(10 + 9) - 10;
					}
				}
				state = 6;
				break;
			/*
			 * State 6: Penultimate
			 * with completed and verified matrices, do stuff with them to get our stuffs.
			 */
			case 6:
				board = new Board(matrix);
				System.out.println("--------------------------------------------------");
				System.out.println("Entered Board:");
				print2dArr(board.board);
				System.out.println("Augmented Board:");
				print2dArr(board.augBoard);
				System.out.println("Maximum:");
				System.out.println(board.maximum);
				System.out.println("Route:");
				printRoute(board.route);
				state = 0;
				break;
			}
		}
	}


	private String prompt(String print) {
		System.out.println("--------------------------------------------------");
		System.out.println(print);
		String response = sc.nextLine();
		if(response.equals("exit")) state = 0;
		return response.toLowerCase();
	}
	
	private void print2dArr(int[][] arr) {
		for(int i = 0; i < arr.length; i++) {
			for(int j = 0; j < arr[i].length; j++) {
				System.out.print("[");
				System.out.printf(String.format("%1$4s", arr[i][j]));
				System.out.print("]");
			}
			System.out.println();
		}
	}
	
	private void printRoute(int[] arr) {
		for(int i = 0; i < arr.length; i++) {
			if(i == arr.length - 1)
				System.out.print(arr[i]);
			else 
				System.out.print(arr[i] + " -> ");
		}
		System.out.println();
	}

}
