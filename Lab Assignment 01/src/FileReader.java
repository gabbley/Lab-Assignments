/*	The FileReader class takes in 3-4 files, checks the first two files 
 * comparing and if balanced), and uses the last two files to
 * emulate a "MadLibs" game, prompting the user or using the fourth file
 * to replace any missing words in the story provided.
 * 
 * */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;


public class FileReader {

	//private static String outputFile = "output.txt";

	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("Not enough files provided");
			System.exit(1);
		}

		Scanner in = openFile(args[0]);
		if (in == null) {
			System.exit(1);
		}
		int part = 1;
		PrintWriter out = canBeOpened("output.txt", part);
		
		part1(args[0], out); part++;
		part2(args[0], args[1], out);
		if (args.length != 4){
		out.println(userMadLibs((part3(openFile(args[2])))));
		}
		else{
			ArrayList<String> p3 = userMadLibs((part3(openFile(args[2]))));
			part4(args[3], p3);
		}
		
		out.close();
			
		
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

	public static void part1(String file, PrintWriter out) {
		//PrintWriter output = canBeOpened(file, 1);
		Scanner p1 = openFile(file);
		if (checkBraces(p1)) {
			out.println("Braces Balanced\n");
		} else {
			out.println("Braces Not Balanced\n");
		}

	}

	public static void part2(String file1, String file2, PrintWriter out) {
		//PrintWriter output = canBeOpened(file2, 2);
		Scanner p1 = openFile(file1);
		Scanner p2 = openFile(file2);
		if (compareFiles(p1, p2)) {
			out.println("Files Identical\n");
		} else {
			out.println("Files Not Identical\n");
		}
	}

	public static ArrayList<String> part3(Scanner story) {
		ArrayList<String> everyLine = new ArrayList<String>();
		ArrayList<String> finalWord = new ArrayList<String>();


		while (story.hasNextLine()) { //while there are more words
			everyLine = (everyWord(story.nextLine() + "\n"));
			finalWord.addAll(everyLine);
		}
		
		return finalWord;
	}
	
	public static void part4(String file, ArrayList<String> p3){
	//	PrintWriter output = canBeOpened(file, 4);
		Scanner p4 = openFile(file);
		
		
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
			if (str1.get(i).equals(str2.get(i))){
				result = true;
			}
			else result = false;
		}
		
	return result;

	}
	
	public static ArrayList<String> everyWord(String words){
	      ArrayList<String> strArr = new ArrayList<String>();
	      String s = "";
	      for (int i = 0; i < words.length(); i++){
	      
	         if (words.charAt(i) != '<') {
	            s += ""+ words.charAt(i);
	            if ((words.charAt(i) == ' ')){
	               strArr.add(s);
	               s = "";
	            }
	         }
	         else if (words.charAt(i) == '<'){
	            String addThis = words.substring(i, words.indexOf('>')+1);
	            strArr.add(addThis);
	            i += addThis.length();
	         }
	      		
	      	//put everything before a space in 
	      }
	   	
	      return strArr;
	   		
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
	
	public static ArrayList<String> userMadLibs(ArrayList<String> s){
		ArrayList<String> finalLibs = new ArrayList<String>(s.size());
		ArrayList<String> responses = new ArrayList<String>();
		for (int i = 0; i<s.size(); i++){
			if (s.get(i).indexOf('<') == -1){
				finalLibs.add(s.get(i));
			}
			else{ //if < and if certain arg p3
				String type = removeLibs(s.get(i));
				Scanner kb = new Scanner(System.in);
				System.out.println("Enter: " + type);
				String lib = kb.next();
				responses.add(lib);
				finalLibs.add(lib);
//				
//				kb.close();
			}
			
			//else indicates a part 4
		}
		return finalLibs;
		
	}
	
	public static String removeLibs(String lib){
		lib = lib.replace("<", "");
		lib = lib.replace("/", "");
		lib = lib.replace("\"", "");
		lib = lib.replace(">", "");
		
		return lib;
	}
	
	public static void reopenFile(){
		
	}

}
