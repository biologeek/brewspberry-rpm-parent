package net.brewspberry.brewery.service;

import java.util.List;

import org.springframework.stereotype.Service;

import net.brewspberry.brewery.exceptions.ElementNotFoundException;
import net.brewspberry.brewery.exceptions.ServiceException;
import net.brewspberry.brewery.exceptions.ValidationException;
import net.brewspberry.brewery.model.Brew;

@Service
public interface BrewService {

	List<Brew> getAllBrews();

	Brew getCurrentBrew();

	Brew createBrew(Brew brew) throws ServiceException, ElementNotFoundException, ValidationException;

}
