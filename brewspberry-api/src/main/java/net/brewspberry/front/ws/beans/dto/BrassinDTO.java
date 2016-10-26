package net.brewspberry.front.ws.beans.dto;

import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;

import net.brewspberry.business.beans.Brassin;
import net.brewspberry.business.beans.BrewStatus;
import net.brewspberry.business.beans.Etape;
import net.brewspberry.business.beans.EtapeType;
import net.brewspberry.business.exceptions.ConversionException;
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

	private BrassinDTO() {

	}

	public static BrassinDTO getInstance() {

		if (instance == null) {
			instance = new BrassinDTO();
		}
		return instance;
	}

	public SimpleBrewResponse toSimpleBrewResponse(Brassin brassin) {
		if (brassin != null) {
			SimpleBrewResponse resp = new SimpleBrewResponse();

			resp.setId(brassin.getBra_id());
			resp.setBeginning(brassin.getBra_debut());
			resp.setEnd(brassin.getBra_fin());
			resp.setDescription(brassin.getBra_nom());
			resp.setMaj(brassin.getBra_date_maj());
			resp.setQuantity(new Float(brassin.getBra_quantiteEnLitres()));
			resp.setStatus(brassin.getBra_statut());
			resp.setType(brassin.getBra_type());

			return resp;
		}
		return null;
	}

	/**
	 * Converts main attributes using toSimpleBrewResponse method.
	 * 
	 * Converts all steps to complex steps
	 * 
	 * @param brassin
	 * @return
	 */
	public ComplexBrewResponse toComplexBrewResponse(Brassin brassin) {
		if (brassin != null) {
			ComplexBrewResponse resp = new ComplexBrewResponse(this.toSimpleBrewResponse(brassin));

			for (Etape etp : brassin.getBra_etapes()) {

				CompleteStep stepResponse = new StepDTO().toCompleteStep(etp);
				resp.getSteps().add(stepResponse);

			}

			resp.getSteps().sort(new Comparator<SimpleStepResponse>() {

				@Override
				public int compare(SimpleStepResponse o1, SimpleStepResponse o2) {
					return o1.getNumber() - o2.getNumber();
				}
			});

			return resp;
		}
		return null;
	}

	public Brassin toBusinessObject(SimpleBrewResponse req) throws ServiceException {

		Brassin brew = new Brassin();

		/*
		 * Only used when updating brew
		 */
		brew.setBra_id(req.getId());
		brew.setBra_nom(req.getDescription());
		brew.setBra_debut(req.getBeginning());
		brew.setBra_fin(req.getEnd());
		brew.setBra_date_maj(req.getMaj());
		brew.setBra_quantiteEnLitres((double) req.getQuantity());
		brew.setBra_type(req.getType());
		brew.setBra_statut(req.getStatus());

		return brew;
	}
	

	public Brassin toBusinessObject(ComplexBrewResponse req) throws ServiceException {

		Brassin brew = this.toBusinessObject((SimpleBrewResponse) req);
		try {
			brew.setBra_etapes(new HashSet<Etape>(new StepDTO().toBusinessObjectList(req.getSteps())));
		} catch (ConversionException e) {
			throw new ServiceException(e.getMessage());
		}

		brew.setBra_malts(new MaltDTO().toBusinessObjectList(req.getMalts()));
		brew.setBra_houblons(new HopDTO().toBusinessObjectList(req.getHops()));
		brew.setBra_levures(new YeastDTO().toBusinessObjectList(req.getYeasts()));

		return brew;
	}

}
