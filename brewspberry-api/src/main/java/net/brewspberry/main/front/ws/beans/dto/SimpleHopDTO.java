package net.brewspberry.main.front.ws.beans.dto;

import java.util.ArrayList;
import java.util.List;

import net.brewspberry.main.business.beans.brewing.SimpleHoublon;
import net.brewspberry.main.business.beans.brewing.SimpleHoublon.HopType;
import net.brewspberry.main.business.beans.stock.StockUnit;
import net.brewspberry.main.front.ws.DTO;
import net.brewspberry.main.front.ws.beans.requests.IngredientRequest;
import net.brewspberry.main.front.ws.beans.requests.IngredientRequest.IngredientType;

public class SimpleHopDTO implements DTO<SimpleHoublon, IngredientRequest>{

	@Override
	public SimpleHoublon toBusinessObject(IngredientRequest cplObj) {

		SimpleHoublon smal = new SimpleHoublon();
		
		
		smal.setIng_desc(cplObj.getDescription());
		smal.setIng_fournisseur(cplObj.getProvider());
		smal.setStb_id(cplObj.getId());
		smal.setIng_desc(cplObj.getDescription());
		smal.setIng_fournisseur(cplObj.getProvider());
		smal.setStb_id(cplObj.getId());
		smal.setShbl_acide_alpha(cplObj.getAlphaAcid());
		smal.setShbl_aromes(cplObj.getAroma());
		smal.setShbl_type(HopType.valueOf(cplObj.getHopType()));
		smal.setShbl_variete(cplObj.getVariety());
		smal.setIng_unitary_price(cplObj.getUnitaryPrice());
		smal.setIng_unitary_price_unit(StockUnit.fromString(cplObj.getUnitaryPriceUnit()));
		smal.setIng_unitary_weight(cplObj.getUnitaryWeight());
		smal.setIng_unitary_weight_unit(StockUnit.fromString(cplObj.getUnitaryWeightUnit()));
				
		return smal;
	}

	@Override
	public List<SimpleHoublon> toBusinessObjectList(List<IngredientRequest> cplLst) {
		List<SimpleHoublon> res = new ArrayList<SimpleHoublon>();
		
		for (IngredientRequest el : cplLst){
			
			res.add(this.toBusinessObject(el));
		}
		
		return res;
	}

	@Override
	public IngredientRequest toFrontObject(SimpleHoublon cplObj) {

		IngredientRequest res = new IngredientRequest();
		
		
		res.setId(cplObj.getStb_id());
		res.setAroma(cplObj.getShbl_aromes());
		res.setHopType(cplObj.getShbl_type().name());
		res.setProvider(cplObj.getIng_fournisseur());
		res.setType(IngredientType.HOP);
		res.setDescription(cplObj.getIng_desc());
		res.setAlphaAcid(cplObj.getShbl_acide_alpha());
		res.setVariety(cplObj.getShbl_variete());
		res.setUnitaryPrice(cplObj.getIng_unitary_price());
		res.setUnitaryPriceUnit(cplObj.getIng_unitary_price_unit().name());
		res.setUnitaryWeight(cplObj.getIng_unitary_weight());
		res.setUnitaryWeightUnit(cplObj.getIng_unitary_weight_unit().name());
		
		return res;
	}

	@Override
	public List<IngredientRequest> toFrontObjectList(List<SimpleHoublon> cplLst) {
		List<IngredientRequest> res = new ArrayList<IngredientRequest>();
		
		
		for (SimpleHoublon el : cplLst){
			
			res.add(this.toFrontObject(el));
			
		}
		
		return res;	
	}

}
