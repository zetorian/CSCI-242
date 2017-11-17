
/**
 * Represents an adjacency list: a list of vertices with attached to a list of edges. The list is unbounded;
 * the backing array grows as additional elements are added.  Duplicate vertex names are not recommended, and may
 * lead to unintended behavior.
 * 
 * HOW TO USE THIS CLASS<br>
 * This is an adjacency list structure, which is used to represent a graph.  For more information on adjacency
 * lists, see pages 361-362 of the textbook.<br<br>
 * 
 * Creating an adjacency list is fairly straightfoward. It is declared like any other object, and the angle
 * brackets specify the type that the vertex is storing for its name.  (For example, if you're representing
 * airports with this adjacency list, you could put String inside the brackets, and use strings later in the 
 * program to add vertices with airport code names).<br<br>
 * 
 * AdjacencyList<String> list = new AdjacencyList<String>();   <br><br>
 * 
 * Both vertices and edges can be added to the AdjacencyList with the overloaded add method. All versions
 * of the add method will either accept an argument of type T (whatever you put in the <> at the declaration
 * of the class instance - in this example, Strings) or a vertex argument.  If you supply an argument of type T,
 * a vertex with that type will be created for you.  However, add all vertices to the graph before making
 * an edge between them; otherwise, those verticies may not appear on iterative operations.<br><br>
 * 
 * Vertices can be accessed using a for-each loop or iterator.  Details about this are given in the javadoc
 * for the iterator() method.  In turn, vertices have their own interators which allow sequential access to
 * all edges associated with them.
 * 
 * @author Luke Dramko
 * @version 1.0
 */
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.StringBuilder;

public class AdjacencyList<T> implements Iterable<Vertex<T>>
{
    private Vertex[] vertices;
    private int endPosition = 0;

    /**
     * Creates a blank Adjacency List with initial capacity 10
     */
    public AdjacencyList() {
        vertices = new Vertex[10];
    }
    
    /**
     * Creates a blank Adjacency list with the specified starting capacity.
     */
    public AdjacencyList(int size) {
        vertices = new Vertex[size];
    }

    /**
     * Adds a vertex to the adjacecy list of the specified name.
     */
    public void add(T element) {
        add(new Vertex(element));
    }
    
    /**
     * Adds a supplied vertex element to the adjacency list
     */
    public void add(Vertex v) {
        if (endPosition + 1 == vertices.length) { //Table doubling extends a full array
            Vertex[] temp = new Vertex[vertices.length * 2];
            for (int i = 0; i < vertices.length; i++) {
                temp[i] = vertices[i];
            }
            vertices = temp;
        }
        
        vertices[endPosition] = v;
        endPosition++;
    }
    
    /**
     * Adds an UNDIRECTED edge between two vertices with names element1 and element2.  If one (or both)
     * of these new vertices don't exist, a new vertex will be created with the specified element(s). 
     * Those vertices will be added to the adjacency list.  Then, the edge is created.
     */
    public void add(T element1, T element2, int weight) {
        Vertex v1 = find(element1);
        Vertex v2 = find(element2);
        if (v1 == null) {
            v1 = new Vertex(element1);
            add(v1);
        }
        if (v2 == null) {
            v2 = new Vertex(element2);
            add(v2);
        }
        v1.addEdgeTo(v2, weight);
    }
    
    /**
     * Adds an UNDIRECTED edge between the two supplied vertices.  If those vertices aren't already in the
     * graph, they will be added.
     */
    public void add(Vertex v1, Vertex v2, int weight) {
        v1.addEdgeTo(v2, weight);
    }
    
    /**
     * This method accepts paramters for whatever the name of the vertices are.
     * 
     * If the boolean parameter at the end of the parameter list is true, this method creates a directed
     * edge between the two supplied vertices.  Otherwise, an undirected edge is created.
     */
    public void add(T start, T end, int weight, boolean isDiedge) {
        Vertex st = find(start);
        Vertex en = find(end);
        if (start == null) {
            st = new Vertex(start);
            add(st);
        }
        if (end == null) {
            en = new Vertex(end);
            add(en);
        }
        add(st, en, weight, isDiedge);
    }
    
    /**
     * If the boolean parameter at the end of the parameter list is true, this method creates a directed
     * edge between the two supplied vertices.  Otherwise, an undirected edge is created.
     */
    public void add(Vertex start, Vertex end, int weight, boolean isDiedge) {
        if (isDiedge) 
            start.addDiEdgeTo(end, weight);
         else 
            start.addEdgeTo(end, weight);
    }
    
    /**
     * This method can be used to set all vertices' d values (which represent the curent shortest distance, d, 
     * in Dijkstra's algorithm) to a specific value.<br><br>
     * 
     * This is certianly useful for Dijkstra's algorithm: send INTEGER.MAX_VALUE as an argument.<br>
     */
    public void setDValuesTo(int input) {
        for (Vertex v : this) {
            v.d = input;
        }
    }
    
    /**
     * This method sets the boolean flags in the vertices to be the input value: either true or false.
     */
    public void setFlagsTo(boolean input) {
        for (Vertex v : this) {
            v.flag = input;
        }
    }
    
    /**
     * Returns the iterator over the elements (vertices) in this instance of AdjacencyList.<br><br>
     * 
     * This is useful if you want to perform operatons on each vertex in the adjacency list.  Just like
     * reading in from a file, you can call hasNext() and next() to get each item in the list. (Unlike 
     * reading from a file, however, there is no nextLine(), because that wouldn't make sense).<br><br>
     * 
     * This feature also allows you to use a for-each loop (also known as an enhanced for loop).  These
     * are the loops with the form:<br><br>
     * 
     * for (Vertex v : AdjacencyListName) {<br>
     *      //Statments<br>
     *  }
     */
    public Iterator<Vertex<T>> iterator() {
        return new AdjacencyListIterator();
    }
    
    /**
     * Searches for a vertex with the specified name.  Because vertices in and Adjacency List are unordered,
     * linear search is used.
     * 
     * You don't nead an overloaded Vertex parameter version for this method, because if you have the vertex, 
     * you don't need to find it, assuming you don't go and create vertices outside of the AdjacencyList class.
     */
    public Vertex<T> find(T name) {
        for (Vertex v : this) {
            if (v.name.equals(name)) {
                return v;
            }
        }
        return null;
    }
    
    /**
     * Returns a doubly-deep copy of the AdjacencyList.  Both the AdjacencyList and the vertices and edges
     * inside it are completely different objects than those of the original AdjacencyList.  Making structural
     * modifications to this list will not in any way a affect the copy.
     */
    public AdjacencyList<T> deepCopy() {
        AdjacencyList<T> copy = new AdjacencyList<T>(vertices.length);
        for (Vertex<T> v : this) {
            Vertex vCopy = new Vertex(v.name);
            copy.add(vCopy);
        }
        
        for (Vertex<T> v : this) {
            for (Edge e : v) {
                copy.add(copy.find(v.name), copy.find((T)(e.end.name)), e.weight, true);
            }
        }
        return copy;
    }
    
    /**
     * Returns the AdjacencyList as a String with the following format:<br><br>
     * Vertex 1 (newline)<br>
     * Vertex 2 (newline)<br>
     * etc.
     */
    public String toString() {
        StringBuilder text = new StringBuilder("");
        for (Vertex v : this) {
            text.append(v);
            text.append("\n");
        }
        return text.toString();
    }
    
    /**
     * This inner class defines the Iterator returned by the iterator method.
     */
    public class AdjacencyListIterator implements Iterator<Vertex<T>> {
        int position = 0;
        
        /**
         * Returns true if vertices[position] is inside the array and not null.
         */
        public boolean hasNext() {
            if (position < AdjacencyList.this.vertices.length && null != AdjacencyList.this.vertices[position]) 
                return true;
            else 
                return false;
        }
        
        /**
         * Returns the next vertex in the AdjacencyList, if one exists.  Otherwise, it'll throw a 
         * NoSuchElementException, so watch out!
         */
        public Vertex<T> next() {
            if (!hasNext())
                throw new NoSuchElementException();
            return vertices[position++];   //Note that this uses a postfix increment operator.
        }                                  //This means "position" is used, then incremented.
    }
}
