package net.brewspberry.monitoring.model;

import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractDevice {

	@Id
	@GeneratedValue
	private Long id;
	private String uuid;
	private DeviceType type;
	private String pin;
	private int pinAddress;
	@Enumerated(EnumType.STRING)
	private SwitchStatus pinState;
	private Date lastStateChangeDate;

	public Date getLastStateChangeDate() {
		return lastStateChangeDate;
	}

	public void setLastStateChangeDate(Date lastStateChangeDate) {
		this.lastStateChangeDate = lastStateChangeDate;
	}

	public SwitchStatus getPinState() {
		return pinState;
	}

	public void setPinState(SwitchStatus pinState) {
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

}
