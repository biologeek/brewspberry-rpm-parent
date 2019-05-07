package net.brewspberry.brewery.model;

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;

@Entity
public class Hop extends AbstractIngredient {

	@Enumerated(EnumType.STRING)
	private HopType type;
	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "quantity", column = @Column(name = "alpha_acid_qty")),
			@AttributeOverride(name = "unit", column = @Column(name = "alpha_acid_unit")) })
	private CustomQuantity alphaAcid;

	@ManyToMany(mappedBy = "hops")
	private List<Brew> brew;

	public enum HopType {
		BITTER, AROMATIC, BOTH;
	}

	public HopType getType() {
		return type;
	}

	public void setType(HopType type) {
		this.type = type;
	}

	public CustomQuantity getAlphaAcid() {
		return alphaAcid;
	}

	public void setAlphaAcid(CustomQuantity alphaAcid) {
		this.alphaAcid = alphaAcid;
	}

	public List<Brew> getBrew() {
		return brew;
	}

	public void setBrew(List<Brew> brew) {
		this.brew = brew;
	}

}
