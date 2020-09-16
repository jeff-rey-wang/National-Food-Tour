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

import java.util.*;
/**
 * Class to represent a city object
 * @author Jeffrey Wang
 *
 */
public class City {
	
	private final String name;  // name of city
	private final double lat;  // latitude value of city
	private final double lon;  //longitude value of city

	/**
	 * Initializes city object
	 * @param name name of city
	 * @param lat latitude value of city
	 * @param lon longitude value of city
	 */
	public City (String name, double lat, double lon) {	
		this.name = name;
		this.lat = lat;
		this.lon = lon;
	}
	
	/**
	 * Retrieves name of city
	 * @return city name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Retrieve latitude value of city
	 * @return latitude value
	 */
	public double getLat() {
		return this.lat;
	}
	
	/**
	 * Retrieve longitude value of city
	 * @return longitude value
	 */
	public double getLon() {
		return this.lon;
	}
}
