package net.brewspberry.front.ws.beans.responses;

import java.util.List;

public class MergedTemperatureMeasurementsForChart {

	private long lastID;
	List<TemperatureChartData> concretes;
	List<TemperatureChartData> theoreticals;
	
	
	public List<TemperatureChartData> getConcretes() {
		return concretes;
	}
	public void setConcretes(List<TemperatureChartData> arrayList) {
		this.concretes = arrayList;
	}
	public List<TemperatureChartData> getTheoreticals() {
		return theoreticals;
	}
	public void setTheoreticals(List<TemperatureChartData> arrayList) {
		this.theoreticals = arrayList;
	}
	public long getLastID() {
		return lastID;
	}
	public void setLastID(long lastID) {
		this.lastID = lastID;
	}
}
