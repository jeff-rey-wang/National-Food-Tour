package cas2xb3_A2_wang_jw;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *  The {@code DijkstraSP} class represents a data type for solving the
 *  single-source shortest paths problem in edge-weighted digraphs
 *  where the edge weights are nonnegative.
 *  <p>
 *  This implementation uses <em>Dijkstra's algorithm</em> with a
 *  <em>binary heap</em>. The constructor takes
 *  &Theta;(<em>E</em> log <em>V</em>) time in the worst case,
 *  where <em>V</em> is the number of vertices and <em>E</em> is
 *  the number of edges. Each instance method takes &Theta;(1) time.
 *  It uses &Theta;(<em>V</em>) extra space (not including the
 *  edge-weighted digraph).
 *  <p>
 *  For additional documentation,    
 *  see <a href="https://algs4.cs.princeton.edu/44sp">Section 4.4</a> of    
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne. 
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class DijkstraSP {
	private HashMap<String, Stop> vertices; // collection of vertices, referenced by city name 
	private HashMap<Integer, Stop> verticesIndex;  // collection of vertices, referenced by their index
    private HashMap<Stop, Double> distTo;          // distTo.get(v) = distance  of shortest s->v path
    private HashMap<Stop, DirectedEdge> edgeTo;    // edgeTo.get(v) = last edge on shortest s->v path
    private IndexMinPQ<Double> pq;    // priority queue of vertices

    /**
     * Computes a shortest-paths tree from the source vertex s to every other
     * vertex in the edge-weighted digraph G.
     *
     * @param  G the edge-weighted digraph
     * @param  s the source vertex name
     * @throws IllegalArgumentException if an edge weight is negative
     */
    public DijkstraSP(EdgeWeightedDigraph G, String s) {
        for (DirectedEdge e : G.edges()) {
            if (e.weight() < 0)
                throw new IllegalArgumentException("edge " + e + " has negative weight");
        }
        // initialize fields
        vertices = G.vertices();  
        distTo = new HashMap<Stop, Double>();
        edgeTo = new HashMap<Stop, DirectedEdge>();
        for (String v : vertices.keySet()) {
        	distTo.put(vertices.get(v), Double.POSITIVE_INFINITY);
        	edgeTo.put(vertices.get(v), null);
        }
        validateVertex(vertices.get(s));
        distTo.put(vertices.get(s), 0.0);
        // set nodes adjacent to source to a weight of 0 since no meal is purchased in source stop
        for (DirectedEdge e: G.adj(vertices.get(s))) {
        	e.setWeight(0.0);
        }
        vertices.get(s).noFood();

        this.verticesIndex = new HashMap<Integer, Stop>();
        for (String v : vertices.keySet()) {
        	verticesIndex.put(vertices.get(v).getIndex(), vertices.get(v));
        }
        
        // relax vertices in order of distance from s
        pq = new IndexMinPQ<Double>(G.V());
        pq.insert(vertices.get(s).getIndex(), distTo.get(vertices.get(s)));
        while (!pq.isEmpty()) {
            Stop v = verticesIndex.get(pq.delMin());
            for (DirectedEdge e : G.adj(v))
                relax(e);
        }

        // check optimality conditions
        assert check(G, vertices.get(s));
    }

    /**
     * relax edge e and update pq if changed
     * @param e edge being relaxed
     */
    private void relax(DirectedEdge e) {
        Stop v = e.from(), w = e.to();
        if (distTo.get(w) > distTo.get(v) + w.pickFood(v).getPrice()) {
            distTo.put(w, distTo.get(v) + w.pickFood(v).getPrice());
            e.setWeight(w.pickFood(v).getPrice());
            edgeTo.put(w, e);
            if (pq.contains(w.getIndex())) pq.decreaseKey(w.getIndex(), distTo.get(w));
            else                pq.insert(w.getIndex(), distTo.get(w));
        }
    }

    /**
     * Returns the length of a shortest path from the source vertex {@code s} to vertex {@code v}.
     * @param  v the destination vertex
     * @return the length of a shortest path from the source vertex {@code s} to vertex {@code v};
     *         {@code Double.POSITIVE_INFINITY} if no such path
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public double distTo(Stop v) {
        validateVertex(v);
        return distTo.get(v);
    }

    /**
     * Returns true if there is a path from the source vertex {@code s} to vertex {@code v}.
     *
     * @param  v the destination vertex
     * @return {@code true} if there is a path from the source vertex
     *         {@code s} to vertex {@code v}; {@code false} otherwise
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public boolean hasPathTo(Stop v) {
        validateVertex(v);
        return distTo.get(v) < Double.POSITIVE_INFINITY;
    }

    /**
     * Returns a shortest path from the source vertex {@code s} to vertex {@code v}.
     *
     * @param  v the destination vertex
     * @return a shortest path from the source vertex {@code s} to vertex {@code v}
     *         as an iterable of edges, and {@code null} if no such path
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public Iterable<String> pathTo(Stop v) {
        validateVertex(v);
        if (!hasPathTo(v)) return null;
        Stack<String> path = new Stack<String>();
        DirectedEdge initEdge = null;
        //path.push(v.getCity().getName() + ": " + v.getFood().getItem() + ", $" + v.getFood().getPrice() + "\n");
        for (DirectedEdge e = edgeTo.get(v); e != null; e = edgeTo.get(e.from())) {
        	initEdge = e;
        	e.to().updateFood(e);
            path.push(String.format("%-20s %-35s $%.2f \n", e.to().getCity().getName(), e.to().getFood().getItem(), e.to().getFood().getPrice()));
        }
        path.push(String.format("%-20s %-35s $%.2f\n", initEdge.from().getCity().getName(), initEdge.from().getFood().getItem(), initEdge.from().getFood().getPrice()));
        path.push(String.format("%-20s %-35s %s\n", "CITY", "ITEM", "PRICE ($)"));
        return path;
    }


    // check optimality conditions:
    // (i) for all edges e:            distTo[e.to()] <= distTo[e.from()] + e.weight()
    // (ii) for all edge e on the SPT: distTo[e.to()] == distTo[e.from()] + e.weight()
    private boolean check(EdgeWeightedDigraph G, Stop s) {

        // check that edge weights are nonnegative
        for (DirectedEdge e : G.edges()) {
            if (e.weight() < 0) {
                System.err.println("negative edge weight detected");
                return false;
            }
        }

        // check that distTo[v] and edgeTo[v] are consistent
        if (distTo.get(s) != 0.0 || edgeTo.get(s) != null) {
            System.err.println("distTo[s] and edgeTo[s] inconsistent");
            return false;
        }
        for (String v : vertices.keySet()) {
            if (vertices.get(v) == s) continue;
            if (edgeTo.get(v) == null && distTo.get(v) != Double.POSITIVE_INFINITY) {
                System.err.println("distTo[] and edgeTo[] inconsistent");
                return false;
            }
        }

        // check that all edges e = v->w satisfy distTo[w] <= distTo[v] + e.weight()
        for (String v : vertices.keySet()) {
            for (DirectedEdge e : G.adj(vertices.get(v))) {
                Stop w = e.to();
                if (distTo.get(v) + e.weight() < distTo.get(w)) {
                    System.err.println("edge " + e + " not relaxed");
                    return false;
                }
            }
        }

        // check that all edges e = v->w on SPT satisfy distTo[w] == distTo[v] + e.weight()
        for (Stop w :edgeTo.keySet()) {
            if (edgeTo.get(w) == null) continue;
            DirectedEdge e = edgeTo.get(w);
            Stop v = e.from();
            if (w != e.to()) return false;
            if (distTo.get(v) + e.weight() != distTo.get(w)) {
                System.err.println("edge " + e + " on shortest path not tight");
                return false;
            }
        }
        return true;
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
     * Returns a shortest path from s to v, or null if no such path.
     * @param v the vertex
     * @return the sequence of vertices on a shortest path, as an String
     */
	public String pathToString(String v) {
    	String output = "";
        for (String stop : pathTo(vertices.get(v))) {
        	output = output.concat(stop);
        }
        return output.substring(0, output.length());
    }

}
