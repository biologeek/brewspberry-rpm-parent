package net.brewsbpberry.brewery.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

/**
 * Represents the status of the step it is attached to within a period
 * 
 * @author xavier
 *
 */
@Entity
public class StepState {

	@Enumerated
	private StepStatus status;
	private LocalDateTime beginning;
	private LocalDateTime end;
	@ManyToOne
	private Step step;

	public StepStatus getStatus() {
		return status;
	}

	public void setStatus(StepStatus status) {
		this.status = status;
	}

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

	public Step getStep() {
		return step;
	}

	public void setStep(Step step) {
		this.step = step;
	}

}
