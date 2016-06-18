package net.brewspberry.business.beans;

import java.io.Serializable;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.stereotype.Component;

@Entity
@Component
public class Levure extends SimpleLevure implements Serializable {
    
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
		// TODO Auto-generated constructor stub
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

	
	

}