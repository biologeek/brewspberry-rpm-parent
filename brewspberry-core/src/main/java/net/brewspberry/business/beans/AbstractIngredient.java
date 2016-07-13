package net.brewspberry.business.beans;

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

import net.brewspberry.business.beans.stock.StockUnit;
import net.brewspberry.business.beans.stock.Stockable;


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
	
	private String ing_desc;
	
    private String ing_fournisseur;

    /**
     * In €/StockUnit
     */
    private float ing_unitary_price;
    
    
    private StockUnit ing_unitary_price_unit;
    /**
     * Stored in StockUnit
     */
    private float ing_unitary_weight;
    private StockUnit ing_unitary_weight_unit;
    

   
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
	@Override
	public String toString() {
		return "Ingredient [ing_desc=" + ing_desc
				+ ", ing_fournisseur=" + ing_fournisseur + "]";
	}
}