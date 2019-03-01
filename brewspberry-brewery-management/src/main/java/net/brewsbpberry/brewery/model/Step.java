package net.brewsbpberry.brewery.model;

import java.util.Date;
import java.util.List;

public class Step {

	private Long id;
	private StepType stepType;
	private String label;
	private Date beginning, end, creationDate, updateDate;
	private List<QuantifiedIngredient> stepIngredients;
	private List<TemperatureStageOperation> temperatureStages;

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

	public Date getBeginning() {
		return beginning;
	}

	public void setBeginning(Date beginning) {
		this.beginning = beginning;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
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