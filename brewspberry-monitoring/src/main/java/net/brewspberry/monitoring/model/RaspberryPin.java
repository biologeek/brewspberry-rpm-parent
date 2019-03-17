package net.brewspberry.monitoring.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;

@Entity
public class RaspberryPin {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	/**
	 * Pi4J pin
	 */
	@Transient
	private Pin pin;
	/**
	 * Pin number starts from 1 on the upper left side to 40 on the bottom right
	 * side
	 */
	private Integer pinNumber;
	
	/**
	 * Name of the pin "GPIOxx"
	 */
	private String pinName;

	@Transient
	private Map<Integer, Pin> BREW_GPIO = new HashMap<>();

	public RaspberryPin() {
		BREW_GPIO.put(11, RaspiPin.GPIO_00);
		BREW_GPIO.put(12, RaspiPin.GPIO_01);
		BREW_GPIO.put(13, RaspiPin.GPIO_02);
		BREW_GPIO.put(15, RaspiPin.GPIO_03);
		BREW_GPIO.put(16, RaspiPin.GPIO_04);
		BREW_GPIO.put(18, RaspiPin.GPIO_05);
		BREW_GPIO.put(22, RaspiPin.GPIO_06);
		BREW_GPIO.put(7, RaspiPin.GPIO_07);
		BREW_GPIO.put(3, RaspiPin.GPIO_08);
		BREW_GPIO.put(5, RaspiPin.GPIO_09);
		BREW_GPIO.put(24, RaspiPin.GPIO_10);
		BREW_GPIO.put(26, RaspiPin.GPIO_11);
		BREW_GPIO.put(19, RaspiPin.GPIO_12);
		BREW_GPIO.put(21, RaspiPin.GPIO_13);
		BREW_GPIO.put(23, RaspiPin.GPIO_14);
		
	}

	public Pin getPin() {
		return BREW_GPIO.get(pinNumber);
	}

	public void setPin(Pin pin) {
		this.pin = pin;
		for (Entry<Integer, Pin> entry : BREW_GPIO.entrySet()) {
			if (entry.getValue().equals(pin)) {
				this.pinNumber = entry.getKey();
				this.pinName = entry.getValue().getName();
			}
		}
	}

	public Integer getPinNumber() {
		return pinNumber;
	}

	public void setPinNumber(Integer pinNumber) {
		this.pinNumber = pinNumber;
		this.pin = BREW_GPIO.get(pinNumber);
	}

	public String getPinName() {
		return pinName;
	}

	public void setPinName(String pinName) {
		this.pinName = pinName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
