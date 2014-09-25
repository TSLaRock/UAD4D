package dataProcessing;

import java.io.*;
import java.util.StringTokenizer;

public class ConvertCoordinates {

	/**
	 * Standalone program to convert latitudinal/longitudanal coordinates to
	 * coordinates that can be used on a cartesian plane in order to compute
	 * Voronoi diagrams for cell phone towers in Senegal
	 * 
	 * @author Timothy LaRock
	 * @version 1.0 9/17/2014
	 * 
	 * @version 1.1 9/19/2014 Added static toXY() method so that other programs
	 *          can use this functionality
	 * 
	 * @param args
	 */

	public static void toXY(String input_file, String output_file) {
		// Earth radius variable for calculation
		final int EARTH_RADIUS = 6371;
		// latitutde and longitude values
		double latitude = 0.0;
		double longitude = 0.0;
		// variables to hold the cartesian coordinates
		double x = 0.0;
		double y = 0.0;

		// Strings used to process data
		String token = "";
		String line = "";
		String header = "";

		// try for IOException
		try {

			// Create bufferedReader to pull in data
			BufferedReader br = new BufferedReader(new FileReader(input_file));

			// BufferedWriter to write data
			BufferedWriter bw = new BufferedWriter(new FileWriter(output_file));

			// The first line of the file is a header, put it in a variable
			header = br.readLine();

			// write the header to the output
			// System.out.println(header);
			bw.write(header);
			// write a new line
			bw.write("\n");

			// Declare tokenizer
			StringTokenizer st;

			// Declare and initialize counter variable
			int i = 0;

			while ((line = br.readLine()) != null) {
				token = "";
				// System.out.println(line);
				st = new StringTokenizer(line, ",");
				i = 0;
				while (st.hasMoreElements()) {
					// get the next token
					token = st.nextToken();

					// if i < 2, write the token
					if (i < 2) {
						bw.write(token + ",");
						System.out.print(token + ",");
					}
					// if i == 2, do longitude
					else if (i == 2) {
						// if i == 2, longitude
						longitude = Float.valueOf(token);
						// longitude = longitude * Math.PI / 180;
					}
					// if i == 3, do latitude
					else if (i == 3) {
						// if i == 3, latitude
						latitude = Float.valueOf(token);
						// latitude = latitude * Math.PI / 180;
					}
					i++;
				}

				double n = (EARTH_RADIUS)
						/ (Math.sqrt(Math.abs((1 - (Math.exp(2.0))))
								* Math.pow(Math.sin(latitude), 2)));

				x = (int) (n * (Math.cos(latitude) * Math.cos(longitude)));
				y = (int) (n * (Math.cos(latitude) * Math.sin(longitude)));

				/*
				 * x = EARTH_RADIUS * Math.sin(latitude) * Math.cos(longitude);
				 * y = EARTH_RADIUS * Math.sin(latitude) * Math.sin(longitude);
				 * z = EARTH_RADIUS * Math.cos(latitude); x_coord = (int) x;
				 * y_coord = (int) y;
				 */
				System.out.print(x + ",");
				System.out.println(y);
				bw.write(x + ",");
				bw.write(y + "\n");
			}
			br.close();
			bw.close();

		} catch (IOException io) {
			System.err.println("CAUGHT IOEXCEPTION IN ConvertCoordinates: "
					+ io.getMessage());
		}

	}

}
