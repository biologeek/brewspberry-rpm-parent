package net.brewspberry.main.business.beans.builders;

import java.util.Date;

import net.brewspberry.main.business.beans.AbstractFinishedProduct;
import net.brewspberry.main.business.beans.AbstractIngredient;
import net.brewspberry.main.business.beans.stock.RawMaterialCounter;
import net.brewspberry.main.business.beans.stock.StockCounter;

public class IngredientStockCounterBuilder extends StockCounterBuilder<RawMaterialCounter> {


	
	public IngredientStockCounterBuilder (){
		
		counter = new RawMaterialCounter();

		counter.setCpt_date_cre(new Date());

		counter.setCpt_date_maj(new Date());
	}
	
	@Override
	public StockCounterBuilder<RawMaterialCounter> ingredient(AbstractIngredient a){
		
		((RawMaterialCounter) counter).setCpt_product(a);
		return this;
		
	}

	
	@Override
	public RawMaterialCounter build() {
		return (RawMaterialCounter) counter;
	}


	@Override
	public StockCounterBuilder<RawMaterialCounter> product(
			AbstractFinishedProduct e) {
		
		return null;
	}
}
