package net.brewspberry.main.front.ws.beans.dto;

import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import net.brewspberry.main.business.beans.Actioner;
import net.brewspberry.main.business.beans.Brassin;
import net.brewspberry.main.business.beans.Etape;
import net.brewspberry.main.business.beans.GenericActionner;
import net.brewspberry.main.front.ws.beans.responses.ActionnerResponse;
import net.brewspberry.main.front.ws.beans.responses.ChartResponse;
import net.brewspberry.main.front.ws.beans.responses.ActionnerResponse.ActionerStatus;
import net.brewspberry.main.front.ws.beans.responses.ActionnerResponse.ActionerType;

public class ActionnerDTO {

	List<ActionnerResponse> resultAPI;
	private Etape step;

	public ActionnerDTO() {
		resultAPI = new ArrayList<ActionnerResponse>();
	}

	public ActionnerDTO toActionnerResponse(List<Actioner> actionners) {
		resultAPI = new ArrayList<>();

		for (Actioner act : actionners) {

			if (act != null) {

				resultAPI.add(this.toActionnerResponse(act));

			}

		}

		return this;
	}

	public List<ActionnerResponse> buildAPI() {
		return resultAPI;
	}

	/**
	 * Transforms business objet to Transfer object
	 * 
	 * @param actionners
	 * @return
	 */
	public ActionnerResponse toActionnerResponse(Actioner actionners) {

		ActionnerResponse resp = new ActionnerResponse();

		resp.setChart(new ArrayList<ChartResponse>());
		resp.setId(actionners.getAct_id());
		resp.setGenericId(actionners.getAct_generic().getGact_id());
		resp.setName(actionners.getAct_generic().getGact_nom());
		resp.setType(ActionerType.valueOf(actionners.getAct_generic().getGact_type().name()));
		resp.setUuid(actionners.getAct_generic().getGact_uuid());
		resp.setPin(actionners.getAct_generic().getGact_raspi_pin());
		resp.setActive(actionners.getAct_generic().getGact_activated());
		resp.setState(ActionerStatus.valueOf(actionners.getAct_generic().getGact_status().name()));
		resp.setUsed(actionners.getAct_used());
		resp.setBegin(actionners.getAct_date_debut() == null ? 0 : actionners.getAct_date_debut().getTime());
		resp.setEnd(actionners.getAct_date_fin() == null ? 0 : actionners.getAct_date_fin().getTime());
		resp.setStepId(actionners.getAct_etape() == null ? null : actionners.getAct_etape().getEtp_id());
		resp.setBrewId(actionners.getAct_brassin() == null ? null : actionners.getAct_brassin().getBra_id());
		resp.setPicture(actionners.getAct_generic().getGact_picture());

		return resp;

	}

	public List<Actioner> toBusinessObjectList(List<ActionnerResponse> actioners) {
		List<Actioner> res = new ArrayList<Actioner>();
		if (actioners != null && !actioners.isEmpty()) {
			for (ActionnerResponse resp : actioners) {

				res.add(this.toBusinessObject(resp));

			}
		}

		return res;
	}

	public Actioner toBusinessObject(ActionnerResponse actioner) {
		Actioner res = new Actioner();

		if (actioner.getBegin() > 0) {
			res.setAct_date_debut(new Date(actioner.getBegin()));
		}
		if (actioner.getEnd() > 0) {
			res.setAct_date_fin(new Date(actioner.getEnd()));
		}

		res.setAct_id(actioner.getId() == 0 ? null : actioner.getId());
		res.setAct_used(actioner.isUsed());
		res.setAct_generic(new GenericActionnerDTO().toBusinessObject(actioner));
		res.setAct_brassin(new Brassin().id(actioner.getBrewId()));
		res.setAct_etape(this.step);

		return res;
	}

	public class GenericActionnerDTO {

		public GenericActionnerDTO() {

		}

		public GenericActionner toBusinessObject(net.brewspberry.main.front.ws.beans.responses.GenericActionner actioner) {
			GenericActionner res = new GenericActionner();

			res.setGact_status(net.brewspberry.main.business.beans.GenericActionner.ActionerStatus.IDLE);
			res.setGact_id(actioner.getGenericId());
			res.setGact_nom(actioner.getName());
			res.setGact_raspi_pin(actioner.getPin());
			res.setGact_type(
					net.brewspberry.main.business.beans.GenericActionner.ActionerType.valueOf(actioner.getType().name()));
			res.setGact_uuid(actioner.getUuid());
			res.setGact_picture(actioner.getPicture());

			return res;
		}

		public GenericActionner toBusinessObject(ActionnerResponse actioner) {
			GenericActionner res = new GenericActionner();

			res.setGact_status(
					actioner.getState() == null ? net.brewspberry.main.business.beans.GenericActionner.ActionerStatus.IDLE
							: net.brewspberry.main.business.beans.GenericActionner.ActionerStatus
									.valueOf(actioner.getState().name()));
			res.setGact_id(actioner.getGenericId());
			res.setGact_nom(actioner.getName());
			res.setGact_raspi_pin(actioner.getPin());
			res.setGact_type(
					net.brewspberry.main.business.beans.GenericActionner.ActionerType.valueOf(actioner.getType().name()));
			res.setGact_uuid(actioner.getUuid());
			res.setGact_picture(actioner.getPictureWithStatus());

			return res;
		}

		public net.brewspberry.main.front.ws.beans.responses.GenericActionner toRawActionnerResponse(
				GenericActionner actionners, boolean alterID) {
			net.brewspberry.main.front.ws.beans.responses.GenericActionner resp = new net.brewspberry.main.front.ws.beans.responses.GenericActionner();

			if (!alterID)
				resp.setGenericId(actionners.getGact_id());
			resp.setName(actionners.getGact_nom());
			resp.setPicture(actionners.getPictureWithStatus());
			resp.setType(ActionerType.valueOf(actionners.getGact_type().name()));
			resp.setUuid(actionners.getGact_uuid());
			resp.setPin(actionners.getGact_raspi_pin());
			resp.setState(actionners.getGact_status().name());

			return resp;

		}

		public List<net.brewspberry.main.front.ws.beans.responses.GenericActionner> toRawActionnerResponse(
				List<GenericActionner> actionners, boolean alterID) {
			List<net.brewspberry.main.front.ws.beans.responses.GenericActionner> result = new ArrayList<>();

			for (GenericActionner act : actionners) {
				if (act != null) {
					result.add(this.toRawActionnerResponse(act, alterID));
				}
			}
			return result;
		}
	}

	public ActionnerDTO step(Etape res) {
		this.step = res;
		return this;
	}

}
