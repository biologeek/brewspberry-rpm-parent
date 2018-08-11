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

	/**
	 * @see DeviceService#switchOnDevice(Object)
	 * @param uuid
	 * @return
	 */
	public T switchOnDevice(String uuid);

	/**
	 * @see DeviceService#switchOnDevice(Object)
	 * @param uuid
	 * @return
	 */
	public T switchOnDevice(Long id);

	/**
	 * Switching On a device does not mean it is being started but is used for
	 * activating the device. <br>
	 * <br>
	 * Concretely, it may consist in simply registering the new state of device,
	 * provisionning pins on Pi
	 * 
	 * @param sensor : sensor object to switch on
	 * @return the device
	 */
	public T switchOnDevice(T sensor);

	public T switchOffDevice(String uuid);

	public T switchOffDevice(Long id);

	public T switchOffDevice(T sensor);

	public TemperatureSensor getDeviceByUUID(String uuid);

}
