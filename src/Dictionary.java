import java.util.*;
import java.io.*;

public class Dictionary {

	ArrayBasedSortedList wordList = new ArrayBasedSortedList();

	Scanner scan = new Scanner(System.in);				 //Set Scanner for Dictionary

	public void menu() {

		while (true) {
			System.out.print("Possible operations: \n" + "f) find the meaning of a word. \n"
					+ "i) insert an entry (a new pair of word, meaning). \n"
					+ "l) load the dictionary from an input file. \n" + "r) remove an entry. \n"
					+ "s) save the contents of the dictionary in an output file. \n" + "x) exit \n"
					+ "Please choose an option (f, i, l, r, s, or x): \n");
			String input = scan.nextLine();
			input = input.toLowerCase();

			switch (input) {
			// Case statements
			case "f":
				System.out.println("You Selected F");
				this.findWord();
				break;
			case "i":
				System.out.println("You Selected I");
				this.addWord();
				break;
			case "l":
				System.out.println("You Selected L");
				this.load();
				break;
			case "r":
				System.out.println("You Selected R");
				this.removeWord();
				break;
			case "s":
				System.out.println("You Selected S");
				this.save();
				break;
			case "x":
				System.out.println("You Selected X");
				this.exit();
				break;
			default:
				System.out.println("Error, this is not a valid input.");
			}
		}
	}

	public void findWord() { 

		Words temp = new Words();				 									//Dummy variable to temporary store data.
		System.out.println("Enter the word for which you want the definition: ");
		String wordInput = scan.nextLine();											//Set wordInput to scanned value
		wordInput = wordInput.toLowerCase(); 										//Ignore casing by switching to lower case
		temp.key = wordInput; 														//set dummy key value
		temp = (Words) wordList.find(temp); 											// Call to the actual function within ADT

		if (temp == null) {
			System.out.println(wordInput + " is not in the list."); 
		}

		else {
			System.out.println("You chose the word: " + temp.key + ". The definition of that word is: " + temp.value);
		}
	}

	public void addWord() {

		Words temp = new Words(); 													//Dummy variable to temporary store data.
		System.out.println("Please enter the word: ");
		String wordInput = scan.nextLine(); 											//Set wordInput to scanned value
		wordInput = wordInput.toLowerCase();											//Ignore casing by switching to lower case
		temp.key = wordInput;
		Words found = (Words) wordList.find(temp);									//Call to actual function within ADT

		if (found != null) {															//Checks if variable is found in Array
			System.out.println("The word you entered already exists in the dictionary. \n"
					+ "Would you like to update the meaning (y/n)?");
			String yn = scan.nextLine();												//Checks against y/n for user input
			yn = yn.toLowerCase();

			switch (yn) {
			case "y":
				System.out.println("Enter the new definition for the word: ");
				String newDef = scan.nextLine();										//sets a dummy variable for inputted new definition
				newDef = newDef.toLowerCase();					
				found.value = newDef;												//sets new definition in Array
				System.out.println("Word has been updated.");
				break;
			case "n":
				System.out.println("Returning to menu");
				break;
			default:
				System.out.println("Invalid response, returning to menu");
				break;
			}
		}

		else {
			System.out.println("Input the definition for that word: "); 				//If variable is not found adds to Array
			wordInput = scan.nextLine();
			wordInput = wordInput.toLowerCase();
			temp.value = wordInput;
			wordList.add(temp);
			System.out.println("The word '" + temp.key + "' with the definition '" + temp.value
					+ "' has been added to the dictionary.");
		}
	}

	public void removeWord() { 														

		Words temp = new Words();													//sets dummy variable
		boolean success = false;														//sets boolean flag for removal
		System.out.println("Input the word to be removed from the dictionary: ");	
		String wordInput = scan.nextLine();											//sets variable for removal
		wordInput = wordInput.toLowerCase();
		temp.key = wordInput;
		success = wordList.remove(temp);												//Call to the actual function within ADT

		if (success) {
			System.out.println("The word was removed");
		}

		else {
			System.out.println("Word not found");
		}
	}

	public void save() {

		System.out.println("Are you sure you wish to save? (y/n)");
		String wordInput = scan.nextLine();											//sets variable for input
		wordInput = wordInput.toLowerCase();

		if (wordInput.equals("y")) {
			try {
				System.out.println("Enter the name of the file you wish to save.");  
				String fileName = scan.nextLine();										//sets file name to input
				fileName = fileName.toLowerCase();	
				File file = (new File(fileName));										//sets to file for file writer
				FileWriter fw = new FileWriter(file);									//creates the file for storage

				for (int i = 0; i < wordList.getNum() + 1; i++) { 						//loops over array to find all values
					fw.write(((Words) wordList.get(i)).key + " " + ((Words) wordList.get(i)).value);		//writes key and value to file.
					fw.write(System.getProperty("line.separator"));
				}
				fw.close();

			} catch (FileNotFoundException e) {
				System.out.println("File not found");
			} catch (IOException e) {
				System.out.println("Error initializing stream");
			}
		}

		else if (wordInput.equals("n")) {
			System.out.println("Returning to menu.");
		}

		else {
			System.out.println("Response not valid. Returning to menu.");
		}
	}

	public void load() {

		System.out.println("Are you sure you wish to load? (y/n)");
		String wordInput = scan.nextLine();										//sets dummy variable for user input
		wordInput = wordInput.toLowerCase();

		if (wordInput.equals("y")) {
			if (wordList.getNum() > -1)											//checks if Array is empty if it isn't dumps current array and creates a new one.
				wordList = new ArrayBasedSortedList();

			try {

				System.out.println("Enter the name of the file to load: ");
				String fileName = scan.nextLine();								//sets variable to user input
				fileName = fileName.toLowerCase();
				FileReader fr = new FileReader(fileName);						//sets a file reader for fileName
				Scanner scanner = new Scanner(fr);

				while (scanner.hasNext()) {

					Words temp = new Words();									//runs through file pulling strings and adding them to array, key : definition
					String str = scanner.nextLine();
					String word = str.substring(0, str.indexOf(' '));
					String def = str.substring(str.indexOf(' ') + 1, str.length());

					temp.key = word;
					temp.value = def;
					wordList.add(temp);
				}
				scanner.close();
				System.out.println("File Loaded");
			} catch (IOException e) {
				System.out.println("ERROR! File not accessed.");
			}
		}

		else if (wordInput.equals("n")) {
			System.out.println("Returning to menu.");
		}

		else {
			System.out.println("Response not valid. Returning to menu.");
		}

	}

	public void exit() {

		System.out.println("Are you sure you wish to exit? (y/n)"); //exits program with user prompting.
		String wordInput = scan.nextLine();
		wordInput = wordInput.toLowerCase();

		if (wordInput.equals("y")) {
			System.out.println("Have a good day!");
			System.exit(0);
		}

		else if (wordInput.equals("n")) {
			System.out.println("Returning to menu.");
		}

		else {
			System.out.println("Response not valid. Returning to menu.");
		}
	}

}
