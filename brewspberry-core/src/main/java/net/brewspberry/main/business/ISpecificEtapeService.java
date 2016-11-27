package net.brewspberry.main.business;

import net.brewspberry.main.business.beans.Etape;
import net.brewspberry.main.business.exceptions.BusinessException;

public interface ISpecificEtapeService {

	
	public Etape terminateStep (Etape etape);
	
	public Etape startStepForReal(Etape step) throws BusinessException;
	public Etape stopStepForReal(Etape step);
}
