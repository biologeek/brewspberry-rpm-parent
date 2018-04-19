package net.brewspberry.monitoring.api;


import net.brewspberry.monitoring.api.charting.Chart;


public class ThAndRealTempMeasurementDto {

	/**
	 * Only used by front to say "I already got until this ID, give me next ones !"
	 * If converting from service to front, use 0. It will be calculated in front application 
	 */
	private long lastID;
	Chart concretes;
	Chart theoreticals;
	
	public ThAndRealTempMeasurementDto() {
		concretes = new Chart();
		theoreticals = new Chart();
	}
	
	public Chart getConcretes() {
		return concretes;
	}
	public void setConcretes(Chart arrayList) {
		this.concretes = arrayList;
	}
	public Chart getTheoreticals() {
		return theoreticals;
	}
	public void setTheoreticals(Chart arrayList) {
		this.theoreticals = arrayList;
	}
	public long getLastID() {
		return lastID;
	}
	public void setLastID(long lastID) {
		this.lastID = lastID;
	}
}
