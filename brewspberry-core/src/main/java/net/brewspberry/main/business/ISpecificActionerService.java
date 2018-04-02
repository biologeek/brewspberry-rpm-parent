package net.brewspberry.main.business;

import java.io.IOException;
import java.util.List;

import net.brewspberry.main.business.beans.brewing.Brassin;
import net.brewspberry.main.business.beans.brewing.Etape;
import net.brewspberry.main.business.beans.monitoring.Actioner;
import net.brewspberry.main.business.beans.monitoring.GenericActionner;
import net.brewspberry.main.business.exceptions.NotAppropriateStatusException;
import net.brewspberry.main.business.exceptions.ServiceException;

public interface ISpecificActionerService {

	public List<Actioner> getActionerByBrassin(Brassin brassin);

	public List<Actioner> getActionnerByEtape(Etape etape);

	public Actioner isAlreadyStoredAndActivated(Actioner arg0);

	public Actioner startActionInDatabase(Actioner actioner) throws ServiceException, NotAppropriateStatusException;

	public Actioner stopActionInDatabase(Actioner actioner) throws ServiceException, NotAppropriateStatusException;

	public String getPIDFromPs(String line) throws IOException;

	public List<GenericActionner> getAllGenericActionners();

	public List<Actioner> getAllActiveActionners();

}