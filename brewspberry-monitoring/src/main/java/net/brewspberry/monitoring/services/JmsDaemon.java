package net.brewspberry.monitoring.services;

import java.util.List;

import net.brewspberry.monitoring.model.TemperatureMeasurement;

/**
 * Defines behaviour for JMS/asynchronously sent informations, such as temperature measurements
 * 
 */
public interface JmsDaemon<T> {
	
	public void sendJms(T object);
	public void sendJms(List<TemperatureMeasurement> measured);
}
