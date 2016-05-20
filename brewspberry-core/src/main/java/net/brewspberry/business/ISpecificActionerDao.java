package net.brewspberry.business;

import java.util.List;

import net.brewspberry.business.beans.Actioner;
import net.brewspberry.business.beans.Brassin;
import net.brewspberry.business.beans.Etape;

public interface ISpecificActionerDao {
	
	public Actioner getActionerByFullCharacteristics (Actioner actioner);
	
	public List<Actioner> getActionerByBrassin(Brassin brassin);
	
	public List<Actioner> getActionnerByEtape(Etape etape);
}
