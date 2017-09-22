import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
//Changes by Mrs Kelly
//Here is a second change

public class FileReader {

	private static String outputFile = "output.txt";
	
	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("Not enough files provided");
			System.exit(1);
		}

		Scanner in = openFile(args[0]);
		if (in == null){
			System.exit(1);
		}

		part1(args[0]);
		part2(args[0], args[1]);
	}

	public static Scanner openFile(String filename) {

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
	
	//create a method that returns either a full word or a word within brackets


	public static void part1(String file) {
		PrintWriter output = canBeOpened(file, 1);
		Scanner p1 = openFile(file);
		if (checkBraces(p1)) {
			output.println("Braces Balanced\n");
		} else {
			output.println("Braces Not Balanced\n");
		}
		
	}
	
	public static void part2(String file1, String file2){
		PrintWriter output = canBeOpened(file2, 2);
		Scanner p1 = openFile(file1);
		Scanner p2 = openFile(file2);
		if (compareFiles(p1, p2)){
			output.println("Files Identical\n");
		}
		else{
			output.println("Files Not Identical\n");
		}
	}
	
	public static void part3(Scanner story){
		ArrayList<String> everyWord = new ArrayList<String>();
		ArrayList<Integer> positions = new ArrayList<Integer>();
		
		while (story.hasNext()){
			int i = 0;
			everyWord.add(story.next()); //add every word to ArrayList
			if (story.next().indexOf("<") != -1 && story.next().indexOf("/>") != -1){
				 positions.add(everyWord.size()-1);
				 i++;
			}
		}
	}
	
	public static boolean compareFiles(Scanner p1, Scanner p2){
		String str1 = "";
		String str2 = "";
		
		while (p1.hasNextLine()) {
		str1 += p1.nextLine();	
		}
		
		while (p2.hasNextLine()) {
		str2 += p1.nextLine();	
		}
		
		return (str1.equals(str2));
		
	}

	public static boolean checkBraces(Scanner in) {
		int openBrace = 0;
		int closedBrace = 0;
		
		while(in.hasNextLine()) {
			char[] arrChar = in.nextLine().toCharArray();
			for (char c : arrChar) {
				if (c== '{') {
					openBrace++;
				}
				else if (c == '}'){
					closedBrace++;
				}
			}
	
		}
		if (openBrace == closedBrace){
			return true;
		}
		else
			return false;
		
	}

}
