package net.brewspberry.business.beans;

import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;

public enum RaspiPins {

	
	GPIO_11("11", RaspiPin.GPIO_00),
	GPIO_12("12", RaspiPin.GPIO_01),
	GPIO_13("13", RaspiPin.GPIO_02),
	GPIO_15("15", RaspiPin.GPIO_03),
	GPIO_16("16", RaspiPin.GPIO_04),
	GPIO_18("18", RaspiPin.GPIO_05),
	GPIO_22("22", RaspiPin.GPIO_06),
	GPIO_07("07", RaspiPin.GPIO_07),
	GPIO_03("03", RaspiPin.GPIO_08),
	GPIO_05("05", RaspiPin.GPIO_09),
	GPIO_24("24", RaspiPin.GPIO_10),
	GPIO_26("26", RaspiPin.GPIO_11),
	GPIO_19("19", RaspiPin.GPIO_12),
	GPIO_21("21", RaspiPin.GPIO_13),
	GPIO_23("23", RaspiPin.GPIO_14);
	
	
	private Pin pi4jPin;
	private String real;

	RaspiPins(String real, Pin pi4jPin) {
		
		this.setReal(real);
		this.setPi4jPin(pi4jPin);
	}

	public Pin getPi4jPin() {
		return pi4jPin;
	}

	public void setPi4jPin(Pin pi4jPin) {
		this.pi4jPin = pi4jPin;
	}

	public String getReal() {
		return real;
	}

	public static String[] getRealPins() {
		String[] pins = new String[RaspiPins.values().length];
		
		int i = 0;
		for(RaspiPins pin : RaspiPins.values()){
			pins[i] = pin.getReal();
			i++;
		}
		
		return pins;
	}


	public static Pin[] getRaspiPins() {
		Pin[] pins = new Pin[RaspiPins.values().length];
		
		int i = 0;
		for(RaspiPins pin : RaspiPins.values()){
			pins[i] = pin.getPi4jPin();
			i++;
		}
		
		return pins;
	}

	public void setReal(String real) {
		this.real = real;
	}
}
