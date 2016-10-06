package net.brewspberry.front.ws.beans.dto;

import java.util.ArrayList;
import java.util.List;

import net.brewspberry.business.beans.SimpleMalt;
import net.brewspberry.business.beans.stock.StockUnit;
import net.brewspberry.front.ws.DTO;
import net.brewspberry.front.ws.beans.requests.IngredientRequest;

public class SimpleMaltDTO implements DTO<SimpleMalt, IngredientRequest>{

	@Override
	public SimpleMalt toBusinessObject(IngredientRequest cplObj) {

		SimpleMalt smal = new SimpleMalt();
		
		
		smal.setIng_desc(cplObj.getDescription());
		smal.setIng_fournisseur(cplObj.getProvider());
		smal.setStb_id(cplObj.getId());
		smal.setIng_desc(cplObj.getDescription());
		smal.setIng_fournisseur(cplObj.getProvider());
		smal.setStb_id(cplObj.getId());
		smal.setSmal_type(cplObj.getMaltType());
		smal.setSmal_cereale(cplObj.getCereal());
		smal.setSmal_couleur(cplObj.getColor());
		smal.setIng_unitary_price(cplObj.getUnitaryPrice());
		smal.setIng_unitary_price_unit(StockUnit.fromString(cplObj.getUnitaryPriceUnit()));
		smal.setIng_unitary_weight(cplObj.getUnitaryWeight());
		smal.setIng_unitary_weight_unit(StockUnit.fromString(cplObj.getUnitaryWeightUnit()));
				
		return smal;
	}

	@Override
	public List<SimpleMalt> toBusinessObjectList(List<IngredientRequest> cplLst) {
		List<SimpleMalt> res = new ArrayList<SimpleMalt>();
		
		for (IngredientRequest el : cplLst){
			
			res.add(this.toBusinessObject(el));
		}
		
		return res;
	}

	@Override
	public IngredientRequest toFrontObject(SimpleMalt cplObj) {

		IngredientRequest res = new IngredientRequest();
		
		
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
		
		return res;
	}

	@Override
	public List<IngredientRequest> toFrontObjectList(List<SimpleMalt> cplLst) {
		List<IngredientRequest> res = new ArrayList<IngredientRequest>();
		
		
		for (SimpleMalt el : cplLst){
			
			res.add(this.toFrontObject(el));
			
		}
		
		return res;	
	}

}
