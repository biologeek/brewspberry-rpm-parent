package net.brewspberry.main.business.beans;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.type.descriptor.java.JavaTypeDescriptorRegistry.FallbackJavaTypeDescriptor;
import org.springframework.stereotype.Component;

import net.brewspberry.main.business.beans.stock.RawMaterialCounter;
import net.brewspberry.main.business.beans.stock.StockCounter;
import net.brewspberry.main.business.beans.stock.StockUnit;
import net.brewspberry.main.business.beans.stock.Stockable;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Component
public abstract class AbstractIngredient extends Stockable {

	/**
	 * Abstract Ingredient represents ingredient general characteristics
	 * 
	 * Not mapped in DB
	 * 
	 */
	private static final long serialVersionUID = -5363007498088123647L;

	String ing_desc;

	String ing_fournisseur;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cpt_product")
	List<RawMaterialCounter> stb_counters;
	/**
	 * In euro/StockUnit
	 */
	float ing_unitary_price;

	StockUnit ing_unitary_price_unit;
	/**
	 * Stored in StockUnit
	 */
	float ing_unitary_weight;
	StockUnit ing_unitary_weight_unit;

	public float getIng_unitary_weight() {
		return ing_unitary_weight;
	}

	public void setIng_unitary_weight(float ing_unitary_weight) {
		this.ing_unitary_weight = ing_unitary_weight;
	}

	public StockUnit getIng_unitary_weight_unit() {
		return ing_unitary_weight_unit;
	}

	public void setIng_unitary_weight_unit(StockUnit ing_unitary_weight_unit) {
		this.ing_unitary_weight_unit = ing_unitary_weight_unit;
	}

	public StockUnit getIng_unitary_price_unit() {
		return ing_unitary_price_unit;
	}

	public void setIng_unitary_price_unit(StockUnit ing_unitary_price_unit) {
		this.ing_unitary_price_unit = ing_unitary_price_unit;
	}

	public String getIng_desc() {
		return ing_desc;
	}

	public void setIng_desc(String ing_desc) {
		this.ing_desc = ing_desc;
	}

	public String getIng_fournisseur() {
		return ing_fournisseur;
	}

	public void setIng_fournisseur(String ing_fournisseur) {
		this.ing_fournisseur = ing_fournisseur;
	}

	public String getIng_disc() {
		return ing_desc;
	}

	public void setIng_disc(String ing_disc) {
		this.ing_desc = ing_disc;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public float getIng_unitary_price() {
		return ing_unitary_price;
	}

	public void setIng_unitary_price(float ing_unitary_price) {
		this.ing_unitary_price = ing_unitary_price;
	}

	public List<RawMaterialCounter> getStb_counters() {
		return stb_counters;
	}

	public void setStb_counters(List<RawMaterialCounter> stb_counters) {
		this.stb_counters = stb_counters;
	}

	@Override
	public String toString() {
		return "AbstractIngredient [ing_desc=" + ing_desc + ", ing_fournisseur=" + ing_fournisseur + ", stb_counters="
				+ stb_counters + ", ing_unitary_price=" + ing_unitary_price + ", ing_unitary_price_unit="
				+ ing_unitary_price_unit + ", ing_unitary_weight=" + ing_unitary_weight + ", ing_unitary_weight_unit="
				+ ing_unitary_weight_unit + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ing_desc == null) ? 0 : ing_desc.hashCode());
		result = prime * result + ((ing_fournisseur == null) ? 0 : ing_fournisseur.hashCode());
		result = prime * result + Float.floatToIntBits(ing_unitary_price);
		result = prime * result + ((ing_unitary_price_unit == null) ? 0 : ing_unitary_price_unit.hashCode());
		result = prime * result + Float.floatToIntBits(ing_unitary_weight);
		result = prime * result + ((ing_unitary_weight_unit == null) ? 0 : ing_unitary_weight_unit.hashCode());
		result = prime * result + ((stb_counters == null) ? 0 : stb_counters.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractIngredient other = (AbstractIngredient) obj;
		if (ing_desc == null) {
			if (other.ing_desc != null)
				return false;
		} else if (!ing_desc.equals(other.ing_desc))
			return false;
		if (ing_fournisseur == null) {
			if (other.ing_fournisseur != null)
				return false;
		} else if (!ing_fournisseur.equals(other.ing_fournisseur))
			return false;
		return true;
	}
	
}