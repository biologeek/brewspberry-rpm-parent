package net.brewspberry.main.business.beans;

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

import net.brewspberry.main.business.beans.stock.RawMaterialCounter;
import net.brewspberry.main.business.beans.stock.StockUnit;
import net.brewspberry.main.business.beans.stock.Stockable;

@Entity
@Component
public class SimpleMalt extends AbstractIngredient {

	/**
	 * 
	 * Represents Malt specific characteristics
	 * 
	 * 
	 */
	private static final long serialVersionUID = 4518248665359104487L;
	private String smal_cereale;
	private String smal_type;
	private int smal_couleur;

	public SimpleMalt() {
		super();
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

	public void setSmal_couleur(int smal_couleur) {
		this.smal_couleur = smal_couleur;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static class Builder {
		
		private SimpleMalt mal;

		public Builder (){
			super();
			mal = new SimpleMalt();
			
		}
		public Builder smal_cereale(String smal_cereale) {
			mal.smal_cereale = smal_cereale;
			return this;
		}

		public Builder smal_type(String smal_type) {
			mal.smal_type = smal_type;
			return this;
		}

		public Builder smal_couleur(int smal_couleur) {
			mal.smal_couleur = smal_couleur;
			return this;
		}
		public Builder ing_desc(String ing_desc) {
			mal.ing_desc = ing_desc;
			return this;
		}

		public Builder ing_fournisseur(String ing_fournisseur) {
			mal.ing_fournisseur = ing_fournisseur;
			return this;
		}

		public Builder stb_counters(List<RawMaterialCounter> stb_counters) {
			mal.stb_counters = stb_counters;
			return this;
		}

		public Builder ing_unitary_price(float ing_unitary_price) {
			mal.ing_unitary_price = ing_unitary_price;
			return this;
		}

		public Builder ing_unitary_price_unit(StockUnit ing_unitary_price_unit) {
			mal.ing_unitary_price_unit = ing_unitary_price_unit;
			return this;
		}

		public Builder ing_unitary_weight(float ing_unitary_weight) {
			mal.ing_unitary_weight = ing_unitary_weight;
			return this;
		}

		public Builder ing_unitary_weight_unit(StockUnit ing_unitary_weight_unit) {
			mal.ing_unitary_weight_unit = ing_unitary_weight_unit;
			return this;
		}
		public SimpleMalt build() {
			return mal;
		}
		
	}

}