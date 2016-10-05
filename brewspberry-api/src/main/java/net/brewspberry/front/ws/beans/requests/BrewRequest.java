package net.brewspberry.front.ws.beans.requests;

import java.util.List;

import net.brewspberry.business.beans.Houblon;
import net.brewspberry.business.beans.Levure;
import net.brewspberry.business.beans.Malt;

public class BrewRequest {
	
	private String name;
	private Long begin;
	private Long update;
	private Long end;
	private Long creation;
	private Double quantity;
	private String type;
	private Integer status;
	private List<HopRequest> hops;
	private List<MaltRequest> malts;
	private List<YeastRequest> yeasts;
	private List<CompleteStepRequest> steps;
	
	
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
	public List<CompleteStepRequest> getSteps() {
		return steps;
	}
	public void setSteps(List<CompleteStepRequest> steps) {
		this.steps = steps;
	}
	public List<HopRequest> getHops() {
		return hops;
	}
	public void setHops(List<HopRequest> hops) {
		this.hops = hops;
	}
	public List<MaltRequest> getMalts() {
		return malts;
	}
	public void setMalts(List<MaltRequest> malts) {
		this.malts = malts;
	}
	public List<YeastRequest> getYeasts() {
		return yeasts;
	}
	public void setYeasts(List<YeastRequest> yeasts) {
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
	

}
