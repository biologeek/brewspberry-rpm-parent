package net.brewspberry.front.ws.beans.responses;

import java.util.List;

public class ActionnerResponse {
	
	
	private long id;
	private String name;
	private String uuid;
	private int type;
	private String picture;
	private ChartResponse chart;
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
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public ChartResponse getChart() {
		return chart;
	}
	public void setChart(ChartResponse chart) {
		this.chart = chart;
	} 

}
