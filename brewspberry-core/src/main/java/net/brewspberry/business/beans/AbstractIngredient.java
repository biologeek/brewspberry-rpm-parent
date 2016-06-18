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


@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Component
public abstract class AbstractIngredient implements Serializable {

	/**
	 * Abstract Ingredient represents ingredient general characteristics
	 * 
	 * Not mapped in DB
	 * 
	 */
	private static final long serialVersionUID = -5363007498088123647L;
	
	@Id@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(nullable=false)
	private long ing_id;
	private String ing_desc;
	
    private String ing_fournisseur;
    

    
	public long getIng_id() {
		return ing_id;
	}
	public String getIng_desc() {
		return ing_desc;
	}
	public void setIng_desc(String ing_desc) {
		this.ing_desc = ing_desc;
	}
	public void setIng_id(long ing_id) {
		this.ing_id = ing_id;
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

	@Override
	public String toString() {
		return "Ingredient [ing_id=" + ing_id + ", ing_desc=" + ing_desc
				+ ", ing_fournisseur=" + ing_fournisseur + "]";
	}
}