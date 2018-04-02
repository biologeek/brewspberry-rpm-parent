package net.brewspberry.main.data;

import java.util.List;

import net.brewspberry.main.business.beans.brewing.Brassin;
import net.brewspberry.main.business.beans.brewing.Etape;
import net.brewspberry.main.business.beans.monitoring.Actioner;
import net.brewspberry.main.business.beans.monitoring.GenericActionner;

public interface ISpecificActionerDao {
	
	public Actioner getActionerByFullCharacteristics (Actioner actioner);
	
	public List<Actioner> getActionerByBrassin(Brassin brassin);

	public List<Actioner> getActionnerByEtape(Etape etape);

	public List<Actioner> getActiveActionners();
	
	public List<GenericActionner> getAllGenericActionners();
	
}
