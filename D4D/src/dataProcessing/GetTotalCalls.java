package dataProcessing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * This class is designed with static methods to extract data
 * from dataset 2 into new files to use with GNUPlot
 * 
 * @author Timothy LaRock
 * @version 1.0 9/25/2014
 *
 */
public class GetTotalCalls {

	/**
	 * This method will get the total calls for each site given 
	 * any input file from Dataset 2.
	 * 
	 * @param inputFile
	 * @param outputFile
	 */
	public static void GetSiteTotals(String inputFile, String outputFile) {
		String line = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(inputFile));

			BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));

			HashMap<Integer, Integer> siteList = new HashMap<Integer, Integer>();
			// initialize every row of siteList to 0
			for (int i = 1; i < 1668; i++) {
				siteList.put(i, 0);
			}
			// declare variables to be used in while loop
			StringTokenizer lineTokenizer;
			StringTokenizer dateTokenizer;
			Integer siteID;
			String token;
			Integer siteSum;
			// while data is being read
			while ((line = br.readLine()) != null) {
				// Tokenize each line
				lineTokenizer = new StringTokenizer(line, ",");
				// find the siteID
				int i = 0;
				while (i < 3) {
					token = lineTokenizer.nextToken();
					// if i is 1, we have the datetime
					if (i == 1) {

						StringTokenizer sepDate = new StringTokenizer(token,
								" ");
						String date = sepDate.nextToken();
						String time = sepDate.nextToken();
						int hour = findTimeOfDay(time);

					}
					// if i is 2, meaning we are at
					// the siteID
					else if (i == 2) {
						// get the siteID as an integer
						siteID = Integer.parseInt(token);
						// get the current sum from the HashMap
						if (siteList.get(siteID) != null)
							siteSum = siteList.get(siteID) + 1;
						else {
							siteSum = 1;
						}
						// Put the new value back in the HashMap
						siteList.put(siteID, siteSum);
					}
					i++;
				}

			}

			for (int i = 1; i < siteList.size(); i++) {
				bw.write(i + "," + siteList.get(i));
				bw.write("\n");

			}

			br.close();
			bw.close();
		} catch (IOException io) {
			System.err.println("IOException CAUGHT IN SortOnSiteID.java : "
					+ io.getMessage());
		}
	}

	
	/**
	 * This method will separate the calls per site into distinct times of day.
	 * It will generate 4 output files, one for each time block, to be used to
	 * plot using GNUPlot.
	 * @param inputFile
	 * @param outputPath
	 */
	public static void GetTemporalTotals(String inputFile, String outputPath)
	{
		String line = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(inputFile));

			/*
			BufferedWriter bw = new BufferedWriter(new FileWriter(outputPath + "0" + ".CSV"));
			BufferedWriter bw1 = new BufferedWriter(new FileWriter(outputPath + "1" + ".CSV"));
			BufferedWriter bw2 = new BufferedWriter(new FileWriter(outputPath + "2" + ".CSV"));
			BufferedWriter bw3 = new BufferedWriter(new FileWriter(outputPath + "3" + ".CSV"));
			*/
			/* This HashMap will hold all of the data for this method
			 * The outer Integer Value is siteID
			 * The first inner Integer Value is temporal (hour)
			 * The second inner Integer Value is the sum for the corresponding hour
			 */
			HashMap<Integer, HashMap<Integer, Integer> > siteMap = new HashMap<Integer, HashMap<Integer, Integer>>();
			
			// initialize the data structure to 0
			for(int i = 1; i < 1668; i++)
			{
				for(int j = 0; j < 4; j++)
				{
					siteMap.put(i, new HashMap<Integer, Integer>());
					System.out.println("Put totalValue " + siteMap.get(i).get(j) + " in location i = " + i + " j = " + j + ".");
				}
			}
			
			
			
			
			br.close();
			
			/*
			bw.close();
			bw1.close();
			bw2.close();
			bw3.close(); 
			*/
		}
		catch(IOException io)
		{
			System.err.println("CAUGHT IOException IN GetTemporalTotals() : " + io.getMessage());
			System.exit(1);
		}
		
		
		
	}
	/**
	 * This method takes a date token as input. The format of the token is
	 * YYYY/MM/DD HH:MM:SS Where the second M and both S values are always 0.
	 * 
	 * It returns one of four values: 0 - morning (02 - 10) 1 - lunch (10 - 15)
	 * 2 - afternoon (15 - 20) 3 - night (20 - 24, 00, 01, 02)
	 * 
	 * @param token
	 * @return
	 */
	public static int findTimeOfDay(String token) {
		int timeOfDay = -1;
		// create another tokenizer to split up the date
		String[] result = token.split(":");
		int hour = Integer.parseInt(result[0]);
		if ((hour > 2) && (hour < 10))
			timeOfDay = 0;
		else if ((hour > 10) && (hour < 15))
			timeOfDay = 1;
		else if ((hour > 15) && (hour < 20))
			timeOfDay = 2;
		else if ((hour > 20) && ((hour < 24) || (hour < 3)))
			timeOfDay = 3;
		return timeOfDay;
	}
}
