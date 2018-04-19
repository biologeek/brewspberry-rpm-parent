package net.brewspberry.monitoring.daemons;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.springframework.util.Assert;

import com.pi4j.io.w1.W1Device;
import com.pi4j.io.w1.W1Master;

import net.brewspberry.monitoring.exceptions.DeviceNotFoundException;
import net.brewspberry.monitoring.model.TemperatureMeasurement;
import net.brewspberry.monitoring.model.TemperatureSensor;
import net.brewspberry.monitoring.repositories.TemperatureMeasurementRepository;
import net.brewspberry.monitoring.services.JmsDaemon;
import net.brewspberry.monitoring.services.impl.TemperatureSensorServicesImpl;
import net.brewspberry.monitoring.services.tech.TemperatureMeasurementJmsService;

/**
 * Daemon thread that will regularly poll sensor to get temperature
 * 
 * @author xavier
 *
 */
public class TemperatureDaemonService implements Runnable, JmsDaemon<TemperatureMeasurement> {

	private TemperatureMeasurementRepository repository;

	private TemperatureMeasurementJmsService jmsService;
	private Map<String, Object> parameters;
	private W1Master oneWireMaster;

	@Override
	public void run() {

		Assert.notNull(jmsService, "JmsService is null !");
		Assert.notNull(repository, "temperatureMeasurementRepository is null !");
		Assert.notNull(oneWireMaster, "1-Wire masters is null !");
		Assert.notEmpty(parameters, "No parameters provided !");
		for (String prm : TemperatureSensor.MANDATORY_REGULAR_POLL_PARAMETERS) {
			Assert.notNull(parameters.get(prm), prm + " is null !");
		}
		Date begin = new Date();
		Calendar endCal = Calendar.getInstance();

		endCal.add(Calendar.MILLISECOND, (int) ((Duration) parameters.get(TemperatureSensor.DURATION)).toMillis());
		while (new Date().before(endCal.getTime())) {

			try {
				List<TemperatureMeasurement> measured = pollSensors(
						(List<TemperatureSensor>) parameters.get(TemperatureSensor.DEVICE_LIST),
						(float) parameters.get(TemperatureSensor.FREQUENCY));
				
				sendJms(measured);
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
		}

	}

	public void sendJms(List<TemperatureMeasurement> measured) {
		jmsService.send(measured);
	}

	/**
	 * Determines which devices to poll and poll them
	 * 
	 * @param sensors
	 * @param frequency
	 * @return
	 * @throws DeviceNotFoundException
	 * @throws IOException
	 */
	private List<TemperatureMeasurement> pollSensors(List<TemperatureSensor> sensors, float frequency)
			throws IOException {
		Set<W1Device> devices = new HashSet<>(0);

		List<TemperatureMeasurement> measurements = new ArrayList<>(0);
		if (sensors == null || sensors.isEmpty()) {
			devices = new HashSet<>(oneWireMaster.getDevices());
		} else {
			List<String> sensorsIds = sensors.stream().map(TemperatureSensor::getUuid).collect(Collectors.toList());
			devices = oneWireMaster.getDevices().stream().filter(t -> sensorsIds.contains(t.getId()))
					.collect(Collectors.toSet());
		}

		for (W1Device device : devices) {

			TemperatureSensor currentSensor;
			try {
				currentSensor = sensors//
						.stream()//
						.filter(t -> t.getUuid().equals(device.getId())).findFirst()//
						.orElseThrow(new Supplier<DeviceNotFoundException>() {
							public DeviceNotFoundException get() {
								return new DeviceNotFoundException("Device not found : " + device.getId());
							}
						});
			} catch (DeviceNotFoundException e) {
				e.printStackTrace();
				continue;
			}
			measurements.add(new TemperatureMeasurement()//
					.date(new Date())//
					.sensor(currentSensor)//
					.temperature(TemperatureSensorServicesImpl.convertRawTemperature(device.getValue()))//
			);//
		}
		return measurements;
	}

	public Map<String, Object> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}

	public TemperatureMeasurementRepository getRepository() {
		return repository;
	}

	public void setRepository(TemperatureMeasurementRepository repository) {
		this.repository = repository;
	}

	public TemperatureMeasurementJmsService getJmsService() {
		return jmsService;
	}

	public void setJmsService(TemperatureMeasurementJmsService jmsService) {
		this.jmsService = jmsService;
	}

	@Override
	public void sendJms(TemperatureMeasurement object) {
		// TODO Auto-generated method stub

	}

	public W1Master getOneWireMaster() {
		return oneWireMaster;
	}

	public void setOneWireMaster(W1Master oneWireMaster) {
		this.oneWireMaster = oneWireMaster;
	}

}
