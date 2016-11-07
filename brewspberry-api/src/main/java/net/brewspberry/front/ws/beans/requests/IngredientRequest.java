package net.brewspberry.front.ws.beans.requests;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;


@JsonInclude(content=Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class IngredientRequest implements Serializable{
	
	public enum IngredientType{
		MALT, HOP, YEAST;		
	}
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2011649263052366049L;
	@JsonProperty(required=false)
	private long id;
	/**
	 * Type of ingredient
	 */
	@JsonProperty(required=true)
	private IngredientType type;
	@JsonProperty(required=true)
	private String provider;
	@JsonProperty(required=true)
	private String description;
	@JsonProperty(required=true)
	private float unitaryPrice;
	@JsonProperty(required=true)
	private String unitaryPriceUnit;
	@JsonProperty(required=true)
	private float unitaryWeight;
	@JsonProperty(required=true)
	private String unitaryWeightUnit;
	
	@JsonProperty(required=false) 
	private String cereal;
	@JsonProperty(required=false)
	private String maltType;
	@JsonProperty(required=false)
	private int color;
	
	
	@JsonProperty(required=false)
	private String variety;
	@JsonProperty(required=false)
	private double alphaAcid;
	@JsonProperty(required=false)
	private String aroma;
	@JsonProperty(required=false)
	private String hopType;
	
	@JsonProperty(required=false)
	private String specie;
	@JsonProperty(required=false)
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
