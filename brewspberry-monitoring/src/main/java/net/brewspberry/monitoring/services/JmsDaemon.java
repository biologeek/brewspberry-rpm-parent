package net.brewspberry.monitoring.services;

import java.util.List;

import net.brewspberry.monitoring.model.TemperatureMeasurement;

/**
 * Defines behaviour for JMS/asynchronously sent informations, such as temperature measurements
 * 
 */
public interface JmsDaemon<T> {
	
	/**
	 * Send a single-object JMS
	 */
	public void sendJms(T object);
	/**
	 * Sends multiple objects into 1 JMS
	 */
	public void sendJms(List<T> measured);
}
