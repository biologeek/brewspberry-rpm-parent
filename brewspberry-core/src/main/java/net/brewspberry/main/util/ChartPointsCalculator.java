package net.brewspberry.main.util;

public class ChartPointsCalculator {
	
	/**
	 * Computes frequency of point to keep for display
	 * @param numberOfPoints to keep
	 * @param listOfTemperatureMeasurementsSize size of raw results list
	 * @param delayofChart time range displayed on chart
	 * @return
	 */
	public static int computePointsFrequencyDisplay (int numberOfPoints, int listOfTemperatureMeasurementsSize, float delayofChart){
		int result = 0;
		
		result = (int) Math.ceil((float) listOfTemperatureMeasurementsSize/(float) numberOfPoints);
		
		
		return result;		
	}

}
