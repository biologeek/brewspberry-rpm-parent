package net.brewspberry.brewery.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.brewspberry.brewery.api.BrewDto;
import net.brewspberry.brewery.exceptions.ElementNotFoundException;
import net.brewspberry.brewery.exceptions.ServiceException;
import net.brewspberry.brewery.exceptions.ValidationException;
import net.brewspberry.brewery.model.Brew;
import net.brewspberry.brewery.repositories.BrewRepository;

@Service
public class DefaultBrewService implements BrewService {

	@Autowired
	private BrewRepository repo;

	@Override
	public List<Brew> getAllBrews() {
		return this.repo.findAll();
	}

	@Override
	public Brew getCurrentBrew() {
		return this.repo.findCurrentBrew();
	}

	@Override
	public Brew createBrew(Brew brew) throws ServiceException, ElementNotFoundException, ValidationException {
		
		return null;
	}

}
