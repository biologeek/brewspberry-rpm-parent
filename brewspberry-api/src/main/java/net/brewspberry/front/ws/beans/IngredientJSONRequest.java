package net.brewspberry.front.ws.beans;

import java.io.Serializable;

import net.brewspberry.business.beans.stock.StockUnit;

public class IngredientJSONRequest implements Serializable{
	
	
	private long id;
	private String type;
	private String provider;
	private String description;
	private String cereal;
	private String maltType;
	private int color;
	private String variety;
	private double alphaAcid;
	private String aroma;
	private int hopType;
	private String specie;
	private String foculation;
	private float unitaryPrice;
	private String unitaryPriceUnit;
	
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCereal() {
		return cereal;
	}
	public void setCereal(String cereal) {
		this.cereal = cereal;
	}
	public String getMaltType() {
		return maltType;
	}
	public void setMaltType(String maltType) {
		this.maltType = maltType;
	}
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
	public String getVariety() {
		return variety;
	}
	public void setVariety(String variety) {
		this.variety = variety;
	}
	public double getAlphaAcid() {
		return alphaAcid;
	}
	public void setAlphaAcid(double alphaAcid) {
		this.alphaAcid = alphaAcid;
	}
	public String getAroma() {
		return aroma;
	}
	public void setAroma(String aroma) {
		this.aroma = aroma;
	}
	public int getHopType() {
		return hopType;
	}
	public void setHopType(int hopType) {
		this.hopType = hopType;
	}
	public String getSpecie() {
		return specie;
	}
	public void setSpecie(String specie) {
		this.specie = specie;
	}
	public String getFoculation() {
		return foculation;
	}
	public void setFoculation(String foculation) {
		this.foculation = foculation;
	}
	public float getUnitaryPrice() {
		// TODO Auto-generated method stub
		return this.unitaryPrice;
	}
	public void setUnitaryPrice(float unitaryPrice) {
		// TODO Auto-generated method stub
		this.unitaryPrice = unitaryPrice;
	}
	public String getUnitaryPriceUnit() {
		return unitaryPriceUnit;
	}
	public void setUnitaryPriceUnit(String unitaryPriceUnit) {
		this.unitaryPriceUnit = unitaryPriceUnit;
	}


}
