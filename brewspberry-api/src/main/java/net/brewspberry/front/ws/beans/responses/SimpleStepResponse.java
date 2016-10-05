package net.brewspberry.front.ws.beans.responses;

public class SimpleStepResponse {
	
	
	
	private long id;
	private String name;
	private int number;
	private long duration;
	private long beginning;
	private long end;
	private long theoreticalTemperature;
	private boolean isActive;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
	public long getBeginning() {
		return beginning;
	}
	public void setBeginning(long beginning) {
		this.beginning = beginning;
	}
	public long getEnd() {
		return end;
	}
	public void setEnd(long end) {
		this.end = end;
	}
	public long getTheoreticalTemperature() {
		return theoreticalTemperature;
	}
	public void setTheoreticalTemperature(long theoreticalTemperature) {
		this.theoreticalTemperature = theoreticalTemperature;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
}