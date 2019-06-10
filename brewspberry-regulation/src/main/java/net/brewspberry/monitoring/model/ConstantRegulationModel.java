package net.brewspberry.monitoring.model;

public class ConstantRegulationModel implements RegulationModel {

	private Long id;
	private Long duration;
	private Float instruction;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public Float getInstruction() {
		return instruction;
	}

	public void setInstruction(Float instruction) {
		this.instruction = instruction;
	}

}
