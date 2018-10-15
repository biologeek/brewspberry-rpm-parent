package net.brewspberry.brewery.api;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

@JsonSubTypes({ @Type(value = Malt.class, name = "M"), //
		@Type(value = Hop.class, name = "H"), //
		@Type(value = Yeast.class, name = "Y"),// 
		@Type(value=Spice.class, name="S")})
@JsonTypeInfo(use = Id.NAME, include = As.PROPERTY, property = "type")
public abstract class AbstractIngredient {
	protected Long id;
	protected String type, brand, model;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
