package net.brewspberry.test.examples;

import net.brewspberry.main.business.beans.brewing.AbstractIngredient;
import net.brewspberry.main.business.beans.brewing.Malt;
import net.brewspberry.main.business.beans.brewing.SimpleMalt;
import net.brewspberry.main.business.beans.stock.StockUnit;

public class IngredientExemple {
	
	
	
public static SimpleMalt aSimpleMalt(){
		
		return new SimpleMalt.Builder()
				.smal_cereale("Barley")//
				.smal_couleur(10)//
				.smal_type("Blond")//
				.ing_desc("Malt Pilsen")//
				.ing_fournisseur("Weyermann")//
				.ing_unitary_price(10)//
				.ing_unitary_price_unit(StockUnit.KILO)//
				.ing_unitary_weight(1)//
				.ing_unitary_weight_unit(StockUnit.KILO)//
				.build();
	}

public static AbstractIngredient aMalt(long brewId, long stepId){
		
		
		return new Malt.Builder(IngredientExemple.aSimpleMalt())
				.brew(BrewExemple.aBrew(brewId))
				.step(BrewExemple.aStep(stepId))
				.quantity(5)
				.build();
	}

}
