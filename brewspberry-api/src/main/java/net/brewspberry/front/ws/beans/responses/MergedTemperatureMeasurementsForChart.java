package net.brewspberry.front.ws.beans.responses;

import java.util.ArrayList;
import java.util.List;

public class MergedTemperatureMeasurementsForChart {

	private long lastID;
	ArrayList<TemperatureChartData> concretes;
	ArrayList<TemperatureChartData> theoreticals;
	
	
	public ArrayList<TemperatureChartData> getConcretes() {
		return concretes;
	}
	public void setConcretes(ArrayList<TemperatureChartData> arrayList) {
		this.concretes = arrayList;
	}
	public ArrayList<TemperatureChartData> getTheoreticals() {
		return theoreticals;
	}
	public void setTheoreticals(ArrayList<TemperatureChartData> arrayList) {
		this.theoreticals = arrayList;
	}
	public long getLastID() {
		return lastID;
	}
	public void setLastID(long lastID) {
		this.lastID = lastID;
	}
}
