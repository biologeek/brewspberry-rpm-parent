package net.brewspberry.monitoring.services;

import java.util.Set;

import net.brewspberry.monitoring.exceptions.ServiceException;
import net.brewspberry.monitoring.model.AbstractDevice;

/**
 * Defines common behaviour for devices such as device registration, setup,
 * retrieving, ...
 *
 * @param <T>
 *            Device type
 */
public interface DeviceService<T extends AbstractDevice> {

	public T getDeviceById(Long id);

	public Set<T> listPluggedDevices();
	public Set<T> listAllDevices();
	public void saveDevice(T device) throws ServiceException;
	public T updateDevice(T toSave, T saved) throws ServiceException;

	public T getDeviceByUUID(String uuid);
	
	/**
	 * Starts a device for a certain time and eventually performs measurement at a specific frequency if device suports it
	 * @param id device ID
	 * @param duration duration of device run
	 * @param frequencyInSeconds frequency of measurement
	 * @return the updated device
	 */
	public T startDevice(T device, Float duration, Integer frequencyInSeconds);

	public T stopDevice(T device);

}
