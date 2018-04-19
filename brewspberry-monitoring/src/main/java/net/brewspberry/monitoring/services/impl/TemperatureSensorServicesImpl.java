package net.brewspberry.monitoring.services.impl;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pi4j.io.w1.W1BaseDevice;
import com.pi4j.io.w1.W1Device;
import com.pi4j.io.w1.W1Master;

import net.brewspberry.monitoring.exceptions.DeviceNotFoundException;
import net.brewspberry.monitoring.exceptions.ServiceException;
import net.brewspberry.monitoring.model.TemperatureMeasurement;
import net.brewspberry.monitoring.model.TemperatureSensor;
import net.brewspberry.monitoring.repositories.TemperatureSensorRepository;
import net.brewspberry.monitoring.services.TemperatureSensorService;

@Service
public class TemperatureSensorServicesImpl implements TemperatureSensorService {

	@Autowired
	private TemperatureSensorRepository repository;

	@Autowired
	private W1Master oneWireMaster;

	private Logger logger = Logger.getLogger(this.getClass().getName());

	@Override
	public TemperatureSensor getDeviceById(Long id) {
		return repository.findById(id).get();
	}

	@Override
	public Set<TemperatureSensor> listPluggedDevices() {
		return new HashSet<TemperatureSensor>((Collection<? extends TemperatureSensor>) repository.findAll());
	}

	@Override
	public TemperatureSensor switchOnDevice(String uuid) {
		TemperatureSensor sensor = repository.findByUuid(uuid);
		return switchOnDevice(sensor);
	}

	@Override
	public TemperatureSensor switchOnDevice(Long id) {
		return switchOnDevice(getDeviceById(id));
	}

	@Override
	public TemperatureSensor switchOnDevice(TemperatureSensor sensor) {
		logger.info("Useless");
		return sensor;
	}

	@Override
	public TemperatureSensor switchOffDevice(String uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TemperatureSensor switchOffDevice(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TemperatureSensor switchOffDevice(TemperatureSensor sensor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TemperatureMeasurement getTemperatureForDevice(TemperatureSensor sensor)
			throws DeviceNotFoundException, ServiceException {
		W1Device foundDevice = null;
		TemperatureMeasurement measurement = null;
		try {
			foundDevice = oneWireMaster.getDevices(0x28)// Take only DS18B20
					.stream()//
					.filter(t -> t.getId().equals(sensor.getUuid()))//
					.findFirst()//
					.get();
			measurement = new TemperatureMeasurement()//
					.date(new Date())//
					.temperature(convertRawTemperature(foundDevice.getValue()))//
					.sensor(sensor);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ServiceException("Read write issue");
		} catch (NoSuchElementException e) {
			throw new DeviceNotFoundException(sensor.getUuid());
		}

		return measurement;
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
		
		
	}

}
