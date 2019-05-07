package net.brewspberry.brewery.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

/**
 * 
 * @author xavier
 *
 */
@Entity
public class Malt extends AbstractIngredient {

	private Integer ebcColor;
	/**
	 * full grain or grinded
	 */
	private String form;

	@ManyToMany(mappedBy = "malts")
	private List<Brew> brew;

	public List<Brew> getBrew() {
		return brew;
	}

	public void setBrew(List<Brew> brew) {
		this.brew = brew;
	}

	public Integer getEbcColor() {
		return ebcColor;
	}

	public void setEbcColor(Integer ebcColor) {
		this.ebcColor = ebcColor;
	}

	public String getForm() {
		return form;
	}

	public void setForm(String form) {
		this.form = form;
	}

}
