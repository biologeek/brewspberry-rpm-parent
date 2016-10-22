package net.brewspberry.front.ws.beans.responses;

import net.brewspberry.business.beans.Etape;
import net.brewspberry.business.beans.EtapeType;
import net.brewspberry.business.beans.PalierType;
import net.brewspberry.business.exceptions.BusinessException;

public class SimpleStepResponse {
	
	
	

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
	public long getRealBeginning() {
		return realBeginning;
	}
	public void setRealBeginning(long realBeginning) {
		this.realBeginning = realBeginning;
	}
	public long getEnd() {
		return end;
	}
	public void setEnd(long end) {
		this.end = end;
	}
	public long getRealEnd() {
		return realEnd;
	}
	public void setRealEnd(long realEnd) {
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


	public PalierType toBusinessStageType(String st) throws BusinessException{
		
		for (PalierType pl : PalierType.values()){
			
			if (pl.name().equals(st)){
				return pl;
			}
		}

		throw new BusinessException("Wrong type of stage !!");
	}
	

	public EtapeType toBusinessStepType(String st) throws BusinessException{
		
		for (EtapeType pl : EtapeType.values()){
			
			if (pl.name().equals(st)){
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

	
	
	
}