package net.brewspberry.main.business.beans.brewing;

import java.io.Serializable;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import net.brewspberry.main.util.StockUnitUtils;

@Entity
@DiscriminatorValue("m")
@Component
public class Malt extends SimpleMalt implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4717132502498393810L;

	/**
	 * Malt attached to brew and step
	 */

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "malt_bra_id")
	private Brassin malt_brassin;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "malt_etp_id")
	private Etape malt_etape;

	private float ing_quantite;
	private float ing_prix;

	public Malt() {
		super();

	}

	public Malt(SimpleMalt sm) {

		super();

		this.setIng_desc(sm.getIng_desc());
		this.setIng_disc(sm.getIng_disc());
		this.setIng_fournisseur(sm.getIng_fournisseur());
		this.setIng_unitary_price(sm.getIng_unitary_price());
		this.setIng_unitary_price_unit(sm.getIng_unitary_price_unit());
		this.setIng_unitary_weight(sm.getIng_unitary_weight());
		this.setIng_unitary_weight_unit(sm.getIng_unitary_weight_unit());
		this.setStb_id(sm.getStb_id());
		this.setStb_counters(sm.getStb_counters());
		this.setSmal_cereale(sm.getSmal_cereale());
		this.setSmal_couleur(sm.getSmal_couleur());
		this.setSmal_type(sm.getSmal_type());
	}

	public Brassin getMalt_brassin() {
		return malt_brassin;
	}

	public void setMalt_brassin(Brassin malt_brassin) {
		this.malt_brassin = malt_brassin;
	}

	public Etape getMalt_etape() {
		return malt_etape;
	}

	public void setMalt_etape(Etape malt_etape) {
		this.malt_etape = malt_etape;
	}

	public float getIng_quantite() {
		return ing_quantite;
	}

	public void setIng_quantite(float ing_quantite) {
		this.ing_quantite = ing_quantite;
	}

	public float getIng_prix() {
		return computePrice();
	}

	public void setIng_prix(float ing_prix) {
		this.ing_prix = ing_prix;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	public float computePrice(){
		
		double unitaryPrice = StockUnitUtils.convertToStandardUnit(ing_unitary_price, ing_unitary_price_unit);
		return new Float(unitaryPrice * ing_quantite).floatValue();
		
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
		Malt other = (Malt) obj;
		if (Float.floatToIntBits(ing_prix) != Float.floatToIntBits(other.ing_prix))
			return false;
		if (Float.floatToIntBits(ing_quantite) != Float.floatToIntBits(other.ing_quantite))
			return false;
		return true;
	}

	public static class Builder {

		Malt malt;

		public Builder() {
			malt = new Malt();
		}

		public Builder(SimpleMalt simple) {
			malt = new Malt(simple);
		}

		
		public Builder brew(Brassin brew) {
			malt.setMalt_brassin(brew);
			return this;
		}

		
		public Builder step(Etape step) {
			malt.setMalt_etape(step);
			return this;
		}

		
		public Builder quantity(float quantity) {
			malt.setIng_quantite(quantity);
			return this;
		}

		
		public Builder price(float price) {
			malt.setIng_prix(price);
			return this;
		}
		
		
		public Malt build(){
			return this.malt;
		}
		
	}

}