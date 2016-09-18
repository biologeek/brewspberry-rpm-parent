package net.brewspberry.business.service;

import net.brewspberry.business.beans.AbstractFinishedProduct;
import net.brewspberry.business.beans.AbstractIngredient;
import net.brewspberry.business.beans.builders.StockCounterBuilder;
import net.brewspberry.business.beans.stock.FinishedProductCounter;

public class FinishedProductStockCounterBuilder extends StockCounterBuilder<FinishedProductCounter> {

	@Override
	public StockCounterBuilder ingredient(AbstractIngredient e) {
		
		return null;
	}

	@Override
	public StockCounterBuilder product(AbstractFinishedProduct e) {
		
		return null;
	}

	@Override
	public FinishedProductCounter build() {
		
		return null;
	}


}
