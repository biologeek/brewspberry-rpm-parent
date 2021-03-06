package net.brewspberry.main.batches.beans;

import net.brewspberry.main.business.beans.brewing.Brassin;
import net.brewspberry.main.business.beans.brewing.Etape;
import net.brewspberry.main.business.beans.monitoring.Actioner;

public class TaskParams {
	
	
	
	private Brassin brew;
	private Etape step;
	private Actioner actioner;
	private String uuid;
	
	
	public Brassin getBrew() {
		return brew;
	}
	public void setBrew(Brassin brew) {
		this.brew = brew;
	}
	public Etape getStep() {
		return step;
	}
	public void setStep(Etape step) {
		this.step = step;
	}
	public Actioner getActioner() {
		return actioner;
	}
	public void setActioner(Actioner actioner) {
		this.actioner = actioner;
	}

	
	public TaskParams brew(Brassin brew) {
		this.brew = brew;
		return this;
	}
	
	public TaskParams step(Etape step) {
		this.step = step;
		return this;
	}
	
	public TaskParams actioner(Actioner actioner) {
		this.actioner = actioner;
		return this;
	}
	
	
	public boolean hasNullAttributes() {

		if (this.getActioner() == null){
			return true;
		}
		
		if (this.getStep() == null){
			return true;
		}
		
		if (this.getBrew() == null){
			return true;
		}
		return false;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

}
