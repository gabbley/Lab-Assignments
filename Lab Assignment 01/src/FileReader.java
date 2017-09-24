import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;


public class FileReader {

	private static String outputFile = "output.txt";

	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("Not enough files provided");
			System.exit(1);
		}

		Scanner in = openFile(args[0]);
		if (in == null) {
			System.exit(1);
		}

		part1(args[0]);
		part2(args[0], args[1]);
		System.out.println(part3(openFile(args[2])));
	}

	public static Scanner openFile(String filename) { //file to Scanner object

		File f = new File(filename);
		Scanner input = null;
		try {
			input = new Scanner(f);
		} catch (FileNotFoundException e) {
			return null;
		}
		return input;
	}

	public static PrintWriter canBeOpened(String filename, int part) { 
		//returns  a Printwriter if file can be opened/found

		File f = new File(filename);
		PrintWriter output = null;
		try {
			output = new PrintWriter(f);
		} catch (FileNotFoundException e) {
			System.out.println("Part" + part + ": Unable to Open File");
			return null;
		}
		return output;
	}

	// create a method that returns either a full word or a word within brackets

	public static void part1(String file) {
		PrintWriter output = canBeOpened(file, 1);
		Scanner p1 = openFile(file);
		if (checkBraces(p1)) {
			System.out.println("Braces Balanced\n");
		} else {
			output.println("Braces Not Balanced\n");
		}

	}

	public static void part2(String file1, String file2) {
		PrintWriter output = canBeOpened(file2, 2);
		Scanner p1 = openFile(file1);
		Scanner p2 = openFile(file2);
		if (compareFiles(p1, p2)) {
			System.out.println("Files Identical\n");
		} else {
			output.println("Files Not Identical\n");
		}
	}

	public static String part3(Scanner story) {
		ArrayList<String> finalWords = new ArrayList<String>();


		while (story.hasNextLine()) { //while there are more words
			finalWords = everyWord(story.nextLine());
		}
		
		return finalWords.toString();
	}

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

		for (int i = 0; i<str1.size(); i++){
			if (!str1.get(i).equals(str2.get(i))){
				result = false;;
			}
		}
		
	return result;

	}
	
	public static ArrayList<String> everyWord(String words){
		ArrayList<String> finalWords = new ArrayList<String>();
		for (int i = 0; i < words.length(); i++){
			String s = "";
		
			if (words.charAt(i) != ' ' || words.charAt(i) != '<') {
				s += words.charAt(i);
			}
			else{
				s = words.substring(i, words.indexOf('>'));
			}
				
			finalWords.add(s);
			//put everything before a space in 
			}
		
		return finalWords;
			
		}


	public static boolean checkBraces(Scanner in) {
		int openBrace = 0;
		int closedBrace = 0;
		int check = 0;
		
		while (in.hasNextLine()) {
			char[] arrChar = in.nextLine().toCharArray();
			for (char c : arrChar) {
				while (check == 0) {
					if (c == '{') {
						openBrace++;
					} 
					
					else if (c == '}') {
						closedBrace++;
						check++;
					}
				}
			}

		}
		return (openBrace == closedBrace);
	}

}
