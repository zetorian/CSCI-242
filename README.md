UPDATE: 12/5/17
I have now added the majority of code for Dijekstra's algorithm, using the same write to file format as MST
 - Ben

UPDATE: 11/25/17
I've written a "write to file" method for the MST (minimum spanning tree).  Since our outputs for each case are so different, I would suggest having a write to file method for each case.  In addition, I've modified the main method to automatically generate the output3.txt file.  I suggest naming the ouput files outputX.txt, where X is the problem number (1 for DFS, 2 for DFS, 3 for MST, and 4 for Dijkstra/SSSP).  It may also be convinient for you guys to print stuff out in the middle of executing your algorithm so you don't have to save intermediate values in some data structure.

UPDATE: 11/23/17
MST code has been added.

---------------------
I've put new updates toward the top of the README file so it's easier to see the them.
--------------------

Horray for the project!

The PriorityQ class had a bug with its isEmpty() method; that has been fixed.

Let me know when the main class, input reading code, and input file are done.  I've finished and tested the MST with a small sample size of n = 7 and it works.  I'm ready to add the MST code.

Just added the Driver class, from my testing it should parse the input files correctly. If you wouldn't mind running your algo on it that would be very helpful as mine is not complete enough yet to test the adjacency list to my satisfaction.
