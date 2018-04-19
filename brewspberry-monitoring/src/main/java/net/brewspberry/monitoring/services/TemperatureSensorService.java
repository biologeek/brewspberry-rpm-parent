package net.brewspberry.monitoring.services;

import java.time.Duration;
import java.time.Period;
import java.util.List;
import java.util.Map;

import net.brewspberry.monitoring.exceptions.DeviceNotFoundException;
import net.brewspberry.monitoring.exceptions.ServiceException;
import net.brewspberry.monitoring.model.TemperatureMeasurement;
import net.brewspberry.monitoring.model.TemperatureSensor;

/**
 * Defines behaviour for temperature sensing.<br>
 * <br>
 * This service allows to handle various specific operations around temperature
 * sensing. It inheritates common devices behaviour
 *
 */
public interface TemperatureSensorService extends DeviceService<TemperatureSensor> {

	/**
	 * Returns temperature measured by sensor.
	 * 
	 * @param sensor
	 *            the sensor
	 * @return a {@link TemperatureMeasurement} object
	 * @throws DeviceNotFoundException
	 * @throws ServiceException
	 */
	public TemperatureMeasurement getTemperatureForDevice(TemperatureSensor sensor)
			throws DeviceNotFoundException, ServiceException;

	/**
	 * As its name suggests, this method will run periodic measuremnts depending on
	 * passed parameters. <br>
	 * <br>
	 * Significant parameters keys and value types :
	 * - frequency : {@link Duration}
	 * - duration : {@link Duration}
	 * 
	 * @param parameters
	 */
	public void runRegularTemperatureMeasurement(List<TemperatureSensor> sensors, Map<String, Object> parameters);

}
