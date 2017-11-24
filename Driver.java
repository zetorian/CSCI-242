/** 
 * This is a skeleton Driver class, with methods in place to parse the input files and allow for calling of the separate
 * algorithms.<br><br>
 *
 * main():
 *		The driving force behind the whole project, please add what you need here, and go ahead and PLEASE add comments
 *		AT THE SAME TIME as you add code so we can understand what is going on.
**/

import java.util.*;
import java.io.*;
public class Driver
{
	
	public static AdjacencyList<String> list; //considering the probable variance in our many algorithms, a public variable seemed the most sensible way to manage the list
	
	/**
	 * As of right now, all this does is read in input and create an adjacency list, please add code to call your
	 * algorithms from here.
	**/
	public static void main(String[] args)
	{
		initialize(args); //Using a helper method to keep main() algorithim-centric
		
		//please add code below this line
		
		
		
		
		
		
	}
	
	/**
	 * This method will take in arguments from the main() method and parse the input files, putting them into 
	 * a public adjacency list which can then be manipulated by the various algorithms. <br><br>
	 * 
	 * It works by parsing a tab delimited data file of the form (vertex1    vertex2    weight)
	 * the data is read line by line and put into an array which is then fed to AdjacencyList's add method
	 * if an exception is thrown, by a title line perhaps, the line is skipped and noted on System.err, in most
	 * cases this can be safely ignored, just make sure to check that the correct number of data lines were loaded <br><br>
	 *
	 * If you need to make any changes to this method, please document them well as they will affect everyone's code.
	**/
	private static void initialize(String[] args)
	{
		//as of right now, I see no reason to use this file, but its here in case anyone wants it
		String vertexFileName = "DATA-FINAL-F17-verticies.csv";
		
		
		String dataFileName = "DATA-FINAL-F17.txt"; //the data file that will actually be parsed
		
		//WIP: this conditional will parse args, if we need to specify an alternate input(s).
		// as this is not required, I only plan to finish it if I have extra time, it would however
		// make our code more flexible
		if (args.length == 2)
		{
			vertexFileName = args[1];
			dataFileName = args[0];
		}
		if (args.length == 1)
		{
			dataFileName = args[0];
		}
		
		//initializing the adjacency list
		list = new AdjacencyList<String>();
		
		File inputFile = new File(dataFileName);
		//because of the nature of how the adjacency list object works, it seemed most effecient to me
		//to use the add edge format to create the list, if anyone sees a simpler way, please change 
		//this to reflect it.
		Scanner input = null;
		try {
			input = new Scanner(inputFile);
		} catch (FileNotFoundException e) {
			System.err.println("ERROR: cannot find data file: " + dataFileName + ", please try a different data file.");
			System.exit(-1);
		}
		int i = 1;
		while(input.hasNext())
		{
			String temp = input.nextLine();
			String[] dataEntry = temp.split("\t");
			try {
				list.add(dataEntry[0], dataEntry[1], Integer.parseInt(dataEntry[2]));
				i++;
			} catch (Exception e) {
				//System.err.println(e.getMessage());
				System.err.println("Skipping line: " + i++ + " as it does not appear to be data.");
			}
		}
		
		System.out.println("\nloaded " + i + " lines of data successfully");
	}
}
