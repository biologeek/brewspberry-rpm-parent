package net.brewspberry.main.business.beans.stock;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

public enum StockUnit {

	METRE(1, null, "m", 1), CENTIMETRE(2, METRE, "cm", 0.01), LITRE(3, null, "L", 1), GRAMME(5, null, "g", 1), KILO(4,
			GRAMME, "kg", 1000), BOUTEILLE_33_CL(6, LITRE, "btl 33 cl", 0.33), BOUTEILLE_75_CL(7, LITRE, "btl 50 cl",
					0.75), BOUTEILLE_50_CL(8, LITRE, "btl 75 cl", 0.5), FUT_5_L(9, LITRE, "fut 5 L", 5), FUT_20_L(10,
							LITRE, "fut 20 L",
							20), FUT_30_L(11, LITRE, "fut 30 L", 30), SAC_1_KG(12, GRAMME, "sac 1 kg", 1000), SAC_5_KG(
									13, GRAMME, "sac 5 kg", 5000), SAC_25_KG(14, GRAMME, "sac 25 kg", 25000);

	private double stu_multi;
	private String stu_value;
	private StockUnit stu_parent;
	// @Id@GeneratedValue(strategy=GenerationType.AUTO)
	private int stu_id;

	// @OneToMany(mappedBy="cpt_unit", fetch=FetchType.LAZY)
	private List<StockCounter> stu_counters;

	StockUnit() {

	}

	StockUnit(int stu_id, StockUnit stu_parent, String stu_value, double stu_multi) {

		this.stu_id = stu_id;
		this.stu_parent = stu_parent;
		this.stu_value = stu_value;
		this.stu_multi = stu_multi;

	}

	public double getStu_multi() {
		return stu_multi;
	}

	public void setStu_multi(double stu_multi) {
		this.stu_multi = stu_multi;
	}

	public String getStu_value() {
		return stu_value;
	}

	public void setStu_value(String stu_value) {
		this.stu_value = stu_value;
	}

	public StockUnit getStu_parent() {
		return stu_parent;
	}

	public void setStu_parent(StockUnit stu_parent) {
		this.stu_parent = stu_parent;
	}

	public int getStu_id() {
		return stu_id;
	}

	public void setStu_id(int stu_id) {
		this.stu_id = stu_id;
	}

	public boolean hasParent() {

		if (this.getStu_parent() != null) {
			return true;
		}
		return false;

	}

	/**
	 * returns first StockUnit that value that matches unit name
	 * Method ignores case
	 * @param unitaryPriceUnit
	 * @return
	 */
	public static StockUnit fromString(String unitaryPriceUnit) {

		for (StockUnit value : StockUnit.values()) {

			if (value.name().equalsIgnoreCase(unitaryPriceUnit)) {
				return value;
			}
		}
		return null;
	}

}
