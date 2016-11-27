package net.brewspberry.main.business.beans;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.springframework.stereotype.Component;

@Entity
@Component
public class Levure extends SimpleLevure {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 6748515267938143079L;


	/**
	 * 
	 * Link between Levure, brew and step
	 * 
	 */
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="lev_bra_id")
    private Brassin lev_brassin;

    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="lev_etp_id")
    private Etape lev_etape;
    

    private float ing_quantite;
    private float ing_prix;
    
	public Levure() {
		super();
		
	}

	public Brassin getLev_brassin() {
		return lev_brassin;
	}

	public void setLev_brassin(Brassin lev_brassin) {
		this.lev_brassin = lev_brassin;
	}

	public Etape getLev_etape() {
		return lev_etape;
	}

	public void setLev_etape(Etape lev_etape) {
		this.lev_etape = lev_etape;
	}

	public float getIng_quantite() {
		return ing_quantite;
	}

	public void setIng_quantite(float ing_quantite) {
		this.ing_quantite = ing_quantite;
	}

	public float getIng_prix() {
		return ing_prix;
	}

	public void setIng_prix(float ing_prix) {
		this.ing_prix = ing_prix;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Float.floatToIntBits(ing_prix);
		result = prime * result + Float.floatToIntBits(ing_quantite);
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
		Levure other = (Levure) obj;
		if (Float.floatToIntBits(ing_prix) != Float.floatToIntBits(other.ing_prix))
			return false;
		if (Float.floatToIntBits(ing_quantite) != Float.floatToIntBits(other.ing_quantite))
			return false;
		return true;
	}

	
	

}