package net.brewspberry.brewery.api;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

@JsonSubTypes({ @Type(value = Malt.class, name = "M"), //
		@Type(value = Hop.class, name = "H"), //
		@Type(value = Yeast.class, name = "Y"), //
		@Type(value = Spice.class, name = "S") })
@JsonTypeInfo(use = Id.NAME, include = As.PROPERTY, property = "type")
public abstract class AbstractIngredient {
	protected Long id;
	protected String type;
	protected String brand;
	protected String model;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

}
