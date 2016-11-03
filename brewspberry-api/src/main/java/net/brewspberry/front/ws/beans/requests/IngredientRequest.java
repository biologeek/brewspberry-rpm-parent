package net.brewspberry.front.ws.beans.requests;

import java.io.Serializable;

import net.brewspberry.business.beans.stock.StockUnit;

public class IngredientRequest implements Serializable{
	
	public enum IngredientType{
		MALT, HOP, YEAST;		
	}
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2011649263052366049L;
	private long id;
	/**
	 * Type of ingredient
	 */
	private IngredientType type;
	private String provider;
	private String description;
	private float unitaryPrice;
	private String unitaryPriceUnit;
	private float unitaryWeight;
	private String unitaryWeightUnit;
	
	private String cereal;
	private String maltType;
	private int color;
	
	
	private String variety;
	private double alphaAcid;
	private String aroma;
	private String hopType;
	
	private String specie;
	private String foculation;
	
	
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public IngredientType getType() {
		return type;
	}
	public void setType(IngredientType type) {
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
	public String getHopType() {
		return hopType;
	}
	public void setHopType(String hopType) {
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
		
		return this.unitaryPrice;
	}
	public float getUnitaryWeight() {
		return unitaryWeight;
	}
	public void setUnitaryWeight(float unitaryWeight) {
		this.unitaryWeight = unitaryWeight;
	}
	public String getUnitaryWeightUnit() {
		return unitaryWeightUnit;
	}
	public void setUnitaryWeightUnit(String unitaryWeightUnit) {
		this.unitaryWeightUnit = unitaryWeightUnit;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public void setUnitaryPrice(float unitaryPrice) {
		
		this.unitaryPrice = unitaryPrice;
	}
	public String getUnitaryPriceUnit() {
		return unitaryPriceUnit;
	}
	public void setUnitaryPriceUnit(String unitaryPriceUnit) {
		this.unitaryPriceUnit = unitaryPriceUnit;
	}


}
