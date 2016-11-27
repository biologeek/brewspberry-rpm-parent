package net.brewspberry.main.front.ws.beans.dto;

import java.util.ArrayList;
import java.util.List;

import net.brewspberry.main.business.beans.SimpleLevure;
import net.brewspberry.main.business.beans.stock.StockUnit;
import net.brewspberry.main.front.ws.DTO;
import net.brewspberry.main.front.ws.beans.requests.IngredientRequest;
import net.brewspberry.main.front.ws.beans.requests.IngredientRequest.IngredientType;

public class SimpleYeastDTO implements DTO<SimpleLevure, IngredientRequest>{

	@Override
	public SimpleLevure toBusinessObject(IngredientRequest cplObj) {

		SimpleLevure smal = new SimpleLevure();
		
		
		smal.setIng_desc(cplObj.getDescription());
		smal.setIng_fournisseur(cplObj.getProvider());
		smal.setStb_id(cplObj.getId());
		smal.setIng_desc(cplObj.getDescription());
		smal.setIng_fournisseur(cplObj.getProvider());
		smal.setStb_id(cplObj.getId());
		smal.setSlev_aromes(cplObj.getAroma());
		smal.setSlev_espece(cplObj.getSpecie());
		smal.setSlev_floculation(cplObj.getFoculation());
		smal.setIng_unitary_price(cplObj.getUnitaryPrice());
		smal.setIng_unitary_price_unit(StockUnit.fromString(cplObj.getUnitaryPriceUnit()));
		smal.setIng_unitary_weight(cplObj.getUnitaryWeight());
		smal.setIng_unitary_weight_unit(StockUnit.fromString(cplObj.getUnitaryWeightUnit()));
				
		return smal;
	}

	@Override
	public List<SimpleLevure> toBusinessObjectList(List<IngredientRequest> cplLst) {
		List<SimpleLevure> res = new ArrayList<SimpleLevure>();
		
		for (IngredientRequest el : cplLst){
			
			res.add(this.toBusinessObject(el));
		}
		
		return res;
	}

	@Override
	public IngredientRequest toFrontObject(SimpleLevure cplObj) {

		IngredientRequest res = new IngredientRequest();
		
		
		res.setId(cplObj.getStb_id());
		res.setAroma(cplObj.getSlev_aromes());
		res.setFoculation(cplObj.getSlev_floculation());
		res.setProvider(cplObj.getIng_fournisseur());
		res.setType(IngredientType.YEAST);
		res.setDescription(cplObj.getIng_desc());
		res.setSpecie(cplObj.getSlev_espece());
		res.setUnitaryPrice(cplObj.getIng_unitary_price());
		res.setUnitaryPriceUnit(cplObj.getIng_unitary_price_unit().name());
		res.setUnitaryWeight(cplObj.getIng_unitary_weight());
		res.setUnitaryWeightUnit(cplObj.getIng_unitary_weight_unit().name());
		
		return res;
	}

	@Override
	public List<IngredientRequest> toFrontObjectList(List<SimpleLevure> cplLst) {
		List<IngredientRequest> res = new ArrayList<IngredientRequest>();
		
		
		for (SimpleLevure el : cplLst){
			
			res.add(this.toFrontObject(el));
			
		}
		
		return res;	
	}

}
