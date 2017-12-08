import java.util.*;
import java.io.*;

public class DFSTree 
{
	private static int total = 0;
	private static File file = new File("output1.txt");
	private static PrintWriter output = null;
	private static HashMap<Integer, Edge> discovery = new HashMap<Integer, Edge>();
	private static HashMap<Integer, Edge> back = new HashMap<Integer, Edge>();
	
	/**
	 * Sets up the recursive DFS method, the PrintWriter, and generally handles the running of DFS.
	 * 
	 * This is the method that should be called if you want to do DFS.
	 */
	public static void DFSSearch(AdjacencyList<String> t)
	{
	    try {
		   output = new PrintWriter(file);
		} catch (Exception e) {
		   System.err.println("Error in creating output file: " + e.getMessage());    
		}
		
		output.println("Grand Forks");
		DFS(t.find("Grand Forks"));
		output.println("\n\nTotal Weight :"+total);
		printD(t);
		printB(t);

		output.close();
	}
	
	private static void DFS(Vertex<String> start)
	{
		start.flag = false;
		for(Edge e : start)
		{
			Vertex<String> other = e.end;
			if(other.flag)
			{
			    discovery.put(e.hashCode(), e);
				total += e.weight;
				output.println(other.name);
				DFS(other);
			}
			else
			{
			    if (!discovery.containsValue(e)) {
			        back.put(e.hashCode(), e);
			    }
			}
		}
	}
	
	/**
	 * Prints the discovery edges to a file
	 */
	private static void printD(AdjacencyList<String> t)
	{
		output.println("\n\nDiscovery Edges");
		for (Integer i : discovery.keySet()) {
		    output.println(discovery.get(i));
		}
	}
	
	/**
	 * Prints the back edges to a file
	 */
	private static void printB(AdjacencyList<String> t)
	{
		output.println("\n\nBack Edges");
		for (Integer i : discovery.keySet()) {
		    output.println(discovery.get(i));
		}
	}
}
