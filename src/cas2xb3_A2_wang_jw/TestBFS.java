/*		Student Information
 * 		-------------------
 * 		Student Name: Wang, Jeffrey
 * 		Course Code: SE 2XB3
 * 		Lab Section: L02
 * 		
 * 		I attest that the following code being
 * 		submitted is my own individual work.
 */
package cas2xb3_A2_wang_jw;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Class to test BFS
 * @author Jeffrey Wang
 *
 */
public class TestBFS {

	ArrayList<String[]> cityList;
	ArrayList<String[]> pathList;
	HashMap<String, City> cities;
	ArrayList<City[]> paths;
	Digraph map;
	BreadthFirstDirectedPaths bfs;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		cityList = FileReader.parseCity(FileReader.parseLines("data/USCities.csv", true));
		pathList = FileReader.parseConnect(FileReader.parseLines("data/connectedCities.txt", false));
		cities = new HashMap<String, City>();
		for (int i = 0; i < cityList.size(); i++) {
			City cityObj = new City(cityList.get(i)[0], Double.parseDouble(cityList.get(i)[1]), Double.parseDouble(cityList.get(i)[2]));
			cities.put(cityList.get(i)[0], cityObj);
		}

		// Create a set of city to city edge
		paths = new ArrayList<City[]>();
		for (int i = 0; i < pathList.size(); i++) {
			City[] pathPair = {null, null};
			pathPair[0] = cities.get(pathList.get(i)[0]);
			pathPair[1] = cities.get(pathList.get(i)[1]);
			paths.add(pathPair);
		}
		
		// Create non-weight digraph, add edges
		map = new Digraph(cities);
		for (int i = 0; i < paths.size(); i++) {
			map.addEdge(paths.get(i)[0], paths.get(i)[1]);

		}
		bfs = new BreadthFirstDirectedPaths(map, "BOSTON");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		cityList = null;
		pathList = null;
		cities = null;
		paths = null;
		map = null;
		bfs = null;
		
	}

	/**
	 * Test method for {@link cas2xb3_A2_wang_jw.BreadthFirstDirectedPaths#hasPathTo(java.lang.String)}.
	 */
	@Test
	public void testHasPathToString() {
		for (String v : cities.keySet()) {
			assert(bfs.hasPathTo(v));
		}
	}
	
	/**
	 * Test method for {@link cas2xb3_A2_wang_jw.DepthFirstDirectedPaths#hasPathTo(java.lang.String)}.
	 */
	@Test (expected = NullPointerException.class)
	public void testHasPathToStringFail() {
		bfs.hasPathTo("EL PASO");
	}

	/**
	 * Test method for {@link cas2xb3_A2_wang_jw.BreadthFirstDirectedPaths#pathToString(java.lang.String)}.
	 */
	@Test
	public void testPathToString() {
		assert(bfs.pathToString("MINNEAPOLIS").contentEquals("BFS: BOSTON, NEW YORK CITY, PITTSBURGH, COLUMBUS, CHICAGO, MINNEAPOLIS"));
	}
	/**
	 * Test method for {@link cas2xb3_A2_wang_jw.BreadthFirstDirectedPaths#pathToString(java.lang.String)}.
	 */
	@Test
	public void testPathToString2() {
		assert(bfs.pathToString("HOUSTON").contentEquals("BFS: BOSTON, NEW YORK CITY, PITTSBURGH, COLUMBUS, INDIANAPOLIS, ST. LOUIS, KANSAS CITY, OKLAHOMA CITY, DALLAS, HOUSTON"));
	}
	/**
	 * Test method for {@link cas2xb3_A2_wang_jw.BreadthFirstDirectedPaths#pathToString(java.lang.String)}.
	 */
	@Test
	public void testPathToString3() {
		assert(bfs.pathToString("LOS ANGELES").contentEquals("BFS: BOSTON, NEW YORK CITY, PITTSBURGH, COLUMBUS, INDIANAPOLIS, ST. LOUIS, KANSAS CITY, DENVER, SALT LAKE CITY, LAS VEGAS, LOS ANGELES"));
	}
	
	/**
	 * Test method for {@link cas2xb3_A2_wang_jw.BreadthFirstDirectedPaths#pathToString(java.lang.String)}.
	 */
	@Test (expected = NullPointerException.class)
	public void testPathToStringFail() {
		bfs.pathToString("EL PASO");
	}

}
