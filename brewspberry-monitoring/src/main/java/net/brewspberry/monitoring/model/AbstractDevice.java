package net.brewspberry.monitoring.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractDevice {

	

	public static final Map<String, Pin> BREW_GPIO = new HashMap<String, Pin>();

	static {
		BREW_GPIO.put("11", RaspiPin.GPIO_00);
		BREW_GPIO.put("12", RaspiPin.GPIO_01);
		BREW_GPIO.put("13", RaspiPin.GPIO_02);
		BREW_GPIO.put("15", RaspiPin.GPIO_03);
		BREW_GPIO.put("16", RaspiPin.GPIO_04);
		BREW_GPIO.put("18", RaspiPin.GPIO_05);
		BREW_GPIO.put("22", RaspiPin.GPIO_06);
		BREW_GPIO.put("07", RaspiPin.GPIO_07);
		BREW_GPIO.put("03", RaspiPin.GPIO_08);
		BREW_GPIO.put("05", RaspiPin.GPIO_09);
		BREW_GPIO.put("24", RaspiPin.GPIO_10);
		BREW_GPIO.put("26", RaspiPin.GPIO_11);
		BREW_GPIO.put("19", RaspiPin.GPIO_12);
		BREW_GPIO.put("21", RaspiPin.GPIO_13);
		BREW_GPIO.put("23", RaspiPin.GPIO_14);
	};
	
	@Id
	@GeneratedValue
	protected Long id;
	protected String uuid;
	protected DeviceType type;
	protected String pin;
	protected int pinAddress;
	@Enumerated(EnumType.STRING)
	protected DeviceStatus pinState;
	protected Date lastStateChangeDate, creationDate, updateDate;
	protected String name;
	protected boolean isPlugged;
	

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public boolean isPlugged() {
		return isPlugged;
	}

	public void setPlugged(boolean isPlugged) {
		this.isPlugged = isPlugged;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getLastStateChangeDate() {
		return lastStateChangeDate;
	}

	public void setLastStateChangeDate(Date lastStateChangeDate) {
		this.lastStateChangeDate = lastStateChangeDate;
	}

	public DeviceStatus getPinState() {
		return pinState;
	}

	public void setPinState(DeviceStatus pinState) {
		this.pinState = pinState;
	}

	public int getPinAddress() {
		return pinAddress;
	}

	public void setPinAddress(int pinAddress) {
		this.pinAddress = pinAddress;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public DeviceType getType() {
		return type;
	}

	public void setType(DeviceType type) {
		this.type = type;
	}

	
	public AbstractDevice uuid(String t) {
		this.uuid = t;
		return this;
	}
	public AbstractDevice type(DeviceType temperatureSensor) {
		this.type = temperatureSensor;
		return this;
	}

	public AbstractDevice plugged(boolean b) {
		this.isPlugged = b;
		return this;
	}
}
