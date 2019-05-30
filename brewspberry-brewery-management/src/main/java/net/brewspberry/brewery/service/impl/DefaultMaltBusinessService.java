package net.brewspberry.brewery.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.brewspberry.brewery.exceptions.ServiceException;
import net.brewspberry.brewery.exceptions.ValidationException;
import net.brewspberry.brewery.model.AbstractIngredient;
import net.brewspberry.brewery.model.Malt;
import net.brewspberry.brewery.repositories.MaltRepository;
import net.brewspberry.brewery.service.IngredientBusinessService;

@Service
public class DefaultMaltBusinessService implements IngredientBusinessService<Malt> {

	@Autowired
	private MaltRepository maltRepo;

	@Override
	public Malt preSave(AbstractIngredient bean) throws ValidationException, ServiceException {
		// TODO Auto-generated method stub
		return (Malt) bean;
	}

	@Override
	public Malt postSave(AbstractIngredient bean) throws ServiceException {
		// TODO Auto-generated method stub
		return (Malt) bean;
	}

	@Override
	public void validate(AbstractIngredient toSave) throws ValidationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Malt merge(AbstractIngredient elt, AbstractIngredient savedOne) {

		Malt eltH = (Malt) elt;
		Malt savedOneH = (Malt) savedOne;
		savedOneH.setEbcColor(eltH.getEbcColor());
		savedOneH.setForm(eltH.getForm());
		return (Malt) savedOne;
	}

	@Override
	public List<Malt> getAll() {
		return this.maltRepo.findAll();
	}

}
