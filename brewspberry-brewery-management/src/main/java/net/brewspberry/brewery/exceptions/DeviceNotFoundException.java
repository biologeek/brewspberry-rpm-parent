package net.brewspberry.brewery.exceptions;

public class DeviceNotFoundException extends Exception {

	public DeviceNotFoundException(String uuid) {
		super(uuid);
	}

}
