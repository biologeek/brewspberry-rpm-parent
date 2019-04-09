package net.brewspberry.monitoring.model;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractDevice {

	@Id
	@GeneratedValue
	protected Long id;
	protected String uuid;
	@Enumerated(EnumType.STRING)
	protected DeviceType type;
	@ManyToOne
	protected RaspberryPin pin;
	@Enumerated(EnumType.STRING)
	protected DeviceStatus pinState;
	protected LocalDateTime lastStateChangeDate, creationDate, updateDate;
	protected String name;
	protected boolean isPlugged;

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public LocalDateTime getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(LocalDateTime updateDate) {
		this.updateDate = updateDate;
	}

	public void setLastStateChangeDate(LocalDateTime lastStateChangeDate) {
		this.lastStateChangeDate = lastStateChangeDate;
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

	public LocalDateTime getLastStateChangeDate() {
		return lastStateChangeDate;
	}

	public DeviceStatus getPinState() {
		return pinState;
	}

	public void setPinState(DeviceStatus pinState) {
		this.pinState = pinState;
	}

	public RaspberryPin getPin() {
		return pin;
	}

	public void setPin(RaspberryPin pin) {
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
		uuid.replace("\\n", "");
		this.uuid = uuid;
	}

	public DeviceType getType() {
		return type;
	}

	public void setType(DeviceType type) {
		this.type = type;
	}

	public AbstractDevice creationDate(LocalDateTime creation) {
		this.creationDate = creation;
		return this;
	}

	public AbstractDevice lastChangedDate(LocalDateTime lastChange) {
		this.lastStateChangeDate = lastChange;
		return this;
	}

	public AbstractDevice id(Long id) {
		this.id = id;
		return this;
	}

	public AbstractDevice name(String name) {
		this.name = name;
		return this;
	}

	public AbstractDevice pin(RaspberryPin pin) {
		this.pin = pin;
		return this;
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
