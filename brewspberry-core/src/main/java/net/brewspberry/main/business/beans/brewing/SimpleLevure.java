package net.brewspberry.main.business.beans.brewing;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((slev_aromes == null) ? 0 : slev_aromes.hashCode());
		result = prime * result + ((slev_espece == null) ? 0 : slev_espece.hashCode());
		result = prime * result + ((slev_floculation == null) ? 0 : slev_floculation.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimpleLevure other = (SimpleLevure) obj;
		if (slev_aromes == null) {
			if (other.slev_aromes != null)
				return false;
		} else if (!slev_aromes.equals(other.slev_aromes))
			return false;
		if (slev_espece == null) {
			if (other.slev_espece != null)
				return false;
		} else if (!slev_espece.equals(other.slev_espece))
			return false;
		if (slev_floculation == null) {
			if (other.slev_floculation != null)
				return false;
		} else if (!slev_floculation.equals(other.slev_floculation))
			return false;
		return true;
	}
	

}