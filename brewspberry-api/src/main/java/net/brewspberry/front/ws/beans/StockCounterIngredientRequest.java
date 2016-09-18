package net.brewspberry.front.ws.beans;

public class StockCounterIngredientRequest {
	
	
	private long productID;
	private double stockMotion;
	private long counterTypeID;
	
	
	
	public long getProductID() {
		return productID;
	}
	public void setProductID(long productID) {
		this.productID = productID;
	}
	public double getStockMotion() {
		return stockMotion;
	}
	public void setStockMotion(double stockMotion) {
		this.stockMotion = stockMotion;
	}
	public long getCounterTypeID() {
		return counterTypeID;
	}
	public void setCounterTypeID(long counterTypeID) {
		this.counterTypeID = counterTypeID;
	}
	
	

}
