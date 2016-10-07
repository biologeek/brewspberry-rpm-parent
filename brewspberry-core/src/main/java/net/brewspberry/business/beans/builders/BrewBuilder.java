package net.brewspberry.business.beans.builders;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder.In;

import net.brewspberry.business.beans.Biere;
import net.brewspberry.business.beans.Brassin;
import net.brewspberry.business.beans.Etape;
import net.brewspberry.business.beans.Houblon;
import net.brewspberry.business.beans.Levure;
import net.brewspberry.business.beans.Malt;

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

	public BrewBuilder status(Integer id) {

		BrewBuilder.obj.setBra_statut(id);
		return this;

	}

	public BrewBuilder hops(List<Houblon> id) {

		BrewBuilder.obj.setBra_houblons(id);
		return this;

	}

	public BrewBuilder steps(List<Etape> id) {

		BrewBuilder.obj.setBra_etapes(id);
		return this;

	}

	public BrewBuilder type(String id) {

		BrewBuilder.obj.setBra_type(id);
		return this;

	}

	public BrewBuilder yeasts(List<Levure> id) {

		BrewBuilder.obj.setBra_levures(id);
		return this;

	}

	public BrewBuilder malts(List<Malt> id) {

		BrewBuilder.obj.setBra_malts(id);
		return this;

	}

}
