package net.brewsbpberry.brewery.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Represents a heating / cooling stage operation inside a particular step.<br>
 * For example if during a step, mixture must be heat at 50°C during 20 min,
 * then cooled to 20°C at 2°C per minute, 2 {@link TemperatureStageOperation}s
 * will be created. <br>
 * <br>
 * A step may contain several {@link TemperatureStageOperation} to reflect the
 * process.<br>
 * <br>
 * 
 * {@link TemperatureStageOperation} can be used to represent stable
 * temperatures and linear gradients
 *
 */
@Entity
@Table
public class TemperatureStageOperation {

	@Id
	@GeneratedValue
	private Long id;
	@Enumerated(EnumType.STRING)
	private StageType stageType;
	private Long duration;
	private Float beginningSetPoint, endSetPoint;
	@ManyToOne
	private Step step;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public StageType getStageType() {
		return stageType;
	}

	public Step getStep() {
		return step;
	}

	public void setStep(Step step) {
		this.step = step;
	}

	public void setStageType(StageType stageType) {
		this.stageType = stageType;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public Float getBeginningSetPoint() {
		return beginningSetPoint;
	}

	public void setBeginningSetPoint(Float beginningSetPoint) {
		this.beginningSetPoint = beginningSetPoint;
	}

	public Float getEndSetPoint() {
		return endSetPoint;
	}

	public void setEndSetPoint(Float endSetPoint) {
		this.endSetPoint = endSetPoint;
	}
}
