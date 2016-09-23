package net.brewspberry.front.ws.beans.responses;

import java.util.List;

import net.brewspberry.business.beans.Etape;

public class ComplexBrewResponse extends SimpleBrewResponse {

		
	private List<Etape> steps;
	
	

	public ComplexBrewResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<Etape> getSteps() {
		return steps;
	}

	public void setSteps(List<Etape> steps) {
		this.steps = steps;
	}
	
	
}
