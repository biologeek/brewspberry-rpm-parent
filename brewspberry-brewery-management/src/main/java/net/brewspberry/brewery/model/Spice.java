package net.brewspberry.brewery.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

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

	@ManyToMany(mappedBy="spices")
	private List<Brew> brew;

	public String getShape() {
		return shape;
	}

	public void setShape(String shape) {
		this.shape = shape;
	}

	public List<Brew> getBrew() {
		return brew;
	}

	public void setBrew(List<Brew> brew) {
		this.brew = brew;
	}
	

}
