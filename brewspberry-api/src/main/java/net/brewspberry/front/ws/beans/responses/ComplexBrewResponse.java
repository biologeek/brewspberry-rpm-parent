package net.brewspberry.front.ws.beans.responses;

import java.util.List;

import net.brewspberry.business.beans.Biere;
import net.brewspberry.front.ws.beans.requests.CompleteStep;
import net.brewspberry.front.ws.beans.requests.ConcreteIngredientRequest;

public class ComplexBrewResponse extends SimpleBrewResponse {

		
	private List<CompleteStep> steps;
	private Biere beer;

	private List<ConcreteIngredientRequest> malts;
	private List<ConcreteIngredientRequest> hops;
	private List<ConcreteIngredientRequest> yeasts;
	

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

	public List<ConcreteIngredientRequest> getMalts() {
		return malts;
	}

	public void setMalts(List<ConcreteIngredientRequest> malts) {
		this.malts = malts;
	}

	public List<ConcreteIngredientRequest> getHops() {
		return hops;
	}

	public void setHops(List<ConcreteIngredientRequest> hops) {
		this.hops = hops;
	}

	public List<ConcreteIngredientRequest> getYeasts() {
		return yeasts;
	}

	public void setYeasts(List<ConcreteIngredientRequest> yeasts) {
		this.yeasts = yeasts;
	}
	
	
}
