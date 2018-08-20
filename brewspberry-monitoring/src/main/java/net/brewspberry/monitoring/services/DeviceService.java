package net.brewspberry.monitoring.services;

import java.util.Set;

import net.brewspberry.monitoring.exceptions.ServiceException;
import net.brewspberry.monitoring.model.TemperatureSensor;

/**
 * Defines common behaviour for devices such as device registration, setup,
 * retrieving, ...
 *
 * @param <T>
 *            Device type
 */
public interface DeviceService<T> {

	public T getDeviceById(Long id);

	public Set<T> listPluggedDevices();
	public Set<T> listAllDevices();
	public void saveDevice(T device) throws ServiceException;

	public TemperatureSensor getDeviceByUUID(String uuid);

}
