package net.brewspberry.main.front.ws.beans.responses;

import java.util.List;

import net.brewspberry.main.business.beans.monitoring.GenericActionner.ActionerStatus;
import net.brewspberry.main.util.Constants;

public class ActionnerResponse {
	
	
	private long genericId;
	private long id;
	private String name;
	private String uuid;
	private ActionerType type;
	private long stepId;
	private long brewId;

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
	private ActionerStatus state;
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
		return getPictureWithStatus();
	}
	public void setPicture(String picture) {
		if (this.picture == null && picture == null){
			picture = getPictureWithStatus();
		}
		else {
			this.picture = picture;
		}
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
	public ActionerStatus getState() {
		return state;
	}
	public void setState(ActionerStatus state) {
		this.state = state;
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
	public long getGenericId() {
		return genericId;
	}
	public void setGenericId(long genericId) {
		this.genericId = genericId;
	}
	public long getStepId() {
		return stepId;
	}
	public void setStepId(long stepId) {
		this.stepId = stepId;
	}
	public long getBrewId() {
		return brewId;
	}
	public void setBrewId(long brewId) {
		this.brewId = brewId;
	}
	

	

	public String getPictureWithStatus() {

		if (this.state.equals(ActionerStatus.STARTED)) {
			switch (this.type) {
			case DS18B20:
				return Constants.DS18B20_ON;
			case ENGINE_RELAY:
				return Constants.ENGINE_ON;
			case PUMP_RELAY:
				return Constants.PUMP_ON;
			default:
				return "";
			}
		} else {
			switch (this.type) {
			case DS18B20:
				return Constants.DS18B20_OFF;
			case ENGINE_RELAY:
				return Constants.ENGINE_OFF;
			case PUMP_RELAY:
				return Constants.PUMP_OFF;
			default:
				return "";
			}
		}
	}

}
