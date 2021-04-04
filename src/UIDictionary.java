/*
 * Public dictionary to store strings that are to be used by the UserInterface class.
 */
public class UIDictionary {
	public String mainMenu;
	public String fileEntry;
	public String numEq;
	public String numberEntryError;
	public String debug;
	public String exit;
	public String[] variables;
	public String badState;
	public String errorEntry;
	public String solutionEntry;
	public String numColRow;

	public UIDictionary() {
		mainMenu = 			"Choose an input method: \n"
							+ "a: File entry\n"
							+ "b: Manual entry\n"
							+ "c: Random Matrices";
		
		numberEntryError =	"Number format exception!";
		
		fileEntry =			"Enter the name of the file containing the board.\n"
								+ "Please include the extension.\n"
								+ "Type 'back' to return to input method selection.";
		
		debug =				"hey, you made it here!\n"
								+ "and this is a new line.\n"
								+ "now exiting...\n";
		
		exit = 				"Exiting...\n";

		badState = 			"Illegal state accessed! This is definietly a bug within the UI loop.\n"
							+ "The program will now restart. Press Enter to contine...";
	}
}
