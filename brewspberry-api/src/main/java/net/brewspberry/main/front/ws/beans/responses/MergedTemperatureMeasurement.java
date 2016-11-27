package net.brewspberry.main.front.ws.beans.responses;

import java.util.List;

import net.brewspberry.main.business.beans.ConcreteTemperatureMeasurement;
import net.brewspberry.main.business.beans.TheoreticalTemperatureMeasurement;

public class MergedTemperatureMeasurement {

	
	List<ConcreteTemperatureMeasurement> concrete;
	
	List<TheoreticalTemperatureMeasurement> theoretical;

	public List<ConcreteTemperatureMeasurement> getConcrete() {
		return concrete;
	}

	public void setConcrete(List<ConcreteTemperatureMeasurement> concrete) {
		this.concrete = concrete;
	}
	public MergedTemperatureMeasurement concrete(List<ConcreteTemperatureMeasurement> concrete) {
		this.concrete = concrete;
		return this;		
	}

	public List<TheoreticalTemperatureMeasurement> getTheoretical() {
		return theoretical;
	}

	public void setTheoretical(List<TheoreticalTemperatureMeasurement> theoretical) {
		this.theoretical = theoretical;
	}

	public MergedTemperatureMeasurement theoretical(List<TheoreticalTemperatureMeasurement> theoretical) {
		this.theoretical = theoretical;
		return this;
	}
}
