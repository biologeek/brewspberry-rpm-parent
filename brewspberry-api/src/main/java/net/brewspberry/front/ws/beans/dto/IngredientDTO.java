package net.brewspberry.front.ws.beans.dto;

import net.brewspberry.business.beans.AbstractIngredient;
import net.brewspberry.business.beans.Malt;
import net.brewspberry.business.beans.SimpleHoublon;
import net.brewspberry.business.beans.SimpleLevure;
import net.brewspberry.business.beans.SimpleMalt;
import net.brewspberry.business.beans.stock.StockUnit;
import net.brewspberry.business.exceptions.DataTransferException;
import net.brewspberry.front.ws.beans.requests.IngredientJSONRequest;

public class IngredientDTO {

	public AbstractIngredient toMalt(IngredientJSONRequest obj) {
		SimpleMalt malt = new SimpleMalt();

		malt.setStb_id(obj.getId());

		malt.setIng_desc(obj.getDescription());
		malt.setIng_disc("m");
		malt.setIng_fournisseur(obj.getProvider());
		malt.setIng_unitary_price(0);
		malt.setSmal_cereale(obj.getCereal());
		malt.setSmal_couleur(obj.getColor());
		malt.setSmal_type(obj.getMaltType());
		malt.setIng_unitary_price(obj.getUnitaryPrice());
		malt.setIng_unitary_price_unit(StockUnit.fromString(obj.getUnitaryPriceUnit()));

		return malt;
	}

	public IngredientJSONRequest toServiceObject(AbstractIngredient ingredient) throws DataTransferException {
		IngredientJSONRequest result = new IngredientJSONRequest();
		
		result.setId(ingredient.getStb_id());
		result.setDescription(ingredient.getIng_desc());
		result.setProvider(ingredient.getIng_fournisseur());
		result.setUnitaryPrice(ingredient.getIng_unitary_price());
		result.setUnitaryPriceUnit(ingredient.getIng_unitary_price_unit().getStu_value());
		
		
		if (ingredient instanceof SimpleMalt){
			
			SimpleMalt malt = (SimpleMalt) ingredient;
			
			result.setCereal(malt.getSmal_cereale());
			result.setColor(malt.getSmal_couleur());
			result.setMaltType(malt.getSmal_type());

			
		} else if (ingredient instanceof SimpleHoublon){
			
			SimpleHoublon hop = (SimpleHoublon) ingredient;
			
			result.setAlphaAcid(hop.getShbl_acide_alpha());
			result.setAroma(hop.getShbl_aromes());
			result.setHopType(hop.getShbl_type());
			result.setVariety(hop.getShbl_variete());
			
		} else if (ingredient instanceof SimpleLevure){
			
			
			SimpleLevure lev = (SimpleLevure) ingredient;
			
			result.setFoculation(lev.getSlev_floculation());
			result.setSpecie(lev.getSlev_espece());
			result.setAroma(lev.getSlev_aromes());
			
		} else {
			
			throw new DataTransferException("Could not convert object ");
		}

		return result;
	}

	
	public AbstractIngredient toHop(IngredientJSONRequest request) {
		SimpleHoublon hop = new SimpleHoublon();

		hop.setStb_id(request.getId());
		hop.setIng_desc(request.getDescription());
		hop.setIng_fournisseur(request.getProvider());
		hop.setShbl_acide_alpha(request.getAlphaAcid());
		hop.setShbl_aromes(request.getAroma());
		hop.setShbl_type(request.getHopType());
		hop.setShbl_variete(request.getVariety());
		hop.setIng_unitary_price(request.getUnitaryPrice());
		hop.setIng_unitary_price_unit(StockUnit.fromString(request.getUnitaryPriceUnit()));

		return hop;
	}

	public AbstractIngredient toYeast(IngredientJSONRequest request) {
		SimpleLevure lev = new SimpleLevure();
		
		lev.setSlev_aromes(request.getAroma());
		lev.setSlev_espece(request.getSpecie());
		lev.setSlev_floculation(request.getFoculation());
		lev.setStb_id(request.getId());
		lev.setIng_desc(request.getDescription());
		lev.setIng_fournisseur(request.getProvider());
		lev.setIng_unitary_price(request.getUnitaryPrice());
		lev.setIng_unitary_price_unit(StockUnit.fromString(request.getUnitaryPriceUnit()));

		return lev;
	}
}
