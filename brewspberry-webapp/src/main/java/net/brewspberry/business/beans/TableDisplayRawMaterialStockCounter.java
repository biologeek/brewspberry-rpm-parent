package net.brewspberry.business.beans;

import net.brewspberry.business.beans.stock.RawMaterialCounter;
import net.brewspberry.business.beans.stock.StockCounter;

public class TableDisplayRawMaterialStockCounter extends TableDisplayStockCounter {

	/**
	 * 
	 */
	private static final long serialVersionUID = -67428853550436058L;

	
	
	private AbstractIngredient str_ingredient;



	public TableDisplayRawMaterialStockCounter(RawMaterialCounter arg0) {
		super(arg0);
		
		this.setStr_ingredient(arg0.getCpt_product());

	}

	public AbstractIngredient getStr_ingredient() {
		return str_ingredient;
	}

	public void setStr_ingredient(AbstractIngredient str_ingredient) {
		this.str_ingredient = str_ingredient;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
