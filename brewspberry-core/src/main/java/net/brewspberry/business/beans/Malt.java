package net.brewspberry.business.beans;

import java.io.Serializable;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@DiscriminatorValue("m")
public class Malt extends SimpleMalt implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -4717132502498393810L;

	/**
	 * Malt attached to brew and step
	 */

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="malt_bra_id")
    private Brassin malt_brassin;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="malt_etp_id")
    private Brassin malt_etape;
    

    private float ing_quantite;
    private float ing_prix;
    
    
	public Malt() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Brassin getMalt_brassin() {
		return malt_brassin;
	}

	public void setMalt_brassin(Brassin malt_brassin) {
		this.malt_brassin = malt_brassin;
	}

	public Brassin getMalt_etape() {
		return malt_etape;
	}

	public void setMalt_etape(Brassin malt_etape) {
		this.malt_etape = malt_etape;
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