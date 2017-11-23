/** 
 * This is a skeleton Driver class, with methods in place to parse the input files into two dimensional Array Lists
 * I understand that it is a bit clunky to use ArrayLists in a two dimensional context, but it seemed more effecient 
 * than using fixed arrays with huge bounds, especially considering we should not have to mess with them after they
 * make their way into the adjacency list, as that is already unbounded thanks to Luke.<br><br>
 *
 * main():
 *		The driving force behind the whole project, please add what you need here, and go ahead and PLEASE add comments
 *		AT THE SAME TIME as you add code so we can understand what is going on.
**/

public class Driver
{
	/**
	 * As of right now, all this does is read in input and create an adjacency list, please add code to call your
	 * algorithms from here.
	**/
	public static void main(String[] args)
	{
		String vertexFileName = "DATA-FINAL-F17-verticies.csv";
		String dataFileName = "DATA-FINAL-F17.csv";
		
		//WIP: this conditional will parse args, if we need to specify an alternate input(s).
		// as this is not required, I only plan to finish it if I have extra time, it would however
		// make our code more flexible
		if (args[0] != null)
		{
			vertexFileName = args[1];
			dataFileName = args[0];
		}
		
		
	}
}
