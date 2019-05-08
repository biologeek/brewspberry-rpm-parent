package net.brewspberry.brewery.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.brewspberry.brewery.exceptions.ElementNotFoundException;
import net.brewspberry.brewery.exceptions.ServiceException;
import net.brewspberry.brewery.exceptions.ValidationException;
import net.brewspberry.brewery.model.Brew;
import net.brewspberry.brewery.repositories.BrewRepository;
import net.brewspberry.brewery.service.AbstractIngredientService;
import net.brewspberry.brewery.service.BrewService;
import net.brewspberry.brewery.service.ProductionService;
import net.brewspberry.brewery.service.StepService;

@Service
public class DefaultBrewService implements BrewService {

	@Autowired
	private BrewRepository repo;
	@Autowired
	private StepService stepService;
	@Autowired
	private AbstractIngredientService ingredientService;
	@Autowired
	private ProductionService productionService;

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
		validateBrew(brew);
		brew.setCreationDate(LocalDateTime.now());
		brew.setUpdateDate(LocalDateTime.now());
		return this.repo.save(brew);
	}

	public Brew updateBrew(Brew brew) throws ServiceException, ElementNotFoundException, ValidationException {
		Brew persisted = repo.getOne(brew.getId());

		if (brew.equals(persisted)) {
			return brew;
		}
		validateBrew(brew);

		/*
		 * Updating subcomponents of Brew 
		 * 1. Steps,
		 * 2. Ingredients,
		 * 3. ProductionReport
		 */
		this.stepService.updateSteps(persisted.getSteps());
		this.ingredientService.updateAll(persisted.getMalts());
		this.ingredientService.updateAll(persisted.getAdditives());
		this.ingredientService.updateAll(persisted.getHops());
		this.ingredientService.updateAll(persisted.getSpices());
		this.ingredientService.updateAll(persisted.getYeasts());
		this.productionService.update(persisted.getProduction());
		persisted = mergeBrew(brew, persisted);

		return repo.save(persisted);
	}

	/**
	 * Merges elements of a brew
	 * 
	 * @param brew
	 * @param persisted
	 * @return
	 */
	private Brew mergeBrew(Brew brew, Brew persisted) {
		persisted.setTitle(brew.getTitle());
		persisted.setBeginning(brew.getBeginning());
		persisted.setEndDate(brew.getEndDate());
		persisted.setUpdateDate(brew.getUpdateDate());
		persisted.setHops(brew.getHops());
		persisted.setMalts(brew.getMalts());
		persisted.setAdditives(brew.getAdditives());
		persisted.setYeasts(brew.getYeasts());
		persisted.setSpices(brew.getSpices());
		persisted.setProduction(brew.getProduction());
		persisted.setSteps(brew.getSteps());

		return persisted;
	}

	/**
	 * Internal method used to validate {@link Brew} functional data coherence
	 * before saving
	 * 
	 * @param brew
	 * @throws ValidationException
	 *             In case a business rule is not respected
	 */
	private void validateBrew(Brew brew) throws ValidationException {
		if (brew.getMalts().isEmpty() && brew.getHops().isEmpty() && brew.getAdditives().isEmpty()
				&& brew.getYeasts().isEmpty() && brew.getSpices().isEmpty())
			throw new ValidationException("No ingredients !");
	}

}
