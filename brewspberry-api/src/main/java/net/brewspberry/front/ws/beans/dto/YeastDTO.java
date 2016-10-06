package net.brewspberry.front.ws.beans.dto;

import java.util.List;

import net.brewspberry.business.beans.Levure;
import net.brewspberry.front.ws.DTO;
import net.brewspberry.front.ws.beans.requests.ConcreteIngredientRequest;

public class YeastDTO implements DTO<Levure, ConcreteIngredientRequest> {

	@Override
	public Levure toBusinessObject(ConcreteIngredientRequest cplObj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Levure> toBusinessObjectList(List<ConcreteIngredientRequest> cplLst) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConcreteIngredientRequest toFrontObject(Levure cplObj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ConcreteIngredientRequest> toFrontObjectList(List<Levure> cplLst) {
		// TODO Auto-generated method stub
		return null;
	}


}
