package net.brewspberry.business;

import java.io.IOException;
import java.util.List;

import net.brewspberry.business.beans.Actioner;
import net.brewspberry.business.beans.Brassin;
import net.brewspberry.business.beans.Etape;
import net.brewspberry.business.exceptions.NotAppropriateStatusException;
import net.brewspberry.exceptions.ServiceException;

public interface ISpecificActionerService extends ISpecificActionerLauncherService{
	
	public List<Actioner> getActionerByBrassin(Brassin brassin);
	
	public List<Actioner> getActionnerByEtape(Etape etape);

	public Actioner isAlreadyStoredAndActivated (Actioner arg0);
	public Actioner startActionInDatabase (Actioner actioner) throws ServiceException, NotAppropriateStatusException;
	public Actioner stopActionInDatabase (Actioner actioner) throws ServiceException, NotAppropriateStatusException;
	
	public String getPIDFromPs(String line) throws IOException;
}