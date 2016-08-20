package net.brewspberry.business;

import net.brewspberry.business.beans.Etape;
import net.brewspberry.business.exceptions.BusinessException;

public interface ISpecificEtapeService {

	
	public Etape terminateStep (Etape etape);
	
	public Etape startStepForReal(Etape step) throws BusinessException;
	public Etape stopStepForReal(Etape step);
}
