package net.brewspberry.main.front.ws.beans;

public class StockUnitLight {

	
	
	private String obj;
	private String trad;
	
	
	public String getObj() {
		return obj;
	}
	public void setObj(String obj) {
		this.obj = obj;
	}
	public String getTrad() {
		return trad;
	}
	public void setTrad(String trad) {
		this.trad = trad;
	}
	
	
	public StockUnitLight obj(String trad) {
		this.obj = trad;
		return this;
	}
	public StockUnitLight trad(String trad) {
		this.trad = trad;
		return this;
	}
	
	
	
	
}
