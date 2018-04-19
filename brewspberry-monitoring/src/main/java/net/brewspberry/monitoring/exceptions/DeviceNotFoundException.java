package net.brewspberry.monitoring.exceptions;

public class DeviceNotFoundException extends Exception {

	public DeviceNotFoundException(String uuid) {
		super(uuid);
	}

}
