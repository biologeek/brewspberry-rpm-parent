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
	
	private static BrewBuilder instance;
	
	
	private Brassin obj;
	
	private BrewBuilder() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Brassin build(){
		
		return obj;
		
	}
	public static BrewBuilder getInstance(){
		
		if (instance == null){
			instance = new BrewBuilder();
		}
		return instance;
	}

	public BrewBuilder id(Long id){
		
		this.obj.setBra_id(id);
		return this;
		
	}
	public BrewBuilder name(String name){
		
		this.obj.setBra_nom(name);
		return this;
		
	}
	public BrewBuilder maj(Date date){
		
		this.obj.setBra_date_maj(date);
		return this;
		
	}
	public BrewBuilder quantity(Double id){
		
		this.obj.setBra_quantiteEnLitres(id);
		return this;
		
	}
	public BrewBuilder beer(Biere id){
		
		this.obj.setBra_Beer(id);
		return this;
		
	}
	public BrewBuilder begin(Date id){
		
		this.obj.setBra_debut(id);
		return this;
		
	}
	

	public BrewBuilder end(Date id){
		
		this.obj.setBra_fin(id);
		return this;
		
	}
	

	public BrewBuilder status(Integer id){
		
		this.obj.setBra_statut(id);
		return this;
		
	}


	public BrewBuilder hops(List<Houblon> id){
		
		this.obj.setBra_houblons(id);
		return this;
		
	}
	


	public BrewBuilder steps(List<Etape> id){
		
		this.obj.setBra_etapes(id);
		return this;
		
	}
	


	public BrewBuilder type(String id){
		
		this.obj.setBra_type(id);
		return this;
		
	}
	


	public BrewBuilder yeasts(List<Levure> id){
		
		this.obj.setBra_levures(id);
		return this;
		
	}
	


	public BrewBuilder malts(List<Malt> id){
		
		this.obj.setBra_malts(id);
		return this;
		
	}
	

}
