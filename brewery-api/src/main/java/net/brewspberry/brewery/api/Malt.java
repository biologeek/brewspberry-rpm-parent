package net.brewspberry.brewery.api;

public class Malt extends AbstractIngredient {

	
	private String form;
	private Integer ebc;
	public Malt() {
		this.type = "M";
	}

	public String getForm() {
		return form;
	}

	public void setForm(String form) {
		this.form = form;
	}

	public Integer getEbc() {
		return ebc;
	}

	public void setEbc(Integer ebc) {
		this.ebc = ebc;
	}
}
