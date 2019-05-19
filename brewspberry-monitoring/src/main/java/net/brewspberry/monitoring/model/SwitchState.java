package net.brewspberry.monitoring.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class SwitchState {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@ManyToOne
	private BinarySwitch device;
	@Enumerated(EnumType.ORDINAL)
	private SwitchStatus statusTo;
	private LocalDateTime date;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BinarySwitch getDevice() {
		return device;
	}

	public void setDevice(BinarySwitch device) {
		this.device = device;
	}

	public SwitchStatus getStatusTo() {
		return statusTo;
	}

	public void setStatusTo(SwitchStatus statusTo) {
		this.statusTo = statusTo;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

}
