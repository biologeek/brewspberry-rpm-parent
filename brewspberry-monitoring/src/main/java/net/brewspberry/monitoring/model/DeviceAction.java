package net.brewspberry.monitoring.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * An action performed by user or automatically. <br>
 * <br>
 * It can represent a physical (device switch ) or logical (temperature sensor
 * switch) action
 * 
 * @author xavier
 *
 */
@Entity
public class DeviceAction {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Enumerated(EnumType.STRING)
	private DeviceStatus status;

	private LocalDateTime date;
	@ManyToOne
	private AbstractDevice device;

	public DeviceStatus getStatus() {
		return status;
	}

	public void setStatus(DeviceStatus status) {
		this.status = status;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public AbstractDevice getDevice() {
		return device;
	}

	public void setDevice(AbstractDevice device) {
		this.device = device;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
