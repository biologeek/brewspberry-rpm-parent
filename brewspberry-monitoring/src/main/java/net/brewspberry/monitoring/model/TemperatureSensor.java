package net.brewspberry.monitoring.model;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class TemperatureSensor extends AbstractDevice {
	/**
	 * Defines mandatory parameters for running a regular poll to sensors
	 */
	public static final String DURATION = "duration";
	public static final String DEVICE_LIST = "deviceList";
	public static final String EXTERNAL_ID = "externalId";
	public static final String THREAD_DUMP_FOLDER = "threadDumpFolder";
	/**
	 * Polling frequency in Hz-1
	 */
	public static final String FREQUENCY = "frequency";
	public static final String[] MANDATORY_REGULAR_POLL_PARAMETERS = new String[] { DEVICE_LIST, DURATION, FREQUENCY };
	public static final String DS18B20_UUID_REGEX = "^28-[0-9]{12}+";
	
	
	public TemperatureSensor creationDate(Date creation) {
		this.creationDate = creation;
		return this;
	}


	public TemperatureSensor lastChangedDate(Date lastChange) {
		this.lastStateChangeDate = lastChange;
		return this;
	}


	public TemperatureSensor id(Long id) {
		this.id = id;
		return this;
	}


	public TemperatureSensor name(String name) {
		this.name = name;
		return this;
	}


	public TemperatureSensor pin(String pin) {
		this.pin = pin;
		return this;
	}


	public TemperatureSensor pinAddress(int pinAddress) {
		this.pinAddress = pinAddress;
		return this;
	}
	
}
