package net.brewsbpberry.brewery.model;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class Hop extends AbstractIngredient {

	@Enumerated(EnumType.STRING)
	private HopType type;
	@Embedded
	private CustomQuantity alphaAcid;
	
	public enum HopType {
		BITTER, AROMATIC, BOTH;
	}
}
