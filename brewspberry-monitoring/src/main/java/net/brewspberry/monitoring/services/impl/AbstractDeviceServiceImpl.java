package net.brewspberry.monitoring.services.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import net.brewspberry.monitoring.model.AbstractDevice;
import net.brewspberry.monitoring.repositories.AbstractDeviceRepository;
import net.brewspberry.monitoring.services.CommonDeviceService;
import net.brewspberry.monitoring.services.DeviceService;

@Service
@Qualifier("abstractDeviceServiceImpl")
public class AbstractDeviceServiceImpl implements CommonDeviceService {

	@Autowired
	private ApplicationContext ctx;
	@Autowired
	private AbstractDeviceRepository abstractDeviceRepository;

	/**
	 * Factory that injects concrete device service using its Spring qualifier
	 * @param device
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private <T extends AbstractDevice> DeviceService<T> getSubService(AbstractDevice device) {
		String clazz = device.getClass().getName();
		return (DeviceService<T>) ctx
				.getBean(Character.toLowerCase(clazz.charAt(0)) + clazz.substring(1) + "ServiceImpl");
	}

	@Override
	public AbstractDevice startDevice(Long id, Float duration, Integer frequencyInSeconds) {
		AbstractDevice device = this.abstractDeviceRepository.getOne(id);
		return (AbstractDevice) getSubService(device).startDevice(device, duration, frequencyInSeconds);
	}

	@Override
	public AbstractDevice stopDevice(Long deviceId) {
		AbstractDevice device = this.abstractDeviceRepository.getOne(deviceId);
		return (AbstractDevice) getSubService(device).stopDevice(device);
	}

	@Override
	public Set<AbstractDevice> listAllDevices() {
		return (Set<AbstractDevice>) this.abstractDeviceRepository.findAll();
	}

	@Override
	public AbstractDevice saveDevice(AbstractDevice device) {
		Assert.notNull(device.getPin(), "Device pin is null");
		Assert.notNull(device.getUuid(), "UUID is null");
		return this.abstractDeviceRepository.save(device);
	}

}
