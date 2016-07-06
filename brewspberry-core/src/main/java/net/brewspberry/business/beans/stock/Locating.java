package net.brewspberry.business.beans.stock;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Locating implements Serializable {

	/**
	 * Is a locating (physical place where product is placed)
	 * 
	 */

	@Id@GeneratedValue(strategy=GenerationType.AUTO)
	private long loc_id;

	
	private StockCounter loc_counter;
	
	
	public Locating(){
		super();
	}


	public long getLoc_id() {
		return loc_id;
	}


	public void setLoc_id(long loc_id) {
		this.loc_id = loc_id;
	}


	public StockCounter getLoc_counter() {
		return loc_counter;
	}


	public void setLoc_counter(StockCounter loc_counter) {
		this.loc_counter = loc_counter;
	}
	
	
	
}
