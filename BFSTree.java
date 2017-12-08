import java.util.*;
import java.io.*;

public class BFSTree
{
	private static int total = 0;
	private static File file = new File("BFSOut");
	private static PrintWriter output = null;
	
	public static void BFSSearch(AdjacencyList<String> t)throws FileNotFoundException
	{
		output = new PrintWriter(file);
		t.setFlagsTo(false);
		BFS(t.find("Grand Forks"),t);
		output.println("\n\nTotal Weight :"+total);
		printD(t);
		printC(t);

		output.close();
	}
	
	public static void BFS(Vertex<String> start, AdjacencyList<String> t)
	{
		t.setDValuesTo(Integer.MAX_VALUE);
		LinkedList<Vertex> oldList = new LinkedList<Vertex>();
		LinkedList<Vertex> newList = new LinkedList<Vertex>();
		oldList.add(start);
		start.d = 0;
		start.flag = true;
		do
		{
			newList = new LinkedList<Vertex>();
			for(Vertex<String> v : oldList)
			{
				output.println(v.name);
				for(Edge e : v)
				{
					if(e.end.d > v.d&&!e.end.flag)
					{
						e.end.flag = true;
						e.end.d = v.d+1;
						newList.add(e.end);
						e.type = "D";
						total += e.weight;
					}
					else if(e.end.d == v.d)
					{
						e.type = "C";
					}
				}
			}
			oldList = newList;
		}
		while(!newList.isEmpty());
		
	}
	
	public static void printD(AdjacencyList<String> t)
	{
		output.println("\n\nDiscovery Edges");
		for(Vertex<String> v : t)
		{
			for(Edge e : v)
			{
				if(e.type.equals("D"))
					output.println(e.toString());
				else if(e.type.equals("untouched"))
					output.println("ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR");
			}
		}
	}
	
	public static void printC(AdjacencyList<String> t)
	{
		output.println("\n\nCross Edges");
		for(Vertex<String> v : t)
		{
			for(Edge e : v)
			{
				if(e.type.equals("C"))
					output.println(e.toString());
				else if(e.type.equals("untouched"))
					output.println("ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR");
			}
		}
	}
}
