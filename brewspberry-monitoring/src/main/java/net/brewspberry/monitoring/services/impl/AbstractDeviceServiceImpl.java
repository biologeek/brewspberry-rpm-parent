package net.brewspberry.monitoring.services.impl;

import java.util.Set;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import net.brewspberry.monitoring.exceptions.ServiceException;
import net.brewspberry.monitoring.model.AbstractDevice;
import net.brewspberry.monitoring.repositories.AbstractDeviceRepository;
import net.brewspberry.monitoring.services.DeviceService;

@Service
@Qualifier("AbstractDeviceServiceImpl")
public class AbstractDeviceServiceImpl implements DeviceService<AbstractDevice> {

	@Autowired
	private ApplicationContext ctx;
	@Autowired
	private AbstractDeviceRepository abstractDeviceRepository;

	@Override
	public AbstractDevice getDeviceById(Long id) {
		throw new NotYetImplementedException();
	}

	@Override
	public Set<AbstractDevice> listPluggedDevices() {
		throw new NotYetImplementedException();
	}

	@Override
	public Set<AbstractDevice> listAllDevices() {
		throw new NotYetImplementedException();
	}

	@Override
	public void saveDevice(AbstractDevice device) throws ServiceException {
		throw new NotYetImplementedException();
	}

	@Override
	public AbstractDevice updateDevice(AbstractDevice toSave, AbstractDevice saved) throws ServiceException {
		throw new NotYetImplementedException();
	}

	@Override
	public AbstractDevice getDeviceByUUID(String uuid) {
		throw new NotYetImplementedException();
	}

	private DeviceService<?> getSubService(AbstractDevice device) {
		String clazz = device.getClass().getName();
		return (DeviceService<?>) ctx
				.getBean(Character.toLowerCase(clazz.charAt(0)) + clazz.substring(1) + "ServiceImpl");
	}

	@Override
	public AbstractDevice startDevice(Long id, Float duration, Integer frequencyInSeconds) {

		AbstractDevice device = this.abstractDeviceRepository.getOne(id);
		return (AbstractDevice) getSubService(device).startDevice(id, duration, frequencyInSeconds);
	}

}
