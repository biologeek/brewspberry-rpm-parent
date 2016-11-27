package net.brewspberry.main.front.ws.beans.requests;

import java.util.List;

import net.brewspberry.main.business.beans.Houblon;
import net.brewspberry.main.business.beans.Levure;
import net.brewspberry.main.business.beans.Malt;

public class BrewRequest {
	
	private Long id;
	private String name;
	private Long begin;
	private Long update;
	private Long end;
	private Long creation;
	private Double quantity;
	private String type;
	private Integer status;
	private List<ConcreteIngredientRequest> hops;
	private List<ConcreteIngredientRequest> malts;
	private List<ConcreteIngredientRequest> yeasts;
	private List<CompleteStep> steps;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getBegin() {
		return begin;
	}
	public void setBegin(Long begin) {
		this.begin = begin;
	}
	public Long getUpdate() {
		return update;
	}
	public void setUpdate(Long update) {
		this.update = update;
	}
	public Long getEnd() {
		return end;
	}
	public void setEnd(Long end) {
		this.end = end;
	}
	public Long getCreation() {
		return creation;
	}
	public void setCreation(Long creation) {
		this.creation = creation;
	}
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	public List<CompleteStep> getSteps() {
		return steps;
	}
	public void setSteps(List<CompleteStep> steps) {
		this.steps = steps;
	}

	public List<ConcreteIngredientRequest> getHops() {
		return hops;
	}
	public void setHops(List<ConcreteIngredientRequest> hops) {
		this.hops = hops;
	}
	public List<ConcreteIngredientRequest> getMalts() {
		return malts;
	}
	public void setMalts(List<ConcreteIngredientRequest> malts) {
		this.malts = malts;
	}
	public List<ConcreteIngredientRequest> getYeasts() {
		return yeasts;
	}
	public void setYeasts(List<ConcreteIngredientRequest> yeasts) {
		this.yeasts = yeasts;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	

}
