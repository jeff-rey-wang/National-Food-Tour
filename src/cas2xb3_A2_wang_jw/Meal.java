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

/**
 * Class to represent a meal
 * @author Jeffrey Wang
 *
 */
public class Meal {
	
	private final String item;
	private final double price;
	
	/**
	 * Initializes meal object
	 * @param item name of meal
	 * @param price price of meal
	 */
	public Meal(String item, double price) {
		this.item = item;
		this.price = price;
	}
	
	/**
	 * Retrieve name of meal object
	 * @return name of item
	 */
	public String getItem() {
		return this.item;
	}
	
	/**
	 * Retrieve price of meal
	 * @return price of meal
	 */
	public Double getPrice() {
		return this.price;
	}

}
