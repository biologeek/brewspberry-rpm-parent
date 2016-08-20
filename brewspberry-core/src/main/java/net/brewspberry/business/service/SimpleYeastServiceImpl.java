package net.brewspberry.business.service;

import java.util.ArrayList;
import java.util.List;

import net.brewspberry.business.IGenericDao;
import net.brewspberry.business.IGenericService;
import net.brewspberry.business.ISpecificIngredientService;
import net.brewspberry.business.beans.SimpleLevure;
import net.brewspberry.business.exceptions.ServiceException;
import net.brewspberry.dao.SimpleYeastDAOImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service("simpleYeastServiceImpl")
@Transactional
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
					currentIngredient.setStb_id((long) 0);

					if (!currentIngredient.equals(new SimpleLevure())) {
						result.add(currentIngredient);
					}
				}

			}

		}

		return result;
	}

	@Override
	public SimpleLevure getElementByName(String name) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

}
