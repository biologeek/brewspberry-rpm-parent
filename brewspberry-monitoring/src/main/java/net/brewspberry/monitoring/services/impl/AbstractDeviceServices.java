package net.brewspberry.monitoring.services.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.brewspberry.monitoring.model.AbstractDevice;
import net.brewspberry.monitoring.repositories.AbstractDeviceRepository;
import net.brewspberry.monitoring.services.DeviceService;

@Service
public class AbstractDeviceServices implements DeviceService<AbstractDevice> {
	@Autowired
	AbstractDeviceRepository repository;
	
	@Override
	public AbstractDevice getDeviceById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<AbstractDevice> listPluggedDevices() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<AbstractDevice> listAllDevices() {
		return new HashSet<AbstractDevice>((List<AbstractDevice>) this.repository.findAll());
	}

	@Override
	public AbstractDevice switchOnDevice(String uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractDevice switchOnDevice(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractDevice switchOnDevice(AbstractDevice sensor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractDevice switchOffDevice(String uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractDevice switchOffDevice(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractDevice switchOffDevice(AbstractDevice sensor) {
		// TODO Auto-generated method stub
		return null;
	}

}
