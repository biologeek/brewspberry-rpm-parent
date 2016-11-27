package net.brewspberry.main.business.beans.stock;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;


@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class Stockable implements Serializable {

	/**
	 * This interface is used for objects that can have stock
	 * @return 
	 */
	
	private static final long serialVersionUID = -8877390414003201284L;

	
	@Id@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(nullable=false)
	protected long stb_id;
	


	public long getStb_id() {
		return stb_id;
	}


	public void setStb_id(long stb_id) {
		this.stb_id = stb_id;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (stb_id ^ (stb_id >>> 32));
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
		Stockable other = (Stockable) obj;
		if (stb_id != other.stb_id)
			return false;
		return true;
	}


	
	
}
