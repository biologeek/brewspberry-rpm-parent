package net.brewspberry.business;

import net.brewspberry.business.beans.Biere;
import net.brewspberry.business.beans.Brassin;

public interface ISpecificBrassinDAO {
	
	public Brassin getBrassinByBeer(Biere beer);

}
