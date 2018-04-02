package net.brewspberry.main.business.beans.brewing;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

import net.brewspberry.main.business.beans.stock.FinishedProductCounter;
import net.brewspberry.main.business.beans.stock.StockCounter;
import net.brewspberry.main.business.beans.stock.StockUnit;
import net.brewspberry.main.business.beans.stock.Stockable;


@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class AbstractFinishedProduct extends Stockable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8663439504086317844L;

	private String afp_name;
	
	private float afp_unitary_value;
	private StockUnit afp_unitary_value_unit;

	@OneToMany(fetch=FetchType.LAZY, mappedBy="cpt_product")
	private List<FinishedProductCounter> stb_counters;
	
	public String getAfp_name() {
		return afp_name;
	}
	public void setAfp_name(String afp_name) {
		this.afp_name = afp_name;
	}
	public List<FinishedProductCounter> getStb_counters() {
		return stb_counters;
	}
	public void setStb_counters(List<FinishedProductCounter> stb_counters) {
		this.stb_counters = stb_counters;
	}
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((afp_name == null) ? 0 : afp_name.hashCode());
		result = prime * result + Float.floatToIntBits(afp_unitary_value);
		result = prime * result + ((afp_unitary_value_unit == null) ? 0 : afp_unitary_value_unit.hashCode());
		result = prime * result + ((stb_counters == null) ? 0 : stb_counters.hashCode());
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
		AbstractFinishedProduct other = (AbstractFinishedProduct) obj;
		if (afp_name == null) {
			if (other.afp_name != null)
				return false;
		} else if (!afp_name.equals(other.afp_name))
			return false;
		if (Float.floatToIntBits(afp_unitary_value) != Float.floatToIntBits(other.afp_unitary_value))
			return false;
		if (afp_unitary_value_unit != other.afp_unitary_value_unit)
			return false;
		if (stb_counters == null) {
			if (other.stb_counters != null)
				return false;
		} else if (!stb_counters.equals(other.stb_counters))
			return false;
		return true;
	}
	
}
