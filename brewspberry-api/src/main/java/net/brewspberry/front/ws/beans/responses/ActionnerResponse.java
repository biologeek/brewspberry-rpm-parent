package net.brewspberry.front.ws.beans.responses;

import java.util.List;

public class ActionnerResponse {
	
	
	private long id;
	private String name;
	private String uuid;
	private ActionerType type;

	public enum ActionerType {
		DS18B20, ENGINE_RELAY, PUMP_RELAY
	}
	

	public enum ActionerStatus {
		STOPPED, STARTED, PAUSED, IDLE
	}
	
	private String picture;
	private List<ChartResponse> chart;
	private String pin;
	private boolean isActive;
	private ActionerStatus status;
	private boolean used;
	private long begin;
	private long end;
	
	
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
	public ActionerType getType() {
		return type;
	}
	public void setType(ActionerType string) {
		this.type = string;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public List<ChartResponse> getChart() {
		return chart;
	}
	public void setChart(List<ChartResponse> chart) {
		this.chart = chart;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public ActionerStatus getStatus() {
		return status;
	}
	public void setStatus(ActionerStatus status) {
		this.status = status;
	}
	public boolean isUsed() {
		return used;
	}
	public void setUsed(boolean used) {
		this.used = used;
	}
	public long getBegin() {
		return begin;
	}
	public void setBegin(long begin) {
		this.begin = begin;
	}
	public long getEnd() {
		return end;
	}
	public void setEnd(long end) {
		this.end = end;
	}

}
