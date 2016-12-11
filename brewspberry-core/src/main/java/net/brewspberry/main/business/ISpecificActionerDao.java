package net.brewspberry.main.business;

import java.util.List;

import net.brewspberry.main.business.beans.Actioner;
import net.brewspberry.main.business.beans.Brassin;
import net.brewspberry.main.business.beans.Etape;
import net.brewspberry.main.business.beans.GenericActionner;

public interface ISpecificActionerDao {
	
	public Actioner getActionerByFullCharacteristics (Actioner actioner);
	
	public List<Actioner> getActionerByBrassin(Brassin brassin);

	public List<Actioner> getActionnerByEtape(Etape etape);

	public List<Actioner> getActiveActionners();
	
	public List<GenericActionner> getAllGenericActionners();
	
}
