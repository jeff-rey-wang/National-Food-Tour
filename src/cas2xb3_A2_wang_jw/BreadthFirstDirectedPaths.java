package cas2xb3_A2_wang_jw;

import java.util.HashMap;

/**
 *  The {@code BreadthDirectedFirstPaths} class represents a data type for
 *  finding shortest paths (number of edges) from a source vertex <em>s</em>
 *  (or set of source vertices) to every other vertex in the digraph.
 *  <p>
 *  This implementation uses breadth-first search.
 *  The constructor takes &Theta;(<em>V</em> + <em>E</em>) time in the
 *  worst case, where <em>V</em> is the number of vertices and <em>E</em> is
 *  the number of edges.
 *  Each instance method takes &Theta;(1) time.
 *  It uses &Theta;(<em>V</em>) extra space (not including the digraph).
 *  <p>
 *  For additional documentation, 
 *  see <a href="https://algs4.cs.princeton.edu/42digraph">Section 4.2</a> of 
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class BreadthFirstDirectedPaths {
    
	private static final int INFINITY = Integer.MAX_VALUE;
    
    private HashMap<String, City> vertices;  // vertices.get(v) => reference city objects via city name
    private HashMap<City, Boolean> marked;  // marked.get(v) = is there an s->v path?
    private HashMap<City, City> edgeTo;      // edgeTo.get(v) = last edge on shortest s->v path
    private HashMap<City, Integer> distTo;      // distTo.get(v) = length of shortest s->v path

    /**
     * Computes the shortest path from {@code s} and every other vertex in graph {@code G}.
     * @param G the digraph
     * @param s the source vertex name
     */
    public BreadthFirstDirectedPaths(Digraph G, String s) {
    	// initialize fields
    	vertices = G.vertices();
        distTo = new HashMap<City, Integer>();
        marked = new HashMap<City, Boolean>();
        edgeTo = new HashMap<City, City>();
        for (String v : vertices.keySet()) {
        	marked.put(vertices.get(v), false);
        	edgeTo.put(vertices.get(v), null);
        	distTo.put(vertices.get(v), INFINITY);
        }
        
        validateVertex(vertices.get(s));
        bfs(G, vertices.get(s));
    }

    /**
     * BFS from a single source
     * @param G digraph being searched
     * @param s source vertex
     */
    private void bfs(Digraph G, City s) {
        Queue<City> q = new Queue<City>();
        marked.put(s, true);
        distTo.put(s, 0);
        q.enqueue(s);
        while (!q.isEmpty()) {
            City v = q.dequeue();
            for (City w : G.adj(v)) {
                if (!marked.get(w)) {
                    edgeTo.put(w, v);
                    distTo.put(w, distTo.get(v) + 1);
                    marked.put(w, true);
                    q.enqueue(w);
                }
            }
        }
    }

    /**
     * Is there a directed path from the source to vertex v?
     * @param v the vertex
     * @return true if there is a directed path, false otherwise
     */
    public boolean hasPathTo(City v) {
        validateVertex(v);
        return marked.get(v);
    }

    /**
     * Is there a directed path from the source to vertex v?
     * @param v the vertex name
     * @return true if there is a directed path, false otherwise
     */
    public boolean hasPathTo(String v) {
        validateVertex(vertices.get(v));
        return marked.get(vertices.get(v));
    }
    /**
     * Returns the number of edges in a shortest path from the source to vertex
     * @param v the vertex
     * @return the number of edges in a shortest path
     */
    public int distTo(City v) {
        validateVertex(v);
        return distTo.get(v);
    }

    /**
     * Returns a shortest path from s to v, or null if no such path.
     * @param v the vertex
     * @return the sequence of vertices on a shortest path, as an Iterable
     */
    public Iterable<String> pathTo(City v) {
        validateVertex(v);

        if (!hasPathTo(v)) return null;
        Stack<String> path = new Stack<String>();
        City x;
        for (x = v; distTo.get(x) != 0; x = edgeTo.get(x))
            path.push(x.getName());
        path.push(x.getName());
        return path;
    }
    
    /**
     * Returns a shortest path from s to v, or null if no such path.
     * @param v the vertex name
     * @return the sequence of vertices on a shortest path, as an Iterable
     */
    public Iterable<String> pathTo(String v) {
        validateVertex(vertices.get(v));

        if (!hasPathTo(vertices.get(v))) return null;
        Stack<String> path = new Stack<String>();
        City x;
        for (x = vertices.get(v); distTo.get(x) != 0; x = edgeTo.get(x))
            path.push(x.getName());
        path.push(x.getName());
        return path;
    }
    
    /**
     * Returns a shortest path from s to v, or null if no such path.
     * @param v the vertex
     * @return the sequence of vertices on a shortest path, as an String
     */
    public String pathToString(String v) {
    	String output = "BFS: ";
        for (String city : pathTo(v)) {
        	output = output.concat(city + ", ");
        }
        return output.substring(0, output.length()-2);
    }

    /**
     * Check if a city is a vertex in the graph
     * @param v city being checked
     * @throws IllegalArgumentException if vertex is not part of the set of cities
     */
    private void validateVertex(City v) {
        if (!vertices.containsKey(v.getName()))
        	throw new IllegalArgumentException("vertex " + v.getName() + "is not in the graph");
    }

}