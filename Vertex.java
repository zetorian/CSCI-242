
/**
 * Represents a vertex on the graph.  Vertices have a link to a list of edges, a name (represented
 * by the generic type T), and a shortest path weight, d, that can be used in Dijkstra's algorithm.
 * There is also a boolean flag present that is useful for all sorts of graph-traversing algorithms.
 * It's initialized to true.
 * 
 * @author Luke Dramko
 * @author Ben-Luke Metzger
 * @version 1.1
 */

import java.util.*;
import java.lang.StringBuilder;

public class Vertex<T> implements Iterable<Edge>, Comparable<Vertex<T>>
{
    public T name;
    public int d; //d represents the shortest path in Dijkstra's algorithm.
    public Vertex<T> prev = null; //a sentinal node to hold the previous node traversed for Dijkstra's, initialized to null so as to not take up extra space during other runs
    public boolean flag; //a boolean flag is frequently useful in many graph-traversing algorithms
    public Edge edges;

    /**
     * Constructor for objects of class Vertex.
     * 
     * I mean, it's just a normal constructor.  It does have to have a name (think a "label"), but if
     * you really wanted to, you could just make the name null.<br><br>
     * 
     * Also, note that the variable d is not initialized, while the boolean flag is initialized to true.
     */
    public Vertex(T n)
    {
        name = n;
        flag = true;
    }
    
    /**
     * Adds adds an UNDIRECTED edge to the graph from this vertex to the specified vetex with the
     * specified weight
     * 
     * @param v The other vertex at the end of this edge.
     * @param w The weight of the edge.
     */
    public void addEdgeTo(Vertex v, int w) {
        new Edge(this, v, w, true);
    }
    
    /**
     * Adds a DIRECTED edge to the graph from this edge to the specified vertex with the specified weight.
     * 
     * Note that this metod is addDiEdgeTo instead of addEdgeTo.  Make sure you're adding the right
     * kind of edge.
     * 
     * @param v The other vertex at the end of this edge.
     * @param w The weight of the edge.
     */
    public void addDiEdgeTo(Vertex v, int w) {
        new Edge(this, v, w, false);
    }
    
    /**
     * In an Adjacency List, vertices are kind of lists in and of themselves.  They hold the pointer
     * to a linked list of Edges.  As such this iterator returns all of the edges in order (which
     * is the order of their insertion).<br><br>
     * 
     * This is useful if you want to perform operatons on each edge in the vertex's edge list.  Just like
     * reading in from a file, you can call hasNext() and next() to get each item in the list. (Unlike 
     * reading from a file, however, there is no nextLine(), because that wouldn't make sense).<br><br>
     * 
     * This feature also allows you to use a for-each loop (also known as an enhanced for loop).  These
     * are the loops with the form:<br><br>
     * 
     * for (Edge e : VertexName) {<br>
     *      //Statments<br>
     *  }
     */
    public Iterator<Edge> iterator() {
        return new VertexEdgesIterator();
    }
    
    /**
     * Compares two vertices' d values.
     */
    public int compareTo(Vertex<T> v) {
        return d - v.d;
    }
    
    /**
     * Returns a string representation of the vertex.  It does this in the order of:
     * vertex name: -> edge -> edge -> edge etc.
     */
    public String toString() {
        StringBuilder text = new StringBuilder("");
        text.append(name);
        text.append(": -> ");
        for (Edge e : this) {
            text.append(e);
            text.append(" -> ");
        }
        int extraneous = text.lastIndexOf(" -> ");
        text.replace(extraneous, extraneous + 4, ""); //Removes the unnecessary last -> from the text.
        return text.toString();
    }
    
    /**
     * Defines the Iterator for the iterator method.
     */
    public class VertexEdgesIterator implements Iterator<Edge> {
        Edge cur = Vertex.this.edges;
        
        public boolean hasNext() {
            if (cur != null) 
                return true;
            return false;
        }
        
        public Edge next() {
            if (!hasNext()) 
                throw new NoSuchElementException();
            Edge temp = cur;
            cur = cur.next;
            return temp;
        }
    }
}
