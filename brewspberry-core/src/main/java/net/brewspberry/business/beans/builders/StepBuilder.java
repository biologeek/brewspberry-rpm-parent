package net.brewspberry.business.beans.builders;

import java.util.Date;
import java.util.List;

import net.brewspberry.business.beans.Actioner;
import net.brewspberry.business.beans.DurationBO;
import net.brewspberry.business.beans.Etape;
import net.brewspberry.business.beans.Houblon;
import net.brewspberry.business.beans.Levure;
import net.brewspberry.business.beans.Malt;
import net.brewspberry.business.beans.PalierType;

public class StepBuilder {

	private static StepBuilder instance;
	private Etape obj;

	public static StepBuilder getInstance() {

		if (instance != null){
			instance = new StepBuilder();
		}
		return instance;
	}


	public Etape build(){
		
		return obj;
		
	}

	public StepBuilder id(Long id){
		
		this.obj.setEtp_id(id);
		return this;
		
	}
	public StepBuilder name(String name){
		
		this.obj.setEtp_nom(name);
		return this;
		
	}
	public StepBuilder maj(Date date){
		
		this.obj.setEtp_update_date(date);
		return this;
		
	}
	public StepBuilder stageTye(PalierType id){
		
		this.obj.setEtp_palier_type(id);
		return this;
		
	}
	public StepBuilder realBegin(Date id){
		
		this.obj.setEtp_debut_reel(id);
		return this;
		
	}
	public StepBuilder begin(Date id){
		
		this.obj.setEtp_debut(id);
		return this;
		
	}


	public StepBuilder end(Date id){
		
		this.obj.setEtp_fin(id);
		return this;
		
	}

	public StepBuilder realEnd(Date id){
		
		this.obj.setEtp_fin_reel(id);
		return this;
		
	}
	

	public StepBuilder remark(String id){
		
		this.obj.setEtp_remarque(id);
		return this;
		
	}


	public StepBuilder hops(List<Houblon> id){
		
		this.obj.setEtp_houblons(id);
		return this;
		
	}
	


	public StepBuilder steps(DurationBO id){
		
		this.obj.setEtp_duree(id);
		return this;
		
	}
	


	public StepBuilder actioners(List<Actioner> id){
		
		this.obj.setEtp_actioners(id);
		return this;
		
	}
	


	public StepBuilder yeasts(List<Levure> id){
		
		this.obj.setEtp_levures(id);
		return this;
		
	}
	


	public StepBuilder malts(List<Malt> id){
		
		this.obj.setEtp_malts(id);
		return this;
		
	}
}
