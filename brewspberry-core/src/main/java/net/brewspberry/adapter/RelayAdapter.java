package net.brewspberry.adapter;

import java.util.logging.Logger;

import net.brewspberry.util.LogManager;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;

public class RelayAdapter {
	/**
	 * This class acts as a layer over Pi4J API.
	 * 
	 * Allows to handle actions over relays, change states, get current state,
	 * ...
	 */

	static RelayAdapter instance;
	final GpioController gpioController = GpioFactory.getInstance();
	static final Logger logger = LogManager.getInstance(RelayAdapter.class.getName());

	public RelayAdapter() {
		// TODO Auto-generated constructor stub
	}

	public static RelayAdapter getInstance() {

		if (instance == null)
			instance = new RelayAdapter();

		return instance;
	}

	public Pin getDevicePin() {
		return null;
	}

	public Boolean changePinState(Pin pin) {

		Boolean result = null;
		GpioPinDigitalOutput relay = gpioController
				.provisionDigitalOutputPin(pin);

		PinState state = relay.getState();

		if (state == PinState.HIGH) {
			relay.setState(PinState.LOW);
			result = false;

		} else if (state == PinState.LOW) {
			relay.setState(PinState.HIGH);
			result = true;
		}

		return result;
	}
	
	/**
	 * Changes Pin state
	 * @param pin
	 * @param newPinState
	 * @return
	 */
	public Boolean changePinState(Pin pin, PinState newPinState) {

		Boolean result = null;
		GpioPinDigitalOutput relay = gpioController
				.provisionDigitalOutputPin(pin);

		PinState state = relay.getState();

		if (state == newPinState) {
			logger.info("Not changing state as state="+this.getStateAsString(state));

		} else if (state == PinState.LOW) {
			relay.setState(PinState.HIGH);
			result = true;
		}

		return result;
	}

	public String getStateAsString(Pin device) {

		String result = "";
		GpioPinDigitalOutput relay = gpioController
				.provisionDigitalOutputPin(device);

		PinState state = relay.getState();

		switch (state) {

		case HIGH:

			result = "HIGH";
			break;
		case LOW:

			result = "LOW";
			break;
		default:

			result = "LOW";
			break;

		}
		return result;
	}
	
	public String getStateAsString(PinState state) {

		String result = "";

		
		switch (state) {

		case HIGH:

			result = "HIGH";
			break;
		case LOW:

			result = "LOW";
			break;
		default:

			result = "LOW";
			break;

		}
		return result;
	}
}
