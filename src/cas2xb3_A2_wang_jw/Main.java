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

import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

/**
 * Main file
 * @author Jeffrey Wang
 *
 */
public class Main {

	public static void main(String args[]) throws IOException {
		
		// Maintain lists of data
		ArrayList<String[]> cityList = FileReader.parseCity(FileReader.parseLines("data/USCities.csv", true));
		ArrayList<String[]> pathList = FileReader.parseConnect(FileReader.parseLines("data/connectedCities.txt", false));
		ArrayList<String[]> mcds = FileReader.parseRestaurant(FileReader.parseLines("data/mcdonalds.csv", true));
		ArrayList<String[]> bks = FileReader.parseRestaurant(FileReader.parseLines("data/burgerking.csv", true));
		ArrayList<String[]> wendys = FileReader.parseRestaurant(FileReader.parseLines("data/wendys.csv", true));
		ArrayList<String[]> menu = FileReader.parseMenu(FileReader.parseLines("data/menu.csv", true));
		ArrayList<ArrayList<Meal>> allMeals = new ArrayList<ArrayList<Meal>>();
		ArrayList<ArrayList<Restaurant>> allRestaurants = new ArrayList<ArrayList<Restaurant>>();
		
		// Split meals by restaurant of origin
		for (int i = 0; i < 3; i++) {
			allMeals.add(new ArrayList<Meal>());
		}
		int j = 0;
		while (menu.get(j)[0].startsWith("M")) {
			allMeals.get(0).add( new Meal(menu.get(j)[1], Double.parseDouble(menu.get(j)[2].substring(1))));
			j++;
		}
		while (menu.get(j)[0].startsWith("B")) {
			allMeals.get(1).add( new Meal(menu.get(j)[1], Double.parseDouble(menu.get(j)[2].substring(1))));
			j++;
		}
		while (j < menu.size() && menu.get(j)[0].startsWith("W")) {
			allMeals.get(2).add( new Meal(menu.get(j)[1], Double.parseDouble(menu.get(j)[2].substring(1))));
			j++;
		}
		
		// Split restuarants by name
		for (int i = 0; i < 3; i++) {
			allRestaurants.add(new ArrayList<Restaurant>());
		}
		for (int i = 0; i < mcds.size(); i++) {
			Restaurant location = new Restaurant("McDonald's", Double.parseDouble(mcds.get(i)[1]), Double.parseDouble(mcds.get(i)[0]), allMeals);
			allRestaurants.get(0).add(location);
		}
		for (int i = 0; i < bks.size(); i++) {
			Restaurant location = new Restaurant("Burger King", Double.parseDouble(bks.get(i)[1]), Double.parseDouble(bks.get(i)[0]), allMeals);
			allRestaurants.get(1).add(location);
		}
		for (int i = 0; i < wendys.size(); i++) {
			Restaurant location = new Restaurant("Wendy's", Double.parseDouble(wendys.get(i)[1]), Double.parseDouble(wendys.get(i)[0]), allMeals);
			allRestaurants.get(2).add(location);
		}

		//  Create set of city objects referenced by name 
		HashMap<String, City> cities = new HashMap<String, City>();
		for (int i = 0; i < cityList.size(); i++) {
			City cityObj = new City(cityList.get(i)[0], Double.parseDouble(cityList.get(i)[1]), Double.parseDouble(cityList.get(i)[2]));
			cities.put(cityList.get(i)[0], cityObj);
		}

		// Create a set of city to city edge
		ArrayList<City[]> paths = new ArrayList<City[]>();
		for (int i = 0; i < pathList.size(); i++) {
			City[] pathPair = {null, null};
			pathPair[0] = cities.get(pathList.get(i)[0]);
			pathPair[1] = cities.get(pathList.get(i)[1]);
			paths.add(pathPair);
		}
		
		// Create non-weight digraph, add edges
		Digraph nonWeightMap = new Digraph(cities);
		for (int i = 0; i < paths.size(); i++) {
			nonWeightMap.addEdge(paths.get(i)[0], paths.get(i)[1]);

		}
		
		// Compute path via BFS and DFS
		BreadthFirstDirectedPaths bfs = new BreadthFirstDirectedPaths(nonWeightMap, "BOSTON");
		DepthFirstDirectedPaths dfs = new DepthFirstDirectedPaths(nonWeightMap, "BOSTON");
		
		// Create a set of stops referenced by city name
		HashMap<String, Stop> stops = new HashMap<String, Stop>();
		int index = 0;
		for (String v : cities.keySet()) {
			Stop stopObj = new Stop(cities.get(v), allRestaurants, index);
			stops.put(v, stopObj);
			index++;
		}
		
		// Create a set of stop to stop edges
		ArrayList<Stop[]> stopPaths = new ArrayList<Stop[]>();
		for (int i = 0; i < pathList.size(); i++) {
			Stop[] stopsPair = {null, null};
			stopsPair[0] = stops.get(pathList.get(i)[0]);
			stopsPair[1] = stops.get(pathList.get(i)[1]);
			stopPaths.add(stopsPair);
		}
		
		// Create weighted digraph, add edges
		EdgeWeightedDigraph WeightMap = new EdgeWeightedDigraph(stops);
		for (int i = 0; i < paths.size(); i++) {
			WeightMap.addEdge(new DirectedEdge(stopPaths.get(i)[0], stopPaths.get(i)[1]));

		}
		
		// Compute cheapest path via dijkstra's algorithm
		DijkstraSP sp = new DijkstraSP(WeightMap, "BOSTON");

		// Write outputs to file
		FileWriter output  = new FileWriter("data/a2_out.txt");
		output.write(bfs.pathToString("MINNEAPOLIS"));
		output.write("\n");
		output.write(dfs.pathToString("MINNEAPOLIS"));
		output.write("\n");
		output.write(sp.pathToString("MINNEAPOLIS"));
		output.close();
		}

}
