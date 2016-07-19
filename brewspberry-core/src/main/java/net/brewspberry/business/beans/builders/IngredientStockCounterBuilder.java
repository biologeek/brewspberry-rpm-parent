package net.brewspberry.business.beans.builders;

import net.brewspberry.business.beans.AbstractIngredient;
import net.brewspberry.business.beans.stock.RawMaterialCounter;
import net.brewspberry.business.beans.stock.StockCounter;

public class IngredientStockCounterBuilder extends StockCounterBuilder<RawMaterialCounter> {


	
	public IngredientStockCounterBuilder (){
		
		counter = new RawMaterialCounter();
	}
	
	
	
	public StockCounterBuilder ingredient(AbstractIngredient a){
		
		((RawMaterialCounter) counter).setCpt_product(a);
		return this;
		
	}

	
	@Override
	public RawMaterialCounter build() {
		return (RawMaterialCounter) counter;
	}
}
