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

/**
 * Class to represent a stop on roadtrip
 * @author Jeffrey Wang
 *
 */
public class Stop {
	
	private final City city;
	private Restaurant store;
	private Meal food;
	private final int index;
	
	/**
	 * Initialize stop object
	 * @param city city name of stop
	 * @param restaurants restaurant chosen for stop
	 * @param index number corresponding to this stop
	 */
	public Stop(City city, ArrayList<ArrayList<Restaurant>> restaurants, int index) {
		this.index = index;
		this.city = city;
		this.store = null;
		// Pick a viable restaurant from a set of nearby restaurants
		// McDonalds > Burger King > Wendys
		for (ArrayList<Restaurant> franchise : restaurants) {
			for (Restaurant location : franchise) {
				if (location.getLat() - city.getLat() <=  0.5 || location.getLat() - city.getLat() >=  -0.5) {
					this.store = location;
					break;
				}
			}
			if (store != null) {
				break;
			}
		}
		this.food = null;
	}
	
	/**
	 * Retrieve city of stop
	 * @return the city
	 */
	public City getCity() {
		return this.city;
	}
	/**
	 * Retrieve index of stop
	 * @return the index
	 */
	public int getIndex() {
		return this.index;
	}
	/**
	 * Retrieve restaurant of stop
	 * @return the chosen restaurant
	 */
	public Restaurant getStore() {
		return this.store;
	}
	/**
	 * Retrieve meal of stop
	 * @return the meal
	 */
	public Meal getFood() {
		return this.food;
	}
	/**
	 * Pick the meal of current stop with respect to last stop's meal
	 * @param lastStop the previous stop
	 * @return the chosen meal object
	 */
	public Meal pickFood(Stop lastStop) {
		Meal cheapest = store.getMenu().get(0);
		for (Meal item : store.getMenu()) {
			if(lastStop.getFood() != item && item.getPrice() < cheapest.getPrice()) {
				cheapest = item;
			}
		}
		this.food = cheapest;
		return this.food;
	}
	/**
	 * Set a stop to have no meal
	 */
	public void noFood() {
		this.food = new Meal("No Meal", 0.0);
	}
	/**
	 * Update a food option with respect to a stop's edge
	 * @param e corresponding edge
	 */
	public void updateFood(DirectedEdge e) {
		for (int i = 0; i < store.getMenu().size(); i++) {
			if(e.weight() == store.getMenu().get(i).getPrice()) {
				this.food = store.getMenu().get(i);
			}

		}
	}
	
	

}
