package net.brewspberry.regulation.model;


import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class TemperatureAlgorithmData  extends AbstractAlgorithmData{
	
	
	Date date;
	double actualTemperatureValue;
	
	double[] derivedValues;
	double[] integratedValues;
	
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public double getActualTemperatureValue() {
		return actualTemperatureValue;
	}
	public void setActualTemperatureValue(double actualTemperatureValue) {
		this.actualTemperatureValue = actualTemperatureValue;
	}
	public double[] getDerivedValues() {
		return derivedValues;
	}
	public void setDerivedValues(double[] derivedValues) {
		this.derivedValues = derivedValues;
	}
	public double[] getIntegratedValues() {
		return integratedValues;
	}
	public void setIntegratedValues(double[] integratedValues) {
		this.integratedValues = integratedValues;
	}
	

}
