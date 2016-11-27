package net.brewspberry.main.business;

import java.util.List;

import net.brewspberry.main.business.beans.Biere;
import net.brewspberry.main.business.beans.Brassin;
import net.brewspberry.main.business.beans.BrewStatus;

public interface ISpecificBrassinDAO {
	
	public Brassin getBrassinByBeer(Biere beer);

	public List<Brassin> getBrewByStates(List<BrewStatus> statuses);

}
