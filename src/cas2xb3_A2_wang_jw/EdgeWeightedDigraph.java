package cas2xb3_A2_wang_jw;


import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *  The {@code EdgeWeightedDigraph} class represents a edge-weighted
 *  digraph of vertices named 0 through <em>V</em> - 1, where each
 *  directed edge is of type {@link DirectedEdge} and has a real-valued weight.
 *  It supports the following two primary operations: add a directed edge
 *  to the digraph and iterate over all of edges incident from a given vertex.
 *  It also provides methods for returning the indegree or outdegree of a
 *  vertex, the number of vertices <em>V</em> in the digraph, and
 *  the number of edges <em>E</em> in the digraph.
 *  Parallel edges and self-loops are permitted.
 *  <p>
 *  This implementation uses an <em>adjacency-lists representation</em>, which
 *  is a vertex-indexed array of {@link Bag} objects.
 *  It uses &Theta;(<em>E</em> + <em>V</em>) space, where <em>E</em> is
 *  the number of edges and <em>V</em> is the number of vertices.
 *  All instance methods take &Theta;(1) time. (Though, iterating over
 *  the edges returned by {@link #adj(int)} takes time proportional
 *  to the outdegree of the vertex.)
 *  Constructing an empty edge-weighted digraph with <em>V</em> vertices
 *  takes &Theta;(<em>V</em>) time; constructing an edge-weighted digraph
 *  with <em>E</em> edges and <em>V</em> vertices takes
 *  &Theta;(<em>E</em> + <em>V</em>) time. 
 *  <p>
 *  For additional documentation,
 *  see <a href="https://algs4.cs.princeton.edu/44sp">Section 4.4</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class EdgeWeightedDigraph {
	
    private HashMap<String, Stop> vertices; // collection of vertices referenced by string
    private final int V;                // number of vertices in this digraph
    private int E;                      // number of edges in this digraph
    private HashMap<Stop, ArrayList<DirectedEdge>> adjList;    // adj[v] = adjacency list for vertex v

    /**  
     * Initializes an edge-weighted digraph from the specified input stream.
     * The format is the number of vertices <em>V</em>,
     * followed by the number of edges <em>E</em>,
     * followed by <em>E</em> pairs of vertices and edge weights,
     * with each entry separated by whitespace.
     *
     * @param  in the input stream
     * @throws IllegalArgumentException if {@code in} is {@code null}
     * @throws IllegalArgumentException if the endpoints of any edge are not in prescribed range
     * @throws IllegalArgumentException if the number of vertices or edges is negative
     */
    public EdgeWeightedDigraph(HashMap<String, Stop> cityNodes) {
        if (cityNodes == null) throw new IllegalArgumentException("argument is null");
        try {
        	this.vertices = cityNodes;
            this.V = cityNodes.size();
            if (V < 0) throw new IllegalArgumentException("number of vertices in a Digraph must be nonnegative");
            this.adjList = new HashMap<Stop, ArrayList<DirectedEdge>>();
            for (String v : vertices.keySet()) {
                this.adjList.put(vertices.get(v), new ArrayList<DirectedEdge>());
            }
            this.E = 0;
        }   
        catch (NoSuchElementException e) {
            throw new IllegalArgumentException("invalid input format in EdgeWeightedDigraph constructor", e);
        }
    }

    /**
     * Returns the number of vertices in this edge-weighted digraph.
     *
     * @return the number of vertices in this edge-weighted digraph
     */
    public int V() {
        return V;
    }

    /**
     * Returns the number of edges in this edge-weighted digraph.
     *
     * @return the number of edges in this edge-weighted digraph
     */
    public int E() {
        return E;
    }
    
    /**
     * Returns the vertices in this digraph.
     *
     * @return the vertices in this digraph
     */
    public HashMap<String, Stop> vertices() {
        return vertices;
    }

    /**
     * Check if vertex is in this digraph.
     *
     * @param  v the vertex
     * @throws IllegalArgumentException unless both {@code 0 <= v < V} and {@code 0 <= w < V}
     */
    private void validateVertex(Stop v) {
        if (!vertices.containsKey(v.getCity().getName()))
        	throw new IllegalArgumentException("vertex " + v.getCity().getName() + "is not in the graph");
    }

    /**
     * Adds the directed edge {@code e} to this edge-weighted digraph.
     *
     * @param  e the edge
     * @throws IllegalArgumentException unless endpoints of edge are between {@code 0}
     *         and {@code V-1}
     */
    public void addEdge(DirectedEdge e) {
        Stop v = e.from();
        Stop w = e.to();
        validateVertex(v);
        validateVertex(w);
        ArrayList<DirectedEdge> newList = this.adjList.get(v); 
        newList.add(e);
        this.adjList.put(v, newList);
        E++;
    }


    /**
     * Returns the directed edges incident from vertex {@code v}.
     *
     * @param  v the vertex
     * @return the directed edges incident from vertex {@code v} as an Iterable
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public Iterable<DirectedEdge> adj(Stop v) {
        validateVertex(v);
        return adjList.get(v);
    }

    /**
     * Returns all directed edges in this edge-weighted digraph.
     * To iterate over the edges in this edge-weighted digraph, use foreach notation:
     * {@code for (DirectedEdge e : G.edges())}.
     *
     * @return all edges in this edge-weighted digraph, as an iterable
     */
    public ArrayList<DirectedEdge> edges() {
        ArrayList<DirectedEdge> list = new ArrayList<DirectedEdge>();
        for (String v : vertices.keySet()) {
            for (DirectedEdge e : adjList.get(vertices.get(v))) {
                list.add(e);
            }
        }
        return list;
    } 

}
