package net.brewsbpberry.brewery.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * A Step is a consistent part of the brewing process. <br>
 * <br>
 * <br>
 * Steps have a type that is among common brewing steps and dating
 * attributes<br>
 * <br>
 * It is composed of a set of {@link QuantifiedIngredient}s and a set of
 * {@link TemperatureStageOperation}
 * 
 * @author xavier
 *
 */
@Entity
public class Step {

	@Id
	@GeneratedValue
	private Long id;

	@Enumerated(EnumType.STRING)
	private StepType stepType;

	private String label;

	private LocalDateTime beginning, end, creationDate, updateDate;

	@OneToMany(mappedBy = "step")
	private List<QuantifiedIngredient> stepIngredients;

	@OneToMany(mappedBy = "step")
	private List<TemperatureStageOperation> temperatureStages;

	/**
	 * Allows to pause
	 *
	 * private List<StepState> states;
	 */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public StepType getStepType() {
		return stepType;
	}

	public void setStepType(StepType stepType) {
		this.stepType = stepType;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
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

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public LocalDateTime getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(LocalDateTime updateDate) {
		this.updateDate = updateDate;
	}

	public enum StepType {
		GRINDING, MASHING, LAUTERING, BOILING, WHIRLPOOL, HOPBACK, COOLING, FERMENTATION, COOL_FERMENTATION, CONDITIONNING, BOTTLE_FERMENTATION;
	}

	public List<QuantifiedIngredient> getStepIngredients() {
		return stepIngredients;
	}

	public void setStepIngredients(List<QuantifiedIngredient> stepIngredients) {
		this.stepIngredients = stepIngredients;
	}

	public List<TemperatureStageOperation> getTemperatureStages() {
		return temperatureStages;
	}

	public void setTemperatureStages(List<TemperatureStageOperation> temperatureStages) {
		this.temperatureStages = temperatureStages;
	}

}
