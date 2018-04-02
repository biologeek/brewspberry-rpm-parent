package net.brewspberry.main.front.ws.beans.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.sql.rowset.serial.SerialException;

import net.brewspberry.main.business.beans.brewing.Brassin;
import net.brewspberry.main.business.beans.brewing.DurationBO;
import net.brewspberry.main.business.beans.brewing.Etape;
import net.brewspberry.main.business.beans.brewing.EtapeType;
import net.brewspberry.main.business.beans.brewing.Houblon;
import net.brewspberry.main.business.beans.brewing.Levure;
import net.brewspberry.main.business.beans.brewing.Malt;
import net.brewspberry.main.business.beans.brewing.PalierType;
import net.brewspberry.main.business.beans.monitoring.Actioner;
import net.brewspberry.main.business.exceptions.BusinessException;
import net.brewspberry.main.business.exceptions.ConversionException;
import net.brewspberry.main.business.exceptions.ServiceException;
import net.brewspberry.main.front.ws.beans.requests.CompleteStep;
import net.brewspberry.main.front.ws.beans.responses.SimpleStepResponse;

public class StepDTO {

	public SimpleStepResponse toSimpleStepResponse(Etape step) {
		if (step != null) {
			SimpleStepResponse res = new SimpleStepResponse();
			res.setBrewID(step.getEtp_brassin().getBra_id());
			res.setId(step.getEtp_id());
			if (step.getEtp_debut() != null)
				res.setBeginning(step.getEtp_debut().getTime());

			if (step.getEtp_debut_reel() != null)
				res.setRealBeginning(step.getEtp_debut_reel().getTime());

			if (step.getEtp_fin() != null)
				res.setEnd(step.getEtp_fin().getTime());

			if (step.getEtp_fin_reel() != null)
				res.setRealEnd(step.getEtp_fin_reel().getTime());

			// res.setBrewID(step.getEtp_brassin().getBra_id());
			res.setCreation(step.getEtp_creation_date().getTime());
			res.setUpdate(step.getEtp_update_date().getTime());
			res.setName(step.getEtp_nom());
			res.setStepType(step.getEtp_etape_type().name());
			res.setComment(step.getEtp_remarque());
			res.setDuration(DurationBO.toLong(step.getEtp_duree()));
			if (step.getEtp_fin_reel() != null && step.getEtp_debut_reel() != null)
				res.setDuration(DurationBO.toLong(step.getEtp_duree()));

			res.setTheoreticalTemperature(res.getTheoreticalTemperature());
			res.setNumber(step.getEtp_numero());
			if (step.getEtp_palier_type() != null)
				res.setStageType(step.getEtp_palier_type().getPlt_libelle());
			res.setActive(step.isEtp_active());
			return res;

		}
		return null;
	}

	/**
	 * Converts Etape business object to full step request/response used in REST
	 * API
	 * 
	 * @param step
	 *            step to convert
	 * @return
	 */
	public CompleteStep toCompleteStep(Etape step) {

		if (step != null) {
			CompleteStep res = new CompleteStep(this.toSimpleStepResponse(step));

			res.setMalts(new MaltDTO().toFrontObjectList(
					new ArrayList<Malt>(step.getEtp_malts() != null ? step.getEtp_malts() : new HashSet<Malt>())));
			res.setHops(new HopDTO().toFrontObjectList(new ArrayList<Houblon>(
					step.getEtp_houblons() != null ? step.getEtp_houblons() : new HashSet<Houblon>())));
			res.setYeasts(new YeastDTO().toFrontObjectList(new ArrayList<Levure>(
					step.getEtp_levures() != null ? step.getEtp_levures() : new HashSet<Levure>())));

			res.setActioners(new ActionnerDTO()
					.toActionnerResponse(new ArrayList<Actioner>(
							step.getEtp_actioner() != null ? step.getEtp_actioner() : new HashSet<Actioner>()))
					.buildAPI());

			return res;
		}
		return null;

	}

	public Etape toBusinessObject(SimpleStepResponse step) throws ConversionException {

		Etape res = new Etape();
		res.setEtp_id(step.getId());
		res.setEtp_creation_date(new Date(step.getCreation()));
		res.setEtp_update_date(new Date(step.getUpdate()));
		res.setEtp_debut(new Date(step.getBeginning()));
		res.setEtp_debut_reel(step.getRealBeginning() == 0 || step.getRealBeginning() == null ? null
				: new Date(step.getRealBeginning()));
		res.setEtp_fin(new Date(step.getEnd()));
		res.setEtp_fin_reel(step.getRealEnd() == 0 || step.getRealEnd() == null ? null
				: new Date(step.getRealEnd()));
		res.setEtp_duree(new DurationBO(step.getDuration(), step.getDurationUnit()));
		res.setEtp_numero(step.getNumber());
		res.setEtp_nom(step.getName());
		res.setEtp_temperature_theorique(step.getTheoreticalTemperature());
		try {
			if (step.getStageType() != null)
				res.setEtp_palier_type(step.toBusinessStageType(step.getStageType()));
		} catch (BusinessException e) {
			throw new ConversionException(e.getMessage());
		}

		try {
			res.setEtp_etape_type(step.toBusinessStepType(step.getStepType()));
		} catch (BusinessException e) {
			throw new ConversionException(e.getMessage());
		}
		
		
		res.setEtp_active(step.isActive());

		return res;
	}

	public Etape toBusinessObject(CompleteStep step) throws ConversionException {

		Etape res = new Etape();
		res.setEtp_id(step.getId());
		res.setEtp_creation_date(new Date(step.getCreation()));
		res.setEtp_update_date(new Date(step.getUpdate()));
		res.setEtp_debut(new Date(step.getBeginning()));
		res.setEtp_debut_reel(step.getRealBeginning() == 0 || step.getRealBeginning() == null ? null
				: new Date(step.getRealBeginning()));
		res.setEtp_fin(new Date(step.getEnd()));
		res.setEtp_fin_reel(step.getRealEnd() == 0 || step.getRealEnd() == null ? null
				: new Date(step.getRealEnd()));
		res.setEtp_duree(new DurationBO(step.getDuration(), step.getDurationUnit()));
		res.setEtp_houblons(new HopDTO().toBusinessObjectList(step.getHops()));
		res.setEtp_malts(new MaltDTO().toBusinessObjectList(step.getMalts()));
		res.setEtp_levures(new YeastDTO().toBusinessObjectList(step.getYeasts()));
		res.setEtp_numero(step.getNumber());
		res.setEtp_nom(step.getName());
		res.setEtp_temperature_theorique(step.getTheoreticalTemperature());
		try {
			res.setEtp_palier_type(step.toBusinessStageType(step.getStageType()));
		} catch (BusinessException e) {
			throw new ConversionException(e.getMessage());
		}

		res.setEtp_actioners(
				new ArrayList<Actioner>(new ActionnerDTO().step(res).toBusinessObjectList(step.getActioners())));

		res.setEtp_active(step.isActive());

		return res;
	}

	public Etape toBusinessObject(CompleteStep step, Brassin attachedBrew) throws ConversionException {

		Etape res = this.toBusinessObject(step);
		res.setEtp_brassin(attachedBrew);

		return res;
	}

	public Etape toBusinessObject(SimpleStepResponse step, Brassin attachedBrew) throws ConversionException {

		Etape res = this.toBusinessObject(step);
		res.setEtp_brassin(attachedBrew);

		return res;
	}

	public List<Etape> toBusinessObjectList(List<CompleteStep> steps) throws ConversionException {
		List<Etape> res = new ArrayList<Etape>();

		if (steps != null) {
			for (CompleteStep step : steps) {

				res.add(this.toBusinessObject(step));

			}
		}
		return res;
	}

}
