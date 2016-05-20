package net.brewspberry.business;

import java.util.List;

import net.brewspberry.business.beans.Actioner;
import net.brewspberry.business.beans.Brassin;
import net.brewspberry.business.beans.Etape;

public interface ISpecificActionerService {
	
	public List<Actioner> getActionerByBrassin(Brassin brassin);
	
	public List<Actioner> getActionnerByEtape(Etape etape);

	public Actioner startAction(Actioner actioner) throws Exception;
	public Actioner stopAction(Actioner actioner) throws Exception;
	public Actioner isAlreadyStoredAndActivated (Actioner arg0);

}
