package net.brewspberry.business.beans;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.stereotype.Component;

import net.brewspberry.business.beans.SimpleHoublon;


@Entity
@Component
public class Houblon extends SimpleHoublon implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -7373607373918515813L;


	/**
	 * 
	 */
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="hbl_bra_id")
    private Brassin hbl_brassin;
    
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="hbl_etp_id")
    private Etape hbl_etape;
    

    private float ing_quantite;
    private float ing_prix;
        
	public Houblon() {
		super();
		
	}


	public Brassin getHbl_brassin() {
		return hbl_brassin;
	}


	public void setHbl_brassin(Brassin hbl_brassin) {
		this.hbl_brassin = hbl_brassin;
	}


	public Etape getHbl_etape() {
		return hbl_etape;
	}


	public void setHbl_etape(Etape hbl_etape) {
		this.hbl_etape = hbl_etape;
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
		Houblon other = (Houblon) obj;
		if (hbl_brassin == null) {
			if (other.hbl_brassin != null)
				return false;
		} else if (!hbl_brassin.equals(other.hbl_brassin))
			return false;
		if (hbl_etape == null) {
			if (other.hbl_etape != null)
				return false;
		} else if (!hbl_etape.equals(other.hbl_etape))
			return false;
		if (Float.floatToIntBits(ing_prix) != Float.floatToIntBits(other.ing_prix))
			return false;
		if (Float.floatToIntBits(ing_quantite) != Float.floatToIntBits(other.ing_quantite))
			return false;
		return true;
	}
	
	
    
}