package net.brewspberry.monitoring.services.impl;

import java.util.Date;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

import net.brewspberry.monitoring.exceptions.StateChangeException;
import net.brewspberry.monitoring.model.BinarySwitch;
import net.brewspberry.monitoring.model.SwitchStatus;
import net.brewspberry.monitoring.repositories.BinarySwitchRepository;
import net.brewspberry.monitoring.services.BinarySwitchService;

@Service
public class BinarySwitchServiceImpl implements BinarySwitchService {

	@Autowired
	private GpioController controller;
	private Logger logger = Logger.getLogger(BinarySwitchServiceImpl.class.getName());
	@Autowired
	private BinarySwitchRepository repository;

	@Override
	public BinarySwitch setSwitchUp(BinarySwitch device) throws StateChangeException {

		GpioPinDigitalOutput pinOutput = controller.provisionDigitalOutputPin(RaspiPin.getPinByName(device.getPin()));
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

		GpioPinDigitalOutput pinOutput = controller.provisionDigitalOutputPin(RaspiPin.getPinByName(device.getPin()));
		if (pinOutput.getState() == PinState.HIGH) {
			logger.warning("Pin state already HIGH for " + device.getUuid() + " at pin " + pinOutput.getPin().getName());
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
		device.setLastStateChangeDate(new Date());
		
		return repository.save(device);
	}

	@Override
	public SwitchStatus getDeviceStatus(BinarySwitch device) {

		GpioPinDigitalOutput pinOutput = controller.provisionDigitalOutputPin(RaspiPin.getPinByName(device.getPin()));

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
 
}
