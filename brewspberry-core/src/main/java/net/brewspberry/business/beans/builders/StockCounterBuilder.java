package net.brewspberry.business.beans.builders;

import net.brewspberry.business.beans.stock.CompteurType;
import net.brewspberry.business.beans.stock.StockCounter;
import net.brewspberry.business.beans.stock.StockUnit;

public abstract class StockCounterBuilder<T> {

	
	
	protected StockCounter counter;


	public StockCounterBuilder type(CompteurType a){
		
		counter.setCpt_counter_type(a);
		
		
		return this;	
		
	}

	public StockCounterBuilder value(double a){
		
		counter.setCpt_value(a);
		
		
		return this;	
		
	}
	

	public StockCounterBuilder unit(StockUnit a){
		
		counter.setCpt_unit(a);
		
		
		return this;	
		
	}
	
	
	public abstract T build();
}
