package net.brewspberry.front.ws.beans;

import net.brewspberry.business.beans.Malt;

public class MaltDTO extends IngredientDTO<Malt> {

	
	private static MaltDTO instance;

	@SuppressWarnings("unchecked")
	public static IngredientDTO<Malt> getInstance(){
		
		return (instance == null)? instance=new MaltDTO():instance;
	}
	
	
	@Override
	public Malt toIngredient() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Malt toTransferObject() {
		// TODO Auto-generated method stub
		return null;
	}

}
