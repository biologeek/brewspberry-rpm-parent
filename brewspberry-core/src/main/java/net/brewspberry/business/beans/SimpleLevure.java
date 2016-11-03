package net.brewspberry.business.beans;

import javax.persistence.Entity;
import org.springframework.stereotype.Component;

@Entity
@Component
public class SimpleLevure extends AbstractIngredient  {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -8449094340159438833L;
	private String slev_espece;
    private String slev_floculation;
    private String slev_aromes;
    
	public SimpleLevure() {
		super();
		
	}

	public String getSlev_espece() {
		return slev_espece;
	}

	public void setSlev_espece(String slev_espece) {
		this.slev_espece = slev_espece;
	}

	public String getSlev_floculation() {
		return slev_floculation;
	}

	public void setSlev_floculation(String slev_floculation) {
		this.slev_floculation = slev_floculation;
	}

	public String getSlev_aromes() {
		return slev_aromes;
	}

	public void setSlev_aromes(String slev_aromes) {
		this.slev_aromes = slev_aromes;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}