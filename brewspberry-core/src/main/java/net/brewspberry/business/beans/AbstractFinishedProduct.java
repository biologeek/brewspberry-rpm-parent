package net.brewspberry.business.beans;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import net.brewspberry.business.beans.stock.StockUnit;
import net.brewspberry.business.beans.stock.Stockable;


@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class AbstractFinishedProduct extends Stockable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8663439504086317844L;

	
	private float afp_unitary_value;
	private StockUnit afp_unitary_value_unit;
	
	
	public float getAfp_unitary_value() {
		return afp_unitary_value;
	}
	public void setAfp_unitary_value(float afp_unitary_value) {
		this.afp_unitary_value = afp_unitary_value;
	}
	public StockUnit getAfp_unitary_value_unit() {
		return afp_unitary_value_unit;
	}
	public void setAfp_unitary_value_unit(StockUnit afp_unitary_value_unit) {
		this.afp_unitary_value_unit = afp_unitary_value_unit;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
