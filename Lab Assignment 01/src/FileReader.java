
/*	<p>
 * The FileReader class takes in 3-4 files, checks the first two files 
 * (comparing and if balanced), and uses the last two files to
 * emulate a "MadLibs" game, prompting the user or using the fourth file
 * to replace any missing words in the story provided.
 * </p>
 * 
 * @author Gabby Baniqued
 * */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class FileReader {

	/**
	 * outputFile is the file name of the output file
	 * PrintWriter writes to the output file
	 * fileNum determines how many arguments are sent in
	 */

	private static String outputFile = "output.txt";
	private static PrintWriter out = canBeOpened(outputFile);
	private static int fileNum = 0;

	public static void main(String[] args) {

		// checks if there are enough files to run
		fileNum = args.length;
		if (fileNum < 3) {
			out.println("Not enough files provided");
			System.exit(1);
		}

		//checks if first file can be found
		Scanner in = openFile(args[0], 0);
		if (in == null) {
			System.exit(1);
		}

		
		part1(args[0]); //part 1
		
		part2(args[0], args[1]); //part 2
		
		
		if (args.length != 4) { //part 3 (if part 4 not applicable)
			out.println(printArray(userMadLibs((part3(args[2])))));
		} else {
			part4(args[3], args[2]); //part 4, if applicable
		}

		out.close(); //closes output file

	}

	/**
	 * 
	 * @return Scanner of the args file
	 * 
	 * @param filename
	 *            file name from args
	 * 
	 * @param part
	 * 			indicates which part the program is running
	 */
	public static Scanner openFile(String filename, int part) { 
	
		File f = new File(filename);
		Scanner input = null;
		try {
			input = new Scanner(f);
		} catch (FileNotFoundException e) {
			out.println("Part" + part + ": Unable to Open File");
			return null;
		}
		return input;
	}

	/**
	 * 
	 * @return PrintWriter to write to output file
	 * 
	 * @param filename
	 *            file name from args
	 */
	public static PrintWriter canBeOpened(String filename) {
		// returns a Printwriter if file can be opened/found

		File f = new File(filename);
		PrintWriter output = null;
		try {
			output = new PrintWriter(f);
		} catch (FileNotFoundException e) {
			System.out.println("Cannot open file: " + filename);
			return null;
		}
		return output;
	}

	/**
	 * <p>
	 * Takes in file and determines if braces are balanced
	 * in the style of java
	 * </p> 
	 * 
	 * @return void, prints to output
	 * 
	 * @param filename
	 *            file name from args
	 */
	public static void part1(String filename) {
		// PrintWriter output = canBeOpened(file, 1);
		Scanner p1 = openFile(filename, 1);
		if (checkBraces(p1)) {
			out.println("Braces Balanced\n");
		} else {
			out.println("Braces Not Balanced\n");
		}

	}
	
	/**
	 * <p>
	 * Takes in two files, indicates if both are completely identical
	 * </p> 
	 * 
	 * @return void, prints to output file
	 * 
	 * @param file1
	 * 			file name for comparison
	 * 
	 * @param file2
	 * 			other file name for comparison
	 */
	public static void part2(String file1, String file2) {
		Scanner p1 = openFile(file1, 2);
		Scanner p2 = openFile(file2, 2);
		if (compareFiles(p1, p2)) {
			out.println("Files Identical\n");
		} else {
			out.println("Files Not Identical\n");
		}
	}

	/**
	 * 
	 * @return ArrayList<String> of the .txt file separated into 
	 * 	elements of words, still contains missing words with tags
	 * 
	 * @param filename
	 *            file name from args
	 */
	public static ArrayList<String> part3(String filename) {
		Scanner p3 = openFile(filename, 3);
		ArrayList<String> everyLine = new ArrayList<String>();
		ArrayList<String> finalWord = new ArrayList<String>();

		while (p3.hasNextLine()) { // while there are more words
			everyLine = (everyWord(p3.nextLine() + "\n"));
			finalWord.addAll(everyLine); //merges all lines of .txt file
		}

		return finalWord;
	}
	
	/**
	 * @return ArrayList<String> of completed .txt file with replacements
	 * 	from fourth file (if applicable)
	 * 
	 * @param file4
	 * 			file name of fourth file for replacement of missing words
	 * 
	 * @param file3
	 * 			file with tagged words, to be replaced and finalized
	 */
	public static ArrayList<String> part4(String file4, String file3) {
		ArrayList<String> lib = new ArrayList<String>();
		Scanner p4 = openFile(file4, 4);
		Scanner p3 = openFile(file3, 3);
		while (p3.hasNextLine() && p4.hasNextLine()) {
			lib = userMadLibs(everyWord(p3.nextLine()), everyWord(p4.nextLine()));
		}

		return lib;

	}

	/**
	 * <p>
	 * Takes in two files and places each's separate words
	 * or missing tagged phrases into separate elements. <br>
	 * Uses .equals to compare each element, returns true if no differences
	 * are detected, false if otherwise.
	 * </p> 
	 * 
	 * @return boolean if files are identical
	 * 
	 * @param p1
	 * 			Scanner file for comparison
	 * 
	 * @param p2
	 * 			other Scanner file for comparison
	 */
	public static boolean compareFiles(Scanner p1, Scanner p2) {
		ArrayList<String> str1 = new ArrayList<String>();
		ArrayList<String> str2 = new ArrayList<String>();
		boolean result = true;

		while (p1.hasNextLine()) {
			str1 = everyWord(p1.nextLine());
		}

		while (p2.hasNextLine()) {
			str2 = (everyWord(p2.nextLine()));
		}

		for (int i = 0; i < str1.size(); i++) {
			if (str1.get(i).equals(str2.get(i))) {
				result = true;
			} else
				result = false;
		}

		return result;

	}
	
	/**
	 * <p>
	 * Takes in file and determines if braces are balanced
	 * in the style of java
	 * </p> 
	 * 
	 * @return ArrayList<String> of array with each word 
	 * 	stored in an separate element for one line of Scanner
	 * 
	 * @param words
	 * 			String of each line from Scanner file
	 */
	public static ArrayList<String> everyWord(String words) {
		ArrayList<String> strArr = new ArrayList<String>();
		String s = "";
		for (int i = 0; i < words.length(); i++) {

			if (words.charAt(i) != '<') {
				s += "" + words.charAt(i);
				if ((words.charAt(i) == ' ')) {
					strArr.add(s);
					s = "";
				}
			} else if (words.charAt(i) == '<') {
				String addThis = words.substring(i, words.indexOf('>', i));
				strArr.add(addThis);
				i += addThis.length();
			}
		}

		return strArr;
	}

	/**
	 * 
	 * @return boolean if braces are balanced
	 * 
	 * @param Scanner
	 * 			file checked if braces are balanced
	 */
	public static boolean checkBraces(Scanner in) {
		int counter = 0;

		while (in.hasNextLine()) {
			char[] arrChar = in.nextLine().toCharArray();
			for (char c : arrChar) {
				if (c == '{') {
					counter++;
				} else if (c == '}') {
					counter--;
				}
			}
		}
		return (counter == 0);
	}

	/**
	 * 
	 * @return ArrayList<String> containing Mad Libs file with replacements
	 * 	by user prompts
	 * 
	 * @param ArrayList<words> each word separate into elements for replacement
	 */
	public static ArrayList<String> userMadLibs(ArrayList<String> words) {

		ArrayList<String> finalLibs = new ArrayList<String>(words.size());
		ArrayList<String> responses = new ArrayList<String>();
		Scanner kb = new Scanner(System.in);

		for (int i = 0; i < words.size(); i++) {
			String lib = "";


			if (words.get(i).indexOf('<') == -1) {
				finalLibs.add(words.get(i));
			} else {

				// remove tags from word
				String type = removeLibs(words.get(i));

				// prompt user response
				System.out.println("Enter: " + type);
				lib = kb.next() + "\n";
				responses.add(lib); // all the responses
				finalLibs.add(lib); // final edited words
				
			}
			

			for (int j = 0; j < words.size(); j++) {
				int counter = 0;
				if (words.get(i).indexOf('<') != -1) {
					words.set(i, responses.get(counter));
				}
			}

		}
		if (!kb.hasNext())
		kb.close(); // close the Scanner
		
		return words;
	}

	/**
	 * 
	 * @return ArrayList<String> contains final Mad Libs replacements by fourth provided file
	 * 	if applicable
	 * 
	 * @param words
	 * 			contains words to be replaced
	 * 
	 * @param answerLibs
	 * 			contains words to replace missing words in tags
	 */
	public static ArrayList<String> userMadLibs(ArrayList<String> words, ArrayList<String> answerLibs) {

		for (int i = 0; i < words.size(); i++) {
			if (words.get(i).indexOf('<') != -1) {
				words.set(i, answerLibs.get(i));
			}
		}
		
		return words;
	}

	/**
	 * 
	 * @return String without tags
	 * 
	 * @param lib
	 * 			String with tags to be removed
	 */
	public static String removeLibs(String lib) {
		lib = lib.replace("<", "");
		lib = lib.replace(">", "");

		return lib;
	}

	/**
	 * 
	 * @return String to be printed from an ArrayList
	 * 
	 * @param libs
	 *            ArrayList to be printed as one String
	 */
	public static String printArray(ArrayList<String> print) {
		String s = "";
		for (int i = 0; i < print.size(); i++) {
			s += print.get(i);
		}

		return s;
	}

}
