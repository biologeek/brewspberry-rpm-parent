package net.brewspberry.monitoring.services;

import java.util.Set;

import net.brewspberry.monitoring.api.DeviceDto;
import net.brewspberry.monitoring.exceptions.ElementNotFoundException;
import net.brewspberry.monitoring.exceptions.ServiceException;
import net.brewspberry.monitoring.model.AbstractDevice;

/**
 * Non specific services for devices. Also serves for routing operations to device-specific services
 */
public interface CommonDeviceService {
	
	

	/**
	 * Starts a device for a certain time and eventually performs measurement at a specific frequency if device suports it
	 * @param id device ID
	 * @param duration duration of device run
	 * @param frequencyInSeconds frequency of measurement
	 * @return the updated device
	 */
	public AbstractDevice startDevice(Long id, Float duration, Integer frequencyInSeconds);

	public AbstractDevice stopDevice(Long deviceId);

	public Set<AbstractDevice> listAllDevices();

	public AbstractDevice saveDevice(AbstractDevice device);

	public void saveDevice(AbstractDevice model, Long id) throws ElementNotFoundException, ServiceException;

}
