package net.brewspberry.monitoring.daemons;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.util.Assert;

import com.pi4j.io.w1.W1Device;
import com.pi4j.io.w1.W1Master;

import net.brewspberry.monitoring.exceptions.DeviceNotFoundException;
import net.brewspberry.monitoring.exceptions.MonitoringException;
import net.brewspberry.monitoring.exceptions.TechnicalException;
import net.brewspberry.monitoring.model.TemperatureMeasurement;
import net.brewspberry.monitoring.model.TemperatureSensor;
import net.brewspberry.monitoring.model.ThreadState;
import net.brewspberry.monitoring.model.ThreadWitness;
import net.brewspberry.monitoring.repositories.TemperatureMeasurementRepository;
import net.brewspberry.monitoring.services.ThreadStateServices;
import net.brewspberry.monitoring.services.ThreadWitnessCheckServices;
import net.brewspberry.monitoring.services.impl.DS18B20TemperatureSensorServicesImpl;

/**
 * Daemon thread that will regularly poll sensor to get temperature
 * 
 */
public class TemperatureDaemonThread implements Runnable/* , JmsDaemon<TemperatureMeasurement> */ {

	private volatile TemperatureMeasurementRepository repository;

	private volatile Map<String, Object> parameters;
	private volatile W1Master oneWireMaster;
	private volatile ThreadStateServices threadServices;
	private volatile ThreadWitnessCheckServices witnessServices;
	private String uuid = UUID.randomUUID().toString();

	private Logger logger = Logger.getLogger(TemperatureDaemonThread.class.getName());

	public TemperatureDaemonThread() {
	}

	public TemperatureDaemonThread(TemperatureMeasurementRepository repository, Map<String, Object> parameters,
			W1Master oneWireMaster, ThreadStateServices threadServices, ThreadWitnessCheckServices witnessServices) {
		super();
		this.repository = repository;
		this.parameters = parameters;
		this.oneWireMaster = oneWireMaster;
		this.threadServices = threadServices;
		this.witnessServices = witnessServices;
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * Periodic polling of sensor temperature using passed parameters (at least
	 * duration, polling frequency and list of sensors)<br>
	 * <br>
	 * In case thread params are not complete or invalid, thread is interrupted.
	 * <br>
	 * <br>
	 * If a thread already exists for device, it does not start.
	 * 
	 */
	public void run() {
		logger.info("Starting thread with UUID " + uuid);
		try {
			checkParams();
		} catch (Exception e) {
			Thread.currentThread().interrupt();
		}

		Calendar endCal = Calendar.getInstance();

		endCal.add(Calendar.MILLISECOND, (int) ((Duration) parameters.get(TemperatureSensor.DURATION)).toMillis());
		List<TemperatureSensor> sensorsList = (List<TemperatureSensor>) parameters.get(TemperatureSensor.DEVICE_LIST);
		while (new Date().before(endCal.getTime())) {

			logger.info("New cycle for " + uuid + " at date "
					+ new SimpleDateFormat("yyyy-MM-dd HH:MM:ss").format(new Date()));
			logger.info("Device list : "
					+ sensorsList.stream().map(t -> t.getUuid()).reduce((p1, p2) -> p1 + "," + p2).get());

			try {
				prePolling();

				List<TemperatureMeasurement> measured = pollSensors(sensorsList);
				logger.info(measured.stream().map(t -> t.getSensor().getUuid() + " : "+t.getTemperature()+ "°C").reduce((a,p)-> a+", "+ p).orElse("No temperature !!")); 
				saveMeasurements(measured);
				threadServices.writeState(ThreadState.noError(uuid));
				Thread.sleep(((Duration) parameters.get(TemperatureSensor.FREQUENCY)).toMillis());
			} catch (MonitoringException e) {
				e.printStackTrace();
				ThreadState threadState = threadServices.readState(e.getMessage());
				threadServices.writeState(
						ThreadState.xErrors(threadState.getErrorOccurence(), e.getDeviceUuid(), e.getMessage()));

				continue;
			} catch (InterruptedException e) {
				e.printStackTrace();
				logger.severe("Thread sleep interrupted !!");
			} catch (TechnicalException e1) {
				continue;
			}
		}

		finalizeThread(sensorsList);
	}

	/**
	 * Operations to execute before polling. <br>
	 * <br>
	 * In this case if witness is no more present, interrupt the thread.
	 */
	private void prePolling() {
		ThreadWitness witness = witnessServices.checkWitness(this.uuid);
		if (witness != null)
			Thread.currentThread().interrupt();
	}

	/**
	 * Called at the end of thread execution, this method releases file locks
	 * 
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
		Assert.notNull(repository, "temperatureMeasurementRepository is null !");
		Assert.notNull(oneWireMaster, "1-Wire master is null !");
		Assert.notNull(threadServices, "ThreadStateService is null !");
		Assert.notEmpty(parameters, "No parameters provided !");
		for (String prm : TemperatureSensor.MANDATORY_REGULAR_POLL_PARAMETERS) {
			Assert.notNull(parameters.get(prm), prm + " is null !");
		}
	}

	private void saveMeasurements(List<TemperatureMeasurement> measured) {
		repository.saveAll(measured);
	}

	/**
	 * Determines which devices to poll and poll them
	 * 
	 * @param sensors
	 * @param frequency
	 * @return
	 * @throws MonitoringException
	 * @throws DeviceNotFoundException
	 * @throws IOException
	 */
	private List<TemperatureMeasurement> pollSensors(List<TemperatureSensor> sensors) throws MonitoringException {

		List<TemperatureMeasurement> measurements = new ArrayList<>(0);

		Set<W1Device> devices = getSensorsToPoll(sensors);

		for (W1Device device : devices) {

			TemperatureSensor currentSensor = null;
			try {
				currentSensor = sensors//
						.stream()//
						.filter(t -> t.getUuid().equals(device.getId())).findFirst()//
						.orElseThrow(() -> new DeviceNotFoundException(device.getId()));

				measurements.add(new TemperatureMeasurement()//
						.date(LocalDateTime.now())//
						.sensor(currentSensor)//
						.temperature(DS18B20TemperatureSensorServicesImpl.convertRawTemperature(device.getValue()))//
				);
			} catch (DeviceNotFoundException e) {
				e.printStackTrace();
				continue;
			} catch (IOException e) {
				throw new MonitoringException(currentSensor.getUuid(), e);
			} //
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
					.filter(t -> {
						
						logger.info("SensorIds : "+sensorsIds+", current : "+t.getId());
						return sensorsIds != null && sensorsIds.contains(t.getId());//
					})
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
