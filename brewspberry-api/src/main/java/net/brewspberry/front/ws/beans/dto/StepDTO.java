package net.brewspberry.front.ws.beans.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.rowset.serial.SerialException;

import net.brewspberry.business.beans.Brassin;
import net.brewspberry.business.beans.Etape;
import net.brewspberry.business.beans.PalierType;
import net.brewspberry.business.exceptions.BusinessException;
import net.brewspberry.business.exceptions.ServiceException;
import net.brewspberry.front.ws.beans.requests.CompleteStepRequest;
import net.brewspberry.front.ws.beans.responses.SimpleStepResponse;

public class StepDTO {

	public SimpleStepResponse toSimpleStepResponse() {
		// TODO Auto-generated method stub
		return null;
	}

	public Etape toBusinessObject(CompleteStepRequest step) throws ServiceException{
		
		
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
			res.setEtp_palier_type(step.toBusiness());
		} catch (BusinessException e) {
			throw new ServiceException(e.getMessage());
		}
		
		res.setEtp_actioners(new ActionnerDTO().toBusinessObjectList(step.getActioners()));
		
		
		
		return null;
	}


	public Etape toBusinessObject(CompleteStepRequest step, Brassin attachedBrew) throws ServiceException{
		
		
		Etape res = this.toBusinessObject(step);
		res.setEtp_brassin(attachedBrew);
		
		
		return null;
	}

	public List<Etape> toBusinessObjectList(List<CompleteStepRequest> steps) throws ServiceException {
		List<Etape> res = new ArrayList<Etape>();
		
		for(CompleteStepRequest step : steps){
			
			res.add(this.toBusinessObject(step));
			
		}
		return res;
	}

}
