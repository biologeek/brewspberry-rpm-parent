package net.brewspberry.business.beans;

import java.io.Serializable;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import net.brewspberry.business.beans.stock.Stockable;


@Entity
@Component
public class SimpleMalt extends AbstractIngredient implements Serializable, Stockable {

    /**
     * 
     * Represents Malt specific characteristics
     * 
	 * 
	 */
	private static final long serialVersionUID = 4518248665359104487L;
	private String smal_cereale;
    private String smal_type;
    private Integer smal_couleur;
    
	public SimpleMalt() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getSmal_cereale() {
		return smal_cereale;
	}

	public void setSmal_cereale(String smal_cereale) {
		this.smal_cereale = smal_cereale;
	}

	public String getSmal_type() {
		return smal_type;
	}

	public void setSmal_type(String smal_type) {
		this.smal_type = smal_type;
	}

	public Integer getSmal_couleur() {
		return smal_couleur;
	}

	public void setSmal_couleur(Integer smal_couleur) {
		this.smal_couleur = smal_couleur;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
}