package net.brewspberry.brewery.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.brewspberry.brewery.exceptions.ServiceException;
import net.brewspberry.brewery.exceptions.ValidationException;
import net.brewspberry.brewery.model.AbstractIngredient;
import net.brewspberry.brewery.repositories.AbstractIngredientRepository;
import net.brewspberry.brewery.service.AbstractIngredientService;
import net.brewspberry.brewery.service.IngredientBusinessService;
import net.brewspberry.brewery.service.IngredientBusinessServiceFactory;

@Service
public class DefaultAbstractIngredientSerivce implements AbstractIngredientService {

	@Autowired
	private IngredientBusinessServiceFactory ibsFactory;
	@Autowired
	private AbstractIngredientRepository abstractIngRepo;

	@SuppressWarnings("unchecked")
	@Override
	public <T extends AbstractIngredient> T save(T toSave) throws ValidationException, ServiceException {
		IngredientBusinessService<? extends AbstractIngredient> service = ibsFactory
				.getBusinessService(toSave.getClass());

		service.validate(toSave);
		toSave = (T) service.preSave(toSave);

		toSave = this.abstractIngRepo.save(toSave);
		return (T) service.postSave(toSave);
	}

	@Override
	public <T extends AbstractIngredient> T getIngredient(Class<T> clazz, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends AbstractIngredient> List<T> updateAll(List<T> toSave) throws ServiceException, ValidationException {
		List<T> res = new ArrayList<>();
		if (toSave == null || toSave.isEmpty())
			return new ArrayList<>();

		for (T elt : toSave) {
			res.add(this.update(elt));
		}
		return res;
	}

	@SuppressWarnings("unchecked")
	private <T extends AbstractIngredient> T update(T elt) throws ServiceException, ValidationException {
		IngredientBusinessService<? extends AbstractIngredient> service = ibsFactory.getBusinessService(elt.getClass());
		if (elt.getId() == null)
			return this.save(elt);
		service.validate(elt);
		AbstractIngredient savedOne = this.abstractIngRepo.getOne(elt.getId());

		savedOne = (AbstractIngredient) Hibernate.unproxy(this.mergeAbstractIngredient(elt, savedOne));
		
		savedOne = service.merge(elt, savedOne);

		savedOne = (T) service.preSave(savedOne);

		savedOne = this.abstractIngRepo.save(savedOne);
		return (T) service.postSave(savedOne);
	}

	private AbstractIngredient mergeAbstractIngredient(AbstractIngredient elt, AbstractIngredient savedOne) {
		savedOne.setBrand(elt.getBrand());
		savedOne.setModel(elt.getModel());
		savedOne.setPackaging(elt.getPackaging());
		return savedOne;
	}

	@Override
	public <T extends AbstractIngredient> List<T> getIngredientsByType(String type) throws ValidationException {
		if (type == null)
			throw new ValidationException("no.type");
		
		return null;
	}

}
