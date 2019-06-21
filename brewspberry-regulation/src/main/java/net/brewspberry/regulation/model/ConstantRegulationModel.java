package net.brewspberry.regulation.model;

import java.time.LocalDateTime;
import java.util.List;

public class ConstantRegulationModel implements RegulationModel {

	private Long id;
	
	private List<String> uuids;
	/**
	 * Duration in milliseconds
	 */
	private Long duration;
	private LocalDateTime end;
	private Float instruction;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<String> getUuids() {
		return uuids;
	}

	public void setUuids(List<String> uuids) {
		this.uuids = uuids;
	}

	public LocalDateTime getEnd() {
		return end;
	}

	public void setEnd(LocalDateTime end) {
		this.end = end;
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
