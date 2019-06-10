package net.brewspberry.brewery.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import net.brewspberry.brewery.api.StageType;

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
public class TemperatureStageOperation {

	@Id
	@GeneratedValue
	private Long id;
	@Enumerated(EnumType.STRING)
	private StageType stageType;
	/**
	 * Duration in seconds
	 */
	private Long duration;
	/**
	 * Temperature at the beginning of the stage
	 */
	private Float beginningSetPoint;
	/**
	 * Temperature at the beginning of the stage
	 */
	private Float endSetPoint;
	/**
	 * Date point of stage beginning
	 */
	private LocalDateTime beginning;
	
	private Long beginningToStep;
	
	@ManyToOne
	private Step step;

	public TemperatureStageOperation() {
		super();
		if (this.beginning != null && step != null)
			this.beginningToStep = this.beginning.until(step.getBeginning(), ChronoUnit.MILLIS);
	}

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

	public Long getBeginningToStep() {
		return beginningToStep;
	}

	public void setBeginningToStep(Long beginningToStep) {
		this.beginningToStep = beginningToStep;
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

	public LocalDateTime getBeginning() {
		return beginning;
	}

	public void setBeginning(LocalDateTime beginning) {
		this.beginning = beginning;
	}
}
