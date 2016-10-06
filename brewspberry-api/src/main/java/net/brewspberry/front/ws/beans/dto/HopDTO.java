package net.brewspberry.front.ws.beans.dto;

import java.util.ArrayList;
import java.util.List;

import net.brewspberry.business.beans.Houblon;
import net.brewspberry.front.ws.DTO;
import net.brewspberry.front.ws.beans.requests.ConcreteIngredientRequest;

public class HopDTO implements DTO<Houblon, ConcreteIngredientRequest> {

	public List<Houblon> toBusinessObjectList(List<ConcreteIngredientRequest> list) {

		List<Houblon> res = new ArrayList<Houblon>();

		for (ConcreteIngredientRequest rq : list) {

			res.add(this.toBusinessObject(rq));

		}

		return res;
	}

	public List<ConcreteIngredientRequest> toFrontObjectList(List<Houblon> etp_Houblons) {
		List<ConcreteIngredientRequest> res = new ArrayList<ConcreteIngredientRequest>();

		for (Houblon rq : etp_Houblons) {

			res.add(this.toFrontObject(rq));

		}

		return res;
	}

	@Override
	public Houblon toBusinessObject(ConcreteIngredientRequest cplObj) {

		Houblon mlt = new Houblon();
		if (cplObj != null) {

			mlt = (Houblon) new SimpleHopDTO().toBusinessObject(cplObj);

			mlt.setIng_quantite(cplObj.getQuantity());
			mlt.setIng_prix(cplObj.getPrice());
			return mlt;
		}

		return null;
	}

	@Override
	public ConcreteIngredientRequest toFrontObject(Houblon cplObj) {
		ConcreteIngredientRequest res = new ConcreteIngredientRequest();

		if (cplObj != null) {

			res = (ConcreteIngredientRequest) new SimpleHopDTO().toFrontObject(cplObj);
			res.setQuantity(cplObj.getIng_quantite());
			res.setPrice(cplObj.getIng_prix());

			return res;

		}

		return null;
	}

}
