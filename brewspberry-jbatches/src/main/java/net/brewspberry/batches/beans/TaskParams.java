package net.brewspberry.batches.beans;

import net.brewspberry.business.beans.Actioner;
import net.brewspberry.business.beans.Brassin;
import net.brewspberry.business.beans.Etape;

public class TaskParams {
	
	
	
	private Brassin brew;
	private Etape step;
	private Actioner actioner;
	
	
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

}
