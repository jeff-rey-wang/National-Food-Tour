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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class of methods to parse the provided datasets
 * @author Jeffrey Wang
 *
 */
public class FileReader {
	
	/**
	 * Read all lines of dataset file
	 * @param name name of file
	 * @param cutFirst true if first row of file is ignored, false otherwise
	 * @return List of strings; each corresponding to line of file
	 */
	public static ArrayList<String> readLines(String name, boolean cutFirst) {
		ArrayList<String> lines = new ArrayList<String>();
		File csv = new File(name);

		try {
			Scanner inFile = new Scanner(csv).useDelimiter("\n");
			while (inFile.hasNext()) {
				String line = inFile.next().toUpperCase();
				lines.add(line);
			}
			inFile.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (lines.size() > 0 && cutFirst) {
			lines.remove(0);
		}
		return lines;
		
	}
	
	/**
	 * Parse sequence of lines into individual string elements
	 * @param lines sequence of lines
	 * @return sequence of lines parsed into string elements with respect to commas
	 */
	public static ArrayList<String[]> parseLines(String name, boolean cutFirst) {
		ArrayList<String> lines = readLines(name, cutFirst);
		
		ArrayList<String[]> parsedLines = new ArrayList<String[]>();
		
		for (String line : lines) {
			String[] parsed = line.split(",");
			parsedLines.add(parsed);
		}
		return parsedLines;
	}
	
	/**
	 * Filter sequence of parsed lines into string elements for city objects
	 * @param lines sequence of parsed lines
	 * @return sequence of parsed lines with elements only relevant for city object creation
	 */
	public static ArrayList<String[]> parseCity(ArrayList<String[]> lines) {		
		ArrayList<String[]> filteredLines = new ArrayList<String[]>();
		
		for (int i = 0; i < lines.size(); i++) {
			String[] filtered = new String[3];
			for (int j = 0; j < 3; j++) {
				filtered[j] = lines.get(i)[j+3];	
				
			}
			filteredLines.add(filtered);
		}
		return filteredLines;
	}
	
	/**
	 * Filter sequence of parsed lines into string elements for edges
	 * @param lines sequence of parsed lines
	 * @return sequence of parsed lines with elements only relevant for edge creation
	 */
	public static ArrayList<String[]> parseConnect(ArrayList<String[]> lines) {
		ArrayList<String[]> filteredLines = new ArrayList<String[]>();
		
		for (int i = 0; i < lines.size(); i++) {
			String[] filtered = lines.get(i);
			filtered[1] = filtered[1].stripLeading();
			filteredLines.add(filtered);
		}
		return filteredLines;

	}
	
	/**
	 * Filter sequence of parsed lines into string elements for restaurants
	 * @param lines sequence of parsed lines
	 * @return sequence of parsed lines with elements only relevant for restaurant object creation
	 */
	public static ArrayList<String[]> parseRestaurant(ArrayList<String[]> lines) {
		ArrayList<String[]> filteredLines = new ArrayList<String[]>();
		
		for (int i = 0; i < lines.size(); i++) {
			String[] filtered = new String[3];
			for (int j = 0; j < 3; j++) {
				filtered[j] = lines.get(i)[j].stripLeading();
			}
			filtered[2] = filtered[2].substring(1, filtered[2].indexOf("-"));
			filteredLines.add(filtered);
		}
		return filteredLines;
	}
	
	/**
	 * Filter sequence of parsed lines into string elements for edges
	 * @param lines sequence of parsed lines
	 * @return sequence of parsed lines with elements only relevant for menu creation
	 */
	public static ArrayList<String[]> parseMenu(ArrayList<String[]> lines) {
		ArrayList<String[]> filteredLines = new ArrayList<String[]>();
		
		for (int i = 0; i < lines.size(); i++) {
			String[] filtered = new String[3];
			for (int j = 0; j < 3; j++) {
				filtered[j] = lines.get(i)[j].stripLeading();
			}
			filteredLines.add(filtered);
		}
		return filteredLines;
	}

}
