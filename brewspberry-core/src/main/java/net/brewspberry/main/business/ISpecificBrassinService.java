package net.brewspberry.main.business;

import java.util.List;

import net.brewspberry.main.business.beans.brewing.Biere;
import net.brewspberry.main.business.beans.brewing.Brassin;

public interface ISpecificBrassinService {
	
	public Brassin getBrassinByBeer(Biere beer);

	public List<Brassin> getActiveBrews();
	

}
