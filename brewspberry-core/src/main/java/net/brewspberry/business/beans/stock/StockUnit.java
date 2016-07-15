package net.brewspberry.business.beans.stock;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public enum StockUnit {
	
	METRE(1, 1, "m", 1),
	CENTIMETRE(2, 1, "cm", 0.01),
	LITRE(3, 3, "L", 1),
	KILO(4, 5, "kg", 1000),
	BOUTEILLE_33_CL(6, 3, "btl 33 cl", 0.33),
	BOUTEILLE_75_CL(7, 3, "btl 50 cl", 0.75),
	BOUTEILLE_50_CL(8, 3, "btl 75 cl", 0.5),
	FUT_5_L(9, 3, "fut 5 L", 5),
	FUT_20_L(10, 3, "fut 20 L", 20),
	FUT_30_L(11, 3, "fut 30 L", 30),
	SAC_1_KG(12, 4, "sac 1 kg", 1),
	SAC_5_KG(13, 4, "sac 5 kg", 5),
	SAC_25_KG(14, 4, "sac 25 kg", 25),
	GRAMME(5, 5, "g", 1);
	
	private double stu_multi;
	private String stu_value;
	private int stu_parent;
	@Id@GeneratedValue(strategy=GenerationType.AUTO)	
	private int stu_id;

	StockUnit(int stu_id, int stu_parent, String stu_value, double stu_multi) {

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

	public int getStu_parent() {
		return stu_parent;
	}

	public void setStu_parent(int stu_parent) {
		this.stu_parent = stu_parent;
	}

	public int getStu_id() {
		return stu_id;
	}

	public void setStu_id(int stu_id) {
		this.stu_id = stu_id;
	}
	

}
