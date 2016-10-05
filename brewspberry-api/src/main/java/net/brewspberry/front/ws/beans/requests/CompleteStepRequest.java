package net.brewspberry.front.ws.beans.requests;

import java.util.List;

import net.brewspberry.business.beans.Houblon;
import net.brewspberry.business.beans.Levure;
import net.brewspberry.business.beans.Malt;
import net.brewspberry.business.beans.PalierType;
import net.brewspberry.business.exceptions.BusinessException;
import net.brewspberry.front.ws.beans.responses.ActionnerResponse;

public class CompleteStepRequest {
	/**
	 * REST API SimpleStep represents 
	 */

	private Long id;
	private Long brewID;
	private String name;
	private int number;
	private long duration;
	private long beginning;
	private long realBeginning;
	private long end;
	private long realEnd;
	private Double theoreticalTemperature;
	private boolean isActive;
	private long creation;
	private long update;
	private List<Malt> malts;
	private List<Houblon> hops;
	private List<Levure> yeasts;
	private String stageType;
	/**
	 * Not reqlly a good option (separate requests and responses, but "la flemme" as we say...)
	 * TODO 
	 */
	private List<ActionnerResponse> actioners;

	public String getStageType() {
		return stageType;
	}

	public void setStageType(String stageType) {
		this.stageType = stageType;
	}

	public List<Malt> getMalts() {
		return malts;
	}

	public void setMalts(List<Malt> malts) {
		this.malts = malts;
	}

	public List<Houblon> getHops() {
		return hops;
	}

	public void setHops(List<Houblon> hops) {
		this.hops = hops;
	}

	public List<Levure> getYeasts() {
		return yeasts;
	}

	public void setYeasts(List<Levure> yeasts) {
		this.yeasts = yeasts;
	}

	public Long getBrewID() {
		return brewID;
	}

	public void setBrewID(Long brewID) {
		this.brewID = brewID;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public long getBeginning() {
		return beginning;
	}

	public void setBeginning(long beginning) {
		this.beginning = beginning;
	}

	public long getEnd() {
		return end;
	}

	public void setEnd(long end) {
		this.end = end;
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

	public long getCreation() {
		return creation;
	}

	public void setCreation(long creation) {
		this.creation = creation;
	}

	public long getUpdate() {
		return update;
	}

	public void setUpdate(long update) {
		this.update = update;
	}

	public long getRealBeginning() {
		return realBeginning;
	}

	public void setRealBeginning(long realBeginning) {
		this.realBeginning = realBeginning;
	}

	public long getRealEnd() {
		return realEnd;
	}

	public void setRealEnd(long realEnd) {
		this.realEnd = realEnd;
	}
	
	public PalierType toBusiness() throws BusinessException{
		for (PalierType pal : PalierType.values()){
			if (pal.getPlt_libelle().equals(this.getStageType())){
				
				return pal;
				
			}
		}
		
		throw new BusinessException("Wrong StageType "+this.getStageType());
	}

	public List<ActionnerResponse> getActioners() {
		return actioners;
	}

	public void setActioners(List<ActionnerResponse> actioners) {
		this.actioners = actioners;
	}

}
