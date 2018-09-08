package net.brewspberry.brewery.api;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class Brew {

	private Long id;
	private String title;
	private Date beginning, end;
	private Quantity totalProduced, totalExpected;
	private List<StepFull> steps;
	private List<AbstractIngredient> ingredients;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<StepFull> getSteps() {
		return steps;
	}

	public void setSteps(List<StepFull> steps) {
		this.steps = steps;
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

	public Quantity getTotalProduced() {
		return totalProduced;
	}

	public void setTotalProduced(Quantity totalProduced) {
		this.totalProduced = totalProduced;
	}

	public Quantity getTotalExpected() {
		return totalExpected;
	}

	public void setTotalExpected(Quantity totalExpected) {
		this.totalExpected = totalExpected;
	}

	public List<AbstractIngredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<AbstractIngredient> ingredients) {
		this.ingredients = ingredients;
	}

}
