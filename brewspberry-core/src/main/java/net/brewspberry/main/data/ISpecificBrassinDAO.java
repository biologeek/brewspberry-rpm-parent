package net.brewspberry.main.data;

import java.util.List;

import net.brewspberry.main.business.beans.brewing.Biere;
import net.brewspberry.main.business.beans.brewing.Brassin;
import net.brewspberry.main.business.beans.brewing.BrewStatus;

public interface ISpecificBrassinDAO {
	
	public Brassin getBrassinByBeer(Biere beer);

	public List<Brassin> getBrewByStates(List<BrewStatus> statuses);

}
