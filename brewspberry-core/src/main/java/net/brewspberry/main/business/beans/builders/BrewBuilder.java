package net.brewspberry.main.business.beans.builders;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder.In;

import net.brewspberry.main.business.beans.brewing.Biere;
import net.brewspberry.main.business.beans.brewing.Brassin;
import net.brewspberry.main.business.beans.brewing.BrewStatus;
import net.brewspberry.main.business.beans.brewing.Etape;
import net.brewspberry.main.business.beans.brewing.Houblon;
import net.brewspberry.main.business.beans.brewing.Levure;
import net.brewspberry.main.business.beans.brewing.Malt;

public class BrewBuilder {


	private static Brassin obj;

	public BrewBuilder() {
		super();
		obj = new Brassin();
		// TODO Auto-generated constructor stub
	}

	public Brassin build() {

		return obj;

	}

	public BrewBuilder id(Long id) {

		BrewBuilder.obj.setBra_id(id);
		return this;

	}

	public BrewBuilder name(String name) {

		BrewBuilder.obj.setBra_nom(name);
		return this;

	}

	public BrewBuilder maj(Date date) {

		BrewBuilder.obj.setBra_date_maj(date);
		return this;

	}

	public BrewBuilder quantity(Double id) {

		BrewBuilder.obj.setBra_quantiteEnLitres(id);
		return this;

	}

	public BrewBuilder beer(Biere id) {

		BrewBuilder.obj.setBra_Beer(id);
		return this;

	}

	public BrewBuilder begin(Date id) {

		BrewBuilder.obj.setBra_debut(id);
		return this;

	}

	public BrewBuilder end(Date id) {

		BrewBuilder.obj.setBra_fin(id);
		return this;

	}

	public BrewBuilder status(BrewStatus id) {

		BrewBuilder.obj.setBra_statut(id);
		return this;

	}

	public BrewBuilder hops(Set<Houblon> id) {

		BrewBuilder.obj.setBra_houblons(id);
		return this;

	}

	public BrewBuilder steps(Set<Etape> id) {

		BrewBuilder.obj.setBra_etapes(id);
		return this;

	}

	public BrewBuilder type(String id) {

		BrewBuilder.obj.setBra_type(id);
		return this;

	}

	public BrewBuilder yeasts(Set<Levure> id) {

		BrewBuilder.obj.setBra_levures(id);
		return this;

	}

	public BrewBuilder malts(Set<Malt> id) {

		BrewBuilder.obj.setBra_malts(id);
		return this;

	}

}
