package net.brewspberry.front.ws.beans.requests;

import java.util.List;

import net.brewspberry.business.beans.Houblon;
import net.brewspberry.business.beans.Levure;
import net.brewspberry.business.beans.Malt;
import net.brewspberry.business.beans.PalierType;
import net.brewspberry.business.exceptions.BusinessException;
import net.brewspberry.front.ws.beans.responses.ActionnerResponse;
import net.brewspberry.front.ws.beans.responses.SimpleStepResponse;

public class CompleteStep extends SimpleStepResponse {
	/**
	 * REST API SimpleStep represents 
	 */

	private List<ConcreteIngredientRequest> malts;
	private List<ConcreteIngredientRequest> hops;
	private List<ConcreteIngredientRequest> yeasts;

	/**
	 * Not really a good option (separate requests and responses, but "la flemme" as we say...)
	 * TODO 
	 */
	private List<ActionnerResponse> actioners;
	public List<ConcreteIngredientRequest> getMalts() {
		return malts;
	}
	public void setMalts(List<ConcreteIngredientRequest> list) {
		this.malts = list;
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
	public List<ActionnerResponse> getActioners() {
		return actioners;
	}
	public void setActioners(List<ActionnerResponse> actioners) {
		this.actioners = actioners;
	}

	
	
	public PalierType toBusinessStageType(String st) throws BusinessException{
		
		for (PalierType pl : PalierType.values()){
			
			if (pl.getPlt_libelle().equals(st)){
				return pl;
			}
		}

		throw new BusinessException("Wrong type of stage !!");
	}

}
