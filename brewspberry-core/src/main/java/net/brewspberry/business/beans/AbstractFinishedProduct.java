package net.brewspberry.business.beans;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import net.brewspberry.business.beans.stock.Stockable;


@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class AbstractFinishedProduct extends Stockable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8663439504086317844L;

}
