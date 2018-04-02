package net.brewspberry.main.business.beans.stock;

import net.brewspberry.main.business.beans.brewing.AbstractIngredient;

public class RawMaterialStockMotion extends AbstractStockMotion{

	
	
	private AbstractIngredient str_product;

	public AbstractIngredient getStr_product() {
		return str_product;
	}

	public void setStr_product(AbstractIngredient str_product) {
		this.str_product = str_product;
	}
	
}
