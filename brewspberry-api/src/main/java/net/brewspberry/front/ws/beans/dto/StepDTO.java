package net.brewspberry.front.ws.beans.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.rowset.serial.SerialException;

import net.brewspberry.business.beans.Brassin;
import net.brewspberry.business.beans.Etape;
import net.brewspberry.business.beans.PalierType;
import net.brewspberry.business.exceptions.BusinessException;
import net.brewspberry.business.exceptions.ConvertionException;
import net.brewspberry.business.exceptions.ServiceException;
import net.brewspberry.front.ws.beans.requests.CompleteStep;
import net.brewspberry.front.ws.beans.responses.SimpleStepResponse;

public class StepDTO {

	public SimpleStepResponse toSimpleStepResponse(Etape step) {
		// TODO
		SimpleStepResponse res = new SimpleStepResponse();
		
		
		res.setId(step.getEtp_id());
		res.setBeginning(step.getEtp_debut().getTime());
		res.setRealBeginning(step.getEtp_debut_reel().getTime());
		res.setEnd(step.getEtp_fin().getTime());
		res.setRealEnd(step.getEtp_fin_reel().getTime());
		res.setBrewID(step.getEtp_brassin().getBra_id());
		res.setCreation(step.getEtp_creation_date().getTime());
		res.setUpdate(step.getEtp_update_date().getTime());
		res.setName(step.getEtp_nom());
		res.setComment(step.getEtp_remarque());
		res.setDuration(step.getEtp_duree().toTimestamp());
		res.setTheoreticalTemperature(res.getTheoreticalTemperature());
		res.setNumber(step.getEtp_numero());
		res.setStageType(step.getEtp_palier_type().getPlt_libelle());
		
		return res;
	}
	
	
	/**
	 * Converts Etape business object to full step request/response used in REST API
	 * @param step step to convert
	 * @return
	 */
	public CompleteStep toCompleteStep(Etape step){
		
		CompleteStep res = new CompleteStep();
		res = (CompleteStep) this.toSimpleStepResponse(step);
		
		
		res.setMalts(new MaltDTO().toFrontObjectList(step.getEtp_malts()));
		res.setHops(new HopDTO().toFrontObjectList(step.getEtp_houblons()));
		res.setYeasts(new YeastDTO().toFrontObjectList(step.getEtp_levures()));
		
		res.setActioners(new ActionnerDTO().toActionnerResponse(step.getEtp_actioner()));
		
		
		
		return res;
		
	}

	public Etape toBusinessObject(CompleteStep step) throws ConvertionException{
		
		
		Etape res = new Etape();
		res.setEtp_id(step.getId());
		res.setEtp_creation_date(new Date(step.getCreation()));
		res.setEtp_update_date(new Date(step.getUpdate()));
		res.setEtp_debut(new Date(step.getBeginning()));
		res.setEtp_debut_reel(new Date(step.getRealBeginning()));
		res.setEtp_fin(new Date(step.getEnd()));
		res.setEtp_houblons(new HopDTO().toBusinessObjectList(step.getHops()));
		res.setEtp_malts(new MaltDTO().toBusinessObjectList(step.getMalts()));
		res.setEtp_levures(new YeastDTO().toBusinessObjectList(step.getYeasts()));
		res.setEtp_numero(step.getNumber());
		res.setEtp_nom(step.getName());
		res.setEtp_temperature_theorique(step.getTheoreticalTemperature());
		try {
			res.setEtp_palier_type(step.toBusinessStageType(step.getStageType()));
		} catch (BusinessException e) {
			throw new ConvertionException(e.getMessage());
		}
		
		res.setEtp_actioners(new ActionnerDTO().toBusinessObjectList(step.getActioners()));
		
		
		
		return res;
	}


	public Etape toBusinessObject(CompleteStep step, Brassin attachedBrew) throws ConvertionException{
		
		
		Etape res = this.toBusinessObject(step);
		res.setEtp_brassin(attachedBrew);
		
		
		return res;
	}

	public List<Etape> toBusinessObjectList(List<CompleteStep> steps) throws ConvertionException {
		List<Etape> res = new ArrayList<Etape>();
		
		for(CompleteStep step : steps){
			
			res.add(this.toBusinessObject(step));
			
		}
		return res;
	}

}
