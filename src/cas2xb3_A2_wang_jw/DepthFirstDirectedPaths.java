package cas2xb3_A2_wang_jw;

import java.util.HashMap;

/**
 *  The {@code DepthFirstDirectedPaths} class represents a data type for
 *  finding directed paths from a source vertex <em>s</em> to every
 *  other vertex in the digraph.
 *  <p>
 *  This implementation uses depth-first search.
 *  The constructor takes &Theta;(<em>V</em> + <em>E</em>) time in the
 *  worst case, where <em>V</em> is the number of vertices and <em>E</em>
 *  is the number of edges.
 *  Each instance method takes &Theta;(1) time.
 *  It uses &Theta;(<em>V</em>) extra space (not including the digraph).
 *  <p>
 *  See {@link DepthFirstDirectedPaths} for a nonrecursive implementation.
 *  For additional documentation,  
 *  see <a href="https://algs4.cs.princeton.edu/42digraph">Section 4.2</a> of  
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne. 
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class DepthFirstDirectedPaths {
	
    private HashMap<String, City> vertices; // vertices.get(v) => reference city objects via city name
    private HashMap<City, Boolean> marked;  // marked.get(v) = true iff v is reachable from s
    private HashMap<City, City> edgeTo;      // edgeTo.get(v) = last city on path from s to v
    private City s;       // source vertex

    /**
     * Computes a directed path from {@code s} to every other vertex in digraph {@code G}.
     * @param  G the digraph
     * @param  s the source vertex name
     */
    public DepthFirstDirectedPaths(Digraph G, String s) {
    	// initialize fields
        vertices = G.vertices();
        marked = new HashMap<City, Boolean>();
        edgeTo = new HashMap<City, City>();
        for (String v : vertices.keySet()) {
        	marked.put(vertices.get(v), false);
        	edgeTo.put(vertices.get(v), null);
        }
        this.s = vertices.get(s);
        validateVertex(this.s);
        dfs(G, this.s);
    }

    /**
     * DFS from vertex v on all unmarked edges
     * @param G the digraph
     * @param v vertex being traversed
     */
    private void dfs(Digraph G, City v) { 
        marked.put(v, true);
        for (City w : G.adj(v)) {
            if (!marked.get(w)) {
                edgeTo.put(w, v);
                dfs(G, w);
            }
        }
    }

    /**
     * Is there a directed path from the source vertex to vertex v?
     * @param  v the vertex
     * @return true if there is a directed path from the source
     *         vertex s to vertex v, false otherwise
     */
    public boolean hasPathTo(City v) {
        validateVertex(v);
        return marked.get(v);
    }
    
    /**
     * Is there a directed path from the source vertex to vertex v?
     * @param  v the vertex name
     * @return true if there is a directed path from the source
     *         vertex s to vertex v, false otherwise
     */
    public boolean hasPathTo(String v) {
        validateVertex(vertices.get(v));
        return marked.get(vertices.get(v));
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
        for (City x = v; x.getName() != s.getName(); x = edgeTo.get(x))
            path.push(x.getName());
        path.push(s.getName());
        return path;
    }
    
    /**
     * Returns a shortest path from s to v, or null if no such path.
     * @param v the vertex
     * @return the sequence of vertices on a shortest path, as an Iterable
     */
    public Iterable<String> pathTo(String v) {
        validateVertex(vertices.get(v));
        if (!hasPathTo(v)) return null;
        Stack<String> path = new Stack<String>();
        for (City x = vertices.get(v); x.getName() != s.getName(); x = edgeTo.get(x))
            path.push(x.getName());
        path.push(s.getName());
        return path;
    }
    
    /**
     * Returns a shortest path from s to v, or null if no such path.
     * @param v the vertex
     * @return the sequence of vertices on a shortest path, as an String
     */
    public String pathToString(String v) {
    	String output = "DFS: ";
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
