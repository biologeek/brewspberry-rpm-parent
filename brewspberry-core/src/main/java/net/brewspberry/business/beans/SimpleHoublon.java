package net.brewspberry.business.beans;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.springframework.stereotype.Component;

import net.brewspberry.business.beans.AbstractIngredient;

@Entity
@Component
public class SimpleHoublon extends AbstractIngredient implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -9085140125050205061L;
	private String shbl_variete;
    private Double shbl_acide_alpha;
    private String shbl_aromes;
    private Integer shbl_type;
    
    
    
	public SimpleHoublon() {
		super();
		// TODO Auto-generated constructor stub
	}



	public String getShbl_variete() {
		return shbl_variete;
	}



	public void setShbl_variete(String shbl_variete) {
		this.shbl_variete = shbl_variete;
	}



	public Double getShbl_acide_alpha() {
		return shbl_acide_alpha;
	}



	public void setShbl_acide_alpha(Double shbl_acide_alpha) {
		this.shbl_acide_alpha = shbl_acide_alpha;
	}



	public String getShbl_aromes() {
		return shbl_aromes;
	}



	public void setShbl_aromes(String shbl_aromes) {
		this.shbl_aromes = shbl_aromes;
	}



	public Integer getShbl_type() {
		return shbl_type;
	}



	public void setShbl_type(Integer shbl_type) {
		this.shbl_type = shbl_type;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}

    
	
    
}