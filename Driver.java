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
		
		//Code for part 1
		DFSTree.DFSSearch(list);
		
		//Code for part 2
		BFS(list, list.find("Grand Forks"), "output2.txt");
		
		//Code for part 3
		writeMSTToFile(minimumSpanningTree(), "output3.txt");
		
		//Code for part 4 (dijkstra's)
		writeSSSPToFile("output4.txt");
		
	}
	
	/**
	 * This method will take in arguments from the main() method and parse the input files, putting them into 
	 * a public adjacency list which can then be manipulated by the various algorithms. <br><br>
	 * 
	 * It works by parsing a tab delimited data file of the form (vertex1    vertex2    weight)
	 * the data is read line by line and put into an array which is then fed to AdjacencyList's add method,
	 * if an exception is thrown (by a title line perhaps) then the line is skipped and noted on System.err, in most
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
			System.exit(-1); //quit because no data was found
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
	 * Performs Breadth First Search on a given graph and writes several statistics of that search out
	 * to a file of a given file name.
	 */
	public static void BFS(AdjacencyList<String> li, Vertex start, String fileName) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new File(fileName));
        } catch (Exception e) {
            System.err.println("Error " + e.getMessage());
            return;
        }
        
        li.setDValuesTo(Integer.MAX_VALUE);
        LinkedList<Vertex> oldList = new LinkedList<Vertex>();
        LinkedList<Vertex> newList = new LinkedList<Vertex>();
        start.d = 0;
        oldList.add(start);
        
        HashMap<Integer, Edge> discovery = new HashMap<Integer, Edge>();
        HashMap<Integer, Edge> cross = new HashMap<Integer, Edge>();
        int totalWeight = 0;
        
        writer.println("Towns Traversed, in Order: ");
        do {
            newList = new LinkedList<Vertex>();
            for (Vertex<String> v : oldList) {
                writer.println(v.name);
                for (Edge e : v) {
                    if (e.end.d > v.d + 1) {
                        e.end.d = v.d + 1;
                        newList.add(e.end);
                        discovery.put(e.hashCode(), e);
                        totalWeight += e.weight;
                    } else if (e.end.d == v.d) {
                        cross.put(e.hashCode(), e);
                    }
                }
            }
            oldList = newList;
        } while (!newList.isEmpty());
        
        writer.println("\n\nTotal Weight of Discovery Edges: " + totalWeight);
        
        writer.println("\n\n--Discovery Edges--");
        for (Integer i : discovery.keySet()) {
            writer.println(discovery.get(i));
        }
        
        writer.println("\n\n--Cross Edges--");
        for (Integer i : cross.keySet()) {
            writer.println(cross.get(i));
        } 
        
        writer.close();
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
    
        /**
     * Writes all of the data for an MST out to a file.
     * 
     * The data written out is in the following format.<br>
     * The toString of the AdjacencyList (the graph)<br>;
     * The message "The total number of edges is: " followed by that value.<br>
     * The message "The total weight of the edges is: " followed by that value.<br>
     */
    public static void writeMSTToFile(AdjacencyList<String> mst, String fileName) {
        PrintWriter writer = null;
        
        try {
            writer = new PrintWriter(new File(fileName));
        } catch (Exception e) {
            System.err.println("An error has been encountered when writing " + fileName + " to a file.");
            System.err.println("The error is: " + e.getMessage() + "\n");
            return;
        }
        
        writer.println(mst + "\n");
        
        int numEdges = 0;
        int weightEdges = 0;
        for (Vertex<String> v : mst) {
            for (Edge e : v) {
                numEdges++;
                weightEdges += e.weight;
            }
        }
        
        writer.println("The total number of edges is: " + numEdges / 2 + "\n");
        writer.print("The total weight of the edges is: " + weightEdges / 2);
        
        writer.close();
    }
    
    /*
     * This is the algorithm to find the single source shortest path using Dijkstra's algorithm
    */
    
    public static Vertex[] SSSP(String startValue, String goalValue)
    {
    	if(startValue.equals(goalValue))
    	{
    		System.err.println("something went very very wrong..."); //hooray for unhelpful error messages!
    		return null;
    	}
    	AdjacencyList<String> sssp = list.deepCopy();
    	sssp.setFlagsTo(false);
    	sssp.setDValuesTo(Integer.MAX_VALUE);
    	
    	Vertex start = sssp.find(startValue);
    	start.d = 0;
    	
    	//initialize the heap implimented priority queue
    	PriorityQ<Vertex<String>> q = new PriorityQ<>();
    	for (Vertex<String> v : sssp)
    	{
    		q.add(v);
    	}
    	q.poll(); //remove the start node from the Queue
    	
    	Vertex<String> curr = start;
    	while(!q.isEmpty())
    	{
    		for (Edge working : curr) //work through all the edges of the currect vertex and calculate their relative weights
    		{
    			Vertex end = working.end;
    			if(end.flag == false && end.d > (curr.d + working.weight))
    			{
    				end.d = curr.d + working.weight;
    				q.decreaseKey(end);
    				end.prev = curr;
    			}
    		}
    		curr.flag = true;
    		
    		curr = q.poll();
    		if(curr.name.equals(goalValue))
    		{
    			return recurPath(curr);
    		}
    	}
    	return null;
    }
    
    private static Vertex[] recurPath(Vertex v)
    {
    	ArrayList<Vertex> list = new ArrayList<Vertex>();
    	list.add(v);
    	while(v.prev != null)
    	{
    		v = v.prev;
    		list.add(v);
    	}
    	
    	Vertex[] arr = new Vertex[list.size()];
	    int j = 0;
    	for (int i = arr.length-1; i >= 0; i--)
    	{
    		arr[j++] = list.get(i);
    	}
    	return arr;
    }
    
    public static void writeSSSPToFile(String fileName)
    {
    	PrintWriter writer = null;
    	try {
    		writer = new PrintWriter(fileName);
    	} catch (Exception e) {
    		System.err.println("ERROR WHILE PRINTING: " + e.getMessage());
    	}
    	
    	Vertex[] path1 = SSSP("Grand Forks","Seattle");
    	int distance1 = findPathDistance(path1);
    	Vertex[] path2 = SSSP("Seattle","Los Angeles");
    	int distance2 = findPathDistance(path2);
    	Vertex[] path3 = SSSP("Los Angeles","Dallas");
    	int distance3 = findPathDistance(path3);
    	Vertex[] path4 = SSSP("Dallas","Miami");
    	int distance4 = findPathDistance(path4);
    	Vertex[] path5 = SSSP("Miami","Boston");
    	int distance5 = findPathDistance(path5);
    	Vertex[] path6 = SSSP("Boston","Chicago");
    	int distance6 = findPathDistance(path6);
    	Vertex[] path7 = SSSP("Chicago","Grand Forks");
    	int distance7 = findPathDistance(path7);
    	
    	writer.println("SSSP:\n");
    	writer.println(stringifyVertexes(path1));
    	writer.println("route1 distance: " + distance1 + "\n");
    	
    	writer.println(stringifyVertexes(path2));
    	writer.println("route2 distance: " + distance2 + "\n");
    	
    	writer.println(stringifyVertexes(path3));
    	writer.println("route3 distance: " + distance3 + "\n");
    	
    	writer.println(stringifyVertexes(path4));
    	writer.println("route4 distance: " + distance4 + "\n");

    	writer.println(stringifyVertexes(path5));
    	writer.println("route5 distance: " + distance5 + "\n");

    	writer.println(stringifyVertexes(path6));
		writer.println("route6 distance: " + distance6 + "\n");
		
    	writer.println(stringifyVertexes(path7));
		writer.println("route7 distance: " + findPathDistance(path7) + "\n");
    	
    	writer.println("Total distance of routes: " + (distance1 + distance2 + distance3 + distance4 + distance5 + distance6 + distance7));
    	writer.close();
    }
    
    private static String stringifyVertexes(Vertex[] vs)
    {
    	if(vs.length == 0)
    	{
    		return "";
    	}
    	
    	String str = ""; 
    	str += vs[0].name;
    	for (int i = 1; i < vs.length; i++)
    	{
    		str += " " + i + "-> " + vs[i].name;
    	}
    	return str;
    }
    
    private static int findPathDistance(Vertex[] vs)
    {
    	if(vs.length == 0)
    	{
    		return -1;
    	}
    	
    	int tmp = vs[0].d;
    	for (int i = 1; i < vs.length; i++)
    	{
    		tmp += vs[i].d;
    	}
    	return tmp;
    }
}
