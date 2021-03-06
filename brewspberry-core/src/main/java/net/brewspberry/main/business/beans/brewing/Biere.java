package net.brewspberry.main.business.beans.brewing;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.springframework.stereotype.Component;


@Entity
@Component
public class Biere extends AbstractFinishedProduct {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8568385997181631075L;

	private String beer_style;
	private float beer_alcohol;
	private int beer_density;
	private int beer_color_ebc;
	private String beer_aroma;
	private int beer_bubbles;
	private int beer_notation;
	private String beer_comment;
	private double beer_quantity;
	private double beer_conso_progress;
	private int beer_init_bottles;
	private int beer_remaining_bottles;
	private Date beer_first_drink_date;
	
	@OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="bra_beer_id")
	private Brassin beer_brassin;

	
	public Biere() {
		super();
		
	}

	

	public Brassin getBeer_brassin() {
		return beer_brassin;
	}



	public void setBeer_brassin(Brassin brassin) {
		this.beer_brassin = brassin;
	}




	public String getBeer_style() {
		return beer_style;
	}


	public void setBeer_style(String beer_style) {
		this.beer_style = beer_style;
	}


	public float getBeer_alcohol() {
		return beer_alcohol;
	}


	public void setBeer_alcohol(float beer_alcohol) {
		this.beer_alcohol = beer_alcohol;
	}


	public int getBeer_density() {
		return beer_density;
	}


	public void setBeer_density(int beer_density) {
		this.beer_density = beer_density;
	}


	public int getBeer_color_ebc() {
		return beer_color_ebc;
	}


	public void setBeer_color_ebc(int beer_color_ebc) {
		this.beer_color_ebc = beer_color_ebc;
	}


	public String getBeer_aroma() {
		return beer_aroma;
	}


	public void setBeer_aroma(String beer_aroma) {
		this.beer_aroma = beer_aroma;
	}


	public int getBeer_bubbles() {
		return beer_bubbles;
	}


	public void setBeer_bubbles(int beer_bubbles) {
		this.beer_bubbles = beer_bubbles;
	}


	public int getBeer_notation() {
		return beer_notation;
	}


	public void setBeer_notation(int beer_notation) {
		this.beer_notation = beer_notation;
	}


	public String getBeer_comment() {
		return beer_comment;
	}


	public void setBeer_comment(String beer_comment) {
		this.beer_comment = beer_comment;
	}


	public double getBeer_quantity() {
		return beer_quantity;
	}


	public void setBeer_quantity(double beer_quantity) {
		this.beer_quantity = beer_quantity;
	}


	public double getBeer_conso_progress() {
		return beer_conso_progress;
	}


	public void setBeer_conso_progress(double beer_conso_progress) {
		this.beer_conso_progress = beer_conso_progress;
	}


	public int getBeer_init_bottles() {
		return beer_init_bottles;
	}


	public void setBeer_init_bottles(int beer_init_bottles) {
		this.beer_init_bottles = beer_init_bottles;
	}


	public int getBeer_remaining_bottles() {
		return beer_remaining_bottles;
	}


	public void setBeer_remaining_bottles(int beer_remaining_bottles) {
		this.beer_remaining_bottles = beer_remaining_bottles;
	}


	public Date getBeer_first_drink_date() {
		return beer_first_drink_date;
	}


	public void setBeer_first_drink_date(Date beer_first_drink_date) {
		this.beer_first_drink_date = beer_first_drink_date;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	@Override
	public String toString() {
		return "Biere [beer_style=" + beer_style + ", beer_alcohol="
				+ beer_alcohol + ", beer_density=" + beer_density
				+ ", beer_color_ebc=" + beer_color_ebc + ", beer_aroma="
				+ beer_aroma + ", beer_bubbles=" + beer_bubbles
				+ ", beer_notation=" + beer_notation + ", beer_comment="
				+ beer_comment + ", beer_quantity=" + beer_quantity
				+ ", beer_conso_progress=" + beer_conso_progress
				+ ", beer_init_bottles=" + beer_init_bottles
				+ ", beer_remaining_bottles=" + beer_remaining_bottles
				+ ", beer_first_drink_date=" + beer_first_drink_date
				+ "]";
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(beer_alcohol);
		result = prime * result + ((beer_aroma == null) ? 0 : beer_aroma.hashCode());
		result = prime * result + ((beer_brassin == null) ? 0 : beer_brassin.hashCode());
		result = prime * result + beer_bubbles;
		result = prime * result + beer_color_ebc;
		result = prime * result + ((beer_comment == null) ? 0 : beer_comment.hashCode());
		long temp;
		temp = Double.doubleToLongBits(beer_conso_progress);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + beer_density;
		result = prime * result + ((beer_first_drink_date == null) ? 0 : beer_first_drink_date.hashCode());
		result = prime * result + beer_init_bottles;
		result = prime * result + beer_notation;
		temp = Double.doubleToLongBits(beer_quantity);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + beer_remaining_bottles;
		result = prime * result + ((beer_style == null) ? 0 : beer_style.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Biere other = (Biere) obj;
		if (Float.floatToIntBits(beer_alcohol) != Float.floatToIntBits(other.beer_alcohol))
			return false;
		if (beer_aroma == null) {
			if (other.beer_aroma != null)
				return false;
		} else if (!beer_aroma.equals(other.beer_aroma))
			return false;
		if (beer_brassin == null) {
			if (other.beer_brassin != null)
				return false;
		} else if (!beer_brassin.equals(other.beer_brassin))
			return false;
		if (beer_bubbles != other.beer_bubbles)
			return false;
		if (beer_color_ebc != other.beer_color_ebc)
			return false;
		if (beer_comment == null) {
			if (other.beer_comment != null)
				return false;
		} else if (!beer_comment.equals(other.beer_comment))
			return false;
		if (Double.doubleToLongBits(beer_conso_progress) != Double.doubleToLongBits(other.beer_conso_progress))
			return false;
		if (beer_density != other.beer_density)
			return false;
		if (beer_first_drink_date == null) {
			if (other.beer_first_drink_date != null)
				return false;
		} else if (!beer_first_drink_date.equals(other.beer_first_drink_date))
			return false;
		if (beer_init_bottles != other.beer_init_bottles)
			return false;
		if (beer_notation != other.beer_notation)
			return false;
		if (Double.doubleToLongBits(beer_quantity) != Double.doubleToLongBits(other.beer_quantity))
			return false;
		if (beer_remaining_bottles != other.beer_remaining_bottles)
			return false;
		if (beer_style == null) {
			if (other.beer_style != null)
				return false;
		} else if (!beer_style.equals(other.beer_style))
			return false;
		return true;
	}
	
	
	
	public static class Builder {
		
		Biere instance;
		
		public Builder(long brewId){
			instance = new Biere();
			instance.setStb_id(brewId);
		}
		
		
		public Builder alcohol(float rate){
			instance.setBeer_alcohol(rate);
			return this;		
		}
		
		
		public Builder aroma(String aroma){
			instance.setBeer_aroma(aroma);
			return this;		
		}
		
		
		public Builder brew(Brassin brew){
			instance.setBeer_brassin(brew);
			return this;		
		}
		
		
		public Builder bubbles(int bubbles){
			instance.setBeer_bubbles(bubbles);
			return this;		
		}
		
		
		public Builder color(int ebc){
			instance.setBeer_color_ebc(ebc);
			return this;		
		}
		
		
		public Builder comment(String comment){
			instance.setBeer_comment(comment);
			return this;		
		}
		
		
		public Builder conso(double conso){
			instance.setBeer_conso_progress(conso);
			return this;		
		}
		
		
		public Builder quantity(double qty){
			instance.setBeer_quantity(qty);
			return this;		
		}


		public Biere build() {
			return instance;
		}
		
	}
	
	
	
}
