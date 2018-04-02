package net.brewspberry.main.business.beans.builders;

import java.util.Date;
import java.util.List;
import java.util.Set;

import net.brewspberry.main.business.beans.brewing.DurationBO;
import net.brewspberry.main.business.beans.brewing.Etape;
import net.brewspberry.main.business.beans.brewing.Houblon;
import net.brewspberry.main.business.beans.brewing.Levure;
import net.brewspberry.main.business.beans.brewing.Malt;
import net.brewspberry.main.business.beans.brewing.PalierType;
import net.brewspberry.main.business.beans.monitoring.Actioner;

public class StepBuilder {

	private Etape obj;

	public StepBuilder() {

		obj = new Etape();
	}

	public Etape build() {

		return obj;

	}

	public StepBuilder id(Long id) {

		this.obj.setEtp_id(id);
		return this;

	}

	public StepBuilder name(String name) {

		this.obj.setEtp_nom(name);
		return this;

	}

	public StepBuilder maj(Date date) {

		this.obj.setEtp_update_date(date);
		return this;

	}

	public StepBuilder stageType(PalierType id) {

		this.obj.setEtp_palier_type(id);
		return this;

	}

	public StepBuilder realBegin(Date id) {

		this.obj.setEtp_debut_reel(id);
		return this;

	}

	public StepBuilder begin(Date id) {

		this.obj.setEtp_debut(id);
		return this;

	}

	public StepBuilder end(Date id) {

		this.obj.setEtp_fin(id);
		return this;

	}

	public StepBuilder realEnd(Date id) {

		this.obj.setEtp_fin_reel(id);
		return this;

	}

	public StepBuilder remark(String id) {

		this.obj.setEtp_remarque(id);
		return this;

	}

	public StepBuilder hops(Set<Houblon> id) {

		this.obj.setEtp_houblons(id);
		return this;

	}


	public StepBuilder actioners(List<Actioner> id) {

		this.obj.setEtp_actioners(id);
		return this;

	}

	public StepBuilder yeasts(Set<Levure> id) {

		this.obj.setEtp_levures(id);
		return this;

	}

	public StepBuilder malts(Set<Malt> id) {

		this.obj.setEtp_malts(id);
		return this;

	}
}
