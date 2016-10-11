package net.brewspberry.front.ws.beans.responses;

import java.util.Date;
import java.util.List;

import net.brewspberry.business.beans.Biere;
import net.brewspberry.business.beans.EtapeType;
import net.brewspberry.front.ws.beans.requests.CompleteStep;

public class ComplexBrewResponse extends SimpleBrewResponse {

		
	private List<CompleteStep> steps;
	private Biere beer;
	

	public ComplexBrewResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ComplexBrewResponse(SimpleBrewResponse simpleBrewResponse) {
		
		
		this.setId(simpleBrewResponse.getId());
		this.setDescription(simpleBrewResponse.getDescription());
		this.setBeginning(simpleBrewResponse.getBeginning());
		this.setEnd(simpleBrewResponse.getEnd());
		this.setMaj(simpleBrewResponse.getMaj());
		this.setQuantity(simpleBrewResponse.getQuantity());
		this.setStatus(simpleBrewResponse.getStatus());
		this.setType(simpleBrewResponse.getType());
	}

	public List<CompleteStep> getSteps() {
		return steps;
	}

	public void setSteps(List<CompleteStep> steps) {
		this.steps = steps;
	}

	public Biere getBeer() {
		return beer;
	}

	public void setBeer(Biere beer) {
		this.beer = beer;
	}
	
	
}
