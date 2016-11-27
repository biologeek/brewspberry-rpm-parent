package net.brewspberry.main.business;

import java.util.List;

import net.brewspberry.main.business.beans.Biere;
import net.brewspberry.main.business.beans.Brassin;

public interface ISpecificBrassinService {
	
	public Brassin getBrassinByBeer(Biere beer);

	public List<Brassin> getActiveBrews();
	

}
