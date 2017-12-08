import java.util.*;
import java.io.*;

public class DFSTree 
{
	private static int total = 0;
	private static File file = new File("DFSOut");
	private static PrintWriter output = null;
	
	public static void DFSSearch(AdjacencyList<String> t)throws FileNotFoundException
	{
		output = new PrintWriter(file);
		t.setFlagsTo(false);
		output.println("Grand Forks");
		DFS(t.find("Grand Forks"));
		output.println("\n\nTotal Weight :"+total);
		printD(t);
		printB(t);

		output.close();
	}
	
	public static void DFS(Vertex<String> start)
	{
		start.flag = true;
		for(Edge e : start)
		{
			Vertex<String> other = e.end;
			if(!other.flag)
			{
				e.type = "D";
				total += e.weight;
				output.println(other.name);
				DFS(other);
			}
			else
			{
					e.type = "B";
			}
		}
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
	
	public static void printB(AdjacencyList<String> t)
	{
		output.println("\n\nBack Edges");
		for(Vertex<String> v : t)
		{
			for(Edge e : v)
			{
				if(e.type.equals("B"))
					output.println(e.toString());
				else if(e.type.equals("untouched"))
					output.println("ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR");
			}
		}
	}
}
