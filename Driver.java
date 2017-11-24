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
		
		//Code for part 3
		//Just prints out the data for now; we'll have to add a printwriter thing later.
		AdjacencyList<String> mst = minimumSpanningTree();
		System.out.println(mst);
		
		
		
		
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
	
	/**
     * Finds the Minimum Spanning Tree of the supplied AdjacencyList and returns it as a different
     * AdjacencyList.<br><br>
     * 
     * The algorithm used here is Prim's algorithm (Prim/Jarník)
     */
    public static AdjacencyList minimumSpanningTree() {
        AdjacencyList<String> mst = list.deepCopy();
        
        mst.setDValuesTo(Integer.MAX_VALUE);
        mst.setFlagsTo(true);  //All vertices in the priority queue are true.
        
        mst.iterator().next().d = 0;
        
        HashSet<Edge> edgeSet = new HashSet<Edge>();
        PriorityQ<Vertex<String>> Q = new PriorityQ<Vertex<String>>();
        
        for (Vertex v : mst) {
            Q.add(v);
        }
        
        while (!Q.isEmpty()) {
            Vertex<String> current = Q.poll();
            current.flag = false;
            for (Edge e : current) {
                //!e.end.flag means the item is not in the priority queue
                if (!e.end.flag && e.weight == current.d) {
                    edgeSet.add(e);
                    break;  //If multiple paths with the same minimum weight exist, we should only choose one.
                }
            }
            
            for (Edge e : current) {
                if (e.end.flag && e.weight < e.end.d) {
                    e.end.d = e.weight;
                    Q.decreaseKey(e.end);
                }
            }
        }
        
        /*What's left from he big while loop above is a set of all of the edges that make up the 
          minimum spanning tree.  Now, we have to construct a tree from those edges.*/
        
        for (Vertex v : mst) { //Clears out all of the edges, leaving only the vertices
            v.edges = null;
        }

        for (Edge e : edgeSet) { //Builds the MST from the edges specified by Prim's algorithm.
            e.start.addEdgeTo(e.end, e.weight);
        }
        
        return mst;
    }
}
