package cas2xb3_A2_wang_jw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

import com.sun.jdi.Type;

/**
 *  The {@code Digraph} class represents a directed graph of vertices
 *  named 0 through <em>V</em> - 1.
 *  It supports the following two primary operations: add an edge to the digraph,
 *  iterate over all of the vertices adjacent from a given vertex.
 *  It also provides
 *  methods for returning the indegree or outdegree of a vertex, 
 *  the number of vertices <em>V</em> in the digraph, 
 *  the number of edges <em>E</em> in the digraph, and the reverse digraph.
 *  Parallel edges and self-loops are permitted.
 *  <p>
 *  This implementation uses an <em>adjacency-lists representation</em>, which
 *  is a vertex-indexed array of {@link Bag} objects.
 *  It uses &Theta;(<em>E</em> + <em>V</em>) space, where <em>E</em> is
 *  the number of edges and <em>V</em> is the number of vertices.
 *  All instance methods take &Theta;(1) time. (Though, iterating over
 *  the vertices returned by {@link #adj(int)} takes time proportional
 *  to the outdegree of the vertex.)
 *  Constructing an empty digraph with <em>V</em> vertices takes
 *  &Theta;(<em>V</em>) time; constructing a digraph with <em>E</em> edges
 *  and <em>V</em> vertices takes &Theta;(<em>E</em> + <em>V</em>) time.
 *  <p>
 *  For additional documentation,
 *  see <a href="https://algs4.cs.princeton.edu/42digraph">Section 4.2</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */

public class Digraph {    
    private HashMap<String, City> vertices; // set of city vertices referenced by string
    private final int V;           // number of vertices in this digraph
    private int E;                 // number of edges in this digraph
    private HashMap<City, ArrayList<City>> adjList;    // adj.get(v) = adjacency list for vertex v
    

    /**  
     * Initializes a digraph from the specified input stream.
     * The format is the number of vertices V,
     * followed by the number of edges E,
     * followed by E pairs of vertices, with each entry separated by whitespace.
     *
     * @param cityNodes set of vertices
     * @throws IllegalArgumentException if cityNodes is null
     * @throws IllegalArgumentException if the endpoints of any edge are not in prescribed range
     * @throws IllegalArgumentException if the number of vertices or edges is negative
     * @throws IllegalArgumentException if the input stream is in the wrong format
     */
    public Digraph(HashMap<String, City> cityNodes) {
        if (cityNodes == null) throw new IllegalArgumentException("argument is null");
        try {
        	this.vertices = cityNodes;
            this.V = ((HashMap<String,City>) cityNodes).size();
            if (V < 0) throw new IllegalArgumentException("number of vertices in a Digraph must be nonnegative");
            this.adjList = new HashMap<City, ArrayList<City>>();
            for (String v : vertices.keySet()) {
                this.adjList.put(vertices.get(v), new ArrayList<City>());
            }
            this.E = 0;
        }
        catch (NoSuchElementException e) {
            throw new IllegalArgumentException("invalid input format in Digraph constructor", e);
        }
    }
    
    /**
     * Returns the vertices in this digraph.
     * @return the vertices in this digraph
     */
    public HashMap<String, City> vertices() {
        return vertices;
    }
        
    /**
     * Returns the number of vertices in this digraph.
     *
     * @return the number of vertices in this digraph
     */
    public int V() {
        return V;
    }

    /**
     * Returns the number of edges in this digraph.
     *
     * @return the number of edges in this digraph
     */
    public int E() {
        return E;
    }


    /**
     * Check if vertex is in this digraph.
     *
     * @param  v the vertex
     * @throws IllegalArgumentException if v doesn't exist in the collection of vertices
     */
    private void validateVertex(City v) {
        if (!vertices.containsKey(v.getName()))
        	throw new IllegalArgumentException("vertex " + v.getName() + "is not in the graph");
    }

    /**
     * Adds the directed edge v->w to this digraph.
     *
     * @param  v the tail vertex
     * @param  w the head vertex
     */
    public void addEdge(City v, City w) {
        validateVertex(v);
        validateVertex(w);
        ArrayList<City> newList = this.adjList.get(v);
        newList.add(w);
        this.adjList.put(v, newList);
        E++;
    }

	/**
     * Returns the vertices adjacent from vertex {@code v} in this digraph.
     *
     * @param  v the vertex
     * @return the vertices adjacent from vertex {@code v} in this digraph, as an iterable
     */
    public ArrayList<City> adj(City v) {
        validateVertex(v);
        return this.adjList.get(v);
    }
}
