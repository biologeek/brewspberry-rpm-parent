package net.brewspberry.front.ws.beans.requests;

public class ConcreteIngredientRequest extends IngredientRequest {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6781378358611800911L;
	private float quantity;
	private float price;
	
	
	
	public float getQuantity() {
		return quantity;
	}
	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	
	
	

}
