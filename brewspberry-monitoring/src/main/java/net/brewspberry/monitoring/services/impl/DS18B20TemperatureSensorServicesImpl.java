package net.brewspberry.monitoring.services.impl;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.validation.ValidationException;

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
import net.brewspberry.monitoring.exceptions.TechnicalException;
import net.brewspberry.monitoring.model.DeviceStatus;
import net.brewspberry.monitoring.model.DeviceType;
import net.brewspberry.monitoring.model.TemperatureMeasurement;
import net.brewspberry.monitoring.model.TemperatureSensor;
import net.brewspberry.monitoring.repositories.TemperatureMeasurementRepository;
import net.brewspberry.monitoring.repositories.TemperatureSensorRepository;
import net.brewspberry.monitoring.services.TemperatureSensorService;
import net.brewspberry.monitoring.services.ThreadStateServices;
import net.brewspberry.monitoring.services.ThreadWitnessServices;

/**
 * Service that handles operations around DS18B20 temperature sensors
 *
 */
@Service
@Qualifier("temperatureSensorServiceImpl")
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
	ThreadStateServices threadStateService;
	@Autowired
	ThreadWitnessServices threadWitnessService;


	@Override 
	public TemperatureSensor getDeviceById(Long id) {
		return repository.findById(id).get();
	}

	/**
	 * Lists plugged devices (only DS18B20 and compares with those parametered.
	 * Plugged devices are automatically saved as new devices
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
								.findFirst()//
								.orElse(null);

						if (isSaved != null) {
							pluggedAndSaved.add(isSaved);
						} else if (isSaved == null && t.getFamilyId() == 0x28) {
							// if not, add it as a new device
							TemperatureSensor toSave = (TemperatureSensor) new TemperatureSensor()//
									.uuid(t.getId().substring(0, t.getId().length() - 2))//
									.type(DeviceType.TEMPERATURE_SENSOR)//
									.plugged(true);

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
		target.setOneWireMaster(oneWireMaster);
		target.setParameters(parameters);
		target.setThreadServices(threadStateService);
		logger.info(String.format("Starting regular polling with UUID={} parameters : {}", threadUUID, parameters));

		try {
			threadWitnessService.witnessThreadStart(threadUUID);
			Thread t = new Thread(target, target.getUuid());
			t.start();
		} catch (IllegalAccessException e) {
			// thrown in case device is already started
			logger.warning("device already started");
		} catch (TechnicalException e) {
			logger.severe(e.getMessage());
		}
	}

	@Override
	public void runRegularTemperatureMeasurementStr(String device, Map<String, Object> parameters) {
		TemperatureSensor sensors = this.repository.findByUuid(device);
		runRegularTemperatureMeasurement(Arrays.asList(sensors), parameters);
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
	public List<TemperatureMeasurement> getTemperatureForDevicesUuids(List<String> deviceUuids,
			boolean saveMeasurements) {
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
			throw new ServiceException("Failed to save sensor", "sensor.save.error");
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

	@Override
	public TemperatureSensor updateDevice(TemperatureSensor toSave, TemperatureSensor saved) throws ServiceException {
		if (toSave == null)
			return saved;

		if (!saved.getId().equals(toSave.getId()))
			throw new ServiceException("Failed to update sensor", "id.different");

		validateSensor(toSave);

		mergeSensor(toSave, saved);

		return repository.save(saved);
	}

	/**
	 * Method in charge of merging to-save sensor and saved one. Handles specific
	 * cases where additional operations are necesary
	 * 
	 * @param toSave
	 * @param saved
	 */
	void mergeSensor(TemperatureSensor toSave, TemperatureSensor saved) {

		saved.setLastStateChangeDate(toSave.getLastStateChangeDate());
		saved.setName(toSave.getName());
		saved.setPin(toSave.getPin());
		saved.setPlugged(toSave.isPlugged());
		saved.setPinState(toSave.getPinState());
		saved.setUpdateDate(new Date());
	}

	void validateSensor(TemperatureSensor sensor) {
		if (sensor.getUuid() != null) {
			sensor.getUuid().matches(TemperatureSensor.DS18B20_UUID_REGEX);
		}

		if (new Date().before(sensor.getCreationDate())) {
			throw new ValidationException("creation.date.future");
		}
	}

	@Override
	public TemperatureSensor startDevice(TemperatureSensor sensor, Float duration, Integer frequencyInSeconds) {
		runRegularTemperatureMeasurement(Arrays.asList(sensor), bodyToParameters(duration, frequencyInSeconds, null));
		sensor.setPinState(DeviceStatus.RUNNING);
		sensor.setLastStateChangeDate(new Date());
		return repository.save(sensor);
	}

	@Override
	public TemperatureSensor stopDevice(TemperatureSensor device) {
		this.threadStateService.cleanState(device.getUuid());
		device.setPinState(DeviceStatus.STOPPED);
		device.setLastStateChangeDate(new Date());
		return this.repository.save(device);
	}

	private Map<String, Object> bodyToParameters(Float duration, Integer frequency, Long externalId) {
		Map<String, Object> params = new HashMap<>();
		params.put(TemperatureSensor.DURATION, duration * 1000);
		params.put(TemperatureSensor.EXTERNAL_ID, externalId);
		params.put(TemperatureSensor.FREQUENCY, Duration.ofSeconds(frequency));
		return params;
	}

}
