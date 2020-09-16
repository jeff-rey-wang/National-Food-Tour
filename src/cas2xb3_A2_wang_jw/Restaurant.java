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

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class to represent a restaurant location
 * @author Jeffrey Wang
 *
 */
public class Restaurant {
	
	private String name;
	private double[] coord = new double[2];
	private ArrayList<Meal> menu; 
	
	/**
	 * Initialize restaurant object
	 * @param name name of restaurant
	 * @param lat restaurant latitude
	 * @param lon retaurant longitude
	 * @param menu 3 lists of items corresponding to each restaurant
	 */
	public Restaurant(String name, double lat, double lon, ArrayList<ArrayList<Meal>> menu) {
		this.name = name;
		this.coord[0] = lat;
		this.coord[1] = lon;
		// menu.get(0) => mcdonalds menu
		if (this.name.startsWith("M")) {
			this.name = "McDonald's";
			this.menu = menu.get(0);
		} // menu.get(1) => burger king menu
		else if (this.name.startsWith("B")) {
			this.menu = menu.get(1);
		} // menu.get(2) => wendys menu
		else {
			this.name = "Wendy's";
			this.menu = menu.get(2);
		}	
	}
	/**
	 * Retrieve name of restaurant
	 * @return restaurant name
	 */
	public String getName() {
		return this.name;
	}
	/**
	 * Retrieve latitude of restaurant
	 * @return restaurant latitude
	 */
	public double getLat() {
		return this.coord[0];
	}
	/**
	 * Retrieve longitude of restaurant
	 * @return restaurant longitude
	 */
	public double getLon() {
		return this.coord[1];
	}
	/**
	 * Retrieve menu of restaurant
	 * @return list of restaurant's items
	 */
	public ArrayList<Meal> getMenu() {
		return this.menu;
	}
	

}
