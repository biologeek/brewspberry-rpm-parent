package net.brewspberry.front.ws.beans.dto;

import net.brewspberry.business.beans.Brassin;
import net.brewspberry.business.beans.EtapeType;
import net.brewspberry.front.ws.beans.SimpleBrewResponse;
import net.brewspberry.front.ws.beans.UserFullBean;

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
		resp.setDebut(brassin.getBra_debut());
		resp.setFin(brassin.getBra_fin());
		resp.setDescription(brassin.getBra_nom());
		resp.setMaj(brassin.getBra_date_maj());
		resp.setQuantite(brassin.getBra_quantiteEnLitres().floatValue());
		resp.setStatut(EtapeType.getValueByID(brassin.getBra_statut()));
		resp.setType(brassin.getBra_type());
		
		
		return resp;
	}

}
