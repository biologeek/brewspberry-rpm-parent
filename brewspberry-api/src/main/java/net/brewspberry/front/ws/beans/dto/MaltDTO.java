package net.brewspberry.front.ws.beans.dto;

import java.util.ArrayList;
import java.util.List;

import net.brewspberry.business.beans.Malt;
import net.brewspberry.front.ws.DTO;
import net.brewspberry.front.ws.beans.requests.ConcreteIngredientRequest;

public class MaltDTO implements DTO<Malt, ConcreteIngredientRequest> {

	public List<Malt> toBusinessObjectList(List<ConcreteIngredientRequest> list) {

		List<Malt> res = new ArrayList<Malt>();
		if (list != null && list.size() > 0) {
			for (ConcreteIngredientRequest rq : list) {

				res.add(this.toBusinessObject(rq));

			}
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


			
			res.setId(cplObj.getStb_id());
			res.setCereal(cplObj.getSmal_cereale());
			res.setColor(cplObj.getSmal_couleur());
			res.setProvider(cplObj.getIng_fournisseur());
			res.setType("malt");
			res.setDescription(cplObj.getIng_desc());
			res.setMaltType(cplObj.getSmal_type());
			res.setUnitaryPrice(cplObj.getIng_unitary_price());
			res.setUnitaryPriceUnit(cplObj.getIng_unitary_price_unit().name());
			res.setUnitaryWeight(cplObj.getIng_unitary_weight());
			res.setUnitaryWeightUnit(cplObj.getIng_unitary_weight_unit().name());
			res.setQuantity(cplObj.getIng_quantite());
			res.setPrice(cplObj.getIng_prix());

			return res;

		}

		return null;
	}

}