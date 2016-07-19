package net.brewspberry.business.beans.stock;

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
	public long stb_id;
	


	public long getStb_id() {
		return stb_id;
	}


	public void setStb_id(long stb_id) {
		this.stb_id = stb_id;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	
	
}
