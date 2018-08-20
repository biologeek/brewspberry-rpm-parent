package net.brewspberry.brewery.api;

public class StepLight {

	protected Long id;
	protected String name;
	protected String type;
	protected Quantity plannedDuration, effectiveDuration;

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
