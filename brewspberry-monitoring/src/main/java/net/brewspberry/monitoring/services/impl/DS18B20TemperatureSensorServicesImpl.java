package net.brewspberry.monitoring.services.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.pi4j.io.w1.W1Device;
import com.pi4j.io.w1.W1Master;

import net.brewspberry.monitoring.daemons.TemperatureDaemonThread;
import net.brewspberry.monitoring.exceptions.DeviceNotFoundException;
import net.brewspberry.monitoring.exceptions.ServiceException;
import net.brewspberry.monitoring.model.DeviceType;
import net.brewspberry.monitoring.model.TemperatureMeasurement;
import net.brewspberry.monitoring.model.TemperatureSensor;
import net.brewspberry.monitoring.repositories.TemperatureMeasurementRepository;
import net.brewspberry.monitoring.repositories.TemperatureSensorRepository;
import net.brewspberry.monitoring.services.TemperatureSensorService;
import net.brewspberry.monitoring.services.ThreadStateServices;
import net.brewspberry.monitoring.services.ThreadWitnessServices;
import net.brewspberry.monitoring.services.tech.TemperatureMeasurementJmsService;

@Service
/**
 * Service that handles operations around DS18B20 temperature sensors
 *
 */
public class DS18B20TemperatureSensorServicesImpl implements TemperatureSensorService {

	private static final int DS18B20_CONSTANT = 0x28;

	@Autowired
	TemperatureSensorRepository repository;
	
	@Autowired
	TemperatureMeasurementRepository temperatureRepository;

	@Autowired
	W1Master oneWireMaster;

	Logger logger = Logger.getLogger(this.getClass().getName());

	@Value("${thread.dump.folder}")
	String threadDumpFolder;

	@Autowired
	TemperatureMeasurementJmsService jmsService;

	@Autowired
	ThreadStateServices threadStateService;
	@Autowired
	ThreadWitnessServices threadWitnessService;

	@Autowired
	@Qualifier("entityManager")
	/**
	 * Used only for injection purposes
	 */
	EntityManager em;

	@Override // TODO Auto-generated method stub
	public TemperatureSensor getDeviceById(Long id) {
		return repository.findById(id).get();
	}

	/**
	 * Lists plugged devices (only DS18B20 and compares with those parametered. Plugged devices
	 * are automatically saved as new devices
	 */
	@Override
	public Set<TemperatureSensor> listPluggedDevices() {

		List<W1Device> pluggedDevices = oneWireMaster.getDevices(DS18B20_CONSTANT);

		List<TemperatureSensor> savedSensors = (List<TemperatureSensor>) repository.findAll();
		Set<TemperatureSensor> pluggedAndSaved = new HashSet<>(0);

		pluggedDevices.stream()//
				.forEach(new Consumer<W1Device>() {
					@Override
					public void accept(W1Device t) {
						// If device is already saved, add it
						TemperatureSensor isSaved = savedSensors.stream()//
								.filter(u -> u.getUuid().equals(t.getId()))//
								.findFirst().orElse(null);

						if (isSaved != null) {
							pluggedAndSaved.add(isSaved);
						} else if (isSaved == null && t.getFamilyId() == 0x28) {
							// if not, add it as a new device
							TemperatureSensor toSave = (TemperatureSensor) new TemperatureSensor()//
									.uuid(t.getId())//
									.type(DeviceType.TEMPERATURE_SENSOR);

							repository.save(toSave);
							pluggedAndSaved.add(toSave);
						}
					}
				});

		return pluggedAndSaved;
	}

	@Override
	public TemperatureMeasurement getTemperatureForDevice(TemperatureSensor sensor)
			throws DeviceNotFoundException, ServiceException {
		W1Device foundDevice = null;
		TemperatureMeasurement measurement = null;
		try {
			measurement = findDeviceTemperatureByDeviceUUIDs(Arrays.asList(sensor)).get(0);
		} catch (NoSuchElementException e) {
			throw new DeviceNotFoundException(sensor.getUuid());
		}

		return measurement;
	}

	private List<TemperatureMeasurement> findDeviceTemperatureByDeviceUUIDs(List<TemperatureSensor> sensor) {
		List<W1Device> foundDevices;
		List<TemperatureMeasurement> measurements = new ArrayList<>();
		if (sensor != null && sensor.size() > 0) {
			List<String> ids = sensor.stream().map(TemperatureSensor::getUuid).collect(Collectors.toList());
			foundDevices = oneWireMaster.getDevices(DS18B20_CONSTANT)// Take only DS18B20
					.stream()//
					.filter(t -> ids.contains(t.getId())).collect(Collectors.toList());
			sensor.stream().forEach(new Consumer<TemperatureSensor>() {
				@Override
				public void accept(TemperatureSensor temp) {
					W1Device correspondingDevice = foundDevices.stream().filter(t -> t.getId().equals(temp.getUuid()))
							.findFirst().get();
					try {
						measurements.add(new TemperatureMeasurement()//
								.date(new Date())//
								.temperature(convertRawTemperature(correspondingDevice.getValue()))//
								.sensor(temp));
					} catch (IOException e) {
						e.printStackTrace();
					}
	
				}
			});
		}
		return measurements;
	}

	public static Float convertRawTemperature(String value) {
		return Float.valueOf(value) / 100;
	}

	public TemperatureSensorRepository getRepository() {
		return repository;
	}

	public void setRepository(TemperatureSensorRepository repository) {
		this.repository = repository;
	}

	public W1Master getOneWireMaster() {
		return oneWireMaster;
	}

	public void setOneWireMaster(W1Master oneWireMaster) {
		this.oneWireMaster = oneWireMaster;
	}

	@Override
	public void runRegularTemperatureMeasurement(List<TemperatureSensor> sensors, Map<String, Object> parameters) {
		String threadUUID = UUID.randomUUID().toString();
		TemperatureDaemonThread target = new TemperatureDaemonThread();
		target.setJmsService(jmsService);
		target.setOneWireMaster(oneWireMaster);
		target.setParameters(parameters);
		target.setEm(em);
		target.setThreadServices(threadStateService);
		logger.info("Starting regular polling with UUID=" + threadUUID + " parameters : " + parameters);

		try {
			threadWitnessService.witnessThreadStart(threadUUID);
			Thread t = new Thread(target, target.getUuid());
			t.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void runRegularTemperatureMeasurementStr(List<String> devices, Map<String, Object> parameters) {
		List<TemperatureSensor> sensors = devices.stream()//
				.map(str -> this.repository.findByUuid(str))//
				.collect(Collectors.toList());
		runRegularTemperatureMeasurement(sensors, parameters);
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public String getThreadDumpFolder() {
		return threadDumpFolder;
	}

	public void setThreadDumpFolder(String threadDumpFolder) {
		this.threadDumpFolder = threadDumpFolder;
	}

	@Override
	public Set<TemperatureSensor> listAllDevices() {
		return new HashSet<>((List<TemperatureSensor>) repository.findAll());
	}

	@Override
	public List<TemperatureMeasurement> getTemperatureForDevices(List<Long> deviceIds) {
		List<TemperatureSensor> sensors = findDevicesByIds(deviceIds);
		return findDeviceTemperatureByDeviceUUIDs(sensors);
	}

	/**
	 * Method that returns devices using their IDs
	 * 
	 * @param deviceIds
	 *            devices technical IDs
	 * @return a {@link List} of {@link TemperatureSensor}
	 */
	private List<TemperatureSensor> findDevicesByIds(List<Long> deviceIds) {
		return (List<TemperatureSensor>) repository.findAllById(deviceIds);
	}

	@Override
	public List<TemperatureMeasurement> getTemperatureForDevicesUuids(List<String> deviceUuids, boolean saveMeasurements) {
		List<TemperatureSensor> sensors = findDevicesByUuids(deviceUuids);
		List<TemperatureMeasurement> measurements = findDeviceTemperatureByDeviceUUIDs(sensors);
		if (saveMeasurements) {
			this.temperatureRepository.saveAll(measurements);
		}
		return measurements;
	}

	private List<TemperatureSensor> findDevicesByUuids(List<String> deviceUuids) {
		return (List<TemperatureSensor>) repository.findAllByUuid(deviceUuids);
	}

	@Override
	public void saveDevice(TemperatureSensor sensor) throws ServiceException {
		Assert.notNull(sensor, "sensor.null");
		checkUuid(sensor.getUuid());
		TemperatureSensor res = this.repository.save(sensor);
		if (res == null || res.getId() == null)
			throw new ServiceException("sensor.save.error");
	}

	@Override
	public TemperatureSensor getDeviceByUUID(String uuid) {
		checkUuid(uuid);
		return this.repository.findByUuid(uuid);
	}

	private void checkUuid(String uuid) {
		Assert.notNull(uuid, "device.uuid.null");
		Assert.isTrue(uuid.startsWith(String.valueOf(DS18B20_CONSTANT)), "device.uuid.invalid");
	}

}
