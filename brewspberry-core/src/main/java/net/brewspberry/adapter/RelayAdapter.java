package net.brewspberry.adapter;

import java.util.logging.Logger;

import net.brewspberry.util.Constants;
import net.brewspberry.util.LogManager;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;
import com.pi4j.wiringpi.Gpio;

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

	public PinState changePinState(GpioPinDigitalOutput pin) {

		
		pin.toggle();
		
		return pin.getState();
	}
	
	/**
	 * Changes Pin state
	 * @param pin
	 * @param newPinState
	 * @return
	 */
	public Boolean changePinState(GpioPinDigitalOutput pin, PinState newPinState) {

		Boolean result = null;

		PinState state = pin.getState();

		if (state.equals(newPinState)) {
			logger.warning("Not changing state as state="+this.getStateAsString(state));

		} else if (state.equals(PinState.LOW)) {
			pin.setState(PinState.HIGH);
			result = true;
		}else if (state.equals(PinState.HIGH)) {
			pin.setState(PinState.LOW);
			result = true;
		}

		return result;
	}

	public String getStateAsString(GpioPinDigitalOutput device) {

		String result = "";
		
		PinState state = device.getState();


		return this.getStateAsString(state);
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
	
	
	public void setShutdownOptionsandShutdown (GpioController controller, GpioPin pin, PinState state, boolean unexport){
		
		pin.setShutdownOptions(unexport, state);
		
		logger.info("Shutting down "+Constants.BREW_GPIO_TO_STR.get(pin));
		
		controller.shutdown();;
		
	}
}