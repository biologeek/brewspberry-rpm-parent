package net.brewspberry.front.ws.beans.dto;

import net.brewspberry.business.beans.Brassin;
import net.brewspberry.business.beans.Etape;
import net.brewspberry.business.beans.EtapeType;
import net.brewspberry.front.ws.beans.responses.ComplexBrewResponse;
import net.brewspberry.front.ws.beans.responses.SimpleBrewResponse;
import net.brewspberry.front.ws.beans.responses.SimpleStepResponse;
import net.brewspberry.front.ws.beans.responses.UserFullBean;

public class BrassinDTO {
	
	
	private static BrassinDTO instance;
	
	
	private BrassinDTO (){
		
		
	}
	
	
	
	public static BrassinDTO getInstance(){

		if (instance == null){
			instance = new BrassinDTO();
		}
		return instance;
	}


	public SimpleBrewResponse toSimpleBrewResponse(Brassin brassin) {

		SimpleBrewResponse resp = new SimpleBrewResponse();
		
		resp.setId(brassin.getBra_id());
		resp.setBeginning(brassin.getBra_debut());
		resp.setEnd(brassin.getBra_fin());
		resp.setDescription(brassin.getBra_nom());
		resp.setMaj(brassin.getBra_date_maj());
		resp.setQuantity(brassin.getBra_quantiteEnLitres().floatValue());
		resp.setStatus(EtapeType.getValueByID(brassin.getBra_statut()));
		resp.setType(brassin.getBra_type());
		
		
		return resp;
	}
	
	public ComplexBrewResponse toComplexBrewResponse (Brassin brassin){
		
		
		ComplexBrewResponse resp = (ComplexBrewResponse) this.toSimpleBrewResponse(brassin);
		
		for (Etape etp : brassin.getBra_etapes()){
			
			SimpleStepResponse stepResponse = new EtapeDTO().toSimpleStepResponse();
		}
		
		return resp;
		
		
	}

}
