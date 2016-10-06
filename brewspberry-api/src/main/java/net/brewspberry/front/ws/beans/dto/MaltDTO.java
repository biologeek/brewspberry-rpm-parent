package net.brewspberry.front.ws.beans.dto;

import java.util.ArrayList;
import java.util.List;

import net.brewspberry.business.beans.Malt;
import net.brewspberry.front.ws.DTO;
import net.brewspberry.front.ws.beans.requests.ConcreteIngredientRequest;

public class MaltDTO implements DTO<Malt, ConcreteIngredientRequest> {

	public List<Malt> toBusinessObjectList(List<ConcreteIngredientRequest> list) {

		List<Malt> res = new ArrayList<Malt>();

		for (ConcreteIngredientRequest rq : list) {

			res.add(this.toBusinessObject(rq));

		}

		return res;
	}

	public List<ConcreteIngredientRequest> toFrontObjectList(List<Malt> etp_Malts) {
		List<ConcreteIngredientRequest> res = new ArrayList<ConcreteIngredientRequest>();

		for (Malt rq : etp_Malts) {

			res.add(this.toFrontObject(rq));

		}

		return res;
	}

	@Override
	public Malt toBusinessObject(ConcreteIngredientRequest cplObj) {

		Malt mlt = new Malt();
		if (cplObj != null) {

			mlt = (Malt) new SimpleMaltDTO().toBusinessObject(cplObj);

			mlt.setIng_quantite(cplObj.getQuantity());
			mlt.setIng_prix(cplObj.getPrice());
			return mlt;
		}

		return null;
	}

	@Override
	public ConcreteIngredientRequest toFrontObject(Malt cplObj) {
		ConcreteIngredientRequest res = new ConcreteIngredientRequest();

		if (cplObj != null) {

			res = (ConcreteIngredientRequest) new SimpleMaltDTO().toFrontObject(cplObj);
			res.setQuantity(cplObj.getIng_quantite());
			res.setPrice(cplObj.getIng_prix());

			return res;

		}

		return null;
	}

}
