package net.brewspberry.main.business.beans.builders;

import java.util.Date;

import net.brewspberry.main.business.beans.brewing.AbstractFinishedProduct;
import net.brewspberry.main.business.beans.brewing.AbstractIngredient;
import net.brewspberry.main.business.beans.stock.CounterType;
import net.brewspberry.main.business.beans.stock.RawMaterialCounter;
import net.brewspberry.main.business.beans.stock.StockCounter;
import net.brewspberry.main.business.beans.stock.StockUnit;

public class IngredientStockCounterBuilder extends StockCounterBuilder<RawMaterialCounter> {

	
	
	public IngredientStockCounterBuilder() {
		counter = new RawMaterialCounter();
		counter.setCpt_date_cre(new Date());
		counter.setCpt_date_maj(new Date());
	}
	

	public IngredientStockCounterBuilder type(CounterType a){
		counter.setCpt_counter_type(a);
		return this;
	}

	public IngredientStockCounterBuilder value(double a){		
		counter.setCpt_value(a);		
		return this;	
	}

	public IngredientStockCounterBuilder unit(StockUnit a){		
		counter.setCpt_unit(a);	
		return this;		
	}	

	public IngredientStockCounterBuilder ingredient(AbstractIngredient a) {
		((RawMaterialCounter) counter).setCpt_product(a);
		return this;
	}

	@Override
	public RawMaterialCounter build() {
		return (RawMaterialCounter) counter;
	}

}
