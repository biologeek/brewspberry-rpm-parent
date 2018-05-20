package net.brewspberry.monitoring.daemons;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.springframework.util.Assert;

import com.pi4j.io.w1.W1Device;
import com.pi4j.io.w1.W1Master;

import net.brewspberry.monitoring.exceptions.DeviceNotFoundException;
import net.brewspberry.monitoring.exceptions.TechnicalException;
import net.brewspberry.monitoring.model.ThreadState;
import net.brewspberry.monitoring.model.TemperatureMeasurement;
import net.brewspberry.monitoring.model.TemperatureSensor;
import net.brewspberry.monitoring.repositories.TemperatureMeasurementRepository;
import net.brewspberry.monitoring.services.JmsDaemon;
import net.brewspberry.monitoring.services.ThreadStateServices;
import net.brewspberry.monitoring.services.ThreadWitnessCheckServices;
import net.brewspberry.monitoring.services.ThreadWitnessServices;
import net.brewspberry.monitoring.services.impl.DS18B20TemperatureSensorServicesImpl;
import net.brewspberry.monitoring.services.impl.ThreadStateServicesImpl;
import net.brewspberry.monitoring.services.tech.TemperatureMeasurementJmsService;

/**
 * Daemon thread that will regularly poll sensor to get temperature
 * 
 * @author xavier
 *
 */
public class TemperatureDaemonThread implements Runnable, JmsDaemon<TemperatureMeasurement> {

	private volatile TemperatureMeasurementRepository repository;

	private volatile TemperatureMeasurementJmsService jmsService;
	private volatile Map<String, Object> parameters;
	private volatile W1Master oneWireMaster;
	private volatile ThreadStateServices threadServices;
	private volatile ThreadWitnessCheckServices witnessServices;
	private String uuid = UUID.randomUUID().toString();

	private EntityManager em;

	private Logger logger = Logger.getLogger(TemperatureDaemonThread.class.getName());

	public TemperatureDaemonThread() {
	}

	@Override
	/**
	 * Periodic polling of sensor temperature using passed parameters (at least
	 * duration, polling frequency and list of sensors)
	 */
	public void run() {
		
		checkParams();
		Date begin = new Date();
		Calendar endCal = Calendar.getInstance();

		endCal.add(Calendar.MILLISECOND, (int) ((Duration) parameters.get(TemperatureSensor.DURATION)).toMillis());
		List<TemperatureSensor> sensorsList = (List<TemperatureSensor>) parameters.get(TemperatureSensor.DEVICE_LIST);
		while (new Date().before(endCal.getTime())) {
			prePolling();
			try {
				List<TemperatureMeasurement> measured = pollSensors(sensorsList);

				saveMeasurements(measured);
				threadServices.writeState(ThreadState.noError(uuid));
				Thread.sleep((long) parameters.get(TemperatureSensor.FREQUENCY));
			} catch (IOException | TechnicalException e) {
				e.printStackTrace();
				try {
					threadServices.writeState(ThreadState
							.xErrors(threadServices.readState(e.getMessage()).getErrorOccurence(), e.getMessage()));
				} catch (TechnicalException e1) {
					e1.printStackTrace();
				}
				continue;
			} catch (InterruptedException e) {
				e.printStackTrace();
				logger.severe("Thread sleep interrupted !!");
			}
		}

		finalizeThread(sensorsList);
	}
	/**
	 * Operations to execute before polling
	 */
	private void prePolling() {
		witnessServices.checkWitness(this.uuid);
	}

	/**
	 * Called at the end of thread execution, this method releases file locks
	 * @param sensorsList
	 */
	private void finalizeThread(List<TemperatureSensor> sensorsList) {
		try {
			threadServices.cleanState(sensorsList//
					.stream()//
					.map(TemperatureSensor::getUuid)//
					.collect(Collectors.toList()));
		} catch (TechnicalException e) {
			e.printStackTrace();
		}
	}

	private void checkParams() {
		Assert.notNull(jmsService, "JmsService is null !");
		Assert.notNull(repository, "temperatureMeasurementRepository is null !");
		Assert.notNull(oneWireMaster, "1-Wire masters is null !");
		Assert.notNull(threadServices, "ThreadStateService is null !");
		Assert.notEmpty(parameters, "No parameters provided !");
		for (String prm : TemperatureSensor.MANDATORY_REGULAR_POLL_PARAMETERS) {
			Assert.notNull(parameters.get(prm), prm + " is null !");
		}
	}

	private void saveMeasurements(List<TemperatureMeasurement> measured) {
		repository.saveAll(measured);
	}

	public void sendJms(List<TemperatureMeasurement> measured) {
		if (measured == null || measured.isEmpty())
			return;
		else {
			jmsService.send(measured);
		}
	}

	@Override
	public void sendJms(TemperatureMeasurement measured) {
		if (measured == null)
			return;
		else {
			jmsService.send(measured);
		}
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
	private List<TemperatureMeasurement> pollSensors(List<TemperatureSensor> sensors) throws IOException {

		List<TemperatureMeasurement> measurements = new ArrayList<>(0);

		Set<W1Device> devices = getSensorsToPoll(sensors);

		for (W1Device device : devices) {

			TemperatureSensor currentSensor;
			try {
				currentSensor = sensors//
						.stream()//
						.filter(t -> t.getUuid().equals(device.getId())).findFirst()//
						.orElseThrow(new Supplier<DeviceNotFoundException>() {
							public DeviceNotFoundException get() {
								return new DeviceNotFoundException(device.getId());
							}
						});
			} catch (DeviceNotFoundException e) {
				e.printStackTrace();
				continue;
			}
			measurements.add(new TemperatureMeasurement()//
					.date(new Date())//
					.sensor(currentSensor)//
					.temperature(DS18B20TemperatureSensorServicesImpl.convertRawTemperature(device.getValue()))//
			);//
		}
		return measurements;
	}

	/**
	 * Determines which sensors will be polled. To poll all sensors, set parameter
	 * to null or empty list
	 * 
	 * @param sensors
	 * @return
	 */
	private Set<W1Device> getSensorsToPoll(List<TemperatureSensor> sensors) {
		Set<W1Device> devices;
		if (sensors == null || sensors.isEmpty()) {
			devices = new HashSet<>(oneWireMaster.getDevices());
		} else {
			List<String> sensorsIds = sensors.stream()//
					.map(TemperatureSensor::getUuid)//
					.collect(Collectors.toList());
			devices = oneWireMaster.getDevices()//
					.stream()//
					.filter(t -> sensorsIds != null && sensorsIds.contains(t.getId()))//
					.collect(Collectors.toSet());
		}
		return devices;
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

	public W1Master getOneWireMaster() {
		return oneWireMaster;
	}

	public void setOneWireMaster(W1Master oneWireMaster) {
		this.oneWireMaster = oneWireMaster;
	}

	public ThreadStateServices getThreadServices() {
		return threadServices;
	}

	public void setThreadServices(ThreadStateServices threadServices) {
		this.threadServices = threadServices;
	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public ThreadWitnessCheckServices getWitnessServices() {
		return witnessServices;
	}

	public void setWitnessServices(ThreadWitnessCheckServices witnessServices) {
		this.witnessServices = witnessServices;
	}

}
