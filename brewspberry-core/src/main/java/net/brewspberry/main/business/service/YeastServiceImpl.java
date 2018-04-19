package net.brewspberry.main.business.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.brewspberry.main.business.IGenericDao;
import net.brewspberry.main.business.IGenericService;
import net.brewspberry.main.business.ISpecificIngredientService;
import net.brewspberry.main.business.beans.brewing.Levure;
import net.brewspberry.main.business.beans.brewing.SimpleLevure;
import net.brewspberry.main.business.exceptions.DAOException;
import net.brewspberry.main.business.exceptions.ServiceException;

@Service (value="yeastServiceImpl")
@Transactional
public class YeastServiceImpl implements IGenericService<Levure>,
		ISpecificIngredientService {

	@Autowired
	IGenericDao<Levure> levureDao;
	@Autowired
	IGenericDao<SimpleLevure> slevureDao;

	
	
	@Override
	public void deleteElement(long arg0) {

		levureDao.deleteElement(arg0);
	}

	@Override
	public void deleteElement(Levure arg0) {

		levureDao.deleteElement(arg0);
	}

	@Override
	public List<Levure> getAllDistinctElements() {
		return levureDao.getAllDistinctElements();
	}

	@Override
	public List<Levure> getAllElements() {
		return levureDao.getAllElements();
	}

	@Override
	public Levure getElementById(long arg0) {
		return levureDao.getElementById(arg0);
	}

	@Override
	public Levure save(Levure arg0) throws Exception {
		return levureDao.save(arg0);

	}

	@Override
	public Levure update(Levure arg0) throws DAOException {
		return levureDao.update(arg0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Levure> getIngredientFromArrayId(String[] array, String[] arrayQte, String[] arrayPrix) {
		List<Levure> result = new ArrayList<Levure>();

		if (array != null) {

			if (array.length != 0) {

				for (int i = 0; i < array.length; i++) {

					long currentID = 0;

					try {
						currentID = Long.parseLong(array[i]);
					} catch (Exception e) {
						e.printStackTrace();
					}

					SimpleLevure currentSimpleIngredient = slevureDao
							.getElementById(currentID);
					
					Levure currentIngredient = new Levure();

					currentIngredient.setIng_desc(currentSimpleIngredient.getIng_desc());
					currentIngredient.setIng_disc(currentSimpleIngredient.getIng_disc());
					currentIngredient.setIng_fournisseur(currentSimpleIngredient.getIng_fournisseur());
					currentIngredient.setSlev_aromes(currentSimpleIngredient.getSlev_aromes());
					currentIngredient.setSlev_floculation(currentSimpleIngredient.getSlev_floculation());
					currentIngredient.setSlev_espece(currentSimpleIngredient.getSlev_espece());
					
					
					if (!currentIngredient.equals(new Levure())) {
						result.add(currentIngredient);
					}
				}

			}

		}

		return result;
	}

	@Override
	public Levure getElementByName(String name) throws ServiceException {
		
		return null;
	}

}
