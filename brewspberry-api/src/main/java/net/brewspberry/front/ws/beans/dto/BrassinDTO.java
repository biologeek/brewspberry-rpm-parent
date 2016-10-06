package net.brewspberry.front.ws.beans.dto;

import java.util.Date;

import net.brewspberry.business.beans.Brassin;
import net.brewspberry.business.beans.Etape;
import net.brewspberry.business.beans.EtapeType;
import net.brewspberry.business.exceptions.ConvertionException;
import net.brewspberry.business.exceptions.ServiceException;
import net.brewspberry.front.ws.beans.requests.BrewRequest;
import net.brewspberry.front.ws.beans.requests.CompleteStep;
import net.brewspberry.front.ws.beans.responses.ComplexBrewResponse;
import net.brewspberry.front.ws.beans.responses.ComplexStepResponse;
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
	
	
	
	/**
	 * Converts main attributes using toSimpleBrewResponse method.
	 * 
	 * Converts all steps to complex steps
	 * @param brassin
	 * @return
	 */
	public ComplexBrewResponse toComplexBrewResponse (Brassin brassin){
		
		
		ComplexBrewResponse resp = (ComplexBrewResponse) this.toSimpleBrewResponse(brassin);
		
		for (Etape etp : brassin.getBra_etapes()){
			
			CompleteStep stepResponse = new StepDTO().toCompleteStep(etp);
			resp.getSteps().add(stepResponse);
			
		}
		
		
		
		
		
		return resp;
		
		
	}



	public Brassin toBusinessObject(BrewRequest req) throws ServiceException {
		
		Brassin brew = new Brassin();
		
		/*
		 * Only used when updating brew 
		 */
		brew.setBra_id(req.getId());
		brew.setBra_nom(req.getName());
		brew.setBra_debut(new Date(req.getBegin()));
		try {
			brew.setBra_etapes(new StepDTO().toBusinessObjectList(req.getSteps()));
		} catch (ConvertionException e) {
			throw new ServiceException(e.getMessage());
		}
		
		brew.setBra_fin(new Date(req.getEnd()));
		
		brew.setBra_malts(new MaltDTO().toBusinessObjectList(req.getMalts()));
		brew.setBra_houblons(new HopDTO().toBusinessObjectList(req.getHops()));
		brew.setBra_levures(new YeastDTO().toBusinessObjectList(req.getYeasts()));
		brew.setBra_quantiteEnLitres(req.getQuantity());
		brew.setBra_type(req.getType());
		brew.setBra_statut(req.getStatus());
		
		
		return brew;
	}


}
