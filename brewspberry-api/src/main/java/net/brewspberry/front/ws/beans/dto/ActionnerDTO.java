package net.brewspberry.front.ws.beans.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import net.brewspberry.business.beans.Actioner;
import net.brewspberry.business.beans.GenericActionner;
import net.brewspberry.front.ws.beans.responses.ActionnerResponse;
import net.brewspberry.front.ws.beans.responses.ActionnerResponse.ActionerStatus;
import net.brewspberry.front.ws.beans.responses.ActionnerResponse.ActionerType;
import net.brewspberry.front.ws.beans.responses.ChartResponse;

public class ActionnerDTO {

	List<ActionnerResponse> resultAPI;

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
		resp.setName(actionners.getAct_generic().getAct_nom());
		resp.setPicture(actionners.getAct_generic().getAct_picture());
		resp.setType(ActionerType.valueOf(actionners.getAct_generic().getAct_type().name()));
		resp.setUuid(actionners.getAct_generic().getAct_uuid());
		resp.setPin(actionners.getAct_generic().getAct_raspi_pin());
		resp.setActive(actionners.getAct_generic().getAct_activated());
		resp.setStatus(ActionerStatus.valueOf(actionners.getAct_generic().getAct_status().name()));
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

		res.setAct_date_debut(new Date(actioner.getBegin()));
		res.setAct_date_fin(new Date(actioner.getEnd()));
		res.setAct_id(actioner.getId());
		res.setAct_used(actioner.isUsed());
		res.setAct_generic(new GenericActionnerDTO().toBusinessObject(actioner));

		return res;
	}

	
	public class GenericActionnerDTO {
		
		
		public GenericActionnerDTO() {
			
		}

		public GenericActionner toBusinessObject(net.brewspberry.front.ws.beans.responses.GenericActionner actioner) {
			GenericActionner res = new GenericActionner();

			
			res.setAct_status(net.brewspberry.business.beans.GenericActionner.ActionerStatus.IDLE);
			res.setAct_id(actioner.getId());
			res.setAct_nom(actioner.getName());
			res.setAct_picture(actioner.getPicture());
			res.setAct_raspi_pin(actioner.getPin());
			res.setAct_type(net.brewspberry.business.beans.GenericActionner.ActionerType.valueOf(actioner.getType().name()));
			res.setAct_uuid(actioner.getUuid());

			return res;
		}

		public GenericActionner toBusinessObject(ActionnerResponse actioner) {
			GenericActionner res = new GenericActionner();

			
			res.setAct_status(net.brewspberry.business.beans.GenericActionner.ActionerStatus.IDLE);
			res.setAct_id(actioner.getId());
			res.setAct_nom(actioner.getName());
			res.setAct_picture(actioner.getPicture());
			res.setAct_raspi_pin(actioner.getPin());
			res.setAct_type(net.brewspberry.business.beans.GenericActionner.ActionerType.valueOf(actioner.getType().name()));
			res.setAct_uuid(actioner.getUuid());

			return res;
		}
		


		public net.brewspberry.front.ws.beans.responses.GenericActionner toRawActionnerResponse(GenericActionner actionners, boolean alterID) {
			net.brewspberry.front.ws.beans.responses.GenericActionner resp = new net.brewspberry.front.ws.beans.responses.GenericActionner();

			if (!alterID)
				resp.setId(actionners.getAct_id());
			resp.setName(actionners.getAct_nom());
			resp.setPicture(actionners.getOffPicture(actionners.getAct_type()));
			resp.setType(ActionerType.valueOf(actionners.getAct_type().name()));
			resp.setUuid(actionners.getAct_uuid());
			resp.setPin(actionners.getAct_raspi_pin());
			resp.setState(actionners.getAct_status().name());
			

			return resp;

		}
		
		

		
		public List<net.brewspberry.front.ws.beans.responses.GenericActionner> toRawActionnerResponse(List<GenericActionner> actionners, boolean alterID) {
			List<net.brewspberry.front.ws.beans.responses.GenericActionner> result = new ArrayList<>();

			for (GenericActionner act : actionners) {

				if (act != null) {

					result.add(this.toRawActionnerResponse(act, true));

				}

			}

			return result;

		}

	}

}
