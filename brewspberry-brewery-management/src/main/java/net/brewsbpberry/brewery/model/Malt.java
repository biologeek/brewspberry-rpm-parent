package net.brewsbpberry.brewery.model;

import javax.persistence.Entity;

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
