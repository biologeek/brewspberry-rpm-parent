package net.brewspberry.front.ws;

import java.util.List;
import java.util.zip.DataFormatException;

import net.brewspberry.business.exceptions.BusinessException;
import net.brewspberry.business.exceptions.ValidationException;
import net.brewspberry.front.ws.beans.requests.BrewRequest;
import net.brewspberry.front.ws.beans.responses.ComplexBrewResponse;
import net.brewspberry.front.ws.beans.responses.SimpleBrewResponse;

public interface IBrewRESTService {

	
	
	public  ComplexBrewResponse getCompleteBrew(long brewID) throws BusinessException, DataFormatException;
	public SimpleBrewResponse getSimpleBrew(long brewID) throws DataFormatException, BusinessException;
	public List<? extends SimpleBrewResponse> getAllBrews(String lightOrFull) throws Exception;	
	public <T extends SimpleBrewResponse> List<T> getAllActiveBrews(String lightOrFull);
	public <T extends SimpleBrewResponse>T addBrew(ComplexBrewResponse req) throws BusinessException, ValidationException;
	public <T extends SimpleBrewResponse> T updateBrew(ComplexBrewResponse req) throws BusinessException, ValidationException;
}
