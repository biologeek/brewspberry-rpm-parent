package net.brewspberry.monitoring.services.impl;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.logging.Logger;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;

import net.brewspberry.monitoring.exceptions.ServiceException;
import net.brewspberry.monitoring.exceptions.StateChangeException;
import net.brewspberry.monitoring.model.BinarySwitch;
import net.brewspberry.monitoring.model.SwitchStatus;
import net.brewspberry.monitoring.repositories.BinarySwitchRepository;
import net.brewspberry.monitoring.services.BinarySwitchService;

/**
 * Service that handles operations on binary switches.
 * 
 * <br>
 * <br>
 * For Raspberry Pi, pin state = PinState.LOW means relay is closed (device is
 * UP)
 *
 */

@Service
@Qualifier("binarySwitchServiceImpl")
public class BinarySwitchServiceImpl implements BinarySwitchService {

	private GpioController controller;
	private Logger logger = Logger.getLogger(BinarySwitchServiceImpl.class.getName());
	@Autowired
	private BinarySwitchRepository repository;

	public BinarySwitchServiceImpl() {
		controller = GpioFactory.getInstance();
	}

	@Override
	public BinarySwitch setSwitchUp(BinarySwitch device) throws StateChangeException {

		GpioPinDigitalOutput pinOutput = controller.provisionDigitalOutputPin(device.getPin().getPin());
		if (pinOutput.getState() == PinState.LOW) {
			logger.warning("Pin state already LOW for " + device.getUuid() + " at pin " + pinOutput.getPin().getName());
			return device;
		} else {
			try {
				pinOutput.low();
				return changeState(device, SwitchStatus.UP);
			} catch (Exception e) {
				logger.severe("Got an error during state change to LOW for " + device.getUuid() + " at pin "
						+ pinOutput.getPin().getName());
				e.printStackTrace();
				throw new StateChangeException(e.getMessage());
			}
		}
	}

	@Override
	public BinarySwitch setSwitchDown(BinarySwitch device) throws StateChangeException {

		GpioPinDigitalOutput pinOutput = controller.provisionDigitalOutputPin(device.getPin().getPin());
		if (pinOutput.getState() == PinState.HIGH) {
			logger.warning(
					"Pin state already HIGH for " + device.getUuid() + " at pin " + pinOutput.getPin().getName());
			return device;
		} else {
			try {
				pinOutput.high();
				return changeState(device, SwitchStatus.DOWN);
			} catch (Exception e) {
				logger.severe("Got an error during state change to HIGH for " + device.getUuid() + " at pin "
						+ pinOutput.getPin().getName());
				e.printStackTrace();
				throw new StateChangeException(e.getMessage());
			}
		}
	}

	/**
	 * Updates JPA-managed bean to save state and change date
	 * 
	 * @param device
	 * @param state
	 * @return
	 */
	private BinarySwitch changeState(BinarySwitch device, SwitchStatus state) {
		device.setSwitchStatus(state);
		device.setLastStateChangeDate(LocalDateTime.now());

		return repository.save(device);
	}

	@Override
	public SwitchStatus getDeviceStatus(BinarySwitch device) {

		GpioPinDigitalOutput pinOutput = controller.provisionDigitalOutputPin(device.getPin().getPin());

		if (pinOutput.getState() == PinState.HIGH)
			return SwitchStatus.DOWN;
		else
			return SwitchStatus.UP;
	}

	public GpioController getController() {
		return controller;
	}

	public void setController(GpioController controller) {
		this.controller = controller;
	}

	public BinarySwitchRepository getRepository() {
		return repository;
	}

	public void setRepository(BinarySwitchRepository repository) {
		this.repository = repository;
	}

	@Override
	public BinarySwitch getDeviceById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<BinarySwitch> listPluggedDevices() {
		return null;
	}

	@Override
	public Set<BinarySwitch> listAllDevices() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveDevice(BinarySwitch device) throws ServiceException {

	}

	@Override
	public BinarySwitch getDeviceByUUID(String uuid) {
		return null;
	}

	@Override
	public BinarySwitch updateDevice(BinarySwitch toSave, BinarySwitch saved) throws ServiceException {
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
	private void mergeSensor(BinarySwitch toSave, BinarySwitch saved) {

		saved.setLastStateChangeDate(toSave.getLastStateChangeDate());
		saved.setName(toSave.getName());
		saved.setPin(toSave.getPin());
		saved.setPlugged(toSave.isPlugged());
		saved.setPinState(toSave.getPinState());
		saved.setUpdateDate(LocalDateTime.now());
	}

	private void validateSensor(BinarySwitch sensor) {
		if (LocalDateTime.now().isBefore(sensor.getCreationDate())) {
			throw new ValidationException("creation.date.future");
		}
	}

	@Override
	public BinarySwitch startDevice(BinarySwitch device, Long duration, Integer frequencyInSeconds) {
		if (duration == null) {
			return startDeviceForUndefinedPeriod(device);
		} else {
			return startDeviceForPeriod(device, duration);
		}
	}

	private BinarySwitch startDeviceForPeriod(BinarySwitch device, Long duration) {
		
		return null;
	}

	private BinarySwitch startDeviceForUndefinedPeriod(BinarySwitch device) {

		return null;
	}

	@Override
	public BinarySwitch stopDevice(BinarySwitch device) {
		// TODO Auto-generated method stub
		return null;
	}

}
