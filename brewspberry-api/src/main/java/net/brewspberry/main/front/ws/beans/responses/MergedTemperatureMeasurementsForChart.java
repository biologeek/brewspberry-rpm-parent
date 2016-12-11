package net.brewspberry.main.front.ws.beans.responses;

import java.util.ArrayList;
import java.util.List;

public class MergedTemperatureMeasurementsForChart {

	/**
	 * Only used by front to say "I already got until this ID, give me next ones !"
	 * If converting from service to front, use 0. It will be calculated in front application 
	 */
	private long lastID;
	List<TemperatureChartData> concretes;
	List<TemperatureChartData> theoreticals;
	
	public MergedTemperatureMeasurementsForChart() {
		concretes = new ArrayList<>();
		theoreticals = new ArrayList<>();
	}
	
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
