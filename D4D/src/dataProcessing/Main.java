package dataProcessing;

public class Main {
	
	public static void main(String[] args) {

		/* Make a call to toXY
		ConvertCoordinates
				.toXY("/home/timothy/D4DProject/ContextData/SITE_ARR_LONLAT.CSV",
						"/home/timothy/D4DProject/ContextData/SITE_ARR_LONLAT_CARTESIAN.CSV");
		*/
		// Get the total number of calls for each site from Dataset 2
		// GetTotalCalls.GetSiteTotals("/home/timothy/D4DProject/SET2_P01.CSV", "/home/timothy/D4DProject/SET2_P01_Totals.CSV");

		GetTotalCalls.GetTemporalTotals("/home/timothy/D4DProject/SET2_P01.CSV", "/home/timothy/D4DProject/TemporalData/");
	}

}
