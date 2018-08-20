package net.brewspberry.monitoring.services.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.brewspberry.monitoring.exceptions.ServiceException;
import net.brewspberry.monitoring.model.AbstractDevice;
import net.brewspberry.monitoring.model.TemperatureSensor;
import net.brewspberry.monitoring.repositories.AbstractDeviceRepository;
import net.brewspberry.monitoring.services.DeviceService;

@Service
/**
 * Service used for all devices (example find all devices, save one, ...)
 *
 */
public class AbstractDeviceServices implements DeviceService<AbstractDevice> {
	@Autowired
	AbstractDeviceRepository repository;
	
	@Override
	public AbstractDevice getDeviceById(Long id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<AbstractDevice> listPluggedDevices() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<AbstractDevice> listAllDevices() {
		return new HashSet<AbstractDevice>((List<AbstractDevice>) this.repository.findAll());
	}

	@Override
	public void saveDevice(AbstractDevice device) throws ServiceException {
		throw new UnsupportedOperationException();
	}

	@Override
	public TemperatureSensor getDeviceByUUID(String uuid) {
		throw new UnsupportedOperationException();
	}
}
