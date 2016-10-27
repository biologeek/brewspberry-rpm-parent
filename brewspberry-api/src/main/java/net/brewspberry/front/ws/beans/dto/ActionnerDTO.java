package net.brewspberry.front.ws.beans.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.brewspberry.business.beans.Actioner;
import net.brewspberry.front.ws.beans.responses.ActionnerResponse;
import net.brewspberry.front.ws.beans.responses.ActionnerResponse.ActionerStatus;
import net.brewspberry.front.ws.beans.responses.ActionnerResponse.ActionerType;
import net.brewspberry.front.ws.beans.responses.ChartResponse;

public class ActionnerDTO {

	public List<ActionnerResponse> toActionnerResponse(List<Actioner> actionners) {

		List<ActionnerResponse> result = new ArrayList<ActionnerResponse>();
		for (Actioner act : actionners) {

			if (act != null) {

				result.add(this.toActionnerResponse(act));

			}

		}

		return result;
	}
	
	
	public List<ActionnerResponse> toRawActionnerResponse(List<Actioner> actionners) {


		List<ActionnerResponse> result = new ArrayList<ActionnerResponse>();
		for (Actioner act : actionners) {

			if (act != null) {

				result.add(this.toRawActionnerResponse(act));

			}

		}

		return result;
 
	}

	private ActionnerResponse toRawActionnerResponse(Actioner actionners) {
		ActionnerResponse resp = new ActionnerResponse();

		resp.setChart(new ArrayList<ChartResponse>());
		resp.setName(actionners.getAct_nom());
		resp.setPicture(actionners.getOffPicture(actionners.getAct_type()));
		resp.setType(ActionerType.valueOf(actionners.getAct_type().name()));
		resp.setUuid(actionners.getAct_uuid());
		resp.setPin(actionners.getAct_raspi_pin());
		resp.setActive(false);
		resp.setStatus(ActionerStatus.IDLE);
		resp.setUsed(false);
		
		return resp;

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
		resp.setName(actionners.getAct_nom());
		resp.setPicture(actionners.getAct_picture());
		resp.setType(ActionerType.valueOf(actionners.getAct_type().name()));
		resp.setUuid(actionners.getAct_uuid());
		resp.setPin(actionners.getAct_raspi_pin());
		resp.setActive(actionners.getAct_activated());
		resp.setStatus(ActionerStatus.valueOf(actionners.getAct_status().name()));
		resp.setUsed(actionners.getAct_used());
		resp.setBegin(actionners.getAct_date_debut().getTime());
		resp.setEnd(actionners.getAct_date_fin().getTime());

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

		res.setAct_activated(actioner.isActive());
		res.setAct_date_debut(new Date(actioner.getBegin()));
		res.setAct_status(net.brewspberry.business.beans.Actioner.ActionerStatus.valueOf(actioner.getStatus().name()));
		res.setAct_date_fin(new Date(actioner.getEnd()));
		res.setAct_id(actioner.getId());
		res.setAct_nom(actioner.getName());
		res.setAct_picture(actioner.getPicture());
		res.setAct_raspi_pin(actioner.getPin());
		res.setAct_type(net.brewspberry.business.beans.Actioner.ActionerType.valueOf(actioner.getType().name()));
		res.setAct_used(actioner.isUsed());
		res.setAct_uuid(actioner.getUuid());

		return res;
	}

}
