package net.brewspberry.brewery.api;

import java.time.LocalDateTime;

public class StepLight {

	protected Long id;
	protected String name;
	protected String type;
	protected Quantity plannedDuration, effectiveDuration;
	protected LocalDateTime beginning, end;
	/**
	 * Step position inside the brew
	 */
	protected Integer position;
	public LocalDateTime getBeginning() {
		return beginning;
	}

	public void setBeginning(LocalDateTime beginning) {
		this.beginning = beginning;
	}

	public LocalDateTime getEnd() {
		return end;
	}

	public void setEnd(LocalDateTime end) {
		this.end = end;
	}

	public Quantity getPlannedDuration() {
		return plannedDuration;
	}

	public void setPlannedDuration(Quantity plannedDuration) {
		this.plannedDuration = plannedDuration;
	}

	public Quantity getEffectiveDuration() {
		return effectiveDuration;
	}

	public void setEffectiveDuration(Quantity effectiveDuration) {
		this.effectiveDuration = effectiveDuration;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
