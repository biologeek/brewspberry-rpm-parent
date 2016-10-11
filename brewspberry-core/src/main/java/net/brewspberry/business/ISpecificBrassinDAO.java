package net.brewspberry.business;

import java.util.List;

import net.brewspberry.business.beans.Biere;
import net.brewspberry.business.beans.Brassin;
import net.brewspberry.business.beans.BrewStatus;

public interface ISpecificBrassinDAO {
	
	public Brassin getBrassinByBeer(Biere beer);

	public List<Brassin> getBrewByStates(List<BrewStatus> statuses);

}
