package net.brewspberry.business.beans;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.springframework.stereotype.Component;

import net.brewspberry.business.beans.stock.Stockable;


@Entity
@Component
public class Biere extends AbstractFinishedProduct {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8568385997181631075L;

	private String beer_name;
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
		// TODO Auto-generated constructor stub
	}

	

	public Brassin getBeer_brassin() {
		return beer_brassin;
	}



	public void setBeer_brassin(Brassin brassin) {
		this.beer_brassin = brassin;
	}


	public String getBeer_name() {
		return beer_name;
	}


	public void setBeer_name(String beer_name) {
		this.beer_name = beer_name;
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
		return "Biere [beer_name=" + beer_name
				+ ", beer_style=" + beer_style + ", beer_alcohol="
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
	
	
	
}
