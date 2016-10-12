package net.brewspberry.front.ws.beans.requests;

public class StockCounterRequest {
	
	private long id;
	private String type;
	private double value;
	private String unit;
	private long creation;
	private long update;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public long getCreation() {
		return creation;
	}
	public void setCreation(long creation) {
		this.creation = creation;
	}
	public long getUpdate() {
		return update;
	}
	public void setUpdate(long update) {
		this.update = update;
	}
	
	

}
