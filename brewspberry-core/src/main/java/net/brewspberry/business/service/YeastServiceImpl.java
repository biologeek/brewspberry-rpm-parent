package net.brewspberry.business.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.brewspberry.business.IGenericDao;
import net.brewspberry.business.IGenericService;
import net.brewspberry.business.ISpecificIngredientService;
import net.brewspberry.business.beans.Levure;
import net.brewspberry.business.beans.SimpleLevure;
import net.brewspberry.dao.SimpleYeastDAOImpl;
import net.brewspberry.dao.YeastDAOImpl;

@Service
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
	public Levure update(Levure arg0) {
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

	public class SimpleYeastServiceImpl implements IGenericService<SimpleLevure>,
			ISpecificIngredientService {

		IGenericDao<SimpleLevure> levureDao = new SimpleYeastDAOImpl();

		@Override
		public void deleteElement(long arg0) {
			levureDao.deleteElement(arg0);

		}

		@Override
		public void deleteElement(SimpleLevure arg0) {
			levureDao.deleteElement(arg0);

		}

		@Override
		public List<SimpleLevure> getAllDistinctElements() {
			return levureDao.getAllDistinctElements();
		}

		@Override
		public List<SimpleLevure> getAllElements() {
			return levureDao.getAllElements();
		}

		@Override
		public SimpleLevure getElementById(long arg0) {
			return levureDao.getElementById(arg0);
		}

		@Override
		public SimpleLevure save(SimpleLevure arg0) throws Exception {
			
			return levureDao.save(arg0);
		}

		@Override
		public SimpleLevure update(SimpleLevure arg0) {
			return levureDao.update(arg0);
		}

		@SuppressWarnings("unchecked")
		@Override
		public List<SimpleLevure> getIngredientFromArrayId(String[] array, String[] arrayQte, String[] arrayPrix) {
			List<SimpleLevure> result = new ArrayList<SimpleLevure>();

			if (array != null) {

				if (array.length != 0) {

					for (int i = 0; i < array.length; i++) {

						long currentID = 0;

						try {
							currentID = Long.parseLong(array[i]);
						} catch (Exception e) {
							e.printStackTrace();
						}

						SimpleLevure currentIngredient = levureDao
								.getElementById(currentID);
						currentIngredient.setIng_id((long) 0);

						if (!currentIngredient.equals(new SimpleLevure())) {
							result.add(currentIngredient);
						}
					}

				}

			}

			return result;
		}

	}
}
