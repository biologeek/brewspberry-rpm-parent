package net.brewspberry.main.front.ws.beans.responses;

import net.brewspberry.main.business.beans.EtapeType;
import net.brewspberry.main.business.beans.PalierType;
import net.brewspberry.main.business.exceptions.BusinessException;

public class SimpleStepResponse implements Comparable<SimpleStepResponse> {

	private Long id;
	private Long brewID;
	private String name;
	private int number;
	private Long duration;
	private int durationUnit;
	private Long beginning;
	private Long realBeginning;
	private Long end;
	private Long realEnd;
	private Double theoreticalTemperature;
	private boolean isActive;
	private Long creation;
	private Long update;
	private String stageType;
	private String stepType;
	private String comment;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBrewID() {
		return brewID;
	}

	public void setBrewID(Long brewID) {
		this.brewID = brewID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public Long getBeginning() {
		return beginning;
	}

	public void setBeginning(Long beginning) {
		this.beginning = beginning;
	}

	public Long getRealBeginning() {
		return realBeginning;
	}

	public void setRealBeginning(Long realBeginning) {
		this.realBeginning = realBeginning;
	}

	public Long getEnd() {
		return end;
	}

	public void setEnd(Long end) {
		this.end = end;
	}

	public Long getRealEnd() {
		return realEnd;
	}

	public void setRealEnd(Long realEnd) {
		this.realEnd = realEnd;
	}

	public Double getTheoreticalTemperature() {
		return theoreticalTemperature;
	}

	public void setTheoreticalTemperature(Double theoreticalTemperature) {
		this.theoreticalTemperature = theoreticalTemperature;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Long getCreation() {
		return creation;
	}

	public void setCreation(Long creation) {
		this.creation = creation;
	}

	public Long getUpdate() {
		return update;
	}

	public void setUpdate(Long update) {
		this.update = update;
	}

	public String getStageType() {
		return stageType;
	}

	public void setStageType(String stageType) {
		this.stageType = stageType;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public PalierType toBusinessStageType(String st) throws BusinessException {
		if (st != null) {
			for (PalierType pl : PalierType.values()) {

				if (pl.name().equals(st)) {
					return pl;
				}
			}
		} else {
			return null;
		}
		throw new BusinessException("Wrong type of stage !!");
	}

	public EtapeType toBusinessStepType(String st) throws BusinessException {

		for (EtapeType pl : EtapeType.values()) {

			if (pl.name().equals(st)) {
				return pl;
			}
		}

		throw new BusinessException("Wrong type of step !!");
	}

	public String getStepType() {
		return stepType;
	}

	public void setStepType(String stepType) {
		this.stepType = stepType;
	}

	@Override
	public int compareTo(SimpleStepResponse o) {
		if (this.getNumber() > o.getNumber())
			return 1;
		else if (this.getNumber() == o.getNumber())
			return 0;
		else // (this.getNumber() < o.getNumber())
			return -1;
	}

	public int getDurationUnit() {
		return durationUnit;
	}

	public void setDurationUnit(int durationUnit) {
		this.durationUnit = durationUnit;
	}

}