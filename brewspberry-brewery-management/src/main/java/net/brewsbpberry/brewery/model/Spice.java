package net.brewsbpberry.brewery.model;

import javax.persistence.Entity;

/**
 * Spices can be added to beer recipe to add specific flavour, such as sweet
 * orange, cinnamon, ...<br>
 * <br>
 * 
 * @author xavier
 *
 */
@Entity
public class Spice extends AbstractIngredient {

	/**
	 * grinded, pellet, ...
	 */
	private String shape;

	public String getShape() {
		return shape;
	}

	public void setShape(String shape) {
		this.shape = shape;
	}
	

}
